package org.opencompare;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.HTMLExporter;
import org.opencompare.api.java.io.PCMLoader;
import org.opencompare.api.java.value.*;

import java.io.*;
import java.util.Comparator;

import java.util.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class HTMLExporterCustom extends HTMLExporter {
    private Document doc;
    private Element body;
    private PCMMetadata metadata;
    private Element tr;
    private Param parameter;

    //modif par cheisda le 9.11.2015
    public String generatedHtml;


    Document.OutputSettings settings = new Document.OutputSettings();
    private String templateFull = "<html>\n\t<head>\n\t\t<meta charset=\"utf-8\"/>\n\t\t<title></title>\n\t</head>\n\t<body>\n\t</body>\n</html>";
    private LinkedList<AbstractFeature> nextFeaturesToVisit;
    private int featureDepth;

    public String toHTML(PCM pcm) {
        this.settings.prettyPrint();
        this.doc = Jsoup.parse(this.templateFull);
        this.body = this.doc.body();
        this.doc.head().select("title").first().text(pcm.getName());
        if(this.metadata == null) {
            this.metadata = new PCMMetadata(pcm);
        }

        pcm.accept(this);
        return this.doc.outputSettings(this.settings).outerHtml();
    }

    public String generateHTML(PCMContainer container) {
        this.metadata = container.getMetadata();
        //return this.toHTML(container.getPcm());

        //modif by cheisda 9.11.2015
        generatedHtml = this.toHTML(container.getPcm());
        return generatedHtml;

    }



    public void visit(PCM pcm) {
        // Init html table
        this.body.appendElement("h1").text(pcm.getName());
        Element title = this.body.appendElement("h1");
        title.attr("id", "title").text(pcm.getName());
        Element table = this.body.appendElement("table");
        table.attr("id", "matrix_" + pcm.getName().hashCode()).attr("border", "1");
        //Boolean rangein, rangeout = false;

        // Get Features
        this.featureDepth = pcm.getFeaturesDepth();
        LinkedList featuresToVisit = new LinkedList();
        this.nextFeaturesToVisit = new LinkedList();
        featuresToVisit.addAll(pcm.getFeatures());

        /*this.tr = table.appendElement("tr");
        this.tr.appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product");*/

        Iterator var5;
        this.featuresRow(pcm, table);

        // Traitement sur les produits récupérés par pcm.getProduct()
        var5 = pcm.getProducts().iterator();
        Iterator<DataStyle> itParam = parameters.getDataStyleParam().iterator();
        String name;
        //gez
        while(var5.hasNext() && itParam.hasNext()) {
            Product var7 = (Product) var5.next();
            DataStyle ds;
            ds = itParam.next();
            this.tr = table.appendElement("tr");
            this.tr.appendElement("th").text(var7.getName());
            Iterator<Cell> var8 = var7.getCells().iterator();
            while(var8.hasNext()) {
                Cell cell = var8.next();
                cell.getContent();
               // this.tr = table.appendElement("tr");
                //** By Chloé hu

                name = ds.getName();
                if (name.contains("rangein")) { // A modifier, en haut mettre des booléens si rangein ou rangeout dans fichier Param
                    rangeIn(ds.getBorneinf(), ds.getBornesup(), ds.getValue());
                } else if (name.contains("rangeout")) {
                    rangeOut(ds.getBorneinf(), ds.getBornesup(), ds.getValue());
                }
                // table.appendElement("td").addClass(name);

                Element td = this.tr.appendElement("td").addClass(name);
                td.appendElement("span").text(cell.getContent());
            }
        }

        //By Romain
        if(this.parameters.isShowBottomNameFeatures() && !this.parameters.isReversePCM()){
            this.featuresRow(pcm, table);
        }

    }

    public void featuresRow(PCM pcm, Element table){
        // List of features
        LinkedList featuresToVisit = new LinkedList();
        this.nextFeaturesToVisit = new LinkedList();
        featuresToVisit.addAll(pcm.getFeatures());

        this.tr = table.appendElement("tr");
        this.tr.appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product");
        Iterator var5;
        while(!featuresToVisit.isEmpty()) {
            Collections.sort(featuresToVisit, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    return 0;
                }

                public int compare(AbstractFeature feat1, AbstractFeature feat2) {
                    return HTMLExporterCustom.this.metadata.getFeaturePosition(feat1) - HTMLExporterCustom.this.metadata.getFeaturePosition(feat2);
                }
            });

            var5 = featuresToVisit.iterator();
           /* Iterator<DataStyle> itParam = parameter.getDataStyleParam().iterator();
            String name;

            while (var5.hasNext() && itParam.hasNext()) {
                AbstractFeature product = (AbstractFeature) var5.next();
                //** Affichage des entête et des produits
                //** By Chloé on 18/11
                DataStyle ds = (DataStyle) itParam.next();
                product.accept(this);
                ds.accept(this);
                name = ds.getName();
                table.appendElement("th").addClass(name); //! Attention à fonction append() ou appendElement()
                // Demander si on doit mettre la classe dans un span plutôt que dans th
            }*/

            while(var5.hasNext()) {
                AbstractFeature product = (AbstractFeature)var5.next();
                product.accept(this);
            }


            featuresToVisit = this.nextFeaturesToVisit;
            this.nextFeaturesToVisit = new LinkedList();
            --this.featureDepth;
            if(this.featureDepth >= 1) {
                this.tr = table.appendElement("tr");
            }
        }
    }

    public void visit(Feature feature) {
        Element th = this.tr.appendElement("th");
        if(this.featureDepth > 1) {
            th.attr("rowspan", Integer.toString(this.featureDepth));
        }

        th.text(feature.getName());
    }

    public void visit(FeatureGroup featureGroup) {
        Element th = this.tr.appendElement("th");
        if(!featureGroup.getFeatures().isEmpty()) {
            th.attr("colspan", Integer.toString(featureGroup.getFeatures().size()));
        }

        th.text(featureGroup.getName());
        this.nextFeaturesToVisit.addAll(featureGroup.getFeatures());
    }

    public void visit(Product product, DataStyle ds) {
        this.tr.appendElement("th").text(product.getName());
        List cells = product.getCells();
        Collections.sort(cells, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }

            public int compare(Cell cell1, Cell cell2) {
                return HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell1.getFeature()) - HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell2.getFeature());
            }
        });
        Iterator var3 = cells.iterator();

        while(var3.hasNext()) {
            Cell cell = (Cell)var3.next();
            Element td = this.tr.appendElement("td");
            td.appendElement("span").text(cell.getContent());
        }

    }

    public void visit(Product product) {
        this.tr.appendElement("th").text(product.getName());
        List cells = product.getCells();
        Collections.sort(cells, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }

            public int compare(Cell cell1, Cell cell2) {
                return HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell1.getFeature()) - HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell2.getFeature());
            }
        });
        Iterator var3 = cells.iterator();

        while(var3.hasNext()) {
            Cell cell = (Cell)var3.next();
            Element td = this.tr.appendElement("td");
            td.appendElement("span").text(cell.getContent());
        }

    }

    //11.11.2015
    private Param parameters;

    public Param getParameters() {
        return parameters;
    }

    public void setParameters(Param parameters) {
        this.parameters = parameters;
    }

    //constructeur de la classe
    public HTMLExporterCustom(String fileName) {
        Param param = new Param(fileName);
        setParameters(param);
    }



    /* Main */
    public static void main(String[] args) throws IOException {
/*

 */
        // Load a PCM
        File pcmFile = new File("pcms/PCM1/tesssvtttt369852147.pcm");
        //File paramFile = new File("pcms/PCM1/param1.json");

        // read the json file
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();

        HTMLExporterCustom te = new HTMLExporterCustom("PCM1/params1.json");
        //System.out.println(te.toHTML(pcm));


        //modif by cheisda 24.11.2015


        generateHTMLFile(te);



    }//fin main


    //Modif by Cheisda

    public static void generateHTMLFile(HTMLExporterCustom test) {

        File HTMLGeneratedFile = new File("src\\HTMLGenerated.html");


        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src\\HTMLGenerated.html"), "utf-8"));
            writer.write(String.valueOf(test));
        } catch (IOException ex) {
            // report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }

    }


    public String export(PCMContainer container) {
        return null;
    }

    public String toHTML(PCMContainer container) {
        return null;
    }


    private boolean rangeIn(int borneinf, int bornesup, int valuePCM) {
        return ((valuePCM >= borneinf) && (valuePCM <= bornesup));
    }

    private boolean rangeOut(int borneinf, int bornesup, int valuePCM) {
        return ((valuePCM <= borneinf) || (valuePCM >= bornesup));
    }

    public void visit(Cell cell) {

    }

    // @Override
    public void visit(BooleanValue booleanValue) {

    }

    public void visit(Conditional conditional) {

    }

    public void visit(DateValue dateValue) {

    }

    public void visit(Dimension dimension) {

    }

    public void visit(IntegerValue integerValue) {

    }

    public void visit(Multiple multiple) {

    }

    public void visit(NotApplicable notApplicable) {

    }

    public void visit(NotAvailable notAvailable) {

    }

    public void visit(Partial partial) {

    }

    public void visit(RealValue realValue) {

    }

    public void visit(StringValue stringValue) {

    }

    public void visit(Unit unit) {

    }

    public void visit(Version version) {

    }
}
