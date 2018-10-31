package ch.zhaw.ps16_gruppe08.cars;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Behandelt alle Datenbankbefehle in Bezug zu Mitarbeiter
 * speichert sie die Mitarbeiter in einer HashMap
 * @version 1.0
 * 
 */
public class MitarbeiterDb {

    private static HashMap<Integer, Mitarbeiter> mitarbeitende= new HashMap<>();
	
    /**
     * Methode führt den select aus für die 
     * Datenbank-Tabelle Mitarbeiter
     */
    public static void select()
    {
        try 
        {
            Connection con = DbConnection.connect();
            Statement st = con.createStatement();
            String query = "SELECT * FROM Mitarbeiter";
            ResultSet result = st.executeQuery(query);
            while(result.next()) 
            {
                int status = result.getInt("istaktiv");
                boolean istAktiv;
                if(status == 1)
                { 
                    istAktiv = true;
                } 
                else
                { 
                    istAktiv = false;
                }
                          
                mitarbeitende.put(result.getInt("mitarbeiterid"),
                                  new Mitarbeiter(
                                          result.getInt("mitarbeiterid"),
                                          istAktiv,
                                          result.getString("name"),
                                          result.getString("vorname"),
                                          result.getString("telefonnr"),
                                          result.getString("email")));
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
        return "INSERT INTO mitarbeiter (NAME, VORNAME, "+
               "TELEFONNR, EMAIL, ISTAKTIV) VALUES (?,?,?,?,?)";
    }
    
    /**
     * Gibt den SQL-Befehl zurück, welcher für das Updaten benötigt wird
     */
    public static String getSqlStringUpdate()
    {
        return "UPDATE mitarbeiter SET NAME = ?, VORNAME = ?, "+
               " TELEFONNR = ?, EMAIL = ?, ISTAKTIV = ? "+
               " WHERE MITARBEITERID = ?";
    }
    	
	
    /**
     * Liefert eine Liste von Mitarbeitenden aufgrund des Suchstrings
     * @param searchString
     * @return Liste von Fahrzeugen
     * @throws SQLException
     */
    public static ArrayList<Mitarbeiter> sucheMitFilter(
						final String searchString)
			throws SQLException 
    {

        select();
        ArrayList<Mitarbeiter> liste = new ArrayList<>();
        for(Integer element : mitarbeitende.keySet())
        {
            liste.add(mitarbeitende.get(element));
        }
        liste.sort(null);
        return liste;         
	}


    /**
     * Lädt die Daten eines Mitarbeiters aus der Datenbank
     * @param id
     * @return ein Mitarbeiter aus der Datenbank
     */
    public static Mitarbeiter sucheMitId(final int mitarbeiterId)
    {
        if(!mitarbeitende.containsKey(mitarbeiterId))
        {
            select();
        }
        return mitarbeitende.get(mitarbeiterId);
    }



    /**
     * Speichert einen Mitarbeiter in der Datenbank.
     * @param mitarbeiter
     */
    public static int speichern(Mitarbeiter mitarbeiter)
    {
        try
        {
            
            String SQLBefehl;
            Connection con = DbConnection.connect();
            if(mitarbeiter.getMitarbeiterId()==0)
            {
                SQLBefehl = MitarbeiterDb.getSqlStringInsert();
            }
            else
            {
                SQLBefehl = MitarbeiterDb.getSqlStringUpdate();
            }               
            String generatedColumns[] = { "MitarbeiterId" };
            PreparedStatement st = con.prepareStatement(SQLBefehl, 
                                                        generatedColumns);
            st.setString(1, mitarbeiter.getName());
            st.setString(2, mitarbeiter.getVorname());          
            st.setString(3, mitarbeiter.getTelNr());
            st.setString(4, mitarbeiter.getEmail());
            int statusAktiv;
            if(mitarbeiter.istStatusAktiv())
            {
                statusAktiv = 1;
            }
            else
            {
                statusAktiv = 0;
            }
            st.setInt(5, statusAktiv);
            if(mitarbeiter.getMitarbeiterId()!= 0)
            {
                st.setInt(6, mitarbeiter.getMitarbeiterId());
            }
            st.executeUpdate();

            if(mitarbeiter.getMitarbeiterId()==0)
            {      
                ResultSet generatedKeys = st.getGeneratedKeys();   
                if (generatedKeys.next()) 
                {
                    mitarbeiter.setMitarbeiterId
                                           ((int) generatedKeys.getInt(1));
                }
                else 
                {
                    throw new SQLException("Fehler bei Sql-Befehl "+
                                           ", Mitarbeitende konnte nicht "+
                                           "gespeichert werden.");
                } 

            }   
            mitarbeitende.put(mitarbeiter.getMitarbeiterId(), mitarbeiter);
            st.close();          
       
       } catch (Exception e) { 
            e.printStackTrace();
            }
        
        return mitarbeiter.getMitarbeiterId();    
    }
	
	 /**
     * Gibt Liste aller aktiven und nicht abwesenden Mitarbeiter für ein 
     * bestimmtes Datum zurück.
     * @param datum
     * @return ArrayList<Mitarbeiter>
     */

    public static ArrayList<Mitarbeiter> 
        gibVerfuegbareMitarbeiter(Calendar datum) 
    {
                          
        ArrayList<Mitarbeiter> liste = new ArrayList<>();
            
            try 
            {
                   
                Connection con = DbConnection.connect();
                String query="SELECT * FROM MITARBEITER "+
                    "LEFT OUTER JOIN ABWESENHEIT ON "+
                    "MITARBEITER.MITARBEITERID = ABWESENHEIT.MITARBEITERID "+ 
                    "AND ABWESENHEITVON < ? "+ 
                    "AND ABWESENHEITBIS > ? "+
                    "WHERE ABWESENHEITID IS NULL AND ISTAKTIV = 1";               
                PreparedStatement st = con.prepareStatement(query);
                
                st.setDate(1,ZeitUtil.calendarZuSql(datum));
                st.setDate(2,ZeitUtil.calendarZuSql(datum));

                ResultSet result = st.executeQuery();
                while(result.next()) 
                {
                    int status = result.getInt("istaktiv");
                    boolean istAktiv;
                    if(status == 1)
                    { 
                        istAktiv = true;
                    } 
                    else
                    { 
                        istAktiv = false;
                    }
                              
                   liste.add(new Mitarbeiter(
                    		result.getInt("mitarbeiterid"),
                    		istAktiv,
                    		result.getString("name"),
                    		result.getString("vorname"),
                    		result.getString("telefonnr"),
                    		result.getString("email")));
                }
                result.close();
                st.close();
            } catch (Exception e) { 
                e.printStackTrace();
            }
        liste.sort(null);    
        return liste;
    }

}
