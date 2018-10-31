package ch.zhaw.ps16_gruppe08.cars;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class AbwesenheitMitarbeiterTest {
	
	private AbwesenheitMitarbeiter abwesenheit1;
	private Calendar datumVon;
	private Calendar datumBis;
	
	/** 
	 * Legt einen Mitarbeiter an, der für alle Testfälle 
	 * benutzt werden kann.
	 */
	@Before
	public void setUp() throws Exception 
	{
		datumVon = Calendar.getInstance();
		datumVon.clear();
		datumVon.set(2017,6,3);
		
		datumBis = Calendar.getInstance();
		datumBis.clear();
		datumBis.set(2017,6,10);
		
		abwesenheit1 = new AbwesenheitMitarbeiter(1,1,datumVon,datumBis);
	}

	/**
     * Testet die getter und setter Methoden
     */
    @Test
    public void testSetterUndGetterKundentermin() 
    {
    	abwesenheit1.setMitarbeiterId(19);
    	assertEquals(19, abwesenheit1.getMitarbeiterId());
    	
    	abwesenheit1.setAbwesenheitVon(datumVon);
    	assertEquals(datumVon, 
    			abwesenheit1.getAbwesenheitVon());
    	
    	abwesenheit1.setAbwesenheitBis(datumBis);
    	assertEquals(datumBis, 
    			abwesenheit1.getAbwesenheitBis());
    }
    
    /**
     * Testet ob das Datum korrekt formatiert wird
     */
    @Test
    public void testeFormatierungDatum()
    {   
        Calendar datum1 = Calendar.getInstance();
        Calendar datum2 = Calendar.getInstance();
        
        datum1.clear();
        datum2.clear();
        
        datum1.set(2017,3,11);
        datum2.set(2017,4,2);
        
        AbwesenheitMitarbeiter abwesenheit = 
                new AbwesenheitMitarbeiter(1,1,datum1, datum2);
        
        assertEquals("11.04.2017",
        		abwesenheit.getAbwesenheitVonFormatiert());
        assertEquals("02.05.2017",
        		abwesenheit.getAbwesenheitBisFormatiert());
    }
   
    
    /**
     * Testet die Sortierung der Termien
     */
    @Test
    public void testeSortierung()
    {
        Calendar datum1 = Calendar.getInstance();
        Calendar datum2 = Calendar.getInstance();
        
        datum1.clear();
        datum2.clear();
        
        datum1.set(2017, 2, 3);
        datum2.set(2017,3,4);
        
    	abwesenheit1.setAbwesenheitVon(datum1);

    	AbwesenheitMitarbeiter abwesenheit2 = 
                new AbwesenheitMitarbeiter(1,1,datum1,datum2);
    	
    	assertEquals(0,abwesenheit1.compareTo(abwesenheit2));
    	
    	abwesenheit2.setAbwesenheitVon(datum2);
    	assertEquals(-1, abwesenheit1.compareTo(abwesenheit2));
    	
    	abwesenheit1.setAbwesenheitVon(datum2);
    	abwesenheit2.setAbwesenheitVon(datum1);
    	
    	assertEquals(1,abwesenheit1.compareTo(abwesenheit2));
    }
}