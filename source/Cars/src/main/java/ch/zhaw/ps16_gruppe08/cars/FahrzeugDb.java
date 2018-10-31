package ch.zhaw.ps16_gruppe08.cars;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Behandelt alle Datenbankbefehle in Bezug zu Fahrzeugen, solange die 
 * Datenbankverbindung noch nicht aktiv ist,
 * speichert sie die Fahrzeuge in einer ArrayList
 * 
 */
public class FahrzeugDb{

    private static HashMap<Integer,Fahrzeug> fahrzeuge = 
		       new HashMap<>();

	
    /**
     * Methode führt den select aus für die 
     * Datenbank-Tabelle Fahrzeug
     */
    public static void select(final HashMap<String,Object> selektierungsWerte)
	{
        fahrzeuge.clear();
	    try 
	    {
	        Connection con = DbConnection.connect();
            String query = "SELECT * FROM Fahrzeug";
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
                    if(key.equals("ISTAKTIV"))
                    {
                        st.setInt(index,(Integer)selektierungsWerte.get(key));
                    }
                    index++;
                }
            } 
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
                                            
                fahrzeuge.put(result.getInt("fahrzeugid"),
                                  new Fahrzeug(result.getInt("fahrzeugid"),
                                  istAktiv,
                                  result.getString("typ"),
                                  result.getString("kontrollschild")));
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
        return "INSERT INTO fahrzeug (ISTAKTIV, TYP, KONTROLLSCHILD) "+
               " VALUES (?,?,?)";
    }
    
    /**
     * Gibt den SQL-Befehl zurück, welcher für das Updaten benötigt wird
     */
    public static String getSqlStringUpdate()
    {
        return "UPDATE fahrzeug SET ISTAKTIV = ?, TYP = ?, "+
               " KONTROLLSCHILD = ? "+
               " WHERE FAHRZEUGID = ?";
    }	
	
    /**
     * Liefert eine Liste von Fahrzeugen aufgrund des Suchstrings
     * @param searchString
     * @return Liste von Fahrzeugen
     * @throws SQLException
     */
    public static ArrayList<Fahrzeug> sucheMitFilter(
            final HashMap<String,Object> selektierungsWerte)
			throws SQLException 
    {
        select(selektierungsWerte);
        ArrayList<Fahrzeug> liste = new ArrayList<>();
        for(Integer element : fahrzeuge.keySet())
        {
            liste.add(fahrzeuge.get(element));
        }
        liste.sort(null);
        return liste;        
    }


    /**
     * Lädt die Daten eines Fahrzeuges aus der Datenbank
     * @param id
     * @return ein Fahrzeug aus der Datenbank
     */
    public static Fahrzeug sucheMitId(final int fahrzeugId)
    {
	    if(!fahrzeuge.containsKey(fahrzeugId))
	    {
	        select(null);
	    }
	    return fahrzeuge.get(fahrzeugId);
	}



    /**
     * Speichert ein Fahrzeug in der Datenbank
     * @param fahrzeug
     */
    public static int speichern(Fahrzeug fahrzeug)
    {
        try
        {
            int istAktiv = 0;
            if(fahrzeug.istStatusAktiv())
            {
                istAktiv = 1;
            } 
            else 
            {
                istAktiv = 0;
            }           
            
            String SQLBefehl;
            Connection con = DbConnection.connect();
            if(fahrzeug.getFahrzeugId()==0)
            {
                SQLBefehl = FahrzeugDb.getSqlStringInsert();
            }
            else
            {
                SQLBefehl = FahrzeugDb.getSqlStringUpdate();
            }               
            String generatedColumns[] = { "fahrzeugId" };
            PreparedStatement st = con.prepareStatement(SQLBefehl, 
                                                        generatedColumns);
            st.setInt(1, istAktiv);
            st.setString(2, fahrzeug.getTyp());
            st.setString(3, fahrzeug.getKontrollschildNr());
            if(fahrzeug.getFahrzeugId()!= 0)
            {
                st.setInt(4, fahrzeug.getFahrzeugId());
            }
            st.executeUpdate();
   
            if(fahrzeug.getFahrzeugId()==0)
            {      
                ResultSet generatedKeys = st.getGeneratedKeys();   
                if (generatedKeys.next()) 
                {
                    fahrzeug.setFahrzeugId((int) generatedKeys.getInt(1));
                }
                else 
                {
                    throw new SQLException("Fehler bei Sql-Befehl "+
                                           ", Fahrzeug konnte nicht "+
                                           "gespeichert werden.");
                } 

            }   
            fahrzeuge.put(fahrzeug.getFahrzeugId(), fahrzeug);
            st.close();          
       
        } 
        catch (Exception e) 
        { 
            e.printStackTrace();
        }
       
        return fahrzeug.getFahrzeugId();      	

    }
	
    /**
     * Gibt Liste aller aktiven Fahrzeuge für ein bestimmtes Datum zurück.
     * @param datum
     * @return ArrayList<Fahrzeug>
     */
    public static ArrayList<Fahrzeug> gibVerfuegbareFahrzeuge() 
    {
        ArrayList<Fahrzeug> liste = new ArrayList<>();
        try
        {   HashMap<String, Object> werte = new HashMap<>();       
            werte.put("ISTAKTIV",1);
            liste = FahrzeugDb.sucheMitFilter(werte);
        }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	    }
        liste.sort(null);
        return liste;

    }

}
