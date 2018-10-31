package ch.zhaw.ps16_gruppe08.cars;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Behandelt alle Datenbankbefehle in Bezug zu Kundentermine. 
 * Solange die Datenbankverbindung noch nicht aktiv ist,
 * speichert sie die Kundentermine in einer ArrayList.
 * 
 */
public class KundenterminDb {
	

    private static HashMap<Integer, Kundentermin> 
	       kundentermine = new HashMap<>();
	
    /**
     * Methode führt den select aus für die 
     * Datenbank-Tabelle Zeitfenster
     */
    public static void select(final HashMap<String,Object> selektierungsWerte)
    {
        try 
        {
            Connection con = DbConnection.connect();
            String query = "SELECT * FROM ZEITFENSTER";
            if(selektierungsWerte!=null)
            {
                query += " WHERE ";
                for(String key : selektierungsWerte.keySet())
                {
                    query += key +" = ?, ";
                }
                query = query.substring(0,query.length()-2);
            }
            PreparedStatement st = con.prepareStatement(query);
            if(selektierungsWerte!=null)
            {
                int index = 1;
                for(String key : selektierungsWerte.keySet())
                {
                    if(key.equals("DATUM"))
                    {
                        st.setDate(index,
                                   (java.sql.Date)selektierungsWerte.get(key));
                    }
                    index++;
                }
            }            
            

            ResultSet result = st.executeQuery();
            kundentermine.clear();
            while(result.next()) 
            {                                              
                kundentermine.put(result.getInt("kundenterminId"),
                                  new Kundentermin(
                                        result.getInt("kundenterminId"),
                                        result.getInt("kundeId"),
                                        ZeitUtil.stringZuLocalTime(
                                                result.getString("startzeit")),
                                        ZeitUtil.stringZuLocalTime(
                                                result.getString("endzeit")),
                                        ZeitUtil.sqlDateZuCalendar(
                                                result.getDate("datum")),
                                         false));
            }
            result.close();
            st.close();
        } catch (Exception e) { 
            e.printStackTrace();
            }
    }
	
    /**
     * Gibt den SQL-Befehl zurück welcher für das Einfügen benötigt wird
     */
    public static String getSqlStringInsert()
    {
        return "INSERT INTO zeitfenster (STARTZEIT, "+
               "ENDZEIT, DATUM, KUNDEID) VALUES (?,?,?,?)";
    }
    
    /**
     * Gibt den SQL-Befehl zurück, welcher für das Updaten benötigt wird
     */
    public static String getSqlStringUpdate()
    {
        return "UPDATE zeitfenster SET STARTZEIT = ?, ENDZEIT = ?, "+
               " DATUM = ?, KUNDEID = ? "+
               " WHERE KUNDENTERMINID = ?";
    }
	
    /**
     * Liefert eine Liste von Kundentermine aufgrund des Suchstrings
     * @param searchString
     * @return Liste von Kundenterminen
     * @throws SQLException
     */
    public static ArrayList<Kundentermin> sucheMitFilter(
            final HashMap<String,Object> selektierungsWerte)
                    throws SQLException 
    {
        select(selektierungsWerte);
        ArrayList<Kundentermin> liste = new ArrayList<>();
        for(Integer element : kundentermine.keySet())
        {
            liste.add(kundentermine.get(element));
        }
        liste.sort(null);
        return liste;         
    }
	
    /**
     * Lädt die Daten eines Kundentermines aus der Datenbank
     * @param kundenterminId
     * @return ein Kundentermin aus der Datenbank
     */
    public static Kundentermin sucheMitId(final int kundenterminId)
    {
        if(!kundentermine.containsKey(kundenterminId))
        {
            select(null);
        }
        return kundentermine.get(kundenterminId);
    }

    /**
     * Speichert ein Kundentermin in der Datenbank
     * @param kundentermin
     */
    public static int speichern(Kundentermin kundentermin)
    {
        try
        { 
            String SQLBefehl;
            Connection con = DbConnection.connect();
            if(kundentermin.getKundenterminId()==0)
            {
                SQLBefehl = KundenterminDb.getSqlStringInsert();
            }
            else
            {
                SQLBefehl = KundenterminDb.getSqlStringUpdate();
            }               
            String generatedColumns[] = { "Kundenterminid" };
            PreparedStatement st = con.prepareStatement(SQLBefehl, 
                                                        generatedColumns);
            st.setString(1, kundentermin.getStartzeit().toString());
            st.setString(2, kundentermin.getEndzeit().toString());            
            st.setDate(3, ZeitUtil.calendarZuSql(kundentermin.getDatum()));
            st.setInt(4, kundentermin.getKundeId());
            if(kundentermin.getKundenterminId()!= 0)
            {
                st.setInt(5, kundentermin.getKundenterminId());
            }
            st.executeUpdate();
   

            if(kundentermin.getKundenterminId()==0)
            {      
                ResultSet generatedKeys = st.getGeneratedKeys();   
                if (generatedKeys.next()) 
                {
                    kundentermin.setKundenterminId
                                           ((int) generatedKeys.getInt(1));
                }
                else 
                {
                    throw new SQLException("Fehler bei Sql-Befehl "+
                                           ", Fahrzeug konnte nicht "+
                                           "gespeichert werden.");
                } 


            }   
            kundentermine.put(kundentermin.getKundenterminId(), kundentermin);
            st.close();         
       
       
       } catch (Exception e) { 
            e.printStackTrace();
            }
       
        return kundentermin.getKundenterminId();        

    }
	
    /**
     * Gibt Liste aller Kundentermine für ein bestimmtes Datum zurück.
     * @param datum
     * @return ArrayList<Fahrzeug>
     */
    public static ArrayList<Kundentermin> 
		gibVerfuegbareKundentermine(Calendar datumCalendar) 
    {    
        ArrayList<Kundentermin> liste = new ArrayList<>();
        try
        {  
            HashMap<String, Object> werte = new HashMap<>();       
            werte.put("DATUM",ZeitUtil.calendarZuSql(datumCalendar));
            liste = KundenterminDb.sucheMitFilter(werte);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        liste.sort(null);
        return liste;

    }
    
    /**
     * Löscht den Kundentermin mit der Eingegebenen Id
     * @param kundenTerminId
     * @return anzahlGeloeschteZeilen
     */
    public static int loescheZeitfenster(int kundenterminId)
    {    	
    	String query = "DELETE FROM zeitfenster WHERE kundenterminId = ?  "
    	        +"AND NOT EXISTS "
    	        +"(SELECT * FROM Termin WHERE ZeitfensterId = KundenterminId)";

    	int anzahlGeloschteElemente = 0;
    	try
    	{    		
    		Connection con = DbConnection.connect();
    		PreparedStatement st = con.prepareStatement(query);
    		    		
    		st.setInt(1,kundenterminId);
    		
    		anzahlGeloschteElemente=st.executeUpdate();
    		st.close();    		
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}    	
    	return anzahlGeloschteElemente;
    }
}
