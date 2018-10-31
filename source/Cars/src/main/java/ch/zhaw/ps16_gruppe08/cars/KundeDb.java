package ch.zhaw.ps16_gruppe08.cars;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Behandelt alle Datenbankbefehle in Bezug zu Kunden, speichert zusätzlich
 * die Kunden in einer ArrayList, für schnelleren Zugriff bei erneuter Abfrage
 * @version 1.0
 * 
 */
public class KundeDb {

    private static HashMap<Integer,Kunde> kunden = new HashMap<>();
	
    /**
     * Methode führt den select für die 
     * Datenbank-Tabelle Kunde
     */
    public static void select()
    {
            try 
            {
                Connection con = DbConnection.connect();
                Statement st = con.createStatement();
                String query = "SELECT * FROM Kunde";
                ResultSet result = st.executeQuery(query);
                while(result.next()) 
                {
                    int status = result.getInt("status");
                    boolean istAktiv;
                    if(status == 1)
                    { 
                        istAktiv = true;
                    } 
                    else
                    { 
                        istAktiv = false;
                    }
                         
                    kunden.put(result.getInt("kundeId"),
                               new Kunde(result.getInt("kundeid"),
                                         istAktiv,
                                         result.getString("firma"),
                                         new Point(result.getInt("standortx"),
                                         result.getInt("standorty")),
                                         result.getInt("prioritaet"),
                                         result.getString("email"),
                                         result.getString("telnr")
                                         )
                              );
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
     * Gibt den SQL-Befehl zurück welcher für das Einfügen benötigt wird.
     */
    public static String getSqlStringInsert()
    {
        return "INSERT INTO kunde (FIRMA, EMAIL, TELNR, STATUS, "+
               " STANDORTX, STANDORTY,PRIORITAET) VALUES (?,?,?,?,?,?,?)";
	}
	
    /**
     * Gibt den SQL-Befehl zurück, welcher für das Updaten benötigt wird.
     */
    public static String getSqlStringUpdate()
    {
        return "UPDATE kunde SET Firma = ?, EMAIL = ?, TELNR = ?, "+
               " STATUS = ?, STANDORTX = ?, STANDORTY = ?, PRIORITAET = ? "+
               " WHERE KUNDEID = ?";
    }
	
    /**
     * Liefert eine Liste von Kunden aufgrund des Suchstrings.
     * @param searchString
     * @return Liste von Kunde
     * @throws SQLException
     */
    public static ArrayList<Kunde> sucheMitFilter(final String searchString)
		throws SQLException 
    {
        select();
        ArrayList<Kunde> liste = new ArrayList<>();
        for(Integer element : kunden.keySet())
        {
            liste.add(kunden.get(element));
        }
        liste.sort(null);
        return liste;
    }


    /**
     * Lädt die Daten eines Kunden aus der Datenbank.
     * @param kundeId
     * @return ein Kunde aus der Datenbank
     */
    public static Kunde sucheMitId(final int kundeId)
    {
        if(!kunden.containsKey(kundeId))
        {
            select();
        }

        return kunden.get(kundeId);
    }



    /**
     * Speichert ein Kunde in der Datenbank
     * @param kunde
     */
    public static int speichern(Kunde kunde)
    {
        try
        {
            int istAktiv = 0;        
            if(kunde.istStatusAktiv())
            {
                istAktiv = 1;
            } 
            else 
            {
                istAktiv = 0;
            }           
	            
            String SQLBefehl;
            Connection con = DbConnection.connect();
	            
            if(kunde.getKundeId()==0)
            {
                SQLBefehl = KundeDb.getSqlStringInsert();
            }
            else
            {
                SQLBefehl = KundeDb.getSqlStringUpdate();
            }	            
            String generatedColumns[] = { "kundeId" };
            PreparedStatement st = con.prepareStatement(SQLBefehl, 
                                                        generatedColumns);
            st.setString(1, kunde.getName());
            st.setString(2, kunde.getEmailAdresse());
            st.setString(3, kunde.getTelefonNr());
            st.setInt(4, istAktiv);
            st.setInt(5, kunde.getKoordinaten().x);
            st.setInt(6, kunde.getKoordinaten().y);
            st.setInt(7, kunde.getPrioritaet());
            
            if(kunde.getKundeId()!= 0)
            {
                st.setInt(8, kunde.getKundeId());
            }
            st.executeUpdate();
       
            if(kunde.getKundeId()==0)
            {      
                ResultSet generatedKeys = st.getGeneratedKeys();   
                if (generatedKeys.next()) 
                {
                    kunde.setKundeId((int) generatedKeys.getInt(1));
                }
                else 
                {
                    throw new SQLException("Fehler bei Sql-Befehl "+
                                           ", Kunde konnte nicht "+
                                           "gespeichert werden.");
                } 	               
            }

            kunden.put(kunde.getKundeId(), kunde);
    
            st.close();	               
	       
        } catch (Exception e) 
            { 
                e.printStackTrace();
            }
		
        return kunde.getKundeId();		
    }

}
