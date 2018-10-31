package ch.zhaw.ps16_gruppe08.cars;
import java.io.Serializable;

/**
 * Diese Klasse verwaltet einen Mitarbeiter für die Cars-Applikation
 * @version 1.0
 *  
 */

public class Mitarbeiter implements Serializable, Comparable<Mitarbeiter> {

    private static final long serialVersionUID = 2543246243110829142L;
    private int mitarbeiterId;
	private boolean statusAktiv;
	private String name;
	private String vorname;
	private String telNr;
	private String email;
	
	
	/**
	 * @param mitarbeiterId
	 * @param statusAktiv
	 * @param name
	 * @param vorname
	 * @param telNr
	 */
	public Mitarbeiter(final int mitarbeiterId, 
			   		   final boolean statusAktiv, 
			   		   final String name, 
			   		   final String vorname, 
			   		   final String telNr,
			   		   final String email)
	{
		this.mitarbeiterId = mitarbeiterId;
		this.statusAktiv = statusAktiv;
		this.name = name;
		this.vorname = vorname;
		this.telNr = telNr;
		this.email = email;
		
	}
	
	public Mitarbeiter(final int mitarbeiterId, 
                       final boolean statusAktiv, 
                       final String name, 
                       final String vorname, 
                       final String telNr)
	{
	    this(mitarbeiterId,statusAktiv,name,vorname,telNr,"");
	}
		
	/**
	 * Liefert die mitarbeiterId des Mitarbeiters
	 * @return mitarbeiterId
	 */
	public int getMitarbeiterId()
	{
		return mitarbeiterId;
	}
	
	/**
	 * Setzt die mitarbeiterId des Mitarbeiters
	 * @param mitarbeiterId
	 */
	
	public void setMitarbeiterId(final int mitarbeiterId)
	{
		this.mitarbeiterId = mitarbeiterId;
	}
	
	
	/**
	 * Liefert ob der Mitarbeiter aktiv oder inaktiv ist
	 * @return statusAktiv
	 */
	public boolean istStatusAktiv()
	{
		return statusAktiv;
	}
	
	/**
	 * Liefert den Nachnamen des Mitarbeiters
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Liefert den Vornamen des Mitarbeiters
	 * @return vorname
	 */
	public String getVorname()
	{
		return vorname;
	}
	
	/**
	 * Liefert die Telefon-Nummer des Mitarbeiters
	 * @return telNr
	 */
	public String getTelNr()
	{
		return telNr;
	}
	
	/**
	 * Speichert die Email-Adresse des Mitarbeiters
	 */
	public void setEmail(String email)
	{
	    this.email = email;
	}
	
	/**
	 * Gibt die Email-Adresse des Mitarbeiters zurück
	 * @return
	 */
	public String getEmail()
	{
	    return this.email;
	}

	/**
	 * Sortiert die Mitarbeiter nach Name/Vorname
     * @return -1 wenn Name/Vorname des übergebenen Mitarbeiters 
     *            alphabetisch später ist
     *         0  wenn Name/Vorname identisch ist
     *         1  wenn Name/Vorname des übergebenen Mitarbeiters 
     *            alphabetisch früher ist      
     */
    @Override
    public int compareTo(Mitarbeiter mitarbeiter) {
        
        int vergleich = this.name.compareToIgnoreCase(mitarbeiter.getName());
        if(vergleich==0)
        {
            return this.vorname.compareToIgnoreCase(mitarbeiter.getVorname());
        }
        else
        {
            return vergleich;
        }
    }
	


}
