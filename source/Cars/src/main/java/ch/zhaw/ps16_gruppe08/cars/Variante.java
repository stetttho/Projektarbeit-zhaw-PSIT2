package ch.zhaw.ps16_gruppe08.cars;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.awt.Point;
import java.time.LocalTime;

/** 
 * Enthält eine Variante für einen Wochenplan (mehrere Routen)
 * Dazu hält die Klasse Verfügbarkeiten-Objekte welche Mitarbeiter und Fahrzeuge
 * sowie die offenen Kundentermine enthält für ein jeweiliges Datum.
 * 
 * Im HashSet routen werden die Routen (Kombination aus 1 Mitarbeiter,
 * 1 Fahrzeug und mehreren zeitlich hintereinander liegenden Kundenterminen
 * für 1 Arbeitstag) der Variante gespeichert.
 * 
 * In der nichtVergebeneKundentermine-ArrayList werden die Kundentermine
 * festgehalten, welche nicht vergeben wurden, sodass diese in einer anderen 
 * Variante höher gewichtet werden können.
 * 
 * @version 1.1
 *
 */
public class Variante {
    private HashSet<Route> routen = new HashSet<>();
    private ArrayList<Verfugbarkeiten> verfuegbarkeiten;
    private HashMap<Kundentermin,Integer> nichtVergebeneKundentermine;
    private boolean statusDefinitiv;
    private int planId;
    private int varianteId;
    private static final int STANDARD_DAUER_FAHRTWEG = 30;         
    private static final int STANDARD_DAUER_TERMIN = 1;
    private static final LocalTime ARBEITSBEGINN = LocalTime.of(8, 0);
    private static final LocalTime ARBEITSENDE = LocalTime.of(17, 0);
    private static final int BASIS_X_KOORDINATE = 600;
    private static final int BASIS_Y_KOORDINATE = 600;    
    private Point aktuelleKoordinaten = new Point();


    public Variante(int planId, 
                    ArrayList<Verfugbarkeiten> verfuegbarkeiten,
                    HashMap<Kundentermin,Integer> nichtVergebeneKundentermine)
    {
        this.verfuegbarkeiten = verfuegbarkeiten;          
        this.nichtVergebeneKundentermine = nichtVergebeneKundentermine;
        statusDefinitiv = false;
        this.planId = planId;
        routeErstellen();           
        leereRoutenAussortieren();
    }
    
    /**
     * Diese Methode ist die Hauptsteuerung um die Routen zu erstellen, 
     * es wird pro Verfügbarkeiten-Objekt (welches genau einen Tag enthält,
     * eine Berechnung angestossen
     */
    private void routeErstellen() 
    {                   
        for(Verfugbarkeiten element : verfuegbarkeiten)
        {
           termineVergebenFurDatum(element);               
        }        
    }

    /**
     * Die Methode vergibt die Termine pro Verfügbarkeit, wobei
     * eine Verfügbarkeiten-Objekt für ein bestimmtes Datum steht
     * @param verfugbarkeiten
     */
    private void termineVergebenFurDatum(Verfugbarkeiten verfugbarkeiten) 
    {
        for(int count = 0;count < verfugbarkeiten.getAnzahlFahrzeuge(); count++)
           {
                if(verfugbarkeiten.istMitarbeiterVerfuegbar())
                {
                    Route route = 
                            new Route(verfugbarkeiten.getNachsteMitarbeiter(),
                                      verfugbarkeiten.getNachsteFahrzeug(),
                                      verfugbarkeiten.getDatum());
                    routen.add(route);
                    LocalTime aktuelleZeit = ARBEITSBEGINN;
                    aktuelleKoordinaten.setLocation(BASIS_X_KOORDINATE, 
                                                    BASIS_Y_KOORDINATE);
                                        
                    termineFurTagVergeben(verfugbarkeiten, route, aktuelleZeit);                  
                }
           }
    }

