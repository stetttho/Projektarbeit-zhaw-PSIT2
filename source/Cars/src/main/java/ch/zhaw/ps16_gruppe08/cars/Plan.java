package ch.zhaw.ps16_gruppe08.cars;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Die Klasse stellt die von der Applikation erstellten Pläne für die
 * Ausgabe zur Verfügung
 * 
 */
public class Plan implements Serializable {

    private static final long serialVersionUID = 7310024683010654013L;
    private int planId;
    private Calendar vonDatum;
    private Calendar bisDatum;
    
    public Plan(final int planId, 
                final Calendar vonDatum, 
                final Calendar bisDatum)
    {
        this.planId = planId;
        this.vonDatum = vonDatum;
        this.bisDatum = bisDatum;        
    }
    
    /**
     * 
     * Liefert das von Datum für diesen Plan formatiert nach Deutschem Format
     */
    public String getVonDatumFormatiert()
    {
        return ZeitUtil.calendarZuString(this.vonDatum);
    }
    
    /**
     * Liefert das bis Datum für diesen Plan formatiert nach Deutschem
     * Format
     * @return
     */
    public String getBisDatumFormatiert()
    {
        return ZeitUtil.calendarZuString(this.bisDatum);
    }
    
    /**
     * Liefert die Id für diesen Plan
     */
    public int getPlanId()
    {
        return this.planId;
    }

}
