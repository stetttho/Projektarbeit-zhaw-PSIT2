package ch.zhaw.ps16_gruppe08.cars;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Behandelt alle Datenbankbefehle in Bezug zu Plänen
 * @version 1.0
 * 
 */
public class PlanDb {
    
    private static ArrayList<Plan> plane = new ArrayList<>();
        
    /**
     * Gibt den SQL-Befehl zurück welcher für das Einfügen benötigt wird
     */
    public static String getSqlStringInsert()
    {
        return "INSERT INTO Plan (DATUMVON, DATUMBIS) VALUES (?,?)";
    }
    

    /**
     * Selektiert alle Pläne und speichert diese in einer Liste
     */
    public static void select()
    {
        try 
        {
            Connection con = DbConnection.connect();
            Statement st = con.createStatement();
                String query = "SELECT * FROM PLAN";
                ResultSet result = st.executeQuery(query);
                while(result.next()) 
                {
                    plane.add(new Plan(result.getInt("PlanId"),
                              ZeitUtil.sqlDateZuCalendar(
                                    result.getDate("DatumVon")),
                              ZeitUtil.sqlDateZuCalendar(
                                    result.getDate("DatumBis"))));
                }
            result.close();
            st.close();
            } catch (Exception e) { 
            e.printStackTrace();
            }
    }
    
    
    /**
     * Speichert ein Plan in der Datenbank
     * @param datumVon
     * @param datumBis
     */
    public static int speichern(Calendar datumVon, Calendar datumBis)
    {
        int planId = -1;
        try
        {                 
            String SQLBefehl;
            Connection con = DbConnection.connect();
                

            SQLBefehl = PlanDb.getSqlStringInsert();
              
            String generatedColumns[] = { "planId" };
            PreparedStatement st = con.prepareStatement(SQLBefehl, 
                                                        generatedColumns);           
                      
            st.setDate(1, ZeitUtil.calendarZuSql(datumVon));            
            st.setDate(2, ZeitUtil.calendarZuSql(datumBis));        
            
            st.executeUpdate();
            
            ResultSet generatedKeys = st.getGeneratedKeys();   
            if (generatedKeys.next()) 
            {
                planId = generatedKeys.getInt(1);
            }
            else 
            {
                throw new SQLException("Fehler bei Sql-Befehl "+
                                           ", Kunde konnte nicht "+
                                           "gespeichert werden.");
            }                              
    
            st.close();                  
           
        } catch (Exception e) 
            { 
                e.printStackTrace();
            }
        
        return planId;      
    }
    
    /**
     * Gibt die in der Datenbank gespeicherten Pläne zurück, die Pläne
     * werden nur als HashMap abgelegt, Schlüssel ist das Beginndatum
     * und der Eintrag das Endedatum dieses Plans, da ein Plan keine weiteren
     * Informationen trägt
     */
    public static ArrayList<Plan> getAlle()
    {
        plane.clear();
        PlanDb.select();
        return plane;
    }

}
