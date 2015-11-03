package org.opencompare;
import com.projetloki.genesis.*;
import com.projetloki.genesis.image.*;

import javax.swing.text.html.CSS;
import java.io.File;

/**
 * Created by Rom on 10/21/2015.
 */
public class CSSExporter {
    /*static final CssModule MY_CSS_MODULE = new CssModule() {
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

    public static void main(String[] args) {
        Genesis genesis = Genesis.builder()
                .install(MY_CSS_MODULE)
                .setImageFolderUri("")
                .build();
        File f = new File("text.css");
        genesis.writeCssFile(f);
    }*/

}
