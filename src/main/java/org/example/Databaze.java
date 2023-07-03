package org.example;

import java.util.ArrayList;

public class Databaze {
    ArrayList<Pojistenec> seznamPojistenych = new ArrayList<>();

    // case 1 - přidá pojištěnce
    public String pridejPojistence(String jmeno, String prijmeni, byte vek, int teleCislo) {
        seznamPojistenych.add(new Pojistenec(jmeno, prijmeni, vek, teleCislo));
        System.out.println();
        return String.format("Pojištěnec - %s %s - byl zapsán do databáze!", jmeno, prijmeni);
    }
    //case 2 - Vrátí seznam všech pojištěnců
    public ArrayList<Pojistenec> getVsechnyPojistence() {
        ArrayList<Pojistenec> nalezeni = new ArrayList<>();
        for (Pojistenec pojistenec : seznamPojistenych) {
            nalezeni.add(pojistenec);
        }
        return nalezeni;
    }
    // case 3 - vrátí seznam pojištěnců kteří odpovídají zadanému jménu
    public ArrayList<Pojistenec> getPojistenceDleJmena(String jmeno) {
        ArrayList<Pojistenec> nalezeni = new ArrayList<>();
        for (Pojistenec pojistenec : seznamPojistenych) {
            String jmenoZeSeznamu = pojistenec.getJmeno().toLowerCase() + " " + pojistenec.getPrijmeni().toLowerCase();
            if (jmenoZeSeznamu.contains(jmeno)) {
                nalezeni.add(pojistenec);
            }
        }
        return nalezeni;
    }
    //case 4 - odebere pojištěnce ze seznamu
    public void odeberPojistence(Pojistenec pojistenec){
        seznamPojistenych.remove(pojistenec);
    }

    //case 5 - změny údajů
    public void upravJmeno(Pojistenec pojistenec, String noveJmeno){
        pojistenec.setJmeno(noveJmeno);
    }
    public void upravPrijmeni(Pojistenec pojistenec, String novePrijmeni){
        pojistenec.setPrijmeni(novePrijmeni);
    }
    public void upravVek(Pojistenec pojistenec, byte novyVek){
        pojistenec.setVek(novyVek);
    }
    public void upravTeleCislo(Pojistenec pojistenec, int noveTeleCislo){
        pojistenec.setTeleCislo(noveTeleCislo);
    }
}

