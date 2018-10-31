package ch.zhaw.ps16_gruppe08.cars;
 
import static org.junit.Assert.*;

import java.awt.Point;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

public class VarianteTest {
    private Calendar datum;
    private ArrayList<Kundentermin> listeTermine;
    private ArrayList<Fahrzeug> listeFahrzeuge = new ArrayList<>();
    private ArrayList<Mitarbeiter> mitarbeitende = new ArrayList<>();
    private HashMap<Kundentermin,Integer> statistikNichtVergebeneKundentermine;
    private ArrayList<Verfugbarkeiten> verfugbarkeiten = new ArrayList<>();
    private ArrayList<Kunde> kunden = new ArrayList<>();
    

   @Before
   public void setUp() throws Exception 
   {
       datum = Calendar.getInstance();
       datum.set(2017, 3, 13);
       
       mitarbeitende.add(
               new Mitarbeiter(0,
                               true,
                               "Cook", 
                               "James", 
                               "033 333 33 33"));
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
                        
       listeFahrzeuge.add(new Fahrzeug(0,true,"VW Passat","LU 115 679"));
       listeFahrzeuge.add(new Fahrzeug(1,true,"VW Passat","LU 203 584"));
       listeFahrzeuge.add(new Fahrzeug(2,false,"VW Golf","ZH 105 125"));   
       listeFahrzeuge.add(new Fahrzeug(3,true,"Fiat Punto","ZH 105 125"));   
                
       kunden.add(new Kunde(7,
               true,
               "Axa",
               new Point(100,100),
               1,
               "axa@beispiel.ch",
               "+41 44 456 32 21"));
       
       kunden.add(new Kunde(1,
               true,
               "ZKB",
               new Point(900,900),
               3,
               "zkb@beispiel.ch",
               "+41 44 646 87 77"));
        
       kunden.add(new Kunde(2,
               false,
               "Bank Hauser",
               new Point(200,250),
               2,
               "hauser-bank@beispiel.ch",
               "+41 44 456 32 21"));   
       
       kunden.add(new Kunde(3,
               true,
               "Spengler Kurt",
               new Point(300,250),
               2,
               "kurt-spengler@beispiel.ch",
               "+41 46 960 00 01"));  
        
       kunden.add(new Kunde(4,
              true,
              "Bank Weber",
              new Point(450,300),
              2,
              "kurt-spengler@beispiel.ch",
              "+41 46 960 00 01"));
       
       kunden.add(new Kunde(5,
              true,
              "Meier Hans",
              new Point(900,900),
              2,
              "kurt-spengler@beispiel.ch",
              "+41 46 960 00 01")); 
        
       kunden.add(new Kunde(6,
              true,
              "Kimmer Tim",
              new Point(200,200),
              2,
              "kurt-spengler@beispiel.ch",
              "+41 46 960 00 01"));         
       
       listeTermine = new ArrayList<>();
       listeTermine.add(
               new Kundentermin(
                       1, 
                       0, 
                       LocalTime.of(8, 0), 
                       LocalTime.of(9, 0), 
                       datum
                       ));
       listeTermine.get(0).setKunde(kunden.get(0));
       
       listeTermine.add(
               new Kundentermin(
                       2,
                       1,
                       LocalTime.of(9, 0),
                       LocalTime.of(10, 0),
                       datum
                       )); 
       listeTermine.get(1).setKunde(kunden.get(1));
       
       listeTermine.add(
               new Kundentermin(
                       3,
                       2,
                       LocalTime.of(9, 0),
                       LocalTime.of(10, 0),
                       datum
                       ));
       listeTermine.get(2).setKunde(kunden.get(2));

       listeTermine.add(
               new Kundentermin(
                       4,
                       4,
                       LocalTime.of(11, 0),
                       LocalTime.of(12, 0),
                       datum
                       ));
       listeTermine.get(3).setKunde(kunden.get(4));

       listeTermine.add(
               new Kundentermin(
                       5,
                       3,
                       LocalTime.of(11, 0),
                       LocalTime.of(12, 0),
                       datum
                       ));
       listeTermine.get(4).setKunde(kunden.get(3));        

       listeTermine.add(
               new Kundentermin(
                       6,
                       6,
                       LocalTime.of(11, 0),
                       LocalTime.of(12, 0),
                       datum
                       ));
       listeTermine.get(5).setKunde(kunden.get(6));
         
       this.statistikNichtVergebeneKundentermine = new HashMap<>();
        
       verfugbarkeiten.add(new Verfugbarkeiten(datum));
       for(Verfugbarkeiten element : verfugbarkeiten)
       {
           element.setFahrzeuge(listeFahrzeuge);
           element.setKundentermine(listeTermine);
           element.setMitarbeiter(mitarbeitende);           
       }
   }
    
