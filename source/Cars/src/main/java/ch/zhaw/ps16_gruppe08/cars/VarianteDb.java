package ch.zhaw.ps16_gruppe08.cars;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/** 
 * Behandelt alle Datenbankbefehle in Bezug zu Wochenplanvarianten.
 * @version 1.0
 *
 */
public class VarianteDb {
        
    /**
     * Gibt den SQL-Befehl zurück welcher für das Einfügen benötigt wird.
     */
    public static String getSqlStringInsert()
    {
        return "INSERT INTO Wochenplanvariante (PLANID, BEWERTUNG, "+
                            " STATUSDEF) VALUES (?,?,?)";
    }
    
    
    /**
     * Speichert eine WochenplanVariante in der Datenbank inkl.
     * der Informationen zu den Routen.
     * 
     * @param variante
     */
    public static int speichern(Variante variante)
    {
        int varianteId = -1;
        int definitiv;
        try
        {                 
            String SQLBefehl;
            Connection con = DbConnection.connect();
                

            SQLBefehl = VarianteDb.getSqlStringInsert();
              
            String generatedColumns[] = { "VarianteId" };
            PreparedStatement st = con.prepareStatement(SQLBefehl,
                                                        generatedColumns);            
            st.setInt(1, variante.getPlanId());            
            st.setInt(2, variante.getAnzahlVerpassteKunde());
            if(variante.getStatusDefinitiv())
            {
                definitiv = 1;
            }
            else
            {
                definitiv = 0;
            }
            st.setInt(3,definitiv);
            
            st.executeUpdate();
            
            ResultSet generatedKeys = st.getGeneratedKeys();   
            if (generatedKeys.next()) 
            {
                variante.setVarianteId(generatedKeys.getInt(1));
            }
            else 
            {
                throw new SQLException("Fehler bei Sql-Befehl "+
                                           ", Variante konnte nicht "+
                                           "gespeichert werden.");
            }                              
    
            st.close();
            
            for(Route element : variante.getRouten())
            {
                RouteDb.speichern(variante.getPlanId(), 
                                  variante.getVarianteId(), 
                                  element);
            }
           
        } catch (Exception e) 
            { 
                e.printStackTrace();
            }
        
        return varianteId;      
    }

}
