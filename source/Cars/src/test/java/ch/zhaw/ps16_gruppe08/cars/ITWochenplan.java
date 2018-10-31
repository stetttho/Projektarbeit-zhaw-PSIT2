package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Führt diverse Integrationtests aus um die Klasse Wochenplan im
 * Zusammenspiel mit der Datenbank und mit anderen Klassen zu prüfen.
 * 
 */

public class ITWochenplan {
    Wochenplan wochenplan;
    Variante defVariante;
   
    private ArrayList<Mitarbeiter> mitarbeitende;
    private ArrayList<Fahrzeug> fahrzeuge;
    private ArrayList<Kundentermin> kundentermine;
    private ArrayList<Route> routen;

    Calendar datum;
   
    private ArrayList<Kunde> kunden;

    /** 
     * Legt ein Objekt von Routen und Wochenplänen 
     * sowie Listen von Mitarbeitern,
     * Fahrzeugen und Kundenterminen an, die für alle Testfälle 
     * benutzt werden kann.
     */
    @Before
    public void setUp() throws Exception {
        generiereMitarbeiterListe();
        generiereFahrzeugListe();
        generiereKunden();
        generiereKundentermine();
        generiereRouten();
        
        wochenplan = new Wochenplan();
        for(Route route : routen)
        {
                wochenplan.addRoute(route);            
        } 
    }
    
    
    /**
     * Testet ob die Routen korrekt einsortiert werden
     */
    @Test
    public void testeKorrekteEinsortierung()
    {
        wochenplan.resetIndex();
        assertEquals("09.04.2017",wochenplan.getDatumFormatiert());
    }
    
    /**
     * Testet korrekte Verschiebung der Indexe
     */
    @Test
    public void testeKorrekteVerschiebungIndex()
    {
        wochenplan.resetIndex();
        assertEquals("09.04.2017",wochenplan.getDatumFormatiert());
        wochenplan.moveNext();
        assertEquals("10.04.2017",wochenplan.getDatumFormatiert());
        wochenplan.moveNext();
        assertEquals("12.04.2017", wochenplan.getDatumFormatiert());
    }
    
    /**
     * die Termine korrekt sortiert ausgegeben werden
     */
    @Test
    public void testeSortierungTermine()
    {               
        Set<LocalTime> set = wochenplan.getRoute().keySet();
        Iterator<LocalTime> it = set.iterator();
        assertEquals(kundentermine.get(2).getStartzeit(),it.next());
    }
   
    /**
     * Testet ob der Korrekte Kunde zu der zeit zurückgegeben wird
     * Kontrolliert wird dies an der KundenId
     */
    @Test
    public void testeGetKunde()
    {
        wochenplan.setIndex(0);
        assertEquals(0, wochenplan.getKunde(8).getKundeId());
    }
    
    /**
     * Testet ob die korrekte Route zurückgegeben wird. 
     * Es wird über die KundenId getestet, 
     * der zur entsprechenden Zeit auf der Route ist.
     */
    @Test
    public void testeGetRoute()
    {
        wochenplan.setIndex(2);
        assertEquals(3, wochenplan.getRoute().get(
                                ZeitUtil.stringZuLocalTime("12:00")).getKundeId());
    }
    
    
    private void generiereRouten()
    {
        this.routen = new ArrayList<>();
        datum = Calendar.getInstance();
        datum.clear();
        datum.set(2017, 3, 10);
        this.routen.add(new Route(mitarbeitende.get(0),
                                  fahrzeuge.get(0),
                                  (Calendar)datum.clone()
                                  ));
        
        for(Kundentermin element : kundentermine)
        {
            this.routen.get(0).terminHinzufugen(element, 
                                                element.getStartzeit());            
        }

        datum.clear();
        datum.set(2017, 3, 12);
        this.routen.add(new Route(mitarbeitende.get(1),
                                  fahrzeuge.get(1),
                                  (Calendar)datum.clone()
                                  ));   
        for(Kundentermin element : kundentermine)
        {
            this.routen.get(1).terminHinzufugen(element, 
                                                element.getStartzeit());            
        }
        
        datum.clear();
        datum.set(2017, 3, 9);
        this.routen.add(new Route(mitarbeitende.get(2),
                                  fahrzeuge.get(2),
                                  (Calendar)datum.clone()
                                  ));
        for(Kundentermin element : kundentermine)
        {
            this.routen.get(2).terminHinzufugen(element, 
                                                element.getStartzeit());            
        }        
    }
        
    private void generiereMitarbeiterListe() 
    {
        mitarbeitende = new ArrayList<>();
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

    }
    
    private void generiereFahrzeugListe() 
    {
        fahrzeuge = new ArrayList<>();
        fahrzeuge.add(new Fahrzeug(0,true,"VW Passat","LU 115 679"));
        fahrzeuge.add(new Fahrzeug(1,true,"VW Passat","LU 203 584"));  
        fahrzeuge.add(new Fahrzeug(3,true,"Fiat Punto","ZH 105 125"));   
    }
    
    private void generiereKunden() {
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
    
    private void generiereKundentermine() {
       
        kundentermine = new ArrayList<>();
        
        kundentermine.add(
                new Kundentermin(
                        4,
                        4,
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0),
                        datum
                        ));
        kundentermine.get(0).setKunde(kunden.get(4));

        kundentermine.add(
                new Kundentermin(
                        5,
                        3,
                        LocalTime.of(12, 0),
                        LocalTime.of(12, 0),
                        datum
                        ));
        kundentermine.get(1).setKunde(kunden.get(3));  
                
        kundentermine.add(
                new Kundentermin(
                        1, 
                        0, 
                        LocalTime.of(8, 0), 
                        LocalTime.of(9, 0), 
                        datum
                        ));
        kundentermine.get(2).setKunde(kunden.get(0));
        
        kundentermine.add(
                new Kundentermin(
                        2,
                        1,
                        LocalTime.of(16, 0),
                        LocalTime.of(10, 0),
                        datum
                        )); 
        kundentermine.get(3).setKunde(kunden.get(1));
        
        kundentermine.add(
                new Kundentermin(
                        3,
                        2,
                        LocalTime.of(10, 0),
                        LocalTime.of(10, 0),
                        datum
                        ));
        kundentermine.get(4).setKunde(kunden.get(2));      

        kundentermine.add(
                new Kundentermin(
                        6,
                        6,
                        LocalTime.of(13, 0),
                        LocalTime.of(12, 0),
                        datum
                        ));
        kundentermine.get(5).setKunde(kunden.get(6));

    }
}
