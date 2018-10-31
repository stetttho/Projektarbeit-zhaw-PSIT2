package ch.zhaw.ps16_gruppe08.cars;
import static org.junit.Assert.*;

import java.awt.Point;
import java.time.LocalTime;
import java.util.Calendar;

import org.junit.Test;
import org.junit.Before;

public class KundenterminTest {

    private Kundentermin kundentermin;
    private Kunde kunde;
    
    /** 
     * Testet die setPersonId-Methode
     * Diese Methode wird nach der Anbindung an die Datenbank noch geändert.
     */
    @Before
    public void setUp() throws Exception
    {
        kunde = new Kunde(1,
                          true,
                          "Musterkunde",
                          new Point(100,100),
                          2,
                          "info@example.com",
                          "078 627 15 23");
        LocalTime zeit = LocalTime.of(8, 12);
        Calendar datum = Calendar.getInstance();
        kundentermin = new Kundentermin(1,1,zeit,zeit,datum);
    }
    
    /**
     * Testet die getter und setter Methoden
     */
    @Test
    public void testSetterUndGetterKundentermin() {
        kundentermin.setKunde(kunde);
        assertEquals(kunde,kundentermin.getKunde());
        
        Calendar datum = Calendar.getInstance();
        kundentermin.setDatum(datum);
        assertEquals(datum,kundentermin.getDatum());
        
        LocalTime zeit = LocalTime.of(8, 12);       
        kundentermin.setStartzeit(zeit);
        assertEquals(zeit,kundentermin.getStartzeit());
            
        kundentermin.setEndzeit(zeit);
        assertEquals(zeit,kundentermin.getEndzeit());              
        
    }
    
    /**
     * Testet ob die Kundenangaben richtig ausgegeben werden
     */
    @Test
    public void testeKundenAngaben()
    {
        kundentermin.setKunde(kunde);
        assertEquals(kunde.getKundeId(),kundentermin.getKundeId());
        assertEquals(kunde.getName(),kundentermin.getKundeName());
        assertEquals(kunde.getPrioritaet(),kundentermin.getPrioritaet());
    }
    
    /**
     * Testet ob das Datum korrekt formatiert wird
     */
    @Test
    public void testeFormatierungDatum()
    {
        Calendar datum = Calendar.getInstance();
        datum.set(2017, 3, 20);
        kundentermin.setDatum(datum);       
        assertEquals("20.04.2017",kundentermin.getDatumFormatiert());
    }
    
    /**
     * Testet ob die "KundeIstGesetzt" Methode richtige Informationen liefert
     */
    @Test
    public void testeKundeIstGesetzt()
    {
        assertEquals(true,kundentermin.kundeIstGesetzt());
        
        kundentermin.setKunde(kunde);
        assertEquals(true,kundentermin.kundeIstGesetzt());
    }
    
    /**
     * Testet den Vergleich der Termine
     */
    @Test
    public void testeVergleich()
    {
        Calendar datum = Calendar.getInstance();
        Calendar datum2 = Calendar.getInstance();
        
        datum.clear();
        datum2.clear();
        
        datum.set(2017,1,1);
        datum2.set(2017,12,31);
        
        LocalTime zeit = LocalTime.of(8, 12);
        Kundentermin kundentermin2 = new Kundentermin(1,1,zeit,zeit,datum2);
        
        kundentermin.setDatum(datum);
        kundentermin2.setDatum(datum);
              
        assertEquals(0,kundentermin.compareTo(kundentermin2));
        
        kundentermin2.setDatum(datum2);
        assertEquals(-1,kundentermin.compareTo(kundentermin2));
        
        kundentermin.setDatum(datum2);
        kundentermin2.setDatum(datum);
        assertEquals(1,kundentermin.compareTo(kundentermin2));
    }    
}