    /**
     * diese Methode vergibt die Termine für einen Tag, in dem sie
     * solange das Arbeitsende nicht erreicht ist, Termine vergibt
     * @param verfugbarkeiten
     * @param route
     * @param aktuelleZeit
     */
    private void termineFurTagVergeben(Verfugbarkeiten verfugbarkeiten, 
                                       Route route,
                                       LocalTime aktuelleZeit) 
    {
        while(aktuelleZeit.isBefore(ARBEITSENDE) 
                && verfugbarkeiten.getAnzahlKundentermine()>0)
        {
            Kundentermin effektiverTermin = this.terminErmitteln(
                    aktuelleZeit, 
                    verfugbarkeiten.getKundentermine(), 
                    aktuelleKoordinaten);
            
            if(effektiverTermin != null)
            {
                route.terminHinzufugen(effektiverTermin,
                        aktuelleZeit);
                aktuelleKoordinaten = effektiverTermin.getKunde()
                                        .getKoordinaten()
                                        .getLocation();
                loescheKundenTermine(
                        effektiverTermin.getKundeId());
                aktuelleZeit = aktuelleZeit
                        .plusMinutes(STANDARD_DAUER_FAHRTWEG);
            }
            aktuelleZeit = aktuelleZeit.plusHours(STANDARD_DAUER_TERMIN);

        }
    }
    
    /**
     * Sucht die Kundentermine welche zu einer gegebenen Uhrzeit 
     * einen Termin wünschen.
     * 
     * @param uhrzeit
     * @param kundentermine
     * @return ArrayList<Kundentermin>
     */
    private ArrayList<Kundentermin> getKundentermineNachZeit(LocalTime uhrzeit,
                                         ArrayList<Kundentermin> kundentermine)
    {
        ArrayList<Kundentermin> liste = new ArrayList<>();
        for(Kundentermin element : kundentermine)
        {
            if( (element.getStartzeit().isBefore(uhrzeit) 
                    || element.getStartzeit().equals(uhrzeit))
                    && element.getEndzeit().isAfter(uhrzeit)
                    ||element.getEndzeit().equals(uhrzeit))
            {
                liste.add(element);
            }
        }
        
        return liste;
    }
    
    /**
     * Selektiert die zum Standort X und Y nächsten fünf Kunden (bzw.
     * deren Termine.
     * 
     * @param kundentermine Liste der Termine die selektiert werden sollen
     * @param Punkt-Objekt welches die aktuellen Koordinaten enthält
     *        zu welchem die nächsten Termine ermittelt werden sollen
     * @param anzahlRetouren gibt an, wieviele Datensätze zurückgeliefert
     *                       werden sollen
     * @return ArrayList<Kundentermin>
     */
    protected ArrayList<Kundentermin> getNaechsteNachAnzahl(
            ArrayList<Kundentermin> kundentermine,
            Point aktuelleKoordinaten,
            int anzahlRetouren)
    {
        if(aktuelleKoordinaten.x < 0 || aktuelleKoordinaten.y < 0 
                || anzahlRetouren <= 0 || kundentermine == null)
        {
            throw new IllegalArgumentException("Die übergebenen Werte "+
                        "sind ungültig");
        }
        ArrayList<Kundentermin> liste = new ArrayList<>();
        Collections.sort(kundentermine, 
                new KundentermineComparator(aktuelleKoordinaten));
        Iterator<Kundentermin> it = kundentermine.iterator();
        int anzahl = 0;
        
        
        while(it.hasNext() && anzahl < anzahlRetouren)
        {
            liste.add(it.next());
            anzahl++;
        }
        return liste;
    } 
    
    /**
     * Löscht alle für den Kunden eröffneten Kundentermine aus den
     * Verfügbarkeitslisten, damit für einen Kunden nicht zwei Termine
     * vergeben werden eine nicht existierende KundenId führt zu 
     * keinem Fehler.
     * 
     * @param kundeId
     */
    protected void loescheKundenTermine(final int kundeId)
    {
        if(kundeId <= 0)
        {
            throw new IllegalArgumentException("Die KundenId muss "+
                                               "grösser 0 sein");
        }
        for(Verfugbarkeiten element : verfuegbarkeiten)
        {
            element.loscheKundentermine(kundeId);
        }        
    }
    
