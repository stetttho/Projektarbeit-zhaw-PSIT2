package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.assertEquals;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 * Dies testet die Klasse Wochenplan. Methoden welche die Datenbank
 * benötigen werden in den Integrationstests getestet.
 * 
 */

public class WochenplanTest {
    Wochenplan wochenplan;
    Calendar datum;
    
    /**
     * Erstellt einen einfachen Wochenplan mit 2 Arbeitstagen mit jeweils
     * 4 Routen. 
     */
    @Before
    public void setUp() throws Exception 
    {
        wochenplan = new Wochenplan();
        datum = Calendar.getInstance();
        datum.clear();
        datum.set(2017, 3, 10);
        
        for(int count = 1; count < 5; count++)
        {
                wochenplan.addRoute(new Route(
                                new Mitarbeiter(count, true, "Hans", "Vader",
                                                "079 000 00 00"),
                                new Fahrzeug(count, true, "Fahrzeug", "KfZ")
                                ,datum
                                ));
        }
        datum.clear();
        datum.set(2017, 3, 12);
        for(int count = 1; count < 5; count++)
        {
                wochenplan.addRoute(new Route(
                                new Mitarbeiter(count, true, "Hans", "Vader",
                                                "079 000 00 00"),
                                new Fahrzeug(count, true, "Fahrzeug", "KfZ")
                                ,datum
                                ));
        }
        
    }
    
    /**
     * Testet, ob bei der Erstellung eines leeren Wochenplan-Objektes ohne 
     * Hinzufügen einer Route die Anzahl Routen null ist.
     */
    @Test
    public void testeRoutenAnzahlIstNullBeiNeuemWochenplanobjekt()
    {
        Wochenplan wochenplan1 = new Wochenplan();
        assertEquals(0, wochenplan1.getRoutenAnzahl());
    }
    
    /**
     * Testet ob eine Route korrekt zum Wochenplan hinzugefügt wird.
     * 
     */
    @Test
    public void testeAddRoute()
    {
    	Wochenplan wochenplan1 = new Wochenplan();
    	Calendar datum1 = Calendar.getInstance();
        datum1.clear();
        datum1.set(2017, 3, 10);
    	
    	for(int count = 1; count < 5; count++)
    	{
    		wochenplan1.addRoute(new Route(
    				new Mitarbeiter(count, true, "Hans", "Vader",
    						"079 000 00 00"),
    				new Fahrzeug(count, true, "Fahrzeug", "KfZ")
    				,datum1
    				));
    	}
    	
    	assertEquals(4, wochenplan1.getRoutenAnzahl());	
    }    
    
    /**
     * Testet, ob die richtige Anzahl Routen zurückgegeben werden
     */
    @Test
    public void testeKorrekteAnzahlRouten()
    {
        assertEquals(8,wochenplan.getRoutenAnzahl());
    }
    
    /**
     * Testet korrekte Rückgabe des Mitarbeiternamens
     */
    @Test
    public void testeMitarbeiterName()
    {
        wochenplan.setIndex(0);
        assertEquals("Vader Hans", 
                     wochenplan.getMitarbeiterName());
    }

    /**
     * Testet die korrekte Rückgabe des 
     * Fahrzeugs anhand des Kontrollschilds
     */
    @Test
    public void testeGetFahrzeugKontrollSchild()
    {
    	wochenplan.setIndex(0);
    	assertEquals("KfZ", wochenplan.getFahrzeugKontrollSchild());
    }
    
    /**
     * Testet ob die setIndex()-Methode funktioniert
     */
    @Test
    public void testeSetIndex()
    {
    	wochenplan.setIndex(1);
    	assertEquals("10.04.2017", wochenplan.getDatumFormatiert());
    }
    
    /**
     * Testet, ob die resetIndex()-Methode den Index auf 0 setzt.
     */
    @Test
    public void testeResetIndex()
    {
    	wochenplan.setIndex(1);
    	wochenplan.resetIndex();
        assertEquals("10.04.2017",wochenplan.getDatumFormatiert());
    }
    
    /**
     * Teste ob die Exception geworfen wird bei ungültigen Indexwerten
     */
    @Test(expected=IllegalArgumentException.class)
    public void testeIndexSetzungZuHoch()
    {
        wochenplan.setIndex(99);
    }
    
    /**
     * Teste ob die Exception geworfen wird bei ungültigen Indexwerten
     */
    @Test(expected=IllegalArgumentException.class)
    public void testeIndexSetzungNegativWerte()
    {
        wochenplan.setIndex(-1);
    }  
    
    /**
     * Testet ob der korrekte Wochentag zurückgegeben wird.
     */
    @Test
    public void testeGetWochentag()
    {
    	wochenplan.setIndex(1);
    	assertEquals("Montag", wochenplan.getWochentag());
    }
    
    /**
     * Testet ob das Datum korrekt Formatiert zurückgegeben wird
     */
    @Test
    public void testeGetDatumFormatiert()
    {
    	wochenplan.setIndex(1);
    	assertEquals("10.04.2017", wochenplan.getDatumFormatiert());	
    } 

}
