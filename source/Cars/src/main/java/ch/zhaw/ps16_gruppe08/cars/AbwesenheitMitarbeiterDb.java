package ch.zhaw.ps16_gruppe08.cars;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Behandelt alle Datenbankbefehle in Bezug zu Abwesenheiten, solange die 
 * Datenbankverbindung noch nicht aktiv ist,
 * speichert sie die Abwesenheiten in einer HashMap
 * 
 */
public class AbwesenheitMitarbeiterDb
{
	private static HashMap<Integer, AbwesenheitMitarbeiter> abwesenheitMitarbeitende 
		       = new HashMap<>();
	
	/**
	 * Methode führt den select aus für die 
	 * Datenbank-Tabelle Abwesenheit mit Mitarbeitername
	 */
	public static void select(final HashMap<String,Object> selektierungsWerte)
	{
	    try 
        {
            Connection con = DbConnection.connect();
            String query = "SELECT * FROM ABWESENHEIT";
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
                    if(key.equals("ABWESENHEITVON") || 
                            key.equals("ABWESENHEITBIS"))
                    {
                        st.setDate(index,
                                   (java.sql.Date)selektierungsWerte.get(key));
                    }
                    index++;
                }
            }            
            
            ResultSet result = st.executeQuery();
            abwesenheitMitarbeitende.clear();
            while(result.next()) 
            {                                              
                abwesenheitMitarbeitende.put(result.getInt("ABWESENHEITID"),
                                  new AbwesenheitMitarbeiter(
                                        result.getInt("ABWESENHEITID"),
                                        result.getInt("MITARBEITERID"),
                                        ZeitUtil.sqlDateZuCalendar(
                                                result.getDate("ABWESENHEITVON")),
                                        ZeitUtil.sqlDateZuCalendar(
                                                result.getDate("ABWESENHEITBIS"))));
            }
            result.close();
            st.close();
        } 
	    catch (Exception e) 
	    { 
            e.printStackTrace();
        }
	}
	
    /**
     * Gibt den SQL-Befehl zurück welcher für das Einfügen benötigt wird
     */
    public static String getSqlStringInsert()
    {
            return "INSERT INTO abwesenheit (MITARBEITERID, ABWESENHEITVON, "+
                   "ABWESENHEITBIS) VALUES (?,?,?)";
    }
    
    /**
     * Gibt den SQL-Befehl zurück, welcher für das Updaten benötigt wird
     */
    public static String getSqlStringUpdate()
    {
        return "UPDATE abwesenheit SET MITARBEITERID = ?, "
        		+ "ABWESENHEITVON = ?, "+
               "ABWESENHEITBIS = ? " +
               " WHERE ABWESENHEITID = ?";
    }
    	
	
	/**
	 * Liefert eine Liste von Abwesenheit der Mitarbeitenden aufgrund 
	 * des Suchstrings
	 * @param searchString
	 * @return Liste von Fahrzeugen
	 * @throws SQLException
	 */
	public static ArrayList<AbwesenheitMitarbeiter> sucheMitFilter(
			final HashMap<String,Object> selektierungsWerte)
					throws SQLException 
	{
        select(selektierungsWerte);
        ArrayList<AbwesenheitMitarbeiter> liste = new ArrayList<>();
        for(Integer element : abwesenheitMitarbeitende.keySet())
        {
            liste.add(abwesenheitMitarbeitende.get(element));
        }
        liste.sort(null);
        return liste;         
	}


	/**
	 * Lädt die Daten einer Abwesenheit aus der Datenbank
	 * @param abwesenheitId
	 * @return eine Abwesenheit aus der Datenbank
	 */
	public static AbwesenheitMitarbeiter sucheMitId(
			final int abwesenheitId)
	{
	    if(!abwesenheitMitarbeitende.containsKey(abwesenheitId))
	    {
	        select(null);
	    }
		return abwesenheitMitarbeitende.get(abwesenheitId);
	}



	/**
	 * Speichert eine Mitarbeiterabwesenheit in der Datenbank
	 * @param AbwesenheitMitarbeiter
	 */
	public static int speichern(AbwesenheitMitarbeiter 
			abwesenheitMitarbeiter)
	{
		try
		{
            String SQLBefehl;
            Connection con = DbConnection.connect();
            if(abwesenheitMitarbeiter.getAbwesenheitId()==0)
            {
            	SQLBefehl = AbwesenheitMitarbeiterDb.getSqlStringInsert();
            }
            else
            {
            	SQLBefehl = AbwesenheitMitarbeiterDb.getSqlStringUpdate();
            }               
            String generatedColumns[] = { "ABWESENHEITID" };
            PreparedStatement st = con.prepareStatement(SQLBefehl, 
            		generatedColumns);
            st.setInt(1, abwesenheitMitarbeiter.getMitarbeiterId());
            st.setDate(2, ZeitUtil.calendarZuSql(
            		abwesenheitMitarbeiter.getAbwesenheitVon()));          
            st.setDate(3, ZeitUtil.calendarZuSql(
            		abwesenheitMitarbeiter.getAbwesenheitBis()));
            if(abwesenheitMitarbeiter.getAbwesenheitId() != 0)
            {
                st.setInt(4, abwesenheitMitarbeiter.getAbwesenheitId());
            }
            
            st.executeUpdate();
            
            if(abwesenheitMitarbeiter.getAbwesenheitId()==0)
            {      
                ResultSet generatedKeys = st.getGeneratedKeys();   
                if (generatedKeys.next()) 
                {
                	abwesenheitMitarbeiter.setAbwesenheitId
                                           ((int) generatedKeys.getInt(1));
                }
                else 
                {
                    throw new SQLException("Fehler bei Sql-Befehl "+
                                           ", Abwesenheit konnte nicht "+
                                           "gespeichert werden.");
                } 


            }   
            abwesenheitMitarbeitende.put(
            		abwesenheitMitarbeiter.getAbwesenheitId(), 
            		abwesenheitMitarbeiter);
            st.close();                      
		} 
		catch (Exception e) 
		{ 
            e.printStackTrace();
		}
    
        return abwesenheitMitarbeiter.getAbwesenheitId();    
	}
	
}
