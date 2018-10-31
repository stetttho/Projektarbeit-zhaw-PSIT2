package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.*;

import java.awt.Point;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TerminberechnungTest {
    Terminberechnung terminberechnung;
    Calendar datumVon, datumBis;
    ArrayList<Kundentermin> listeTermine;
    private HashMap<Calendar,ArrayList<Fahrzeug>> fahrzeuge;
    private ArrayList<Kunde> kunden;
    private HashMap<Calendar,ArrayList<Kundentermin>> kundentermine;
    private HashMap<Calendar,ArrayList<Mitarbeiter>> mitarbeiter;
    
    /** 
     * Legt ein Objekt von Terminberechnung sowie Listen von Mitarbeitern,
     * Fahrzeugen und Kundenterminen an, die für alle Testfälle 
     * benutzt werden kann.
     */
    @Before
    public void setUp() throws Exception 
    {
        datumVon = Calendar.getInstance();
        datumVon.clear();
        datumVon.set(2017, 4, 8);
        

        datumBis = Calendar.getInstance();
        datumBis.clear();
        datumBis.set(2017, 4, 12);
       
        generiereMitarbeiterListe();
        generiereFahrzeugListe();
        generiereKunden();
        generiereKundentermine();
        terminberechnung = new Terminberechnung(datumVon, datumBis);
    
    }
        
    /**
     * Testet das erstellen eines Objektes mit ungültigen Angaben
     * (von-Datum nach bis-Datum)
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDatumsBereichUngueltig()    
    {
        datumVon = Calendar.getInstance();
        datumVon.clear();
        datumVon.set(2017, 3, 12);
        

        datumBis = Calendar.getInstance();
        datumBis.clear();
        datumBis.set(2017, 3, 11);
        
        terminberechnung = new Terminberechnung(datumVon, datumBis);
    }
    
    /**
     * Testet das erstellen eines Objektes mit ungültigen Angaben
     * (von und bis-Datum am gleichen Tag)
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDatumsBereichUngueltigGleicherTag()    
    {
        datumVon = Calendar.getInstance();
        datumVon.clear();
        datumVon.set(2017, 3, 11);
        

        datumBis = Calendar.getInstance();
        datumBis.clear();
        datumBis.set(2017, 3, 11);
        
        terminberechnung = new Terminberechnung(datumVon, datumBis);
    }    
    
    /**
     * Testet, die Methode getVerfuegbarkeiten, die Anzahl der Verfügbarkeiten
     * Objekte die erstellt werden, richtet sich nach Differenz der Tage, mit
     * diesem Test wird also indirekt die Methode zuBerechnendeAnzahlTage
     * mitgetestet
     */
    @Test
    public void testGetVerfuegbarkeiten()
    {        
        this.terminberechnung = new Terminberechnung(this.datumVon,
                                                     this.datumBis);
              
        assertEquals(5,terminberechnung.getVerfuegbarkeiten().size());         
    }
    

    
    /**
     * Testet die Methode berechneTermine, es sollen soviele Varianten gebildet
     * werden, wie als Parameter angegeben
     */
    @Test
    public void testBerechneTermineGueltig()
    {
        terminberechnung.berechneTermine(1,3);
        assertEquals(3,terminberechnung.getVarianten().size());
        assertEquals(1,terminberechnung.getVarianten().get(0).getPlanId());
    }
    
    /**
     * Testet die Methode berechne Termine, wenn die Anzahl <= 0 ist soll
     * eine IllegalArgumentException geworfen werden
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBerechneTermineUngueltigAnzahlNull()
    {
        terminberechnung.berechneTermine(1,0);
    }

    /**
     * Testet die Methode berechne Termine, wenn die Anzahl <=0  ist soll
     * eine IllegalArgumentException geworfen werden
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBerechneTermineUngueltigAnzahlNegativ()
    {
        terminberechnung.berechneTermine(1,-1);
    }    
    
    /**
     * Testet die Methode berechne Termine, wenn die PlanId <= 0 ist soll
     * eine IllegalArgumentException geworfen werden
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBerechneTermineUngueltigPlanIdNull()
    {
        terminberechnung.berechneTermine(0,1);
    }

    /**
     * Testet die Methode berechne Termine, wenn die PlanId <=0  ist soll
     * eine IllegalArgumentException geworfen werden
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBerechneTermineUngueltigPlanIdNegativ()
    {
        terminberechnung.berechneTermine(-1,1);
    }     
    
    /**
     * Testet die Methode getVarianten
     */
    @Test
    public void testGetVariantenGueltig()
    {
        terminberechnung.berechneTermine(1,1);
        assertEquals(1,terminberechnung.getVarianten().size());
    }
    
    /**
     * Testet die Methode getVarianten, wenn noch keine gebildet wurden
     */
    @Test
    public void testGetVariantenGueltigNull()
    {
        assertEquals(0,terminberechnung.getVarianten().size());
    }
    
    /**
     * Testet ob eine Variante definitiv gesetzt wird
     */
    @Test
    public void testSetzungEinerDefinitivenVariante()
    {
        boolean hatDefinitiveVariante = false;
        terminberechnung.berechneTermine(1,5);
        for(Variante element : terminberechnung.getVarianten())
        {
            if(element.getStatusDefinitiv())
            {
                hatDefinitiveVariante = true;
            }
            
        }
        assertTrue(hatDefinitiveVariante);        
    }
    
    
    /**
     * Testet ob nur eine Variante als definitiv gesetzt ist, nicht mehrere
     */
    @Test
    public void testNichtMehrereVariantenDefinitiv()
    {
        boolean eineDefinitiv=false;
        boolean doppeltDefinitiv = false;
        terminberechnung.berechneTermine(1,5);
        for(Variante element : terminberechnung.getVarianten())
        {
            if(element.getStatusDefinitiv())
            {
                if(eineDefinitiv)
                {
                    doppeltDefinitiv = true;
                }
                else
                {
                    eineDefinitiv = true;
                }
            }
        }
        assertFalse(doppeltDefinitiv);
    }
    
    /**
     * Testet Exception, wenn keine Fahrzeuge, Mitarbeiter oder Kundentermine
     * vorhanden sind
     */
    @Test(expected=IllegalArgumentException.class)
    public void testExceptionBeiNichtVerfuegbarkeit()
    {
        this.datumVon = Calendar.getInstance();
        this.datumVon.clear();
        this.datumVon.set(2000, 1, 8);
        

        this.datumBis = Calendar.getInstance();
        this.datumBis.clear();
        this.datumBis.set(2000, 1, 12);
       
        this.terminberechnung = new Terminberechnung(this.datumVon, 
                                                     this.datumBis);
    }
        
    private void generiereMitarbeiterListe() 
    {
        mitarbeiter = new HashMap<>();
        ArrayList<Mitarbeiter> mitarbeitende = new ArrayList<>();
        mitarbeitende.add(
                new Mitarbeiter(0,
                                true,
                                "Cook", 
                                "James", 
                                "033 333 33 33"));
        mitarbeitende.add(
                new Mitarbeiter(1,
                        true,
                        "Kolumbus",
                        "Christopher",
                        "011 111 11 11"));
        mitarbeitende.add(
                new Mitarbeiter(2,
                                false,
                                "Polo", 
                                "Marco", 
                                "022 222 22 22"));   
        mitarbeitende.add(
                new Mitarbeiter(3,
                                true,
                                "Khan",
                                "Dschingis", 
                                "044 444 44 44"));
        
        Calendar datum = (Calendar) this.datumVon.clone();
        for (int index = 0; index < 5; 
                index++)
        {
            datum.add(Calendar.DAY_OF_MONTH, index);
            mitarbeiter.put(datum, mitarbeitende);
        }
        
    }
    
    private void generiereFahrzeugListe() 
    {
        ArrayList<Fahrzeug> listeFahrzeuge = new ArrayList<>();
        listeFahrzeuge.add(new Fahrzeug(0,true,"VW Passat","LU 115 679"));
        listeFahrzeuge.add(new Fahrzeug(1,true,"VW Passat","LU 203 584"));
        listeFahrzeuge.add(new Fahrzeug(2,false,"VW Golf","ZH 105 125"));   
        listeFahrzeuge.add(new Fahrzeug(3,true,"Fiat Punto","ZH 105 125"));   

        
        fahrzeuge = new HashMap<>(); 
        fahrzeuge.put(datumVon, listeFahrzeuge);
    }
    
    private void generiereKunden() 
    {
        kunden = new ArrayList<>();
        kunden.add(new Kunde(0,
                true,
                "Axa",
                new Point(100,100),
                1,
                "axa@beispiel.ch",
                "+41 44 456 32 21"));
        
        kunden.add(new Kunde(1,
                true,
                "ZKB",
                new Point(900,900),
                3,
                "zkb@beispiel.ch",
                "+41 44 646 87 77"));
        
        kunden.add(new Kunde(2,
                false,
                "Bank Hauser",
                new Point(200,250),
                2,
                "hauser-bank@beispiel.ch",
                "+41 44 456 32 21"));   
        
        kunden.add(new Kunde(3,
                true,
                "Spengler Kurt",
                new Point(300,250),
                2,
                "kurt-spengler@beispiel.ch",
                "+41 46 960 00 01"));  
        
        kunden.add(new Kunde(4,
               true,
               "Bank Weber",
               new Point(450,300),
               2,
               "kurt-spengler@beispiel.ch",
               "+41 46 960 00 01"));
        
        kunden.add(new Kunde(5,
               true,
               "Meier Hans",
               new Point(900,900),
               2,
               "kurt-spengler@beispiel.ch",
               "+41 46 960 00 01")); 
        
        kunden.add(new Kunde(6,
               true,
               "Kimmer Tim",
               new Point(200,200),
               2,
               "kurt-spengler@beispiel.ch",
               "+41 46 960 00 01")); 
    }
    
    private void generiereKundentermine() 
    {
        listeTermine = new ArrayList<>();
        listeTermine.add(
                new Kundentermin(
                        1, 
                        0, 
                        LocalTime.of(8, 0), 
                        LocalTime.of(9, 0), 
                        datumVon
                        ));
        listeTermine.get(0).setKunde(kunden.get(0));
        
        listeTermine.add(
                new Kundentermin(
                        2,
                        1,
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0),
                        datumVon
                        )); 
        listeTermine.get(1).setKunde(kunden.get(1));
        
        listeTermine.add(
                new Kundentermin(
                        3,
                        2,
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0),
                        datumVon
                        ));
        listeTermine.get(2).setKunde(kunden.get(2));

        listeTermine.add(
                new Kundentermin(
                        4,
                        4,
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0),
                        datumVon
                        ));
        listeTermine.get(3).setKunde(kunden.get(4));

        listeTermine.add(
                new Kundentermin(
                        5,
                        3,
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0),
                        datumVon
                        ));
        listeTermine.get(4).setKunde(kunden.get(3));        

        listeTermine.add(
                new Kundentermin(
                        6,
                        6,
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0),
                        datumVon
                        ));
        listeTermine.get(5).setKunde(kunden.get(6));
        
        kundentermine = new HashMap<>();
        kundentermine.put(datumVon, listeTermine);   
    }
}
