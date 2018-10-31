package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class ZeitUtilTest {

    @Before
    public void setUp() throws Exception 
    {
        
    }

    /** 
     * Testet die Umwandlung eines gültigen Datums
     */
    @Test
    public void testeDatumUmwandlung() 
    {
        Calendar datumVergleich = Calendar.getInstance();
        datumVergleich.clear();
        datumVergleich.set(2017, 2, 2);
        Calendar datum = ZeitUtil.stringZuCalendar("02.03.2017");
        assertEquals(datumVergleich,datum);
    }
    
    /**
     * Testet ob bei einem ungültigen Datum eine Exception geworfen wird
     */
    @Test(expected=IllegalArgumentException.class)
    public void testeDatumUmwandlungUngültig()
    {
        ZeitUtil.stringZuCalendar("2.5");
    }
    
    /**
     * Testet die Funktion zur Umwandldung von Calendar nach java.sql.Date
     */
    @Test
    public void testeDatumUmwandlungSql()
    {
        Calendar datumVergleich = Calendar.getInstance();
        datumVergleich.clear();
        datumVergleich.set(2017, 2, 15);
        java.sql.Date datumUmgewandelt = java.sql.Date.valueOf("2017-03-15");
        java.sql.Date datum = ZeitUtil.calendarZuSql(datumVergleich);
        assertTrue(datumUmgewandelt.equals(datum));
    }
    
    /**
     * Testet ob bei einer ungultigen Zeit von der zuständigen Methode
     * eine Exception geworfen wird
     */
    @Test(expected=IllegalArgumentException.class)
    public void testeUngultigeZeit()
    {
        ZeitUtil.stringZuLocalTime("05");
    }
    
    /**
     * Testet ob bei einer gultigen Zeit von der zuständigen Methode
     * die korrekte Zeit geliefert
     */
    @Test
    public void testeGultigeZeit()
    {
        LocalTime zeit = ZeitUtil.stringZuLocalTime("05:00");
        assertEquals(LocalTime.of(5, 0), zeit);
    }  
    
    /**
     * Testet ob die Umwandlung eines SQL-Datums korrekt funktioniert
     */
    @Test
    public void testeUmgwandlungSqlNachCalendar()
    {
        Calendar datumVergleich = Calendar.getInstance();
        datumVergleich.clear();
        datumVergleich.set(2017, 2, 15);
        java.sql.Date datum = java.sql.Date.valueOf("2017-03-15");
        Calendar datumUmgewandelt = ZeitUtil.sqlDateZuCalendar(datum);
        assertTrue(datumUmgewandelt.equals(datumVergleich));        
    }
    
    /**
     * Testet ob die Umwandlung von Calendar nach String korrekt
     * funktioniert
     */
    @Test
    public void testeUmwandlungCalendarNachString()
    {
        Calendar datum = Calendar.getInstance();
        datum.clear();
        datum.set(2017, 4, 6);
        assertEquals("06.05.2017",ZeitUtil.calendarZuString(datum));
    }
    
}
