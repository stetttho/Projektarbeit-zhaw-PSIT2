package ch.zhaw.ps16_gruppe08.cars;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Calendar;


/**
 * Diese Klasse enthält die Zeitfenster,
 * gewünschten Kundenterminen von Kunden für die Cars-Applikation
 *  
 */

public class Kundentermin implements Serializable, Comparable<Kundentermin> {

    private static final long serialVersionUID = -1874902785937393570L;
    private int kundeId;
    private Kunde kunde;
    private boolean istLetzterTermin;
    private int kundenterminId;
    private LocalTime startzeit;
    private LocalTime endzeit;
    private Calendar datum;
    
    /**
     * @param kundenterminId
     * @param kundeId
     * @param startzeit
     * @param endzeit
     * @param datum
     * @param istLetzterTermin
     */
    public Kundentermin(final int kundenterminId, 
                        final int kundeId, 
                        final LocalTime startzeit, 
                        final LocalTime endzeit, 
                        final Calendar datum,
                        final boolean istLetzterTermin)
    {
        
        this.kundenterminId = kundenterminId;
        this.kundeId = kundeId;
        this.kunde = KundeDb.sucheMitId(this.getKundeId());
        this.startzeit = startzeit;
        this.endzeit = endzeit;
        this.datum = datum;
        this.istLetzterTermin = istLetzterTermin;
        
    }
    
    public Kundentermin(final int kundenterminId, 
            final int kundeId, 
            final LocalTime startzeit, 
            final LocalTime endzeit, 
            final Calendar datum)
    {
        this(kundenterminId,kundeId,startzeit,endzeit,datum,false);
    }
    
    /**
     * Gibt den Personenname des Kunden eines Kundentermines zurück
     * @return personName
     */
    public String getKundeName(){
        
        if(kundeIstGesetzt())
        {
            return this.kunde.getName();            
        }
        else
        {
            return null;
        }
    }
    
    
    /**
     * Setter-Methode für die DatenbankId
     * @param kundenterminId
     */
    public void setKundenterminId(final int kundenterminId)
    {
        this.kundenterminId = kundenterminId;
    }
    
    
    /**
    * Setzt den Kunden auf ein bestimmtes Kundenobjekt
    * @param kunde
    */
    public void setKunde(final Kunde kunde)
    {
        this.kunde = kunde;
        this.kundeId = kunde.getKundeId();
    }
    
    /**
    * Gibt das für diesen Termin gesetzte Kunden-Objekt zurück
    * @return kunde
    */
    public Kunde getKunde()
    {
        return this.kunde;
    }    
    
    /**
     * Setter-Methode für die Starzeit des Kundentermins.
     * @param startzeit
     */
    public void setStartzeit(final LocalTime startzeit)
    {
        this.startzeit = startzeit;
    }
    
    /**
     * Setter-Methode für die Endzeit des Kundentermins.
     * @param enzeit
     */
    public void setEndzeit(final LocalTime endzeit)
    {
        this.endzeit = endzeit;
    }
    
    /**
     * Setter-Methode für das Datum des Kundentermins.
     * @param datum
     */
    public void setDatum(final Calendar datum)
    {
        this.datum = datum;
    }
    
    /**
     * Gibt die PersonId eines Kundentermines zurück.
     * @return personId
     */
    public int getKundeId()
    {
        return this.kundeId;

    }
    
    public boolean kundeIstGesetzt()
    {
        if(this.kunde != null)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    /**
     * Gibt die KundenterminId eines Kundentermines zurück.
     * @return kundenterminId
     */
    public int getKundenterminId()
    {
        return this.kundenterminId;
    }
    
    /**
     * Gibt die Startzeit eines Kundentermines zurück.
     * @return startzeit
     */
    public LocalTime getStartzeit()
    {
        return this.startzeit;
    }

    /**
     * Gibt die Endzeit eines Kundentermines zurück.
     * @return endzeit
     */
    public LocalTime getEndzeit()
    {
        return this.endzeit;
    }

    /**
     * Gibt das Datum eines Kundentermines zurück.
     * @return datum
     */
    public Calendar getDatum()
    {
        return this.datum;
    }
    
    public String getDatumFormatiert()
    {      
        return ZeitUtil.calendarZuString(this.datum);
    }
    
    
    /**
     * Gibt die Priorität dieses Termines zurück
     * @return prioritaet
     */
    public int getPrioritaet()
    {
        if(istLetzterTermin)
        {
            return 0;
        }
        else
        {
            if(kundeIstGesetzt())
            {
                return kunde.getPrioritaet();                   
            }
            else
            {
                return -1;
            }
        
        }

    }
    
  
    /**
     * Vergleicht zwei Kundentermine nach ihrem Datum.
     * Beispiel: TerminA.compareTo(TerminB), gibt -1
     * zurück, wennTerminA vor TerminB statt findet.
     * Gibt 0 zurück, wenn TerminA am gleichen Tag
     * statt findet wie TerminB und gibt 1 zurück, 
     * wenn TerminA nach TerminB statt findet.
     */
    @Override
    public int compareTo(Kundentermin termin) {
        if(this.datum.before(termin.getDatum()))
        {
            return -1;
        }
        else
        {
            if(this.datum.after(termin.getDatum()))
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        
    }
    
}