    /**
     * Ermittelt, welcher Termin als nächstes vergeben werden soll.
     * 
     * @param aktuelleZeit
     * @param verfuegbareTermine
     * @param Punkt-Objekt welches die aktuellen Koordinaten enthält
     *        zu welchem die nächsten verfügbaren Termine gesucht werden sollen
     * @param aktuelleKoordinateY
     * @return Kundentermin
     */
    private Kundentermin terminErmitteln(LocalTime aktuelleZeit,
                                ArrayList<Kundentermin> verfuegbareTermine,
                                Point aktuelleKoordinaten)
    {
        ArrayList<Kundentermin> relevanteTermine = 
                getNaechsteNachAnzahl(
                        getKundentermineNachZeit(aktuelleZeit,
                        verfuegbareTermine),
                        aktuelleKoordinaten,
                        5); 
        Kundentermin effektiverTermin = null;
        if(!relevanteTermine.isEmpty())
        {
            effektiverTermin = 
                    relevanteTermine.get(0);
            for(Kundentermin terminElement : relevanteTermine)
            {
                if(!nichtVergebeneKundentermine
                        .containsKey(terminElement))
                {
                    nichtVergebeneKundentermine.put(terminElement, 0);                                
                }
                nichtVergebeneKundentermine.put(terminElement, 
                    (nichtVergebeneKundentermine.get(terminElement)+1)
                    *terminElement.getKunde().getPrioritaet());
                if(nichtVergebeneKundentermine.get(effektiverTermin) < 
                   nichtVergebeneKundentermine.get(terminElement))
                {
                    effektiverTermin = terminElement;
                }
            }                           
        
            nichtVergebeneKundentermine.put(effektiverTermin, 
                 nichtVergebeneKundentermine.get(effektiverTermin)-1);         
        }
       
        return effektiverTermin;
    }
    
    /**
     * Gibt die Anzahl der Kunden, die keinen Termin
     * erhalten für diese Variante zurück.
     * 
     * @return anzahlVerpassterKundentermine
     */
    public int getAnzahlVerpassteKunde()
    {        
        return this.getVerpassteKunden().size();        
    }
    
    /**
     * Gibt eine Liste der verpassten Kunden für diese Variante zurück.
     * 
     * @return HashSet<Kunde>
     */
    public HashSet<Kunde> getVerpassteKunden()
    {
        HashSet<Kunde> kunden = new HashSet<>();
        for(Verfugbarkeiten element : verfuegbarkeiten)
        {
            kunden.addAll(element.getKunden());
        }
        
        return kunden;
    }
    
    /**
     * Entfernt Routen, die keine Termine haben.
     */
    private void leereRoutenAussortieren()
    {
        Iterator<Route> it = routen.iterator();
        while(it.hasNext())
        {
            if(it.next().getAnzahlTermine() == 0)
            {
                it.remove();
            }
        }
    }
    
    /**
     * Gibt die Routen für diese Varianten zurück.
     * 
     * @return routen
     */
    public HashSet<Route> getRouten()
    {
        return routen;
    }
        
    /**
     * Gibt den Status der Variante zurück.
     * 
     * @return statusDefinitiv
     */
    public boolean getStatusDefinitiv()
    {
        return statusDefinitiv;
    }
    
    /**
     * Mit dieser Methode kann der Status definitiv für die
     * Variante gesetzt werden (true = definitiv).
     * 
     * @param statusDefinitiv
     */
    public void setStatusDefinitiv(boolean statusDefinitiv)
    {
        this.statusDefinitiv = statusDefinitiv;
    }
    
    /**
     * Gibt die PlanId für diese Variante zurück.
     * 
     * @return planId
     */
    public int getPlanId()
    {
        return this.planId;
    }
    
    /**
     * Mit dieser Methode kann die DatenbankId für diese Variante 
     * gesetzt werden.
     */
    public void setVarianteId(int varianteId)
    {
        this.varianteId = varianteId;
    }
    
    /**
     * Mit dieser Methode kann die DatenbankId für diese Variante 
     * abgerufen werden.
     */
    public int getVarianteId()
    {
        return this.varianteId;
    }
}
