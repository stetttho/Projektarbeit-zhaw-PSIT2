package ch.zhaw.ps16_gruppe08.cars;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Diese Klasse verwaltet die Abwesenheiten der Mitarbeiter.
 * 
 */

public class AbwesenheitMitarbeiter implements Serializable, 
                Comparable<AbwesenheitMitarbeiter> 
{
    private static final long serialVersionUID = -6950301401963598815L;
    private int abwesenheitId;
	private int mitarbeiterId;
	private Mitarbeiter mitarbeiter;
	private Calendar abwesenheitVon;
	private Calendar abwesenheitBis;
	

	public AbwesenheitMitarbeiter(
            final int abwesenheitId,
            final int mitarbeiterId,
            final Calendar abwesenheitVon,
            final Calendar abwesenheitBis)
    {
        this.abwesenheitId=abwesenheitId;
        this.mitarbeiterId=mitarbeiterId;
        this.mitarbeiter = MitarbeiterDb.sucheMitId(this.mitarbeiterId);
        this.abwesenheitVon=abwesenheitVon;
        this.abwesenheitBis=abwesenheitBis;
    }   	
	public AbwesenheitMitarbeiter(final int abwesenheitId,
            final int mitarbeiterId)
	{
		this.abwesenheitId=abwesenheitId;
        this.mitarbeiterId=mitarbeiterId;
                
        Calendar datum = Calendar.getInstance();
        datum.clear();
        datum.set(1970,0,1);        
        
        this.abwesenheitBis = datum;
        this.abwesenheitVon = datum;
	}
	
	/**
	 * Setter-Methode für abwesenheitId
	 * @param abwesenheitId
	 */
	public void setAbwesenheitId(int abwesenheitId) 
	{
		this.abwesenheitId = abwesenheitId;
	}

    /**
     * Gibt den Personenname des Mitarbeiters einer Abwesenheit zurück
     * @return personName
     */
    public String getMitarbeiterName()
    {    
        if(this.mitarbeiter != null)
        {
            return this.mitarbeiter.getName() + " "
            		+this.mitarbeiter.getVorname();            
        }
        else
        {
            return "";
        }
    }
    
    /**
     * Methode überprüft ob ein Mitarbeiter auf der Abwesenheit gesetzt ist.
     * @return boolean
     */
    public boolean mitarbeiterIstGesetzt()
    {
        if(this.mitarbeiter != null)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }

	/**
	 * Gibt die MitarbeiterId zurück
	 * @return mitarbeiterId
	 */
	public int getMitarbeiterId() 
	{
		return mitarbeiterId;
	}

	/**
	 * Gibt die AbwesenheitId zurück
	 * @return abwesenheitId
	 */
	public int getAbwesenheitId() 
	{
		return abwesenheitId;
	}

	/**
	 * Setter-Methode für mitarbeiterId.
	 * @param mitarbeiterId
	 */
	public void setMitarbeiterId(int mitarbeiterId) 
	{
		this.mitarbeiterId = mitarbeiterId;
	}

	/**
	 * Gibt das Datum des ersten Tages der Abwesenheit zurück
	 * @return datumVon
	 */
	public Calendar getAbwesenheitVon() 
	{
		return abwesenheitVon;
	}
    
	/**
	 * Gibt das DatumVon formatiert als String zurück
	 * @return datumVon formatiert
	 */
	public String getAbwesenheitVonFormatiert()
    {      
        return ZeitUtil.calendarZuString(this.abwesenheitVon);
    }

	/**
	 * Setter-Methode für Abwesenheit von.
	 * @param datumVon
	 */
	public void setAbwesenheitVon(Calendar abwesenheitVon) 
	{
		this.abwesenheitVon = abwesenheitVon;
	}

	/**
	 * Gibt das Datum des letzten Tages der Abwesenheit zurück
	 * @return datumBis
	 */
	public Calendar getAbwesenheitBis() 
	{
		return abwesenheitBis;
	}
	
	/**
	 * Getter-Methode für abwesenheitBis als String
	 * @return datumVon formatiert
	 */
	public String getAbwesenheitBisFormatiert()
    {      
        return ZeitUtil.calendarZuString(this.abwesenheitBis);
    }

	/**
	 * Setter-Methode für abwesenheitBis
	 * @param datumBis
	 */
	public void setAbwesenheitBis(Calendar abwesenheitBis) 
	{
		this.abwesenheitBis = abwesenheitBis;
	}

	/**
	 * Methode, um zu überprüfen, ob DatumVon kleiner als DatumBis ist. 
	 */
    @Override
    public int compareTo(AbwesenheitMitarbeiter abwesenheit) 
    {
        if(this.abwesenheitVon.before(abwesenheit.getAbwesenheitVon()))
        {
            return -1;
        }
        else
        {
            if(this.abwesenheitVon.equals(abwesenheit.getAbwesenheitVon()))
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
    }
}
