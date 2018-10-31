package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class PlanTest {

    @Before
    public void setUp() throws Exception {
    }

    /** 
     * Testet ob die Datum korrekt formatiert zurückgegeben werden
     */
    @Test
    public void testeDatumFormatierung() 
    {
        Calendar datumVon = Calendar.getInstance();
        datumVon.clear();
        datumVon.set(2017, 4, 20);
        
        Calendar datumBis = Calendar.getInstance();
        datumBis.clear();
        datumBis.set(2017, 1, 20);
        
        Plan plan = new Plan(1,datumVon,datumBis);
        
        assertEquals("20.05.2017",plan.getVonDatumFormatiert());
        assertEquals("20.02.2017",plan.getBisDatumFormatiert());
        
    }

}
