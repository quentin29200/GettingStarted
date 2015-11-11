package org.opencompare;
import com.projetloki.genesis.*;
import com.projetloki.genesis.image.*;

import javax.swing.text.html.CSS;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rom on 10/21/2015.
 */
public class CSSExporter {

    private Param parameters;
    private Genesis genesis;
    private List<CssModule> modules = new ArrayList<CssModule>();

    public List<CssModule> getModules(){
        return modules;
    }

    public Genesis getGenesis() {
        return genesis;
    }

    public void setGenesis(Genesis genesis) {
        this.genesis = genesis;
    }

    public Param getParameters() {

        return parameters;
    }

    public void setParameters(Param parameters) {
        this.parameters = parameters;
    }



    public CSSExporter(String fileName){
        Param tmp = new Param(fileName);
        setParameters(tmp);
    }

        static final CssModule MY_CSS_MODULE = new CssModule() {
            @Override public void configure(CssBuilder out) {
                out.addRule(".myButton", Properties.builder()
                        .setWidthPx(80)
                        .setHeightPx(20)
                        .setBackground(Color.ALUMINIUM_3)
                        .set(CursorValue.POINTER)
                        .setBorderRadiusPx(2));

                out.addRule(".myButton:hover", Properties.builder()
                        .setBackground(Color.ALUMINIUM_2)
                        .set(FontWeightValue.BOLD));

                out.addRule("div.myBox", Properties.builder()
                        .set(FloatValue.LEFT)
                        .setWidthPct(50));
            }
        };

    static final CssModule MY_CSS_MODULE_2 = new CssModule() {
        @Override public void configure(CssBuilder out) {
            out.addRule(".myButton2", Properties.builder()
                    .setWidthPx(80)
                    .setHeightPx(20)
                    .setBackground(Color.RED)
                    .set(CursorValue.POINTER)
                    .setBorderRadiusPx(2));

            out.addRule(".myButton2:hover", Properties.builder()
                    .setBackground(Color.BLUE)
                    .set(FontWeightValue.BOLD));
        }
    };

    public void generate(){
        Genesis.Builder build = Genesis.builder();
        for (int i = 0; i < this.getModules().size(); i++) {
            build.install(this.getModules().get(i));
            System.out.println(this.getModules().get(i));
        }
        this.setGenesis(build.build());

        File file = new File("C:/Users/Rom/Documents/Software/zaq.css");
        try {
            this.getGenesis().writeCssFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        CSSExporter tmp = new CSSExporter("params1.json");
        System.out.println(tmp.getParameters().getOrderType());
        tmp.getModules().add(MY_CSS_MODULE);
        tmp.getModules().add(MY_CSS_MODULE_2);

        tmp.generate();

        /*Genesis genesis = Genesis.builder()
                .install(MY_CSS_MODULE)
                .build();*/
        /*Genesis.Builder ge = Genesis.builder();
        ge.install(MY_CSS_MODULE);
        ge.install(MY_CSS_MODULE_2);
        Genesis genesis = ge.build();*/
        String str = tmp.getGenesis().getCss();
        System.out.println(str);

    }

}
