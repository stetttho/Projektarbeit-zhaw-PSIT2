package ch.zhaw.ps16_gruppe08.cars;

import java.awt.Point;
import java.util.Comparator;

/**
 * Diese Klasse stellt die Funktionalität zur Verfügung um Kundentermine nach
 * nach der Entfernung zu einem bestimmten Punkt zu sortieren
 *  
 */

public class KundentermineComparator implements Comparator<Kundentermin> {
    private Point ausgangsKoordinaten;
    
    /**
     * Vergleicht zwei Objekte und gibt Rückmeldungen gemäss den
     * Vorgaben des Comparator Interface
     * @param kundentermin1
     * @param kundentermin2
     * @return int-Wert welcher Vergleich repräsentiert
     */
    public KundentermineComparator (Point ausgangsKoordinaten)
    {
        this.ausgangsKoordinaten = ausgangsKoordinaten.getLocation();
    }
    
    @Override
    public int compare (Kundentermin kundentermin1,
                        Kundentermin kundentermin2)
    {        
        int distanzKundentermin1 = 
                (int) kundentermin1.getKunde()
                          .getKoordinaten().distance(ausgangsKoordinaten);               
        int distanzKundentermin2 =
                (int) kundentermin2.getKunde()
                          .getKoordinaten().distance(ausgangsKoordinaten);
        if(distanzKundentermin1 == distanzKundentermin2)
        {
            return 0;
        }
        if(distanzKundentermin1 < distanzKundentermin2)
        {
            return -1;
        }
        else
        {
            return 1;
        }

    }

}
