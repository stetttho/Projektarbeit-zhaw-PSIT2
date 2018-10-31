package ch.zhaw.ps16_gruppe08.cars;

import java.util.Comparator;

/**
 * Diese Klasse stellt die Funktionalität zur Verfügung um Routen nach
 * nach dem Datum der Route zu sortieren
 *  
 */

public class RoutenComparator implements Comparator<Route> {
    
    /**
     * Vergleicht zwei Objekte und gibt Rückmeldungen gemäss den
     * Vorgaben des Comparator Interface
     * @param route1
     * @param route2
     * @return int-Wert welcher Vergleich repräsentiert
     */
    @Override
    public int compare (Route route1,
                        Route route2)
    {   
        if(route1.getDatum().equals(route2.getDatum()))
        {
            return 0;
        }
        if(route1.getDatum().before(route2.getDatum()))
        {
            return -1;
        }
        else
        {
            return 1;
        }

    }

}
