package ch.zhaw.ps16_gruppe08.cars;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MitarbeiterTest {

	private Mitarbeiter mitarbeiter7;
	/**
	 * Legt einen Mitarbeiter an, der für alle Testfälle 
	 * benutzt werden kann.
	 */ 
	@Before
	public void setUp() throws Exception {
		mitarbeiter7 = new Mitarbeiter(7, 
		                               true, 
		                               "Meier", 
		                               "Hans", 
		                               "0445222222");
	}

	/**
	 * Test, ob MitarbeiterID korrekt ausgelesen wird.
	 */
	@Test
	public void testGetMitarbeiterId() {
		assertEquals(7, mitarbeiter7.getMitarbeiterId() );
	}
	
	/**
	 * Test, ob MitarbeiterID korrekt geschrieben wird.
	 */
	@Test
	public void testSetMitarbeiterId() {
	mitarbeiter7.setMitarbeiterId(55);
		assertEquals(55, mitarbeiter7.getMitarbeiterId());
	}
	
	/**
	 * Test, ob StatusAktiv korrekt ausgelesen wird.
	 */
	@Test
	public void testistStatusAktiv() {
		assertEquals(true, mitarbeiter7.istStatusAktiv());
	}
	
	/**
	 * Test, ob Name korrekt ausgelesen wird.
	 */
	@Test
	public void testGetName() {
		assertEquals("Meier", mitarbeiter7.getName());
	}
	
	/**
	 * Test, ob Vorname korrekt ausgelesen wird.
	 */
	@Test
	public void testGetVorname() {
		assertEquals("Hans", mitarbeiter7.getVorname());
	}
	
	/**
	 * Test, ob TelNr korrekt ausgelesen wird.
	 */
	@Test
	public void testGetTelNr() {
		assertEquals("0445222222", mitarbeiter7.getTelNr());
	}
	
	   /**
     * Testet die Sortierung der Mitarbeiter
     */
    @Test
    public void testeSortierung()
    {
        Mitarbeiter mitarbeiter = new Mitarbeiter(7, 
                true, 
                "Meier", 
                "Hans", 
                "0445222222");
        Mitarbeiter mitarbeiter2 = new Mitarbeiter(7, 
                true, 
                "Meier", 
                "Hans", 
                "0445222222");         
        Mitarbeiter mitarbeiter3 = new Mitarbeiter(7, 
                true, 
                "Meier", 
                "Gustav", 
                "0445222222");        
        
        Mitarbeiter mitarbeiter4 = new Mitarbeiter(7, 
                true, 
                "Meier", 
                "Fustav", 
                "0445222222");          
        
        Mitarbeiter mitarbeiter5 = new Mitarbeiter(7, 
                true, 
                "Neier", 
                "Fustav", 
                "0445222222");    
        Mitarbeiter mitarbeiter6 = new Mitarbeiter(7, 
                true, 
                "Leier", 
                "Fustav", 
                "0445222222");         
        
        Mitarbeiter mitarbeiter7 = new Mitarbeiter(7, 
                true, 
                "meier", 
                "Fustav", 
                "0445222222"); 
        
        assertEquals(0,mitarbeiter.compareTo(mitarbeiter2));
        assertEquals(1,mitarbeiter.compareTo(mitarbeiter3));
        assertEquals(2,mitarbeiter.compareTo(mitarbeiter4));   
        assertEquals(-1,mitarbeiter.compareTo(mitarbeiter5));
        assertEquals(1,mitarbeiter.compareTo(mitarbeiter6));
        assertEquals(2,mitarbeiter.compareTo(mitarbeiter7));             
        

    }

}
