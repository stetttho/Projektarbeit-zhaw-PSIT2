package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
 
public class BenutzerTest {
    private Benutzer benutzer;
    
    @Before
    public void setUp() throws Exception {
        benutzer = new Benutzer();
    }

    @Test
    public void testGetName() {
        benutzer.setName("admin");
        assertEquals("admin",benutzer.getName());
    }

    @Test
    public void testGetPasswort() {
        benutzer.setPasswort("admin");
        assertEquals("admin",benutzer.getPasswort());
    }

    @Test
    public void testGetSchreibrechte() {
        benutzer.setName("admin");
        benutzer.setPasswort("admin");
        benutzer.setSchreibrechte();
        assertTrue(benutzer.getSchreibrechte());
        
        benutzer.setName("leser");
        benutzer.setPasswort("leser");
        benutzer.setSchreibrechte();
        assertFalse(benutzer.getSchreibrechte());
    }

    @Test
    public void testGetAnzahlVersuche() {
        benutzer.setAnzahlVersuche(10);
        assertEquals(10,benutzer.getAnzahlVersuche());
    }

    @Test
    public void testUeberpruefeBenutzer() {
        benutzer.setName("admin");
        assertTrue(benutzer.ueberpruefeBenutzer());
        
        benutzer.setName("leser");
        assertTrue(benutzer.ueberpruefeBenutzer());
        
        benutzer.setName("falscherBenutzer");
        assertFalse(benutzer.ueberpruefeBenutzer());
    }

    @Test
    public void testUeberpruefePasswort() {
        benutzer.setName("admin");
        benutzer.setPasswort("admin");
        assertTrue(benutzer.ueberpruefePasswort());
        
        benutzer.setName("leser");
        benutzer.setPasswort("leser");
        assertTrue(benutzer.ueberpruefePasswort());
        
        benutzer.setName("admin");
        benutzer.setPasswort("falschesPasswort");
        assertFalse(benutzer.ueberpruefePasswort());
        
        benutzer.setName("leser");
        benutzer.setPasswort("falschesPasswort");
        assertFalse(benutzer.ueberpruefePasswort());
    }

}
