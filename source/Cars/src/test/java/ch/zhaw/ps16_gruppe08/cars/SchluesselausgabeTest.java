package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class SchluesselausgabeTest {
    Schluesselausgabe schluesselausgabe;
    private ArrayList<Mitarbeiter> mitarbeitende;
    private ArrayList<Fahrzeug> fahrzeuge;
    Calendar datum;

    
    /** 
     * Legt ein Objekt von Schluesselausgabe sowie Listen von Mitarbeitern 
     * und Fahrzeugen an, die für alle Testfälle benutzt werden können.
     */
    @Before
    public void setUp() throws Exception {
        generiereMitarbeiterListe();
        generiereFahrzeugListe();
        datum = Calendar.getInstance();
        datum.set(2017, 4, 10);
        schluesselausgabe = new Schluesselausgabe(datum);
        fuegeMitarbeiterUndFahrzeugeInSchluesselausgabeEin();
    }
    
    /**
     * Testet, ob Datum richtig gespeichert und zurückgegeben wird
     */
    @Test
    public void testeDatumSpeicherungUndAusgabe()
    {
        assertEquals(ZeitUtil.calendarZuString(datum),
                     schluesselausgabe.getDatumFormatiert());
        
    }
    
    /**
     * Prüfe hinzufügen eines neuen Eintrages von Mitarbeiter und Fahrzeug,
     * und ob der Eintrag korrekt zurückgegeben wird.
     */
    @Test
    public void testeAddEintrag()
    {
        Mitarbeiter mitarbeiter = new Mitarbeiter(
                        142,
                        true,
                        "Schmid",
                        "Albert",
                        "011 111 11 11");
        Fahrzeug fahrzeug = new Fahrzeug(342,true,"Opel Astra","GL 15 679");
        schluesselausgabe.addEintrag(mitarbeiter, fahrzeug);
       
       for(int index = 0; index < schluesselausgabe.getAnzahlEintraege() - 1; index++)
        {
            schluesselausgabe.moveNext();
        } 
        
        assertEquals(fahrzeug, schluesselausgabe.getFahrzeug());
        assertEquals(mitarbeiter, schluesselausgabe.getMitarbeiter());
    }
    
    
    /**
     * Teste ob die Exception geworfen wird bei ungültigen Indexwerten
     */
    @Test(expected=IllegalArgumentException.class)
    public void testeIndexSetzungZuHochFahrzeuge()
    {
        for(int index = 0; index < schluesselausgabe.getAnzahlEintraege() + 1; index++)
        {
            schluesselausgabe.moveNext();
        } 
        schluesselausgabe.getFahrzeug();
    }
    
    /**
     * Teste ob die Exception geworfen wird bei ungültigen Indexwerten
     */
    @Test(expected=IllegalArgumentException.class)
    public void testeIndexSetzungZuHochMitarbeiter()
    {
        for(int index = 0; index < schluesselausgabe.getAnzahlEintraege() + 1; index++)
        {
            schluesselausgabe.moveNext();
        } 
        schluesselausgabe.getMitarbeiter();
    }  
    
    
   
    private void generiereMitarbeiterListe() 
    {
        mitarbeitende = new ArrayList<>();
        mitarbeitende.add(
                new Mitarbeiter(1,
                        true,
                        "Kolumbus",
                        "Christopher",
                        "011 111 11 11"));
        mitarbeitende.add(
                new Mitarbeiter(2,
                                false,
                                "Polo", 
                                "Marco", 
                                "022 222 22 22"));   
        mitarbeitende.add(
                new Mitarbeiter(3,
                                true,
                                "Khan",
                                "Dschingis", 
                                "044 444 44 44"));        
    }
    
    private void generiereFahrzeugListe() 
    {
        fahrzeuge = new ArrayList<>();
        fahrzeuge.add(new Fahrzeug(0,true,"VW Passat","LU 115 679"));
        fahrzeuge.add(new Fahrzeug(1,true,"VW Passat","LU 203 584"));  
        fahrzeuge.add(new Fahrzeug(3,true,"Fiat Punto","ZH 105 125"));   
    }
    
    private void fuegeMitarbeiterUndFahrzeugeInSchluesselausgabeEin()
    {
        for(int index = 0; index < fahrzeuge.size(); index++)
        {
            schluesselausgabe.addEintrag(mitarbeitende.get(index), 
                                         fahrzeuge.get(index)); 
        }
    }
    
}
