package ch.zhaw.ps16_gruppe08.cars;
import java.awt.Point;
import java.io.Serializable;

/**
 * Diese Klasse verwaltet einen Kunden für die Cars-Applikation
 * @version 1.0
 *  
 */

public class Kunde implements Serializable, Comparable<Kunde> {

    private static final long serialVersionUID = 3286246760070439049L;
    private int kundeId;
    private boolean statusAktiv;
    private String name;
    private Point koordinaten;
    private int prioritaet;
    private String emailAdresse;
    private String telNr;
	
	
    /**
     * @param kundeId
     * @param statusAktiv
     * @param name
     * @param Point-Objekt welches die Koordinaten des Kunden enthält, 
     *        Koordinaten dürfen nicht negativ sein
     * @param prioritaet
     * @param emailAdresse
     * @param telNr
     */
    public Kunde(final int kundeId, 
                 final boolean statusAktiv, 
                 final String name, 
                 final Point koordinaten, 
                 final int prioritaet, 
                 final String emailAdresse, 
                 final String telNr)
    {
        setKundeId(kundeId);
        this.statusAktiv = statusAktiv;
        setName(name);
        if(koordinaten.x < 0 || koordinaten.y < 0)
        {
            throw 
              new IllegalArgumentException("ungültige Koordinaten übergeben");
        }
        else
        {
            this.koordinaten = koordinaten;                
        }
    
        setPrioritaet(prioritaet);
        setEmailAdresse(emailAdresse);
        setTelefonNr(telNr);
    }
		
    /**
     * Liefert die KundeId des Kunden
     * @return kundeId
     */
    public int getKundeId()
    {
        return kundeId;
    }
	
    /**
     * Ändert die KundenID
     * @param kundeId
     */
    public void setKundeId(final int kundeId)
    {
        if(kundeId >= 0)
        {
            this.kundeId = kundeId;
        }
        else
        {
            throw new 
            IllegalArgumentException("KundeId kann nicht negativ sein.");
        }
    }

	
    /**
     * Liefert ob der Kunde aktiv oder inaktiv ist
     * @return statusAktiv
     */
    public boolean istStatusAktiv()
    {
        return statusAktiv;
    }
		
    /**
     * Liefert den Namen des Kunden
     * @return name
     */
    public String getName()
    {
        return name;
    }
	
    /**
     * Ändert den Namen
     * @param name
     */
    private void setName(final String name)
    {
        if(name.length() > 0)
        {
            this.name=name;
        }
        else
        {
            throw new 
            IllegalArgumentException("Der Name kann nicht leer sein.");
        }
    }
	    
    /**
     * Liefert die Prioritaet des Kunden
     * @return prioritaet
     */
    public int getPrioritaet()
    {
        return prioritaet;
    }
    
    /**
     * Liefert das Punkt-Objekt welches die Koordinaten enthält
     * @return
     */
    public Point getKoordinaten()
    {
        return this.koordinaten;
    }
	
    /**
     * Ändert die Priorität
     * @param prioritaet
     */
    private void setPrioritaet(final int prioritaet)
    {
        if(prioritaet >= 0)
        {
            this.prioritaet=prioritaet;
        }
        else
        {
            throw new 
            IllegalArgumentException("Priorität kann nicht negativ sein.");
        }
    }
	
    /**
     * Liefert die E-Mail Adresse des Kunden
     * @return telNr
     */
    public String getEmailAdresse()
    {
        return emailAdresse;
    }
	
    /**
     * Ändert die E-Mail Adresse
     * @param emailAdresse
     */
    private void setEmailAdresse(final String emailAdresse)
    {
        if(emailAdresse.length()>0 && emailAdresse.contains("@"))
        {
            this.emailAdresse=emailAdresse;
        }
        else
        {
            throw new 
            IllegalArgumentException("E-Mail-Adresse kann nicht leer sein "
                    + "oder muss ein @ beinhalten.");
        }
    }
	
    /**
     * Liefert die Telefon-Nummer des Kunden
     * @return telNr
     */
    public String getTelefonNr()
    {
        return telNr;
    }
	
    /**
     * Ändert die Telefonnummer
     * @param telNr
     */
    private void setTelefonNr(final String telNr)
    {
        if(telNr.length()>0)
        {
            this.telNr=telNr;
        }
        else
        {
            throw new 
            IllegalArgumentException("Telefonnummer kann nicht leer sein.");
        }
    }

    /**
     * Sortiert die Kunden nach deren Namen
     * @return -1 wenn Name des übergebenen Kunden alphabetisch später ist
     *         0 wenn Name identisch ist
     *         1 wenn Name des übergebenen Kunden alphabetisch früher ist
     */
    @Override
    public int compareTo(Kunde kunde) {
        return this.name.compareToIgnoreCase(kunde.getName());
    }
	
}
