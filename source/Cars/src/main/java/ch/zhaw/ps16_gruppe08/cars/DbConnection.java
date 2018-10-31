package ch.zhaw.ps16_gruppe08.cars;
import java.sql.*;

/** 
 * Diese Klasse stellt die Verbindung zur Datenbank her und hält die Verbindung
 * offen, sodass sie von der Applikation jederzeit genutzt werden kann
 *
 */

public class DbConnection {
	
    private static DbConnection instanz = new DbConnection();
    private Connection verbindung;
    
    /**
     * Konstruktor für die DbConnection-Methode, stellt die Verbindung
     * zur Datenbank her
     */
    private DbConnection()
    {
        try
        { 
            String dbUser = "xyz";
            String dbPass = "xyz";
            String dbURL = "jdbc:oracle:thin:@dublin.zhaw.ch:1521/xe";
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection(dbURL, 
                                                         dbUser, 
                                                         dbPass);
            this.verbindung = con;
        } catch (Exception e) 
            { 
            e.printStackTrace();
            }                   
    }
	
    /**
     * Methode stellt die Verindung zur Datenbank her.
     * Bei Abfrage wird die Connection zurückgegeben.
     * @return DbConnection
     */
    public static Connection connect()
    {
        return DbConnection.getInstanz().getVerbindung();
        
    }
    
    /**
     * Gibt das aktuelle Objekt zurück, auf welchem die Instanz hinterlegt
     * ist
     * @return instanz
     */
    public static DbConnection getInstanz()
    {
        return instanz;
    }
    
    /**
     * Gibt die Verbindung (Connection-Objekt) zurück, auf welches
     * Datenbankbefehle abgesetzt werden können, zuerst wird jedoch noch 
     * geprüft, ob die Verbindung noch aktiv ist (nicht geschlossen wurde),
     * wenn sie geschlossen wurde oder es einen Fehler
     * damit gibt, wird sie neu aufgebaut
     * @return verbindung
     */
    public Connection getVerbindung()
    {
        try 
        {
            if(!this.verbindung.isValid(3))
            {
                this.verbindung.close();
                instanz = new DbConnection();
            }
        } 
        catch (SQLException e) 
        {     
            instanz = new DbConnection();
        }
        return this.verbindung;        
    }
}
