package ch.zhaw.ps16_gruppe08.cars;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 * Fuehrt alle Tests durch.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ 
    // JUnit Tests
    BenutzerTest.class,
    FahrzeugTest.class,
    KundenterminTest.class,
    KundeTest.class,
    MitarbeiterTest.class,
    AbwesenheitMitarbeiterTest.class,
    TerminberechnungTest.class,
    VarianteTest.class,
    WochenplanTest.class,
    ZeitUtilTest.class,
    VerfugbarkeitenTest.class,
    PlanTest.class,
    SchluesselausgabeTest.class,
    
    // Integrationtests
    ITAbwesenheitMitarbeiter.class,
    ITFahrzeug.class,
    ITKundentermin.class,
    ITKunde.class,
    ITMitarbeiter.class,
    ITTerminberechnung.class,
    ITWochenplan.class
    
})

public class AlleTests {

}
