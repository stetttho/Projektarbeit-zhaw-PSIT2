package ch.zhaw.ps16_gruppe08.cars;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeMap;


public class Route {
    private Mitarbeiter mitarbeiter;
    private Fahrzeug fahrzeug;
    private Calendar routeFuerDatum;
    private HashMap<LocalTime,Kundentermin> kundentermine = new HashMap<>();
		
    public Route(Mitarbeiter mitarbeiter, 
                 Fahrzeug fahrzeug,
                 Calendar routeFuerDatum) 
    {
        this.mitarbeiter = mitarbeiter;
        this.fahrzeug = fahrzeug;
        this.routeFuerDatum = (Calendar)routeFuerDatum.clone();
    }
    
    public Route()
    {
        
    }
	
    /** 
     * Fügt einen Termin zur aktuellen Route hinzu
     * @param kundentermin
	 * @param uhrzeit
     */
    public void terminHinzufugen(final Kundentermin kundentermin,
                                 final LocalTime uhrzeit)
    {
        kundentermine.put(uhrzeit,kundentermin);
    }
	    
    /**
     * Gibt die FahrzeugId zurück.
     * @return fahrzeugId
     */
    public int getFahrzeugId()
    {
        return this.fahrzeug.getFahrzeugId();
    }
    
    /**
     * Gibt die Kontrollschildnummer des Fahrzeuges zurück.
     * @return kontrollschildNr
     */
    public String getFahrzeugKontrollSchild()
    {
        return this.fahrzeug.getKontrollschildNr();
    }
    
    
    /**
     * Gibt die MitarbeiterId zurück.
     * @return mitarbeiterId
     */
    public int getMitarbeiterId()
    {
        return this.mitarbeiter.getMitarbeiterId();
    }
    
    /**
     * Gibt das Datum für diese Route zurück.
     * @return routeFuerDatum
     */
    public Calendar getDatum()
    {
        return this.routeFuerDatum;
    }
    
    /**
     * Gibt die HashMap mit der Route zurück.
     */
    public HashMap<LocalTime,Kundentermin> getKundentermine()
    {
        return kundentermine;
    }    
	
	/**
	 * Gibt die Anzahl Termine für diese Route zurück.
	 */
    public int getAnzahlTermine()
    {
        return kundentermine.size();
    }
	
    /**
     * Gibt Vorname und Name des Mitarbeiters als String zurück.
     * 
     */
    public String getMitarbeiterName() 
    {
        String mitarbeiterName = mitarbeiter.getVorname() +" " + 
                                 mitarbeiter.getName();
        return mitarbeiterName;
    }	
    
    /**
     * Gibt die Liste sortiert zurück.
     */
    public TreeMap<LocalTime, Kunde> getTermineSortiert()
    {
        TreeMap<LocalTime, Kunde> map = 
                new TreeMap<LocalTime, Kunde>();
        for(LocalTime key : this.kundentermine.keySet())
        {
            map.put(key, this.kundentermine.get(key).getKunde());
        }
        return map;
    }
       
    /**
     * Gibt zurück, ob Mitarbeiter der Route mit angegebenem 
     * Mitarbeiter übereinstimmt.
     */
    public boolean enthaeltMitarbeiter(Mitarbeiter angegebenerMitarbeiter) 
    {
        return (mitarbeiter.equals(angegebenerMitarbeiter));
    }
    
    public boolean entsprichtDatum(Calendar angegebenesDatum)
    {
        return(routeFuerDatum.equals(angegebenesDatum));
    }

}
