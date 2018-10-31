package ch.zhaw.ps16_gruppe08.cars;
 
import static org.junit.Assert.*;

import java.awt.Point;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class VerfugbarkeitenTest {

    private Verfugbarkeiten verfugbarkeit;
    private ArrayList<Mitarbeiter> listeMitarbeitende = new ArrayList<>();
    private ArrayList<Fahrzeug> listeFahrzeuge = new ArrayList<>();
    private ArrayList<Kundentermin> listeTermine = new ArrayList<>();
    
    @Before
    public void setUp() throws Exception 
    {
        Calendar datum = Calendar.getInstance();
        datum.set(2017, 3, 13);
        
        listeMitarbeitende.add(
                new Mitarbeiter(0,
                                true,
                                "Cook", 
                                "James", 
                                "033 333 33 33"));
        listeMitarbeitende.add(
                new Mitarbeiter(1,
                        true,
                        "Kolumbus",
                        "Christopher",
                        "011 111 11 11"));
        listeMitarbeitende.add(
                new Mitarbeiter(2,
                                false,
                                "Polo", 
                                "Marco", 
                                "022 222 22 22"));   
        listeMitarbeitende.add(
                new Mitarbeiter(3,
                                true,
                                "Khan",
                                "Dschingis", 
                                "044 444 44 44"));     
                                
        listeFahrzeuge.add(new Fahrzeug(0,true,"VW Passat","LU 115 679"));
        listeFahrzeuge.add(new Fahrzeug(1,true,"VW Passat","LU 203 584"));
        listeFahrzeuge.add(new Fahrzeug(2,false,"VW Golf","ZH 105 125"));   
        listeFahrzeuge.add(new Fahrzeug(3,true,"Fiat Punto","ZH 105 125"));   
        
        
        ArrayList<Kunde> kunden = new ArrayList<>();
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
               new Point(400,250),
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
        
        listeTermine.add(
                new Kundentermin(
                        1, 
                        0, 
                        LocalTime.of(8, 0), 
                        LocalTime.of(9, 0), 
                        datum
                        ));
        listeTermine.get(0).setKunde(kunden.get(0));
        
        listeTermine.add(
                new Kundentermin(
                        2,
                        1,
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0),
                        datum
                        )); 
        listeTermine.get(1).setKunde(kunden.get(1));
        
        listeTermine.add(
                new Kundentermin(
                        3,
                        2,
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0),
                        datum
                        ));
        listeTermine.get(2).setKunde(kunden.get(2));

        listeTermine.add(
                new Kundentermin(
                        4,
                        4,
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0),
                        datum
                        ));
        listeTermine.get(3).setKunde(kunden.get(4));

        listeTermine.add(
                new Kundentermin(
                        5,
                        3,
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0),
                        datum
                        ));
        listeTermine.get(4).setKunde(kunden.get(3));        

        listeTermine.add(
                new Kundentermin(
                        6,
                        6,
                        LocalTime.of(11, 0),
                        LocalTime.of(12, 0),
                        datum
                        ));
        listeTermine.get(5).setKunde(kunden.get(6));
        
        listeTermine.add(
                new Kundentermin(
                        6,
                        6,
                        LocalTime.of(9, 0),
                        LocalTime.of(12, 0),
                        datum
                        ));
        listeTermine.get(5).setKunde(kunden.get(6));        
                
        verfugbarkeit = new Verfugbarkeiten(Calendar.getInstance());
        verfugbarkeit.setFahrzeuge(listeFahrzeuge);
        verfugbarkeit.setKundentermine(listeTermine);
        verfugbarkeit.setMitarbeiter(listeMitarbeitende);
    }

    /**
     * Testet ob ein Fahrzeug zurückgeliefert wird
     */
    @Test
    public void testeLieferungFahrzeug() 
    {
        assertEquals(listeFahrzeuge.get(0),verfugbarkeit.getNachsteFahrzeug());
    }
    
    /**
     * Testet ob ein Mitarbeiter zurückgeliefert wird
     */
    @Test
    public void testeLieferungMitarbeiter() 
    {
        assertEquals(listeMitarbeitende.get(0),
                verfugbarkeit.getNachsteMitarbeiter());
    } 
    
    /**
     * Testet ob ein Mitarbeiter zurückgeliefert wird
     */
    @Test
    public void testeLieferungKundentermin() 
    {
        assertEquals(listeTermine.get(0),
                verfugbarkeit.getNachsteKundentermin());
    }  
    
    /**
     * Testet ob die korrekte Anzahl Elemente geliefert wird
     */
    @Test
    public void testeAnzahl()
    {
        assertEquals(listeFahrzeuge.size(),
                verfugbarkeit.getAnzahlFahrzeuge());
        assertEquals(listeMitarbeitende.size(),
                verfugbarkeit.getAnzahlMitarbeiter());
        assertEquals(listeTermine.size(),
                verfugbarkeit.getAnzahlKundentermine());
    }
    
    /**
     * Testet ob die FahrzeugVerfügbar-Methode richtig funktioniert
     */
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void testeFahrzeugVerfugbar()
    {
        int index = 0;
        while(index < verfugbarkeit.getAnzahlFahrzeuge())
        {
            assertTrue(verfugbarkeit.istFahrzeugVerfuegbar());
            verfugbarkeit.getNachsteFahrzeug();
            index++;
        }
        assertFalse(verfugbarkeit.istFahrzeugVerfuegbar());
        verfugbarkeit.getNachsteFahrzeug();
    }
    
    /**
     * Testet ob die MitarbeiterVerfügbar-Methode richtig funktioniert
     */
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void testeMitarbeiterVerfugbar()
    {
        int index = 0;
        while(index < verfugbarkeit.getAnzahlMitarbeiter())
        {
            assertTrue(verfugbarkeit.istMitarbeiterVerfuegbar());
            verfugbarkeit.getNachsteMitarbeiter();
            index++;
        }
        assertFalse(verfugbarkeit.istMitarbeiterVerfuegbar());
        verfugbarkeit.getNachsteMitarbeiter();
    }  
    
    /**
     * Testet ob die Kundentermine eines Kunden korrekt gelöscht werden
     */
    @Test
    public void testLoscheKundentermine()
    {        
        verfugbarkeit.loscheKundentermine(6);
        assertEquals(listeTermine.size()-2,
                verfugbarkeit.getAnzahlKundentermine());
        
    }
    
    /**
     * Testet ob das Set der Kunden korrekt aufgebaut wird
     */
    @Test
    public void testSetKunde()
    {
        assertEquals(7,verfugbarkeit.getKunden().size());
    }
    
    /**
     * Testet ob die Index-Methoden die richtigen Werte liefern
     */
    @Test
    public void testeIndexWerte()
    {
        assertEquals(0,verfugbarkeit.getIndexFahrzeuge());
        assertEquals(0,verfugbarkeit.getIndexKundentermine());
        assertEquals(0,verfugbarkeit.getIndexMitarbeiter());
        
        //setze die Indexe 1 höher
        verfugbarkeit.getNachsteFahrzeug();
        verfugbarkeit.getNachsteKundentermin();
        verfugbarkeit.getNachsteMitarbeiter();
        
        assertEquals(1,verfugbarkeit.getIndexFahrzeuge());
        assertEquals(1,verfugbarkeit.getIndexKundentermine());
        assertEquals(1,verfugbarkeit.getIndexMitarbeiter());
        
        //reste Indexe
        verfugbarkeit.resetIndexe();
        
        assertEquals(0,verfugbarkeit.getIndexFahrzeuge());
        assertEquals(0,verfugbarkeit.getIndexKundentermine());
        assertEquals(0,verfugbarkeit.getIndexMitarbeiter());
    }

}
