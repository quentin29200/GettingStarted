/*

 */
package org.opencompare;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.opencompare.api.java.AbstractFeature;
import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.FeatureGroup;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.PCMMetadata;
import org.opencompare.api.java.Product;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.HTMLExporter;

import org.opencompare.api.java.io.PCMLoader;
import org.opencompare.api.java.value.*;

import java.io.*;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class HTMLExporterCustom extends HTMLExporter {
    /**
     * list of attributes
     */
    private Document doc;
    private Element body;
    private PCMMetadata metadata;
    private Element tr;
    Document.OutputSettings settings = new Document.OutputSettings();
    private String templateFull = "<html>\n\t<head>\n\t\t<meta charset=\"utf-8\"/>\n\t\t<title></title>\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\" />\n\t</head>\n\t<body>\n\t</body>\n</html>";
    private LinkedList<AbstractFeature> nextFeaturesToVisit;
    private int featureDepth;
    private Param parameters;

    /**
     * @return parameters
     */
    public Param getParameters() {
        return parameters;
    }

    /**
     * @param parameters
     */
    public void setParameters(Param parameters) {
        this.parameters = parameters;
    }

    //constructeur de la classe

    /**
     * @param fileName
     */
    public HTMLExporterCustom(String fileName) {
        Param param = new Param(fileName);
        setParameters(param);
    }

    /**
     * @param container
     * @return
     */
    public String export(PCMContainer container) {
        return this.toHTML(container);
    }

    /**
     * @param pcm
     * @return
     */
    public String toHTML(PCM pcm) {
        this.settings.prettyPrint();
        this.doc = Jsoup.parse(this.templateFull);
        this.body = this.doc.body();
        this.doc.head().select("title").first().text(pcm.getName());
        if (this.metadata == null) {
            this.metadata = new PCMMetadata(pcm);
        }

        pcm.accept(this);
        return this.doc.outputSettings(this.settings).outerHtml();
    }

    /**
     * @param container
     * @return contains
     */
    public String toHTML(PCMContainer container) {
        this.metadata = container.getMetadata();
        return this.toHTML(container.getPcm());
    }

    /**
     * @param pcm
     */
    public void visit(PCM pcm) {
        Element title = this.body.appendElement("h1");
        title.attr("id", "title").text(pcm.getName());
        Element table = this.body.appendElement("table");
        table.attr("id", "matrix_" + pcm.getName().hashCode()).attr("border", "1");
        this.featureDepth = pcm.getFeaturesDepth();
        LinkedList featuresToVisit = new LinkedList();
        this.nextFeaturesToVisit = new LinkedList();
        featuresToVisit.addAll(pcm.getFeatures());
        /*this.tr = table.appendElement("tr");
        this.tr.appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product");*/
        Iterator var5;
        this.featuresRow(pcm, table);

        var5 = pcm.getProducts().iterator();
        Iterator<DataStyle> itParam = parameters.getDataStyleParam().iterator();
        String name = null;

        while (var5.hasNext() || itParam.hasNext()) {
            /*Product var7 = (Product)var5.next();
            this.tr = table.appendElement("tr");
            var7.accept(this);*/
            Product var7 = (Product) var5.next();
            List cells = var7.getCells();
            Collections.sort(cells, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Cell cell1 = (Cell) o1;
                    Cell cell2 = (Cell) o2;
                    return HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell1.getFeature()) - HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell2.getFeature());

                }
            });
            Iterator var3 = cells.iterator();
            DataStyle ds = null;
            if (itParam.hasNext()) {
                ds = itParam.next();

            }
            this.tr = table.appendElement("tr");
            this.tr.appendElement("th").text(var7.getName());
            //Iterator<Cell> var8 = var7.getCells().iterator();
            while (var3.hasNext()) {
                Cell cell = (Cell) var3.next();
                cell.getContent();
                //System.out.println(cell);
                // this.tr = table.appendElement("tr");
                //** By Chloé hu
                if (itParam.hasNext()) {
                    name = ds.getName();
                    if (name.contains("rangein")) { // A modifier, en haut mettre des booléens si rangein ou rangeout dans fichier Param
                        rangeIn(ds.getBorneinf(), ds.getBornesup(), ds.getValue());
                    } else if (name.contains("rangeout")) {
                        rangeOut(ds.getBorneinf(), ds.getBornesup(), ds.getValue());
                    }
                }

                Element td = this.tr.appendElement("td").addClass(name);
                td.appendElement("span").text(cell.getContent());
                // table.appendElement("td").addClass(name);


            }
        }

        if (this.parameters.isShowBottomNameFeatures() && !this.parameters.isReversePCM()) {
            this.featuresRow(pcm, table);
        }

    }

    /**
     * @param feature
     */
    public void visit(Feature feature) {
        Element th = this.tr.appendElement("th");
        if (this.featureDepth > 1) {
            th.attr("rowspan", Integer.toString(this.featureDepth));
        }

        th.text(feature.getName());
    }

    /**
     * @param featureGroup
     */
    public void visit(FeatureGroup featureGroup) {
        Element th = this.tr.appendElement("th");
        if (!featureGroup.getFeatures().isEmpty()) {
            th.attr("colspan", Integer.toString(featureGroup.getFeatures().size()));
        }

        th.text(featureGroup.getName());
        this.nextFeaturesToVisit.addAll(featureGroup.getFeatures());
    }

    /**
     * @param product
     * @param ds
     */
    public void visit(Product product, DataStyle ds) {
        this.tr.appendElement("th").text(product.getName());
        List cells = product.getCells();
        Collections.sort(cells, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Cell cell1 = (Cell) o1;
                Cell cell2 = (Cell) o2;
                return HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell1.getFeature()) - HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell2.getFeature());

            }

           /* public int compare(Cell cell1, Cell cell2) {
                return HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell1.getFeature()) - HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell2.getFeature());
            }*/
        });
        Iterator var3 = cells.iterator();

        while (var3.hasNext()) {
            Cell cell = (Cell) var3.next();
            Element td = this.tr.appendElement("td");
            td.appendElement("span").text(cell.getContent());
        }

    }

    /**
     * @param product
     */
    public void visit(Product product) {
        this.tr.appendElement("th").text(product.getName());
        List cells = product.getCells();
        Collections.sort(cells, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Cell cell1 = (Cell) o1;
                Cell cell2 = (Cell) o2;
                return HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell1.getFeature()) - HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell2.getFeature());
            }
            /*public int compare(Cell cell1, Cell cell2) {
                return HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell1.getFeature()) - HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell2.getFeature());
            }*/
        });
        Iterator var3 = cells.iterator();

        while (var3.hasNext()) {
            Cell cell = (Cell) var3.next();
            Element td = this.tr.appendElement("td");
            td.appendElement("span").text(cell.getContent());
        }

    }

    /**
     * @param pcm
     * @param table
     */
    public void featuresRow(PCM pcm, Element table) {
        // List of features
        LinkedList featuresToVisit = new LinkedList();
        this.nextFeaturesToVisit = new LinkedList();
        featuresToVisit.addAll(pcm.getFeatures());

        this.tr = table.appendElement("tr");
        this.tr.appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product");
        Iterator var5;
        while (!featuresToVisit.isEmpty()) {
            Collections.sort(featuresToVisit, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    AbstractFeature feat1 = (AbstractFeature) o1;
                    AbstractFeature feat2 = (AbstractFeature) o2;
                    return HTMLExporterCustom.this.metadata.getFeaturePosition(feat1) - HTMLExporterCustom.this.metadata.getFeaturePosition(feat2);
                }
                /*public int compare(AbstractFeature feat1, AbstractFeature feat2) {
                    return HTMLExporterCustom.this.metadata.getFeaturePosition(feat1) - HTMLExporterCustom.this.metadata.getFeaturePosition(feat2);
                }*/
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

            while (var5.hasNext()) {
                AbstractFeature product = (AbstractFeature) var5.next();
                product.accept(this);
            }


            featuresToVisit = this.nextFeaturesToVisit;
            this.nextFeaturesToVisit = new LinkedList();
            --this.featureDepth;
            if (this.featureDepth >= 1) {
                this.tr = table.appendElement("tr");
            }
        }
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // Load a PCM
        File pcmFile = new File("pcms/PCM1/tesssvtttt369852147.pcm");
        //File paramFile = new File("pcms/PCM1/param1.json");

        // read the json file
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();

        HTMLExporterCustom te = new HTMLExporterCustom("PCM1/params1.json");


        //Generate the HTML file
        generateHTMLFile(te, pcm);
        //Generate the archive file which contains the CSS & HTML files
        generateZIP();

    }//fin main


    /**
     * This function have 2 parameters, a PCM and the new data we want to use to generate the HTML file
     * and the pcm which contains the products matrix
     *
     * @param dataResults
     * @param pcm
     */
    public static void generateHTMLFile(HTMLExporterCustom dataResults, PCM pcm) {
        try {
            //create a new HTML file
            File HTMLGeneratedFile = new File("src\\HTMLGenerated.html");
            //Write inside the HTML file
            FileWriter fileWriter = new FileWriter(HTMLGeneratedFile);
            fileWriter.write(dataResults.toHTML(pcm));
            //Flucing and closing streams
            fileWriter.flush();
            fileWriter.close();

            //Checking wether the file is created
            if (HTMLGeneratedFile == null) {
                System.out.println("le fichier généré est vide");
            } else {
                System.out.println("Bravo le fichier HTML a bien été généré !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Getting both files (CSS&HTML) size in order for the ZIP file to have the same size as the addition
     * of both files sizes
     * Return the size of a file
     *
     * @param filename
     * @return
     */
    public static int getFileSize(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File doesn\'t exist");
            return -1;
        }
        return (int) file.length();
    }

    /**
     * @param filePath
     * @param zos
     * @param filesSize
     * @throws FileNotFoundException
     * @throws IOException           Adding file to the created ZIP file, it needs a output directory filePath, the zos parameter is the previous output ZIP file,
     *                               and the fileSize is the size of the of the ZIP file we want to create
     */
    private static void addToZipFile(String filePath, ZipOutputStream zos, int filesSize) throws FileNotFoundException, IOException {
        System.out.println("Writing '" + filePath + "' to zip file");

        //Getting the concerned files
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);

        //creating the zip file with the filePath
        ZipEntry zipEntry = new ZipEntry(filePath);

        //implements an output stream filter for writing files in the ZIP file format
        zos.putNextEntry(zipEntry);
        //defines the length of the ZIP file
        byte[] bytes = new byte[filesSize];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        //closing the streams
        zos.closeEntry();
        fis.close();
    }


    /**
     * This function generate the archive file composed by HTMLGenerated.html and styles.css
     * The styles.css has previously been generated by the CSSExporter class
     */
    public static void generateZIP() {
        try {
            //Output file with a different name each time it's generated
            FileOutputStream fos = new FileOutputStream("src\\" + System.currentTimeMillis() / 1000 + "TestArchivePDL.zip");
            //Creating the output file
            ZipOutputStream zos = new ZipOutputStream(fos);

            //getting files to zip them
            String fileGeneratedHTMLPath = "src\\HTMLGenerated.html";
            String fileGeneratedCSSPath = "src\\style.css";
            //getting files size
            int CSSSize = getFileSize(fileGeneratedCSSPath);
            int HTMLSize = getFileSize(fileGeneratedHTMLPath);
            //System.out.println("Taille CSS : " + CSSSize + "octets, taille HTML : "+HTMLSize + "octets. ");
            int totalFilesSize = CSSSize + HTMLSize;

            //Adding files to archive ZIP
            addToZipFile(fileGeneratedHTMLPath, zos, totalFilesSize);
            addToZipFile(fileGeneratedCSSPath, zos, totalFilesSize);

            //closing the streams
            zos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param borneinf
     * @param bornesup
     * @param valuePCM
     * @return This function defines the range of values the user wants to highlight
     * the hilighted values are between parameters : borneinf ans bornsup
     */
    private boolean rangeIn(int borneinf, int bornesup, int valuePCM) {
        return ((valuePCM >= borneinf) && (valuePCM <= bornesup));
    }

    /**
     * @param borneinf
     * @param bornesup
     * @param valuePCM
     * @return This function defines the range of values the user wants to highlight
     * the highlighted values are not between parameters : borneinf ans bornsup
     */
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
