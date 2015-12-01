package org.opencompare;

/**
 * Created by Quentin on 09/11/2015.
 */
public class DataStyle {
    private String name;
    private String bgcolor;
    private String txtcolor;
    private int value;
    private int borneinf;
    private int bornesup;
    private int width;

    /**
     * Create a DataStyle object
     * @param name
     * @param bgcolor
     * @param txtcolor
     * @param value
     * @param borneinf
     * @param bornesup
     * @param width
     */
    public DataStyle(String name, String bgcolor, String txtcolor, int value, int borneinf, int bornesup, int width) {
        this.name = name;
        this.bgcolor = bgcolor;
        this.txtcolor = txtcolor;
        this.value = value;
        this.borneinf = borneinf;
        this.bornesup = bornesup;
        this.width = width;
    }


    /**
     * @return name
     * the name in the JSon file
     */
    public String getName() {
        return name;
    }

    /**
     * @return bgcolor
     * the background color attributed in the JSon file
     */
    public String getBgcolor() {
        return bgcolor;
    }

    /**
     * @return txtcolor
     * the text color defined in the JSon file
     */
    public String getTxtcolor() {
        return txtcolor;
    }

    /**
     * @return value
     * the value in the JSon file
     */
    public int getValue() {
        return value;
    }

    /**
     * @return bonrneinf
     * the lower limit of the data defined in the JSon file
     */
    public int getBorneinf() {
        return borneinf;
    }

    /**
     * @return bornesup
     * the higher limit of the data defined in the JSon file
     */
    public int getBornesup() {
        return bornesup;
    }

    /**
     * @return width
     * the width in the JSon file
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return string
     * the list of attributes of DataStyle class
     */
    public String toString() {
        return "DataStyle{" +
                "name='" + name + '\'' +
                ", bgcolor='" + bgcolor + '\'' +
                ", txtcolor='" + txtcolor + '\'' +
                ", value=" + value +
                ", borneinf=" + borneinf +
                ", bornesup=" + bornesup +
                ", width=" + width +
                '}';
    }
}
