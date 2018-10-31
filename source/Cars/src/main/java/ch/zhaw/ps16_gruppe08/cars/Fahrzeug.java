package ch.zhaw.ps16_gruppe08.cars;
import java.io.Serializable;

/**
 * Diese Klasse verwaltet ein Fahrzeug für die Cars-Applikation
 *  
 */

public class Fahrzeug implements Serializable, Comparable<Fahrzeug> {

    private static final long serialVersionUID = 1873977070029228195L;
    private int fahrzeugId;
	private boolean statusAktiv;
	private String typ;
	private String kontrollschildNr;
	
	
	/**
	 * @param fahrzeugId
	 * @param statusAktiv
	 * @param typ
	 * @param kontrollschildNr
	 */
	public Fahrzeug(final int fahrzeugId, 
	                final boolean statusAktiv, 
	                final String typ, 
	                final String kontrollschildNr)
	{
	    this.fahrzeugId = fahrzeugId;
	    this.statusAktiv = statusAktiv;
	    this.typ = typ;
	    this.kontrollschildNr = kontrollschildNr;
		
	}
		
	/**
	 * Liefert die DatenbankId des Fahrzeuges
	 * @return FahrzeugId
	 */
	public int getFahrzeugId()
	{
	    return fahrzeugId;
	}
	
	public void setFahrzeugId(final int fahrzeugId)
	{
	    this.fahrzeugId = fahrzeugId;
	}
	
	
	/**
	 * Liefert ob das Fahrzeug aktiv oder inaktiv ist
	 * @return statusAktiv
	 */
	public boolean istStatusAktiv()
	{
	    return statusAktiv;
	}
	
	/**
	 * Liefert den Typ des Fahrzeuges
	 * @return typ
	 */
	public String getTyp()
	{
	    return typ;
	}
	
	/**
	 * Liefert die Kontrollschild-Nummer des Fahrzeuges
	 * @return kontrollschildNr
	 */
	public String getKontrollschildNr()
	{
	    return kontrollschildNr;
	}

	/**
	 * Gibt an, wie die Fahrzeuge sortiert werden sollen
	 * @return -1 wenn Typ des übergebenen Fahrzeugs alphabetisch später ist
	 *         0 wenn Typ identisch ist
	 *         1 wenn Typ des übergebenen Fahrzeugs alphabetisch früher ist
	 */
    @Override
    public int compareTo(Fahrzeug fahrzeug) {
        return this.typ.compareToIgnoreCase(fahrzeug.getTyp());
    }
	
}