   /**
    * Testet, ob die Datensätze korrekt sortiert werden, dafür
    * werden keine Fahrzeuge oder Mitarbeiter benötigt, deshalb
    * werden diese Listen geleert
    */
   @Test
   public void testGetNaechsteNachAnzahlGueltig() 
   {                        
       mitarbeitende.clear();                
       listeFahrzeuge.clear();   
       
       Variante variante = new Variante(1,verfugbarkeiten,
                               statistikNichtVergebeneKundentermine);
        
       ArrayList<Kundentermin> sortiert = new ArrayList<>();
       sortiert = variante.getNaechsteNachAnzahl(listeTermine,new Point(0,0),5);
        
       assertEquals("Axa",sortiert.get(0).getKundeName());
       assertEquals("Kimmer Tim",sortiert.get(1).getKundeName());
       assertEquals("Bank Hauser", sortiert.get(2).getKundeName());
       assertEquals("Spengler Kurt", sortiert.get(3).getKundeName());
       assertEquals("Bank Weber", sortiert.get(4).getKundeName());
       assertEquals(5,sortiert.size());                
   }
    
   /**
    * Testet die Methode getNaechsteNachAnzahl, ob bei einem negativen
    * StandortX eine Exception geworfen wird
    */
   @Test(expected=IllegalArgumentException.class)
   public void testGetNaechsteNachAnzahlUngueltigStandortXNegativ()
   {
       Variante variante = new Variante(1,verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       variante.getNaechsteNachAnzahl(listeTermine, new Point(-1, 0), 1);
   }
    
   /**
    * Testet die Methode getNaechsteNachAnzahl, ob bei einem negativen
    * StandortY eine Exception geworfen wird
    */
   @Test(expected=IllegalArgumentException.class)
   public void testGetNaechsteNachAnzahlUngueltigStandortYNegativ()
   {
       Variante variante = new Variante(1,verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       variante.getNaechsteNachAnzahl(listeTermine, new Point(0, -1), 1);
   }   
   
   /**
    * Testet die Methode getNaechsteNachAnzahl, ob bei einem
    * AnzahlRetoure==0 eine Exception wirft
    */
   @Test(expected=IllegalArgumentException.class)
   public void testGetNaechsteNachAnzahlUngueltigAnzahlRetoureNull()
   {
       Variante variante = new Variante(1,verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       variante.getNaechsteNachAnzahl(listeTermine, new Point(0, 0), 0);
   }   
   
   /**
    * Testet die Methode getNaechsteNachAnzahl, ob bei einem
    * AnzahlRetoure negativ eine Exception wirft
    */
   @Test(expected=IllegalArgumentException.class)
   public void testGetNaechsteNachAnzahlUngueltigAnzahlRetoureNegativ()
   {
       Variante variante = new Variante(1,verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       variante.getNaechsteNachAnzahl(listeTermine, new Point(0, 0), -1);
   }   
    
   /**
    * Testet die Methode getNaechsteNachAnzahl, ob bei einer
    * leeren Liste eine Exception wirft
    */
   @Test(expected=IllegalArgumentException.class)
   public void testGetNaechsteNachAnzahlUngueltigListeNull()
   {
       Variante variante = new Variante(1,verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       listeTermine.clear();
       variante.getNaechsteNachAnzahl(null, new Point(0, 0), 1);
   }      
    
   /**
    * Testet die Methode loescheKundenTermine. Beim Löschen des Termins für
    * einen Kunden wird erwartet, dass die Anzahl Termine danach um 1 kleiner
    * ist als vorher.
    */
   @Test
   public void testLoescheKundenTermineGueltig()
   {        
       Variante variante = new Variante(7,
               verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       int anzahltermine = 0;
       
       for(Verfugbarkeiten element : verfugbarkeiten)
       {
           element.resetIndexe();
           element.setKundentermine(listeTermine);
           anzahltermine =+ element.getAnzahlKundentermine();
       } 
       variante.loescheKundenTermine(1);
       int anzahltermine_ohne1 = 0;
       for(Verfugbarkeiten element : verfugbarkeiten)
       {
           anzahltermine_ohne1 =+ element.getAnzahlKundentermine();
       }        
       assertEquals(1, anzahltermine-anzahltermine_ohne1);
       
   }
    
   /**
    * Testet die Methode loescheKundenTermine. Die Id des zu löschenden
    * Kunden muss grösser 0 sein
    */
   @Test(expected=IllegalArgumentException.class)
   public void testLoescheKundenTermineUngueltigMitNull()
   {
       Variante variante = new Variante(7,
               verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       variante.loescheKundenTermine(0);        
       
   } 
    
   /**
    * Testet die Methode loescheKundenTermine. Die Id des zu löschenden
    * Kunden muss grösser 0 sein
    */
   @Test(expected=IllegalArgumentException.class)
   public void testLoescheKundenTermineUngueltigMitIdNegativ()
   {
       Variante variante = new Variante(7,
               verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       variante.loescheKundenTermine(-1);             
   }     
    
    /**
     * Teste, ob die Anzahl der verpassten Kunden in diesem Beispiel = 0 ist
     */ 
   @Test
   public void testGetAnzahlVerpassteKundenNull()
   {
       Variante variante = new Variante(1,
                                        verfugbarkeiten,
                                        statistikNichtVergebeneKundentermine);
       assertEquals(0,variante.getAnzahlVerpassteKunde());
   }
   
   /**
    * Teste, ob die Anzahl der verpassten Kunden in diesem Beispiel = 6 ist
    * (6 Kunden die mit diesen Testdaten und ohne Mitarbeitende oder Fahrzeuge
    * verpasst werden
    */ 
  @Test
  public void testGetAnzahlVerpassteKunden()
  {
      for(Verfugbarkeiten element : verfugbarkeiten)
      {
          mitarbeitende.clear();
          element.setMitarbeiter(mitarbeitende);
          listeFahrzeuge.clear();
          element.setFahrzeuge(listeFahrzeuge);
      }
      Variante variante = new Variante(1,
                                       verfugbarkeiten,
                                       statistikNichtVergebeneKundentermine);
      assertEquals(6,variante.getAnzahlVerpassteKunde());
  }   
   
   /**
    * Teste, ob die richtigen Kunden als verpasste Kunden zurückgegeben werden
    */
   
   @Test
   public void testGetVerpassteKunden()
   {
       for(Verfugbarkeiten element : verfugbarkeiten)
       {
           mitarbeitende.clear();
           element.setMitarbeiter(mitarbeitende);
           listeFahrzeuge.clear();
           element.setFahrzeuge(listeFahrzeuge);
       }
       Variante variante = new Variante(1,
                                        verfugbarkeiten,
                                        statistikNichtVergebeneKundentermine);
       //Kunde 5 hat keinen Termin und kann deshalb auch hier nicht auftauchen
       kunden.remove(5);
       //prüft, ob alle verpassten Kunden in den Kunden enthalten sind
       assertTrue(kunden.containsAll(variante.getVerpassteKunden()));
      
       //prüft, ob alle Kunden in den verpassten Kunden enthalten sind
       assertTrue(variante.getVerpassteKunden().containsAll(kunden));
   } 
  
  
   /**
    * Testet die Getter und Setter Methode für den definitiven Status
    * einer Variante (Wert True)
    */
   @Test
   public void testStatusDefinitivTrue()
   {
       Variante variante7 = new Variante(7,
               verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       
       variante7.setStatusDefinitiv(true);
       assertTrue(variante7.getStatusDefinitiv());
   }
  
   /**
    * Testet die Getter und Setter Methode für den definitiven Status
    * einer Variante (Wert False)
    */
   @Test
   public void testStatusDefinitivFalse()
   {
       Variante variante7 = new Variante(7,
               verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
      
       variante7.setStatusDefinitiv(false);
       assertFalse(variante7.getStatusDefinitiv());
   }
  
   /**
    * Testet, ob die PlanId beim Erstellen einer Variante richtig gespeichert 
    * und mit der Methode getPlanId richtig zurückgegeben wird.
    */
   @Test
   public void testGetPlanId()
   {
       Variante variante = new Variante(7,
               verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       assertEquals(7, variante.getPlanId());
      
   }
  
   /**
    * Testet die Getter und Setter Methode für die VarianteId.
    */
   @Test
   public void testVarianteId()
   {
       Variante variante = new Variante(7,
               verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       variante.setVarianteId(77);
       assertEquals(77, variante.getVarianteId());
   }  
      
   /**
    * Testet die getRouten Methode
    */
   @Test
   public void testGetRouten()
   {
       Variante variante = new Variante(1,
               verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       assertEquals(3, variante.getRouten().size());
   }
   
   /*
    * Nachfolgender Test bezieht sich auf die Funktion der Klasse
    */
   
   /**
    * Teste, ob eine Variante keine leeren Routen enthält
    */
   @Test
   public void testeObLeereRoutenVorhanden()
   {
       Variante variante = new Variante(1,
               verfugbarkeiten,
               statistikNichtVergebeneKundentermine);
       for(Route element : variante.getRouten())
       {
           assertTrue(element.getAnzahlTermine() > 0);
       }
   }   
}
