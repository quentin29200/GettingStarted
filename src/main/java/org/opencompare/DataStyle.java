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
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return bgcolor
     */
    public String getBgcolor() {
        return bgcolor;
    }

    /**
     *
     * @return txtcolor
     */
    public String getTxtcolor() {
        return txtcolor;
    }

    /**
     *
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @return bonrneinf
     */
    public int getBorneinf() {
        return borneinf;
    }

    /**
     *
     * @return bornesup
     */
    public int getBornesup() {
        return bornesup;
    }

    /**
     *
     * @return width
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
