package ch.zhaw.ps16_gruppe08.cars;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

/** 
 * Diese Klasse bietet Methoden zur Ausgabe der Wochenpläne 
 * für die Cars-Applikation.
 * Die Klasse hält dabei mehrere Routen bereit welche nach Datum einsortiert
 * sind, sie bietet Methoden um diese Routen einzeln abzurufen
 * Sie merkt sich dabei die zuletzt gelieferte Route und verschiebt den Zeiger
 * vorwärts.
 *
 * @version 1.0
 * 
 */

public class Wochenplan implements Serializable {

    private static final long serialVersionUID = 4667469189090571536L;
    private ArrayList<Route> routen = new ArrayList<>();
    private int index = 0;

    public Wochenplan()
    {

    }    
    
    /**
     * Fügt eine Route zu diesem Wochenplan hinzu, sortiert anschliessend
     * mit dem RoutenComparator die Liste so, dass die Route mit dem kleinsten
     * Termin zuerst ist.
     * 
     * @param route
     */
    public void addRoute(Route route)
    {
        this.routen.add(route);
        this.routen.sort(new RoutenComparator());
        this.resetIndex();
    }
    
    /**
     * Gibt die Anzahl der Routen in diesem Plan aus, kann vorallem für
     * die For-Schleife verwendet werden.
     * 
     * @return int Anzahl Routen
     */
    public int getRoutenAnzahl()
    {
        return this.routen.size();
    }
    
    /**
     * Setzt den Index auf 0 zurück, sodass wieder von vorne mit der Ausgabe
     * begonnen werden kann.
     */
    public void resetIndex()
    {
        this.index = 0;
    }
    
    /**
     * Setzt den Index einen Position hinauf, 
     * sodass die nächste Route ausgegeben wird.
     */
    public void moveNext()
    {
    	this.index++;
    }
    
    /**
     * Setzt den Index auf einen bestimmten Wert, wirft eine 
     * IllegalArgumentExeception wenn Wert grösser als die 
     * Anzahl der Elemente ist.
     */
    public void setIndex(int index)
    {
        if(index > routen.size() || index < 0)
        {
            throw new IllegalArgumentException("der Index muss kleiner sein "+
                                               " als die Anzahl Elemente und "+
                                               "grösser null");
        }
        else
        {
            this.index = index;
        }
    }
      
    /**
     * Gibt den Namen des Mitarbeiters für die aktuelle Route zurück.
     * 
     * @return vorname + " " + nachname
     */
    public String getMitarbeiterName()
    {
        return this.routen.get(index).getMitarbeiterName();          
    }
    
    /**
     * Gibt die Kontrollschildnummer für das aktuelle Fahrzeug zurück.
     * 
     * @return
     */
    public String getFahrzeugKontrollSchild()
    {
        return this.routen.get(index).getFahrzeugKontrollSchild();
    }
    
    /**
     * Formatiert ein Datum auf das Deutsche Format.
     * 
     * @return
     */
    public String getDatumFormatiert()
    {     
        return ZeitUtil.calendarZuString(this.routen.get(index).getDatum());
    }
    
    /**
     * Liefert den Kunden zu einer bestimmten Zeit.
     * 
     * @param zeit
     * @return Kunde
     */
    public Kunde getKunde(double zeit)
    {

        return this.getRoute().get(ZeitUtil.doubleZuLocalTime(zeit));
    }
    
    /**
     * Gibt eine Liste sortiert nach Zeit aus mit dem zugehörigen Kunden, die
     * Zeit ist die jeweilige Startzeit.
     */
    public Map<LocalTime,Kunde> getRoute()
    {
        return this.routen.get(index).getTermineSortiert();
    }
    
    public String getWochentag()
    {     
        return ZeitUtil.calendarZuWochentagString(this.routen
                                                  .get(index).getDatum());
    }
                
}
