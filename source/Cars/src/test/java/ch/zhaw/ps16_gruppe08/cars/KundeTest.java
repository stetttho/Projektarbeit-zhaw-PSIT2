package ch.zhaw.ps16_gruppe08.cars;
import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

/**
 * Testet die Klasse Kunde.
 * 
 */
public class KundeTest {
	
	private Kunde kunde1;
	
	/**
	 * Legt zu beginn der Tests einen Kunden an
	 */
	@Before
	public void setUp(){
	    kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "044 444 44 44");
	}
	
	/**
	 * Test, ob KundeID korrekt ausgegeben wird.
	 */
	@Test
	public void testGetKundeId() {
		assertEquals(124, kunde1.getKundeId() );
	}
	
	/**
	 * Test, ob Status korrekt ausgegeben wird.
	 */
	@Test
	public void testistStatusAktiv()
	{
		assertEquals(true, kunde1.istStatusAktiv() );
	}
	
	/**
	 * Test, ob Name korrekt ausgegeben wird.
	 */
	@Test
	public void testGetName()
	{
		assertEquals("Musterfirma", kunde1.getName() );
	}
		
	/**
	 * Test, ob Methode getKoordinaten die richtigen Werte liefert
	 */
	@Test
	public void testGetKoordinaten()
	{
	    assertEquals(360900, kunde1.getKoordinaten().x); 
	    assertEquals(360300, kunde1.getKoordinaten().y); 
	}
	
	/**
	 * Test, ob Prioritaet korrekt ausgegeben wird.
	 */
	@Test
	public void testGetPrioritaet()
	{
		assertEquals(2, kunde1.getPrioritaet() );
	}
	
	/**
	 * Test, ob E-Mail-Adresse korrekt ausgegeben wird.
	 */
	@Test
	public void testGetEmailAdresse()
	{
		assertEquals("info@musterfirma.ch", kunde1.getEmailAdresse() );
	}
	
	/**
	 * Test, ob Telefonnummer korrekt ausgegeben wird.
	 */
	@Test
	public void testGetTelefonNr()
	{
		assertEquals("044 444 44 44", kunde1.getTelefonNr() );
	}
	
    /**
     * Testet die Sortierung der Kunden
     */
    @Test
    public void testeVergleich()
    {
        Kunde kunde = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "+41 44 999 20 32");
        
        Kunde kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "+41 44 999 20 32");        
        
        Kunde kunde2 = new Kunde(124, 
                true, 
                "Mtusterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "+41 44 999 20 32");        
        
        Kunde kunde3 = new Kunde(124, 
                true, 
                "musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "+41 44 999 20 32");  
        
        Kunde kunde4 = new Kunde(124, 
                true, 
                "Musterfirmb", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "+41 44 999 20 32");         
                    
        assertEquals(0,kunde.compareTo(kunde1));
        assertEquals(1,kunde.compareTo(kunde2));
        assertEquals(0,kunde.compareTo(kunde3));   
        assertEquals(-1,kunde.compareTo(kunde4));
    }	
    
    /**
     * Testet eine gültige KundenId
     */
    @Test
    public void testeSetKundeIdGueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "044 444 44 44");
        assertEquals(124,kunde1.getKundeId());
    }
    
    /**
     * Testet eine ungültige KundenId
     */
    @Test(expected = IllegalArgumentException.class)
    public void testeKundeIdUngueltig()
    {
        kunde1 = new Kunde(-124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "044 444 44 44");
    }
    
    /**
     * Testet die Eingabe eines gültigen Status
     */
    @Test
    public void testeStatusGueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "044 444 44 44");
        assertTrue(kunde1.istStatusAktiv());
    }
    
    /**
     * Testet die Eingabe eines gültigen Namens
     */
    @Test
    public void testeNameGueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "044 444 44 44");
        assertEquals("Musterfirma", kunde1.getName());
    }
    
    /**
     * Testet die Eingabe eines ungültigen Namens
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNameUngueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "044 444 44 44");
        
    }
    
    /**
     * Testet die Eingabe gültiger Koordinaten
     */
    @Test
    public void testKoordinatenGueltig()
    {
        Point koordinaten = new Point(360900,360300);
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                koordinaten, 
                2, 
                "info@musterfirma.ch", 
                "044 444 44 44");
        assertEquals(koordinaten, kunde1.getKoordinaten());        
       
    }
    
    /**
     * Testet die Eingabe ungültiger Koordinaten
     */
    @Test(expected = IllegalArgumentException.class)
    public void testKoordinatenUngueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(-360900,-360300), 
                2, 
                "info@musterfirma.ch", 
                "044 444 44 44");
    }
    
    /**
     * Testet die Eingabe einer gültigen Priorität
     */
    @Test
    public void testPrioritaetGueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                3, 
                "info@musterfirma.ch", 
                "044 444 44 44");
        assertEquals(3, kunde1.getPrioritaet());
    }
    
    /**
     * Testet die Eingabe einer ungültigen Priorität
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPrioritaetUngueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                -1, 
                "info@musterfirma.ch", 
                "044 444 44 44");
    }
    
    /**
     * Testet die Eingabe einer gültigen E-Mail-Adresse
     */
    @Test
    public void testEmailAdresseGueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "test@test.ch", 
                "044 444 44 44"); 
        assertEquals("test@test.ch", kunde1.getEmailAdresse());
    }
    
    /**
     * Testet die Eingabe einer ungültigen E-Mail-Adresse
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmailAdresseUngueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "infomusterfirma.ch", 
                "044 444 44 44");
    }
    
    /**
     * Testet die Eingabe einer gültigen E-Mail-Adresse
     */
    @Test
    public void testTelefonNrGueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "044 444 44 44");
        assertEquals("044 444 44 44", kunde1.getTelefonNr());
    }
    
    /**
     * Testet die Eingabe einer ungültigen Telefon-Nr.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTelefonNrUngueltig()
    {
        kunde1 = new Kunde(124, 
                true, 
                "Musterfirma", 
                new Point(360900,360300), 
                2, 
                "info@musterfirma.ch", 
                "");
    }
    
}