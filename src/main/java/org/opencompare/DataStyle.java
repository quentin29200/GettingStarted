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

    // Constructor
    public DataStyle(String name, String bgcolor, String txtcolor, int value, int borneinf, int bornesup, int width) {
        this.name = name;
        this.bgcolor = bgcolor;
        this.txtcolor = txtcolor;
        this.value = value;
        this.borneinf = borneinf;
        this.bornesup = bornesup;
        this.width = width;
    }


    // Getter
    public String getName() {
        return name;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public String getTxtcolor() {
        return txtcolor;
    }

    public int getValue() {
        return value;
    }

    public int getBorneinf() {
        return borneinf;
    }

    public int getBornesup() {
        return bornesup;
    }

    public int getWidth() {
        return width;
    }

    // Pour les tests
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
