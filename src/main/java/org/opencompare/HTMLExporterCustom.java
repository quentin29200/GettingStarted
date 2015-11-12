package org.opencompare;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.HTMLExporter;
import org.opencompare.api.java.io.PCMLoader;
import org.opencompare.api.java.value.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class HTMLExporterCustom {
    private Document doc;
    private Element body;
    private PCMMetadata metadata;
    private Element tr;
    private  Param parameter;

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

        // Get Features
        this.featureDepth = pcm.getFeaturesDepth();

        // List of features
        LinkedList featuresToVisit = new LinkedList();
        this.nextFeaturesToVisit = new LinkedList();
        featuresToVisit.addAll(pcm.getFeatures());

        this.tr = table.appendElement("tr");
        this.tr.appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product");

        Iterator var5;
     /* while(!featuresToVisit.isEmpty()) {
            Collections.sort(featuresToVisit, new Comparator() {
                public int compare(AbstractFeature feat1, AbstractFeature feat2) {
                    return HTMLExporter.this.metadata.getFeaturePosition(feat1) - HTMLExporter.this.metadata.getFeaturePosition(feat2);
                }
            });
            var5 = featuresToVisit.iterator();

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

        var5 = pcm.getProducts().iterator();

       /*while(var5.hasNext()) {
            Product var7 = (Product)var5.next();
            this.tr = table.appendElement("tr");
            var7.accept(this);
        }*/

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
        File pcmFile = new File("pcms/example.pcm");
        File paramFile = new File("json/param1.json");

        // read the json file
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();

        //displays the HTML File into the console
        HTMLExporter testHtmlExporter = new HTMLExporter();
        HTMLExporterCustom testHTML = new HTMLExporterCustom("params1.json");
        //System.out.println("HTML généré : "+ testHtmlExporter.toHTML(pcm));
        System.out.println("Order type récupéré : " + testHTML.getParameters().getOrderType());
        
        //Il faut parcourir les dataStyle (il possède un attibut "name")
        System.out.println("DataStyle récupéré : " + testHTML.getParameters().getDataStyleParam());





    }//fin main



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

    public void visit(Feature feature) {

    }

    public void visit(FeatureGroup featureGroup) {

    }

    public void visit(Product product) {

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
