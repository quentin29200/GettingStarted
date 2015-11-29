package org.opencompare;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Quentin on 20/11/2015.
 */
public class TestParam {
    @Test
    public void testParam() throws IOException {
        // Path of Params
        String p = "TEST\\params1.json";

        Param param = new Param(p);
        ArrayList<DataStyle> dataTypeCollection;
        dataTypeCollection = (ArrayList<DataStyle>)param.getDataStyleParam();

        assertTrue(dataTypeCollection.get(0).getName().equals("Prixstyle"));
        assertTrue(dataTypeCollection.get(0).getBgcolor().equals("#FFFFFF"));
        assertTrue(dataTypeCollection.get(0).getTxtcolor().equals("#000000"));

        assertTrue(dataTypeCollection.get(1).getName().equals("modelestyle"));
        assertTrue(dataTypeCollection.get(1).getBgcolor().equals("#3B240B"));
        assertTrue(dataTypeCollection.get(1).getTxtcolor().equals("#01DF74"));

        assertTrue(dataTypeCollection.get(2).getName().equals("rangein1"));
        assertTrue(dataTypeCollection.get(2).getBgcolor().equals("#FFFFFF"));
        assertTrue(dataTypeCollection.get(2).getTxtcolor().equals("#000000"));
        assertTrue(dataTypeCollection.get(2).getBorneinf()==0);
        assertTrue(dataTypeCollection.get(2).getBornesup() == 200);

        assertTrue(dataTypeCollection.get(3).getName().equals("rangein2"));
        assertTrue(dataTypeCollection.get(3).getBgcolor().equals("#FFFFFF"));
        assertTrue(dataTypeCollection.get(3).getTxtcolor().equals("#000000"));
        assertTrue(dataTypeCollection.get(3).getBorneinf()==201);
        assertTrue(dataTypeCollection.get(3).getBornesup()==500);

        assertTrue(dataTypeCollection.get(4).getName().equals("rangein3"));
        assertTrue(dataTypeCollection.get(4).getBgcolor().equals("#FFFFFF"));
        assertTrue(dataTypeCollection.get(4).getTxtcolor().equals("#000000"));
        assertTrue(dataTypeCollection.get(4).getBorneinf()==501);
        assertTrue(dataTypeCollection.get(4).getBornesup()==1000);

        assertTrue(dataTypeCollection.get(5).getName().equals("compare1"));
        assertTrue(dataTypeCollection.get(5).getBgcolor().equals("#FF0000"));
        assertTrue(dataTypeCollection.get(5).getTxtcolor().equals("#CCCCCC"));
        assertTrue(dataTypeCollection.get(5).getValue()==200);

        assertTrue(dataTypeCollection.get(6).getName().equals("Productstyle"));
        assertTrue(dataTypeCollection.get(6).getWidth()==120);

        assertTrue(param.getOrderColumn().equals(""));
        assertTrue(param.getOrderType().equals("asc"));
        assertFalse(param.isReversePCM());
        assertFalse(param.isShowBottomNameFeatures());
        assertFalse(param.isShowPCMname());
    }
}
