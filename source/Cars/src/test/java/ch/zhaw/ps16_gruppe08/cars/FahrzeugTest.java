package ch.zhaw.ps16_gruppe08.cars;
import static org.junit.Assert.*;

import org.junit.Test;

public class FahrzeugTest {
	
	
	/** 
	 * Testet, ob die Konstruktoren die Werte korrekt ablegen.
	 */
	@Test
	public void testeKonstruktoren() 
	{
		Fahrzeug fahrzeug = new Fahrzeug(5, true, "VW Golf", "LU 115 679");
		assertEquals(fahrzeug.getFahrzeugId(),5);
		assertEquals(fahrzeug.istStatusAktiv(),true);
		assertEquals(fahrzeug.getTyp(),"VW Golf");
		assertEquals(fahrzeug.getKontrollschildNr(),"LU 115 679");
		
	}
	
	/**
	 * Testet, ob die Set-Methoden die Daten korrekt setzen
	 */
	@Test
	public void testeSetMethoden()
	{
		Fahrzeug fahrzeug = new Fahrzeug(1,true, "WV Golf", "LU115 679");
		fahrzeug.setFahrzeugId(5);
		assertEquals(fahrzeug.getFahrzeugId(),5);
	}
	
	/**
	 * Testet die Sortierung der Fahrzeuge
	 */
	@Test
	public void testeSortierung()
	{
	    Fahrzeug fahrzeug = new Fahrzeug(5, true, "VW Golf", "LU 115 679");
	    Fahrzeug fahrzeug2 = new Fahrzeug(5, true, "VW Golf", "LU 115 679");
	    Fahrzeug fahrzeug3 = new Fahrzeug(5, true, "VV Golf", "LU 115 679");
        Fahrzeug fahrzeug4 = new Fahrzeug(5, true, "vW Golf", "LU 115 679");
        Fahrzeug fahrzeug5 = new Fahrzeug(5, true, "VW Golg", "LU 115 679");        
	    
	    assertEquals(0,fahrzeug.compareTo(fahrzeug2));
	    assertEquals(1,fahrzeug.compareTo(fahrzeug3));
        assertEquals(0,fahrzeug.compareTo(fahrzeug4));	 
        assertEquals(-1,fahrzeug.compareTo(fahrzeug5));
	}

}
