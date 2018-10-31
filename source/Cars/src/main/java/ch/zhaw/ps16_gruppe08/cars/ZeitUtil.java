package ch.zhaw.ps16_gruppe08.cars;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;

/**
 * Die Klasse stellt Funktionen zur Datumsumwandlung bereit.
 * 
 */
public final class ZeitUtil {
    
    private ZeitUtil()
    {
        
    }
    
    /**
     * Wandelt ein Datum in String-Form wie es von der Benutzeroberfläche
     * beispielsweise kommt in ein Calendar-Objekt um.
     * 
     * @param String datum 
     * @return Calendar datum
     * @throws IllegalArgumentException
     */
    public static Calendar stringZuCalendar(final String datum)
        throws IllegalArgumentException
    {
        String[] elemente = datum.split("[\\.]");
        if(elemente.length != 3)
        {
            throw new IllegalArgumentException("ungültiges Datum");
        }
        Calendar datumUmgewandelt = Calendar.getInstance();
        datumUmgewandelt.clear();   
        datumUmgewandelt.set(Integer.parseInt(elemente[2]), 
                     Integer.parseInt(elemente[1])-1,
                     Integer.parseInt(elemente[0]));
        return datumUmgewandelt;
    }
    
    /**
     * Wandelt ein CalendarObjekt zu einem SQL-Datums-Objekt um.
     * 
     * @param datum
     * @return
     */
    public static java.sql.Date calendarZuSql(final Calendar datum)
    {
        LocalDate datumUmgewandelt = datum.getTime()
                .toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        return java.sql.Date.valueOf(datumUmgewandelt);
    }
    
    /**
     * Wandelt ein Sql-Date Objekt in ein Calendar-Objekt um.
     * 
     * @param datum
     * @return
     */
    public static Calendar sqlDateZuCalendar(final java.sql.Date datum)
    {
        Calendar datumUmgewandelt = Calendar.getInstance();
        datumUmgewandelt.setTime(datum);  
        return  datumUmgewandelt;
    }
    
    /**
     * Liefert ein LocalTime Element zurück für eine bestimmte Zeit
     * im String Format.
     * 
     * @param zeit
     * @return
     */
    public static LocalTime stringZuLocalTime(final String zeit)
        throws IllegalArgumentException
    {
        String elemente[] = zeit.split(":");
        if(elemente.length != 2)
        {
            throw new IllegalArgumentException("ungültige Zeit "+zeit);
        }
        
        return LocalTime.of(Integer.parseInt(elemente[0]),
                                  Integer.parseInt(elemente[1]));
    }
    
    /**
     * Wandelt einen Double zu einem LocalTime um.
     * 
     * @param zeit
     * @return localTime
     */
    public static LocalTime doubleZuLocalTime(final double zeit)
    {
        int stunden = (int) zeit;
        double minuten = zeit - stunden;
        minuten = minuten * 60;
        
        return LocalTime.of(stunden, (int)minuten);
    }
    
    /**
     * Wandelt einen Kalender in eine Zeichenkette nach deutschem
     * Format um.
     */
    public static String calendarZuString(Calendar calendar)
    {
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");       
        return format1.format(calendar.getTime());
    }
    
    public static String calendarZuWochentagString(Calendar calendar)
    {
        SimpleDateFormat format1 = new SimpleDateFormat("EEEE", Locale.GERMAN);        
        return format1.format(calendar.getTime());
    }
    
    public static Calendar getDatumHeute()
    {
    	SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
    	Calendar datumHeute = format1.getCalendar(); 
    	datumHeute.setTimeInMillis(System.currentTimeMillis());
    	
    	return datumHeute;
    }
}
