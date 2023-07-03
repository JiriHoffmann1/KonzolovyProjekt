package org.example;

public class Pojistenec {
    private String jmeno;
    private String prijmeni;
    private byte vek;
    private int teleCislo;

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public void setVek(byte vek) {
        this.vek = vek;
    }

    public void setTeleCislo(int teleCislo) {
        this.teleCislo = teleCislo;
    }

    public String getJmeno() {
        return jmeno;
    }
    public String getPrijmeni() {
        return prijmeni;
    }
    public byte getVek() {
        return vek;
    }
    public int getTeleCislo() {
        return teleCislo;
    }

    // tvorba pojištěnce
    public Pojistenec(String jmeno, String prijmeni, byte vek, int teleCislo){
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.vek = vek;
        this.teleCislo = teleCislo;
    }
}
