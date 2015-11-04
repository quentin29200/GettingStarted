package org.opencompare;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.opencompare.api.java.*;
import org.opencompare.api.java.io.HTMLExporter;
import org.opencompare.api.java.value.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
//changement pour commit 
public class HTMLExporterCustom extends WebExporter {
    private Document doc;
    private Element body;
    private PCMMetadata metadata;
    private Element tr;

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
        return this.toHTML(container.getPcm());
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
        while(!featuresToVisit.isEmpty()) {
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

        while(var5.hasNext()) {
            Product var7 = (Product)var5.next();
            this.tr = table.appendElement("tr");
            var7.accept(this);
        }

    }

    public HTMLExporterCustom() {
    }

    public String export(PCMContainer container) {
        return null;
    }

    public String toHTML(PCMContainer container) {
        return null;
    }


    public void visit(Feature feature) {

    }

    public void visit(FeatureGroup featureGroup) {

    }

    public void visit(Product product) {

    }

    public void visit(Cell cell) {

    }

    @Override
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
