package org.example;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Pojistovna {
    Databaze databaze = new Databaze();
    Scanner scanner = new Scanner(System.in);
    public void spustProgram(){                                  // smyčka celého programu, přeruší se příkazem "6"
        while (true) {
            vypisHlavniObrazovku();
            vykonejHlavniPrikaz();
        }
    }
    public void vypisHlavniObrazovku() {                        // hlavní menu programu
        System.out.println();
        System.out.println("-------------------");
        System.out.println("Evidence pojištěných");
        System.out.println("-------------------");
        System.out.println("Vyberte si akci:");
        System.out.println("1 - Přidat nového pojištěnce");
        System.out.println("2 - Vypsat všechny pojištěnce");
        System.out.println("3 - Vyhledat pojištěného");
        System.out.println("4 - Odebrat pojištěnce");
        System.out.println("5 - Upravit údaje o pojištěnci");
        System.out.println("6 - konec");
        System.out.println("-------------------");
    }
    public void vyhubujUzivateli(String duvod){                             // chybové hlášky pro znovupoužití
        System.out.println();
        switch (duvod){
            case "spatneTeleCislo": {
                System.out.println("Zadali jste telefonní číslo ve špatném formátu!");
                System.out.println("Zkuste to prosím znovu!");
                break;
            }
            case "spatneJmeno": {
                System.out.println("Zadali jste nevyhovující jméno!");
                System.out.println("Zkuste to prosím znovu!");
                break;
            }
            case "spatnyVek": {
                System.out.println("Zadali jste nevyhovující věk!");
                System.out.println("Zkuste to prosím znovu!");
                break;
            }
            case "nikdoNenalezen": {
                System.out.println("V databázi se nenachází nikdo s těmito údaji.");
                System.out.println("Zkuste to prosím znovu!");
                break;
            }
            case "nalezenoMoc": {
                System.out.println("Pro tuto operaci bylo nalezeno příliš mnoho pojištěnců.");
                System.out.println("Příště buďte prosím konkrétnější!");
                break;
            }
            case "nevalidniPrikaz": {
                System.out.println("Zadali jste neplatný příkaz.");
                System.out.println("Zkuste to prosím znovu!");
                break;
            }
        }
    }
    public int overPrikaz(int horniHranice){                                      // ověří, jestli je příkaz pro řízení aplikace validní
        System.out.print("Zadejte příkaz: ");
        int prikaz = 0;
        boolean neValidniprikaz = true;
        while (neValidniprikaz) {
            try {
                prikaz = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                vyhubujUzivateli("nevalidniPrikaz");
                continue;
            }
            if (((prikaz < 1) || (prikaz > horniHranice))) {
                vyhubujUzivateli("nevalidniPrikaz");
            } else {
                neValidniprikaz = false;
            }
        }
        return prikaz;
    }

    public void vykonejHlavniPrikaz(){                                        // reakce na vstup uživatele, hlavní kostra programu
        switch (overPrikaz(6)) {
            case 1: {                                                      // Vyžádá si všechny potřebné informace a pak na jejich základě přidá pojištěnce do db.
                System.out.println();
                System.out.println(vyzadejSiInfoAZapisPojistence());
                break;
            }
            case 2: {                                                         // vypíše všechny pojištěnce
                System.out.println();
                overAVypis(najdiVsechnyPojistence());
                break;
            }
            case 3: {                                                       // vyhledá a vypíše pojištěnce podle jména či příjmení
                System.out.println();
                overAVypis(najdiPojistenceDleJmena(vyzadejJmenoAPrijmeni()));
                break;
            }
            case 4: {                                                             // odebere pojištěnce podle jména
                System.out.println();
                System.out.println(odeberPojistence(vyzadejJmenoAPrijmeni()));
                break;
            }
            case 5: {                                                            // najde pojištěnce a změní mu údaje, které si uživatel během tohoto procesu vybere
                System.out.println();
                najdiOverAZmenUdaje();
                break;
            }
            case 6: {                                                                    // konec
                exit(1);
            }
        }
    }
    public String vyzadejSiInfoAZapisPojistence(){                                // case 1 - získá všechny informace a uloží pojištěnce do db
        System.out.println("-------------------");
        System.out.println("Zadejte jméno pojištěnce:");
        String jmeno = overJmeno(2,10);
        System.out.println("Zadejte příjmení pojištěnce:");
        String prijmeni = overJmeno(3,15);
        System.out.println("Zadejte věk:");
        byte vek = overVek();
        System.out.println("Zadejte telefonní číslo v devítimístném formátu:");
        int teleCislo = overTeleCislo();
        databaze.pridejPojistence(jmeno, prijmeni, vek, teleCislo);
        return String.format("Pojištěnec - %s %s - byl přidán do databáze.",jmeno, prijmeni);
    }
    public String overJmeno(int spodniHranice, int vrchniHranice){                  // ověří a vrátí jméno
        String jmeno = "";
        boolean spatneJmeno = true;
        while (spatneJmeno) {
            jmeno = scanner.nextLine().trim();
            if (jmeno.length() < spodniHranice || jmeno.length() > vrchniHranice) {
                vyhubujUzivateli("spatneJmeno");
            } else {
                spatneJmeno = false;
            }
        }
        return jmeno;
    }
    public String vyzadejJmenoAPrijmeni(){                                     // vyžádání jména pro jeho použití při vyhledávání pojištěnců v db
        System.out.println("Zadejte jméno a příjmení:");
        return scanner.nextLine().trim().toLowerCase();
    }

    public ArrayList<Pojistenec> najdiVsechnyPojistence(){                                      // case 2 - najde všechny pojištěnce
        return databaze.getVsechnyPojistence();
    }

    // přetížení metody pro její podobu s parametrem
    public ArrayList<Pojistenec> najdiPojistenceDleJmena(String jmeno){                          // case 3 - najde pojištěnce podle jména
        return databaze.getPojistenceDleJmena(jmeno);
    }

    public ArrayList<Pojistenec> overAVypis(ArrayList<Pojistenec> nalezeni){             // oveří, zda není databáze prázdná, poté vypíše pojištěnce
        if (nalezeni.isEmpty()){
            vyhubujUzivateli("nikdoNenalezen");
        } else {
            System.out.println("Vypisuji pojištěnce:");
            System.out.println();
            for (Pojistenec pojistenec : nalezeni){
                System.out.println(zformatujInfo(pojistenec));
            }
        }
        return nalezeni;
    }
    public String zformatujInfo(Pojistenec pojistenec) {                           // metoda pro formát výpisu všech informací o zvoleném pojištěnci
        String teleCisloString = Integer.toString(pojistenec.getTeleCislo());
        String zformatovaneCislo1 = teleCisloString.substring(0, 3);
        String zformatovaneCislo2 = teleCisloString.substring(3, 6);
        String zrofmatovaneCislo3 = teleCisloString.substring(6, 9);
        return String.format("- %s %s - %d let, tel. číslo: +420 %s %s %s", pojistenec.getJmeno(), pojistenec.getPrijmeni(), pojistenec.getVek(), zformatovaneCislo1, zformatovaneCislo2, zrofmatovaneCislo3);
    }

    public byte overVek(){                                                          // ověří, jestli je zadaný věk kladné číslo do 128
        byte vek = 0;
        boolean spatnyVek = true;
        while (spatnyVek){
            try {
                vek = Byte.parseByte(scanner.nextLine().trim());
                spatnyVek = false;
                if (vek < 0) {
                    vyhubujUzivateli("spatnyVek");
                    spatnyVek = true;
                }
            } catch (Exception e) {
                vyhubujUzivateli("spatnyVek");
            }
        }
        return vek;
    }
    public int overTeleCislo(){                                                  // ověří, jestli je zadané telefonní číslo vůbec číslo a jestli je ve správném formátu
        boolean spatneCislo = true;
        int teleCislo = 0;
        while (spatneCislo){
            try {
                teleCislo = Integer.parseInt(scanner.nextLine().trim());
                spatneCislo = false;
                if ((teleCislo <100000000 || teleCislo >999999999) ){
                    vyhubujUzivateli("spatneTeleCislo");
                    spatneCislo = true;
                }
            } catch (Exception e) {
                vyhubujUzivateli("spatneTeleCislo");
            }
        }
        return teleCislo;
    }
    public String odeberPojistence(String jmeno){                                       // case 4 - odebere pojištěnce
        ArrayList<Pojistenec> nalezen = najdiPojistenceDleJmena(jmeno);
        String vysledekOperace = "";
        if (nalezen.size() > 1){
            vyhubujUzivateli("nalezenoMoc");
        } else if(nalezen.isEmpty()) {
            vyhubujUzivateli("nikdoNenalezen");
        } else {
            for (Pojistenec nalezeny : nalezen){
                for (Pojistenec pojistenec: databaze.seznamPojistenych){
                    if (nalezeny.equals(pojistenec)){
                        System.out.println();
                        vysledekOperace = String.format("Pojištěnec - %s %s - byl odebrán ze seznamu.", pojistenec.getJmeno(), pojistenec.getPrijmeni());
                        databaze.odeberPojistence(pojistenec);
                        break;
                    }
                }
            }
        }
        return vysledekOperace;
    }
    public void vypisNabidkuZmen(){
        System.out.println();
        System.out.println("-------------------");
        System.out.println("Zadejte, jaký údaj si přejete upravit");
        System.out.println("1 - Křestní jméno");
        System.out.println("2 - Příjmení");
        System.out.println("3 - Věk");
        System.out.println("4 - Telefonní číslo");
        System.out.println("-------------------");
    }
    public void najdiOverAZmenUdaje(){                                                              // najde jednoho pojištěnce určeného pro změnu údajů
        ArrayList <Pojistenec> nalezeni = najdiPojistenceDleJmena(vyzadejJmenoAPrijmeni());
        if ((nalezeni.size() == 1)){
            System.out.println("Byl nalezen následující pojištěnec:");
            System.out.println();
            for (Pojistenec pojistenec : nalezeni) {
                System.out.println(zformatujInfo(pojistenec));
                zmenUdaje(nalezeni);
            }
        } else if (nalezeni.size() > 1){
            vyhubujUzivateli("nalezenoMoc");
        } else {
            vyhubujUzivateli("nikdoNenalezen");
        }
    }
    public boolean chcetePokracovat(){                                          // dotaz na pokračování smyčky u změny údajů
        System.out.println();
        System.out.println("-------------------");
        System.out.println("Chcete tomuto pojištěnci změnit další údaj?");
        System.out.println("1 - Ano");
        System.out.println("2 - Ne");
        if (overPrikaz(2) == 1){
            vypisNabidkuZmen();
            return true;
        } else return false;
    }
    public void zmenUdaje(ArrayList<Pojistenec> nalezeni){                              // case 5 - hlavní smyčka menu změny údajů vybraného pojištěnce
        vypisNabidkuZmen();
        System.out.println();
        boolean pokracovat = true;
        while (pokracovat){
            switch (overPrikaz(4)) {
                case 1: {
                    pokracovat = zmenaJmena(nalezeni);
                    break;
                }
                case 2: {
                    pokracovat = zmenaPrijmeni(nalezeni);
                    break;
                }
                case 3: {
                    pokracovat = zmenaVeku(nalezeni);
                    break;
                }
                case 4: {
                    pokracovat = zmenaTeleCisla(nalezeni);
                    break;
                }
            }
        }
    }

    // metody pro změny jednotlivých údajů o vybraném pojištěnci.
    // všechny 4 jsou si dost podobné a věřím, že by je šlo zjednodušit do jedné. Nicméně nevím jak se vypořádat s těmi jednotlivými, byť malými rozdíly, které mezi nimi jsou.

    public boolean zmenaJmena(ArrayList<Pojistenec> nalezeni){
        System.out.println("Zadejte nové jméno:");
        String jmeno = overJmeno(2, 10);
        for (Pojistenec nalezen : nalezeni) {
            for (Pojistenec pojistenec : databaze.seznamPojistenych) {
                if (pojistenec.equals(nalezen)) {
                    databaze.upravJmeno(pojistenec, jmeno);
                    System.out.println("Jméno bylo úspěšně změněno.");
                }
            }
        }
        return chcetePokracovat();
    }
    public boolean zmenaPrijmeni(ArrayList<Pojistenec> nalezeni){
        System.out.println("Zadejte nové příjmení:");
        String jmeno = overJmeno(3, 15);
        for (Pojistenec nalezen : nalezeni) {
            for (Pojistenec pojistenec : databaze.seznamPojistenych) {
                if (pojistenec.equals(nalezen)) {
                    databaze.upravPrijmeni(pojistenec, jmeno);
                    System.out.println("Příjmení bylo úspěšně změněno.");
                }
            }
        }
        return chcetePokracovat();
    }
    public boolean zmenaVeku(ArrayList<Pojistenec> nalezeni){
        System.out.println("Zadejte nový věk:");
        byte vek = overVek();
        for (Pojistenec nalezen : nalezeni) {
            for (Pojistenec pojistenec : databaze.seznamPojistenych) {
                if (pojistenec.equals(nalezen)) {
                    databaze.upravVek(pojistenec, vek);
                    System.out.println("Věk byl úspěšně změněn.");
                }
            }
        }
        return chcetePokracovat();
    }

    public boolean zmenaTeleCisla(ArrayList<Pojistenec> nalezeni){
        System.out.println("Zadejte nové telefonní číslo:");
        int teleCislo = overTeleCislo();
        for (Pojistenec nalezen : nalezeni) {
            for (Pojistenec pojistenec : databaze.seznamPojistenych) {
                if (pojistenec.equals(nalezen)) {
                    databaze.upravTeleCislo(pojistenec, teleCislo);
                    System.out.println("Telefonní číslo bylo úspěšně změněno.");
                }
            }
        }
        return chcetePokracovat();
    }
}

