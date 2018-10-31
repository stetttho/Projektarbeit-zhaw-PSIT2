package ch.zhaw.ps16_gruppe08.cars;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
/** 
 * Diese Klasse hält die Informationen bereit, welche Fahrzeuge, Mitarbeiter
 * und Kundentermine zu einem bestimmten Datum verfügbar sind.
 * 
 * Die Informationen können über die entsprechenden Methoden abgefragt werden,
 * die Klasse bietet auch an Elemente einzeln abzufragen, dazu führt sie
 * einen internen Index, bei jeder Abfragung wird dieser erhöht, mittels
 * resetIndex kann dieser wieder zurückgestellt werden.
 * 
 * Die Klasse lädt beim erstellen eines Objektes automatisch die
 * Verfügbarkeiten für das angegebene Datum, optional können die Listen
 * auch via den zur verfügungstehenden Methoden übergeben werden.
 *
 */
public class Verfugbarkeiten {
    
    private ArrayList<Mitarbeiter> mitarbeiter = new ArrayList<>();
    private ArrayList<Fahrzeug> fahrzeuge = new ArrayList<>();
    private ArrayList<Kundentermin> kundentermine = new ArrayList<>();
    private ArrayList<Kundentermin> kundentermineOriginal = new ArrayList<>();
    private Calendar datum;
    private int indexFahrzeuge = 0;
    private int indexMitarbeiter = 0;
    private int indexKundentermin = 0;
    
    public Verfugbarkeiten(final Calendar datum)
    {
        this.datum = (Calendar)datum.clone();
                   
        mitarbeiter = MitarbeiterDb.gibVerfuegbareMitarbeiter(datum);
        fahrzeuge = FahrzeugDb.gibVerfuegbareFahrzeuge();
        kundentermine.addAll(KundenterminDb.gibVerfuegbareKundentermine(datum));
        kundentermineOriginal.addAll(kundentermine);        
    }
    
    /**
     * Gibt das nächste Listenelement zurück.
     * 
     * @return fahrzeug
     */
    public Fahrzeug getNachsteFahrzeug()
    {
        indexFahrzeuge ++;
        return fahrzeuge.get(indexFahrzeuge-1);        
    }
    
    /**
     * Gibt die Anzahl Fahrzeuge zurück.
     * 
     * @return anzahlFahrzeuge
     */
    public int getAnzahlFahrzeuge()
    {
        return fahrzeuge.size();
    }
    
    /**
     * Gibt die Anzahl Mitarbeiter zurück.
     * 
     * @return anzahlMitarbeiter
     */
    public int getAnzahlMitarbeiter()
    {
        return mitarbeiter.size();
    }
    
    /**
     * Gibt die Anzahl Kundentermine zurück.
     * 
     * @return anzahlKundentermine
     */
    public int getAnzahlKundentermine()
    {
        return kundentermine.size();
    }    
    
    /**
     * Gibt das nächste Listenelement zurück.
     * 
     * @return mitarbeiter
     */
    public Mitarbeiter getNachsteMitarbeiter()
    {
        indexMitarbeiter ++;
        return mitarbeiter.get(indexMitarbeiter-1);        
    } 
    
    /**
     * Gibt das nächste Listenelement zurück.
     * 
     * @return kundentermin
     */
    public Kundentermin getNachsteKundentermin()
    {
        indexKundentermin ++;
        return kundentermine.get(indexKundentermin-1);        
    }   
    
    /**
     * Gibt zurück ob es noch ein Fahrzeug verfügbar hat aufgrund der
     * aktuelle Indexposition.
     */
    
    public boolean istFahrzeugVerfuegbar()
    {
        return fahrzeuge.size() > indexFahrzeuge;
    }
    
    
    /**
     * Gibt zurück ob es noch ein Mitarbeiter verfügbar hat aufgrund der
     * aktuelle Indexposition.
     */
    
    public boolean istMitarbeiterVerfuegbar()
    {
        return mitarbeiter.size() > indexMitarbeiter;
    }  
    
        
    /**
     * Gibt das Datum für diese Verfügbarkeit zurück.
     * 
     * @return datum
     */
    public Calendar getDatum()
    {
        return this.datum;
    }
    
    /**
     * Gibt die Liste der Kundentermine zurück.
     * 
     * @return kundentermine
     */
    public ArrayList<Kundentermin> getKundentermine()
    {
        return this.kundentermine;
    }
    
    
    /**
     * Löscht die Kundentermine zu einem Kunden aus der Liste.
     */
    public void loscheKundentermine(final int kundeId)
    {
        Iterator<Kundentermin> it = kundentermine.iterator();                
        while(it.hasNext())
        {
            Kundentermin kundentermin = it.next();
            if(kundentermin.getKundeId() == kundeId)
            {
                it.remove();
            }
        }
    }
    
    /**
     * Gibt die Anzahl verschiedener Kunden in der Verfügbarkeitsliste 
     * zurück.
     * 
     * @retrun kunden
     */
    public HashSet<Kunde> getKunden()
    {
        HashSet<Kunde> kunden = new HashSet<>();
        for(Kundentermin element : kundentermine)
        {
            kunden.add(element.getKunde());
        }
        return kunden;
    }
    
    /**
     * Ersetzt die vorhandene Liste mit einer anderen.
     * 
     * @param kundentermine
     */
    public void setKundentermine(ArrayList<Kundentermin> kundentermine)
    {
        this.kundentermine.clear();
        this.indexKundentermin = 0;
        this.kundentermine.addAll(kundentermine);
    }
    
    /**
     * Ersetzt die vorhandene Liste mit einer anderen.
     * 
     * @param kundentermine
     */
    public void setFahrzeuge(ArrayList<Fahrzeug> fahrzeuge)
    {
        this.fahrzeuge.clear();
        this.indexFahrzeuge = 0;
        this.fahrzeuge.addAll(fahrzeuge);
    }   
    
    /**
     * Ersetzt die vorhandene Liste mit einer anderen.
     * 
     * @param kundentermine
     */
    public void setMitarbeiter(ArrayList<Mitarbeiter> mitarbeiter)
    {
        this.mitarbeiter.clear();
        this.indexMitarbeiter = 0;
        this.mitarbeiter.addAll(mitarbeiter);
    }  

    /**
     * Setzt die Index für alle Objekte zurück und ersetzt die Kundentermine
     * Liste wieder mit dem Original.
     */
    public void resetIndexe()
    {
        this.indexFahrzeuge = 0;
        this.indexKundentermin = 0;
        kundentermine.clear();
        kundentermine.addAll(kundentermineOriginal);
        this.indexMitarbeiter = 0;
    }
    
    /**
     * Gibt den aktuellen Index für Fahrzeuge zurück.
     */
    public int getIndexFahrzeuge()
    {
        return this.indexFahrzeuge;
    }
    
    /**
     * Gibt den aktuellen Index für Kundentermine zurück.
     */
    public int getIndexKundentermine()
    {
        return this.indexKundentermin;
    }    
    
    /**
     * Gibt den aktuellen Index für Mitarbeiter zurück.
     */
    public int getIndexMitarbeiter()
    {
        return this.indexMitarbeiter;
    }        

}
