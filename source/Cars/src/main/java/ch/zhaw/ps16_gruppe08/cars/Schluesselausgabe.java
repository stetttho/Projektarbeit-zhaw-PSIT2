package ch.zhaw.ps16_gruppe08.cars;
 
import java.util.ArrayList;
import java.util.Calendar;

public class Schluesselausgabe {
    private Calendar datum;
    private ArrayList<Fahrzeug> fahrzeuge = new ArrayList<>();
    private ArrayList<Mitarbeiter> mitarbeiter = new ArrayList<>();
    private int index = 0;
    
    /**
     * Konstruktor für Schluesselausgabe mit Angabe des Datums.
     * @param datum
     */
    public Schluesselausgabe(Calendar datum)
    {
        this.datum = datum;
    }
    
    public Schluesselausgabe(){
        
    }
    
    /**
     * Fügt einen Mitarbeiter und ein Fahrzeug der Schlüsselausgabe hinzu.
     * 
     * @param mitarbeiter
     * @param fahrzeug
     */
    public void addEintrag(Mitarbeiter mitarbeiter, Fahrzeug fahrzeug)
    {
        this.mitarbeiter.add(mitarbeiter);
        this.fahrzeuge.add(fahrzeug);
    }
    
    /**
     * Gibt das Fahrzeug anhand des aktuellen Index zurück.
     * 
     * @return fahrzeug
     */
    public Fahrzeug getFahrzeug()
    {
        if(index > fahrzeuge.size() || index < 0)
        {
            throw new IllegalArgumentException("der Index muss kleiner sein "+
                                               " als die Anzahl Elemente und "+
                                               "grösser gleich null");
        }
        else
        {
            return this.fahrzeuge.get(index);
        }
       
    }
    
    /**
     * Gibt den Mitarbeiter anhand des aktuellen Index zurück.
     * 
     * @return mitarbeiter
     */
    public Mitarbeiter getMitarbeiter()
    {
        if(index > mitarbeiter.size() || index < 0)
        {
            throw new IllegalArgumentException("der Index muss kleiner sein "+
                                               " als die Anzahl Elemente und "+
                                               "grösser gleich null");
        }
        else
        {
            return this.mitarbeiter.get(index);
        }
    }
    
    /**
     * Erhöht den Index um 1.
     */
    public void moveNext()
    {
        this.index++;
    }
    
    /**
     * Gibt die Anzahl aller Einträge zurück, indem die Anzahl Mitarbeiter 
     * gezählt wird.
     * 
     * @return anzahlMitarbeiterEintraege
     */
    public int getAnzahlEintraege()
    {
        return this.mitarbeiter.size();
    }
    
    /**
     * Gibt das aktuelle Datum formatiert zurück.
     * 
     * @return datumDormatiert.
     */
    public String getDatumFormatiert()
    {
        return ZeitUtil.calendarZuString(datum);
    }
}
