package ch.zhaw.ps16_gruppe08.cars;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.HashMap;


/**
 * Behandelt alle Datenbankbefehle in Bezug zu Routen =>> Termine
 * @version 1.0
 * 
 */
public class RouteDb {
        
    /**
     * Gibt den SQL-Befehl zurück welcher für das Einfügen benötigt wird
     */
    public static String getSqlStringInsert()
    {
        return "INSERT INTO Termin (PLANID, VARIANTEID, ZEITFENSTERID, "+
                            " FAHRZEUGID, MITARBEITERID, KUNDEID, " +
                            " DATUM, UHRZEIT) VALUES (?,?,?,?,?,?,?,?)";
    }
    
    
    /**
     * Speichert alle Termine einer Route in der Datenbank.    
     * @param kunde
     * @param varianteId
     * @param route
     */
    public static void speichern(int planId, int varianteId, Route route)
    {
        try
        {                 
            String SQLBefehl;
            Connection con = DbConnection.connect();
                
            SQLBefehl = RouteDb.getSqlStringInsert();                     

            HashMap<LocalTime,Kundentermin> termine = route.getKundentermine();         
            for(LocalTime element : termine.keySet())
            {
                PreparedStatement st = con.prepareStatement(SQLBefehl); 
                st.setInt(1, planId);            
                st.setInt(2, varianteId);
                st.setInt(3,termine.get(element).getKundenterminId());
                st.setInt(4, route.getFahrzeugId());
                st.setInt(5, route.getMitarbeiterId());
                st.setInt(6, termine.get(element).getKundeId());                                               
                st.setDate(7, ZeitUtil.calendarZuSql(route.getDatum()));
                st.setString(8, element.toString());
                st.executeUpdate();
                st.close();
            }
 
            
            
           
        } catch (Exception e) 
            { 
                e.printStackTrace();
            }      
    }

}
