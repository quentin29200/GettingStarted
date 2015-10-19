package org.opencompare;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.opencompare.api.java.*;
import org.opencompare.api.java.io.HTMLExporter;
import org.opencompare.api.java.util.PCMVisitor;
import org.opencompare.api.java.value.*;


public class HTMLExporterCustom implements PCMVisitor {
    private Document doc;
    private Element body;
    Document.OutputSettings settings = new Document.OutputSettings();
    private String templateFull = "<html>\n\t<head>\n\t\t<meta charset=\"utf-8\"/>\n\t\t<title></title>\n\t</head>\n\t<body>\n\t</body>\n</html>";

    public String toHTML(PCM pcm) {
        this.doc = Jsoup.parse(this.templateFull);

        return this.doc.outputSettings(this.settings).outerHtml();
    }

    public HTMLExporterCustom() {

    }

    public String export(PCMContainer container) {
        return null;
    }

    public String toHTML(PCMContainer container) {
        return null;
    }

    public void visit(PCM pcm) {

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