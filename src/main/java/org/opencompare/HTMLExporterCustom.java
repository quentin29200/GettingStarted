/*

 */
package org.opencompare;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ComparisonChain;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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


import java.util.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;
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
    private String templateFull = "<html>\n\t" +
            "<head>\n\t\t<meta charset=\"utf-8\"/>\n\t\t<title></title>\n\t" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"bootstrap/css/bootstrap.min.css\" media=\"screen\" />\n\t" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\" />\n\t" +
            "<script src=\"bootstrap/js/bootstrap.min.js\" />\n\t" +
            "</head>\n\t<body>\n\t</body>\n</html>";
    private LinkedList<AbstractFeature> nextFeaturesToVisit;
    private int featureDepth;
    private Param parameters;

    /**
     *
     * @return parameters
     */
    public Param getParameters() {
        return parameters;
    }

    /**
     *
     * @param parameters
     */
    public void setParameters(Param parameters) {
        this.parameters = parameters;
    }

    //constructeur de la classe

    /**
     *
     * @param fileName
     */
    public HTMLExporterCustom(String fileName) {
        Param param = new Param(fileName);
        this.setParameters(param);
    }

    /**
     *
     * @param container
     * @return
     */
    public String export(PCMContainer container) {
        return this.toHTML(container);
    }

    /**
     *
     * @param pcm
     * @return
     */
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

    /**
     *
     * @param container
     * @return contains
     *
     */
    public String toHTML(PCMContainer container) {
        this.metadata = container.getMetadata();
        return this.toHTML(container.getPcm());
    }

    /**
     *
     * @param pcm
     */
    public void visit(PCM pcm) {
        if(this.getParameters().isShowPCMname()){
            Element title = this.body.appendElement("h1");
            title.attr("id", "title").text(pcm.getName());
        }
        Element table = this.body.appendElement("table");
        //table.attr("id", "matrix_" + pcm.getName().hashCode()).attr("border", "1");
        table.attr("id", "matrix_" + pcm.getName().hashCode()).addClass("table-bordered").addClass("table-hover");
        table.appendElement("tbody");
        this.featureDepth = pcm.getFeaturesDepth();
        LinkedList featuresToVisit = new LinkedList();
        this.nextFeaturesToVisit = new LinkedList();
        featuresToVisit.addAll(pcm.getFeatures());
        Iterator var5;
        this.featuresRow(pcm, table, false);
        var5 = pcm.getProducts().iterator();
        Iterator<DataStyle> itParam = parameters.getDataStyleParam().iterator();
        String name = null;

        if(this.getParameters().isReversePCM()){
            if(!this.getParameters().getOrderColumn().equals("") && !this.getParameters().getOrderType().equals("")){
                Iterator itOrder = pcm.getProducts().iterator();
                if(this.getParameters().getOrderColumn().equals("Product")) {
                    List<Product> tmpProductList = this.productSort(itOrder);
                    Iterator fin = tmpProductList.iterator();
                    this.affectRow(fin, table);
                }else{
                    List<ProductOrder> tmpList = this.cellSort(itOrder);
                    Iterator fin = tmpList.iterator();
                    while(fin.hasNext()){
                        ProductOrder po = (ProductOrder) fin.next();
                        table.getElementById("first-row").getElementsByTag("tr").first().appendElement("th").text(po.getName());
                        Iterator var3 = po.getCells().iterator();
                        this.cellAffectClass(var3);
                    }
                }


            }else{
                this.affectRow(var5, table);
            }

        }else{
            if(!this.getParameters().getOrderColumn().equals("") && !this.getParameters().getOrderType().equals("")){
                Iterator itOrder = pcm.getProducts().iterator();
                if(this.getParameters().getOrderColumn().equals("Product")) {
                    List<Product> tmpProductList = this.productSort(itOrder);
                    Iterator fin = tmpProductList.iterator();
                    this.affectRow(fin, table);
                }else{
                    List<ProductOrder> tmpList = this.cellSort(itOrder);
                    Iterator fin = tmpList.iterator();

                    while(fin.hasNext()){
                        ProductOrder po = (ProductOrder) fin.next();
                        this.tr = table.getElementsByTag("tbody").first().appendElement("tr");
                        boolean b = true;
                        Iterator<DataStyle> itDs = this.getParameters().getDataStyleParam().iterator();
                        while(itDs.hasNext()) {
                            DataStyle ds = itDs.next();
                            if(ds.getName().equals("Productstyle")){
                                this.tr.appendElement("td").addClass("Productstyle").appendElement("span").text(po.getName());
                                b = false;
                                break;
                            }
                        }
                        if(b){
                            this.tr.appendElement("td").appendElement("span").text(po.getName());
                        }
                        Iterator var3 = po.getCells().iterator();
                        this.cellAffectClass(var3);
                    }
                }


            }else{
                this.affectRow(var5, table);
            }

        }

        if(this.parameters.isShowBottomNameFeatures() && !this.parameters.isReversePCM()){
            this.featuresRow(pcm, table, true);
        }
    }

    public void affectRow(Iterator it, Element table){
        boolean isReverse = this.getParameters().isReversePCM();
        while(it.hasNext()) {
            Product var7 = (Product)it.next();
            List cells = var7.getCells();
            Collections.sort(cells, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Cell cell1 = (Cell)o1;
                    Cell cell2 = (Cell)o2;
                    return HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell1.getFeature()) - HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell2.getFeature());

                }
            });
            if(isReverse){
                table.getElementById("first-row").getElementsByTag("tr").first().appendElement("th").text(var7.getName());
            }else{
                boolean b = true;
                this.tr = table.getElementsByTag("tbody").first().appendElement("tr");
                Iterator<DataStyle> itDs = this.getParameters().getDataStyleParam().iterator();
                while(itDs.hasNext()) {
                    DataStyle ds = itDs.next();
                    if(ds.getName().equals("Productstyle")){
                        this.tr.appendElement("td").addClass("Productstyle").appendElement("span").text(var7.getName());
                        b = false;
                        break;
                    }
                }
                if(b){
                    this.tr.appendElement("td").appendElement("span").text(var7.getName());
                }
            }
            Iterator var3 = cells.iterator();
            this.cellAffectClass(var3);

        }//End of product iterator
    }

    public List cellSort(Iterator it){
        List<ProductOrder> l = new ArrayList<ProductOrder>();
        while(it.hasNext()){
            Product p = (Product)it.next();
            List pCells = p.getCells();
            Collections.sort(pCells, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Cell cell1 = (Cell)o1;
                    Cell cell2 = (Cell)o2;
                    return HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell1.getFeature()) - HTMLExporterCustom.this.metadata.getSortedFeatures().indexOf(cell2.getFeature());

                }
            });
            Iterator varr = pCells.iterator();
            while(varr.hasNext()){
                Cell pCell = (Cell)varr.next();
                String newName = this.sansAccents(pCell.getFeature().getName());
                newName = newName.replaceAll("[^a-zA-Z0-9]", "");
                newName = newName.replaceAll("[^\\p{ASCII}]", "-");
                if(newName.toLowerCase().contains(this.getParameters().getOrderColumn().toLowerCase()) || newName.toLowerCase() == this.getParameters().getOrderColumn().toLowerCase()){
                    Iterator itt = l.iterator();
                    int indexItt = 0;
                    if(l.size() == 0){
                        l.add(new ProductOrder(p.getName(), pCells));
                    }else{
                        while(itt.hasNext()){
                            ProductOrder po = (ProductOrder) itt.next();
                            String str = "";
                            Integer val = null;
                            Float f = null;

                            for(int a =0; a < po.getCells().size(); a++){
                                Cell c = (Cell)po.getCells().get(a);
                                String newName2 = this.sansAccents(c.getFeature().getName());
                                newName2 = newName2.replaceAll("[^a-zA-Z0-9]", "");
                                newName2 = newName2.replaceAll("[^\\p{ASCII}]", "-");
                                if(newName2.toLowerCase().contains(this.getParameters().getOrderColumn().toLowerCase()) || newName2.toLowerCase() == this.getParameters().getOrderColumn().toLowerCase()){
                                    String decimalPattern = "([0-9]*)\\.([0-9]*)";
                                    boolean match = Pattern.matches(decimalPattern, c.getContent());
                                    if(this.isInteger(c.getContent())){
                                        val = Integer.parseInt(c.getContent());
                                    }else if(match){
                                        f = Float.parseFloat(c.getContent());
                                    }else{
                                        str = c.getContent();
                                    }
                                    break;
                                }
                            }

                            if(val != null){
                                if(Integer.parseInt(pCell.getContent()) > val){
                                    if(this.getParameters().getOrderType().equals("asc")){
                                        if(!itt.hasNext()){
                                            l.add(new ProductOrder(p.getName(), pCells));
                                            break;
                                        }
                                    }else{
                                        l.add(indexItt, new ProductOrder(p.getName(), pCells));
                                        break;
                                    }

                                }else{
                                    if(this.getParameters().getOrderType().equals("asc")){
                                        l.add(indexItt, new ProductOrder(p.getName(), pCells));
                                        break;
                                    }else{
                                        if(!itt.hasNext()){
                                            l.add(new ProductOrder(p.getName(), pCells));
                                            break;
                                        }
                                    }

                                }
                                indexItt++;
                            }else if(f != null){
                                if(Float.parseFloat(pCell.getContent()) > f){
                                    if(this.getParameters().getOrderType().equals("asc")){
                                        if(!itt.hasNext()){
                                            l.add(new ProductOrder(p.getName(), pCells));
                                            break;
                                        }
                                    }else{
                                        l.add(indexItt, new ProductOrder(p.getName(), pCells));
                                        break;
                                    }

                                }else{
                                    if(this.getParameters().getOrderType().equals("asc")){
                                        l.add(indexItt, new ProductOrder(p.getName(), pCells));
                                        break;
                                    }else{
                                        if(!itt.hasNext()){
                                            l.add(new ProductOrder(p.getName(), pCells));
                                            break;
                                        }
                                    }

                                }
                                indexItt++;
                            }else{
                                if(pCell.getContent().compareToIgnoreCase(str) > 0){
                                    if(this.getParameters().getOrderType().equals("asc")){
                                        if(!itt.hasNext()){
                                            l.add(new ProductOrder(p.getName(), pCells));
                                            break;
                                        }
                                    }else{
                                        l.add(indexItt, new ProductOrder(p.getName(), pCells));
                                        break;
                                    }

                                }else{
                                    if(this.getParameters().getOrderType().equals("asc")){
                                        l.add(indexItt, new ProductOrder(p.getName(), pCells));
                                        break;
                                    }else{
                                        if(!itt.hasNext()){
                                            l.add(new ProductOrder(p.getName(), pCells));
                                            break;
                                        }
                                    }

                                }
                                indexItt++;
                            }

                        }
                    }
                }

            }

        }
        return l;
    }

    public List productSort(Iterator it){
        List<Product> l = new ArrayList<Product>();
        while (it.hasNext()) {
            Product p = (Product) it.next();
            Iterator itt = l.iterator();
            int indexItt = 0;
            if (l.size() == 0) {
                l.add(p);
            } else {
                while (itt.hasNext()) {
                    Product po = (Product) itt.next();
                    if (p.getName().compareToIgnoreCase(po.getName()) > 0) {
                        if (this.getParameters().getOrderType().equals("asc")) {
                            if (!itt.hasNext()) {
                                l.add(p);
                                break;
                            }
                        } else {
                            l.add(indexItt, p);
                            break;
                        }

                    } else {
                        if (this.getParameters().getOrderType().equals("asc")) {
                            l.add(indexItt, p);
                            break;
                        } else {
                            if (!itt.hasNext()) {
                                l.add(p);
                                break;
                            }
                        }

                    }
                    indexItt++;
                }
            }

        }
        return l;
    }

    public void cellAffectClass(Iterator it){
        boolean isReverse = this.getParameters().isReversePCM();
            while(it.hasNext()) {
                Cell cell = (Cell)it.next();
                Element td = this.tr;
                if(isReverse){
                    String newName = this.sansAccents(cell.getFeature().getName());
                    newName = newName.replaceAll("[^a-zA-Z0-9]", "");
                    newName = newName.replaceAll("[^\\p{ASCII}]", "-");
                    td = this.tr.parent().getElementById(newName);
                }
                Iterator<DataStyle> itDs = this.getParameters().getDataStyleParam().iterator();
                while(itDs.hasNext()){
                    DataStyle ds = itDs.next();
                    String decimalPattern = "([0-9]*)\\.([0-9]*)";
                    boolean match = Pattern.matches(decimalPattern, cell.getContent());
                    if (ds.getName().contains("rangein")) {
                        if(isInteger(cell.getContent()) || match){
                            Integer tmp = null;
                            if(match){
                               Float f = Float.parseFloat(cell.getContent());
                               tmp = Math.round(f);
                            }else{
                                tmp = Integer.parseInt(cell.getContent());
                            }
                            if(this.rangeIn(ds.getBorneinf(), ds.getBornesup(), tmp )){
                                td = td.appendElement("td").addClass(ds.getName());
                                break;
                            }
                        }
                    }else if(ds.getName().contains("rangeout")){
                        if(isInteger(cell.getContent()) || match) {
                            Integer tmp = null;
                            if(match){
                                Float f = Float.parseFloat(cell.getContent());
                                tmp = Math.round(f);
                            }else{
                                tmp = Integer.parseInt(cell.getContent());
                            }
                            if (this.rangeIn(ds.getBorneinf(), ds.getBornesup(), tmp)) {
                                td = td.appendElement("td").addClass(ds.getName());
                                break;
                            }
                        }
                    }
                    if(ds.getName().contains("compare")){
                        if(isInteger(cell.getContent()) || match) {
                            Integer tmp = null;
                            Float f = null;
                            if(match){
                                f = Float.parseFloat(cell.getContent());
                                if (f == ds.getValue()) {
                                    td = td.appendElement("td").addClass(ds.getName());
                                    break;
                                }
                            }else{
                                tmp = Integer.parseInt(cell.getContent());
                                if (tmp == ds.getValue()) {
                                    System.out.println("fge");
                                    td = td.appendElement("td").addClass(ds.getName());
                                    break;
                                }
                            }
                        }
                    }
                }//End Datastyle iterator

                if(isReverse){
                    String newName = this.sansAccents(cell.getFeature().getName());
                    newName = newName.replaceAll("[^a-zA-Z0-9]", "");
                    newName = newName.replaceAll("[^\\p{ASCII}]", "-");
                    if(td == this.tr.parent().getElementById(newName)){
                        td = td.appendElement("td");
                    }
                }else{
                    if(td == this.tr){
                        Iterator<DataStyle> itDs2 = this.getParameters().getDataStyleParam().iterator();
                        while(itDs2.hasNext()) {
                            DataStyle ds2 = itDs2.next();
                            if(ds2.getName().endsWith("style")){
                                String str = ds2.getName().substring(0, ds2.getName().length() - 5);
                                String tmp = cell.getFeature().getName().replaceAll("\\s+","");
                                tmp = Normalizer.normalize(tmp, Normalizer.Form.NFD);
                                tmp = tmp.replaceAll("[^\\p{ASCII}]", "");
                                tmp = tmp.replaceAll("[^a-zA-Z0-9]", "");
                                if (tmp.toLowerCase().contains(str.toLowerCase()) || tmp.toLowerCase().equals(str.toLowerCase()) ) {
                                    td = td.appendElement("td").addClass(ds2.getName());
                                }
                            }
                        }
                        if(td == this.tr){
                            td = this.tr.appendElement("td");
                        }
                    }
                }


                td.appendElement("span").text(cell.getContent());
            }//end cell iterator
    }


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static String sansAccents(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }
    /**
     *
     * @param feature
     */
    public void visit(Feature feature) {
        //System.out.println(feature.getCells());
        if(this.getParameters().isReversePCM()){
            Iterator<DataStyle> itDs = this.getParameters().getDataStyleParam().iterator();
            Element e = this.tr;
            String newName = this.sansAccents(feature.getName());
            newName = newName.replaceAll("[^a-zA-Z0-9]", "");
            newName = newName.replaceAll("[^\\p{ASCII}]", "-");

            while(itDs.hasNext()){
                DataStyle ds = itDs.next();
                if(ds.getName().endsWith("style")){
                    String str = ds.getName().substring(0, ds.getName().length() - 5);
                    String tmp = feature.getName().replaceAll("\\s+","");
                    tmp = Normalizer.normalize(tmp, Normalizer.Form.NFD);
                    tmp = tmp.replaceAll("[^\\p{ASCII}]", "");
                    tmp = tmp.replaceAll("[^a-zA-Z0-9]", "");
                    if (tmp.toLowerCase().contains(str.toLowerCase()) || tmp.toLowerCase().equals(str.toLowerCase())) {
                        e = e.parent().getElementsByTag("tbody").first().appendElement("tr").attr("id", newName).addClass(ds.getName());
                    }
                }
            }
            if(e == this.tr){
                e = e.parent().getElementsByTag("tbody").first().appendElement("tr").attr("id", newName);
            }
            /*Element th = this.tr.appendElement("th");
            if(this.featureDepth > 1) {
                th.attr("rowspan", Integer.toString(this.featureDepth));
            }*/
            e.appendElement("td").appendElement("span").text(feature.getName());
        }else{
            Iterator<DataStyle> itDs = this.getParameters().getDataStyleParam().iterator();
            Element th;
            try{
                 th = this.tr.parent().getElementById("last-row").getElementsByTag("tr").first().appendElement("th");
            }catch (Exception e){
                 th = this.tr.parent().getElementById("first-row").getElementsByTag("tr").first().appendElement("th");
            }
            while(itDs.hasNext()){
                DataStyle ds = itDs.next();
                if(ds.getName().endsWith("style")){
                    String str = ds.getName().substring(0, ds.getName().length() - 5);
                    String tmp = feature.getName().replaceAll("\\s+","");
                    tmp = Normalizer.normalize(tmp, Normalizer.Form.NFD);
                    tmp = tmp.replaceAll("[^\\p{ASCII}]", "");
                    tmp = tmp.replaceAll("[^a-zA-Z0-9]", "");
                    if (tmp.toLowerCase().contains(str.toLowerCase()) || tmp.toLowerCase().equals(str.toLowerCase())) {
                        //colgroup.appendElement("col").attr("style", "background-color: " + ds.getBgcolor() + " !important;color: " + ds.getTxtcolor() );
                        th = th.addClass(ds.getName());
                    }
                }
            }
            if(this.featureDepth > 1) {
                th.attr("rowspan", Integer.toString(this.featureDepth));
            }
            th.text(feature.getName());
        }
    }
    /**
     *
     * @param featureGroup
     */
    public void visit(FeatureGroup featureGroup) {
        Element th = this.tr.appendElement("th");
        if(!featureGroup.getFeatures().isEmpty()) {
            th.attr("colspan", Integer.toString(featureGroup.getFeatures().size()));
        }

        th.text(featureGroup.getName());
        this.nextFeaturesToVisit.addAll(featureGroup.getFeatures());
    }

    /**
     *
     * @param product
     */
    public void visit(Product product) {
        this.tr.appendElement("th").text(product.getName());
        List cells = product.getCells();
        Collections.sort(cells, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Cell cell1 = (Cell)o1;
                Cell cell2 = (Cell)o2;
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

    /**
     *
     * @param pcm
     * @param table
     */
    public void featuresRow(PCM pcm, Element table, Boolean bottom){
        // List of features
        LinkedList featuresToVisit = new LinkedList();
        this.nextFeaturesToVisit = new LinkedList();
        featuresToVisit.addAll(pcm.getFeatures());
        this.tr = table.appendElement("tr");
        Iterator<DataStyle> itDataStyle = this.getParameters().getDataStyleParam().iterator();
        boolean b = true;
        while(itDataStyle.hasNext()){
            DataStyle tmp = itDataStyle.next();
            if(tmp.getName().equals("Productstyle")){
                if(this.getParameters().isReversePCM()){
                    this.tr.parent().prependElement("thead").attr("id", "first-row").addClass("ProductStyle").appendElement("tr").appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product");
                    b = false;
                    break;
                }else{
                    if(bottom){
                        this.tr.parent().appendElement("tfoot").attr("id", "last-row").appendElement("tr").appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product").addClass("ProductStyle");
                    }else{
                        this.tr.parent().prependElement("thead").attr("id", "first-row").appendElement("tr").appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product").addClass("ProductStyle");
                    }

                    b = false;
                    break;
                }
            }
        }
        if(b){
            if(this.getParameters().isReversePCM()) {
                this.tr.parent().prependElement("thead").attr("id", "first-row").appendElement("tr").appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product");
            }else{
                if(bottom){
                    this.tr.parent().appendElement("tfoot").attr("id", "last-row").appendElement("tr").appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product");
                }else{
                    this.tr.parent().prependElement("thead").attr("id", "first-row").appendElement("tr").appendElement("th").attr("rowspan", Integer.toString(this.featureDepth)).text("Product");
                }
            }
        }

        Iterator var5;
        while(!featuresToVisit.isEmpty()) {
            Collections.sort(featuresToVisit, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    AbstractFeature feat1 = (AbstractFeature)o1;
                    AbstractFeature feat2 = (AbstractFeature)o2;
                    return HTMLExporterCustom.this.metadata.getFeaturePosition(feat1) - HTMLExporterCustom.this.metadata.getFeaturePosition(feat2);
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
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
/*

 */
        // Load a PCM
        File pcmFile = new File("pcms/PCM4/comparison-nikon-dslr.pcm");
        //File paramFile = new File("pcms/PCM1/param1.json");

        // read the json file
        PCMLoader loader = new KMFJSONLoader();
        PCM pcm = loader.load(pcmFile).get(0).getPcm();

        HTMLExporterCustom te = new HTMLExporterCustom("PCM4/params4.json");
        System.out.println(te.toHTML(pcm));
        HTMLExporter testHtmlExporter = new HTMLExporter();


        //modif by cheisda 24.11.2015
        generateHTMLFile(te, pcm);

        try {

            //TO DO : create a tmp folder
            //Output file
            FileOutputStream fos = new FileOutputStream("src\\"+System.currentTimeMillis()/1000+"TestArchivePDL.zip");
            //Creating the output file
            ZipOutputStream zos = new ZipOutputStream(fos);


            //getting files to zip them
            String fileGeneratedHTMLPath = "src\\HTMLGenerated.html";
            String fileGeneratedCSSPath = "src\\style.css";
            //getting Files size
            int CSSSize = getFileSize(fileGeneratedCSSPath);
            int HTMLSize = getFileSize(fileGeneratedHTMLPath);
            System.out.println("Taille CSS : " + CSSSize + "octets, taille HTML : "+HTMLSize + "octets. ");
            int totalFilesSize = CSSSize+HTMLSize;


            //Adding to archive File
            addToZipFile(fileGeneratedHTMLPath,zos, totalFilesSize);
            addToZipFile(fileGeneratedCSSPath,zos,totalFilesSize);

            //closing the streamsH
            zos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }//fin main


    //Modif by Cheisda

    /**
     *
     * @param dataResults
     * @param pcm
     */
    public static void generateHTMLFile(HTMLExporterCustom dataResults, PCM pcm) {
        try {
            File HTMLGeneratedFile = new File("src\\HTMLGenerated.html");
            FileWriter fileWriter = new FileWriter(HTMLGeneratedFile);
            fileWriter.write(dataResults.toHTML(pcm));
            fileWriter.flush();
            fileWriter.close();
            if (HTMLGeneratedFile == null){
                System.out.println("le fichier généré est vide");
            } else {
                System.out.println("Bravo le fichier HTML a bien été généré !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
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
        return (int)file.length();
    }

    /**
     *
     * @param filePath
     * @param zos
     * @param filesSize
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void addToZipFile(String filePath, ZipOutputStream zos,int filesSize)throws FileNotFoundException,IOException {
        System.out.println("Writing '" + filePath + "' to zip file");

        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(filePath);
        zos.putNextEntry(zipEntry);


        byte[] bytes = new byte[filesSize];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

    /**
     *
     * @param borneinf
     * @param bornesup
     * @param valuePCM
     * @return
     */
    private boolean rangeIn(int borneinf, int bornesup, int valuePCM) {
        return ((valuePCM >= borneinf) && (valuePCM <= bornesup));
    }

    /**
     * @param borneinf
     * @param bornesup
     * @param valuePCM
     * @return
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
