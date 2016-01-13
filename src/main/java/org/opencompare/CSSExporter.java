package org.opencompare;
import com.projetloki.genesis.*;
import com.projetloki.genesis.image.*;

import javax.swing.text.html.CSS;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rom on 10/21/2015.
 */
public class CSSExporter {

    private Param parameters;
    private Genesis genesis;
    private List<CssModule> modules = new ArrayList<CssModule>();

    /**
     *
     * @return modules
     */
    public List<CssModule> getModules(){
        return modules;
    }

    /**
     *
     * @return genesis
     */
    public Genesis getGenesis() {
        return genesis;
    }

    /**
     *
     * @param genesis
     */
    public void setGenesis(Genesis genesis) {
        this.genesis = genesis;
    }

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

    /**
     * Create CSSExporter
     * @param fileName
     */
    public CSSExporter(String fileName){
        Param tmp = new Param(fileName);
        setParameters(tmp);
    }

    /**
     * Change the datastyle document to CSS document
     */
    private void dataStyleToCSS() {
        Collection<DataStyle> dataTypeCollection;
        dataTypeCollection = this.getParameters().getDataStyleParam();
        CssModule CSS_MODULE_TMP;
        for(DataStyle tmp : dataTypeCollection){
            CSS_MODULE_TMP = new CssModule() {
                @Override public void configure(CssBuilder out) {
                    if(tmp.getWidth() != 0){
                        out.addRule("." + tmp.getName(), Properties.builder()
                                .setWidthPx(tmp.getWidth()));
                    }
                    if(tmp.getBgcolor() != ""){
                        out.addRule("." + tmp.getName(), Properties.builder()
                                .setBackground(Color.forHexString(tmp.getBgcolor().substring(1))));
                    }
                    if(tmp.getTxtcolor() != ""){
                        out.addRule("." + tmp.getName(), Properties.builder()
                                .setColor(Color.forHexString(tmp.getTxtcolor().substring(1))));
                    }
                }
            };
            this.getModules().add(CSS_MODULE_TMP);
        }
        // ajouter la ligne .table-container{overflow-x:scroll;width:90%;height:500px} au css

        CSS_MODULE_TMP = new CssModule() {
            @Override public void configure(CssBuilder out) {
                out.addRules(".table-container{overflow-x:scroll;width:90%;height:500px}");
                //out.addRule(".table-container", Properties.builder().set);
            }
        };
        this.getModules().add(CSS_MODULE_TMP);

    }

    /**
     * Permits generation of the css file
     */
    public void generate(){
        this.dataStyleToCSS();
        Genesis.Builder build = Genesis.builder();
        for (int i = 0; i < this.getModules().size(); i++) {
            build.install(this.getModules().get(i));
            System.out.println(this.getModules().get(i));
        }
        this.setGenesis(build.build());

        File file = new File("src\\style.css");
        try {
            this.getGenesis().writeCssFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        CSSExporter tmp = new CSSExporter("PCM4\\params4.json");
        try {
            tmp.generate();
        }
        catch (Exception e){
            System.out.println("Exception : Erreur dans le JSon");
        }
        //Print CSS
        String str = tmp.getGenesis().getCss();
        //System.out.println(str);

    }

}