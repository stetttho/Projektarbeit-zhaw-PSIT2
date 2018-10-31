package ch.zhaw.ps16_gruppe08.cars;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;


/**
 * Behandelt alle Datenbankbefehle in Bezug zu definitiven Wochenplaenen.
 * 
 * 
 */
public class WochenplanDb {
    
    private static Wochenplan wochenplanAusgabe;  
    /**
     * Methode führt den select aus für die 
     * Wochenplan-Tabelle für einen bestimmten Wochenplan.
     */
    public static void selectWochenplan(int fahrzeugId, 
                                        int mitarbeiterId,
                                        int planId)
        {
            try 
            {
                Connection con = DbConnection.connect();
                String query = "select t.Datum, t.Uhrzeit, t.mitarbeiterId, "
                        +      "t.fahrzeugId, t.ZeitfensterId "
                        +      "from termin t join wochenplanvariante w "
                        +      "on t.varianteid = w.varianteid "
                        +      "join plan p "
                        +      "on t.planid = p.planid "
                        +      "join mitarbeiter m "
                        +      "on t.mitarbeiterid = m.mitarbeiterid "
                        +      "join fahrzeug f "
                        +      "on t.fahrzeugid = f.fahrzeugid "
                        +      "join kunde k "
                        +      "on t.kundeid = k.kundeid "
                        +      "where w.statusdef = 1 AND t.planid = ? "; 
     
                if (fahrzeugId > 0)    
                {
                    query += "AND f.fahrzeugId = ? ";
                }
                if (mitarbeiterId > 0)    
                {
                    query += "AND m.mitarbeiterId = ? ";
                }
                query += "ORDER BY Datum, mitarbeiterId, fahrzeugId";
                PreparedStatement st = con.prepareStatement(query);
                st.setInt(1, planId);
                if(fahrzeugId>0) 
                {
                st.setInt(2,fahrzeugId);
                }
                if(mitarbeiterId>0) 
                {
                st.setInt(2,mitarbeiterId);
                }
                
                ResultSet result = st.executeQuery();   
                int letzteMitarbeiterId = 0;
                int letzteFahrzeugId = 0;
                Calendar letztesDatum = Calendar.getInstance();
                Route route = new Route();
                Wochenplan wochenplan = new Wochenplan();
                
                while(result.next()) 
                {       
                    if(result.getInt("fahrzeugId") != letzteFahrzeugId ||
                       result.getInt("mitarbeiterId") != letzteMitarbeiterId ||
                       result.getDate("Datum").after(letztesDatum.getTime()))
                    {  
                        route = new Route(MitarbeiterDb.sucheMitId(
                                                result.getInt("mitarbeiterId")),
                                                FahrzeugDb.sucheMitId(
                                                result.getInt("fahrzeugId")),
                                                ZeitUtil.sqlDateZuCalendar(
                                                     result.getDate("datum")));
                        wochenplan.addRoute(route);  
                        letzteFahrzeugId = result.getInt("fahrzeugId");
                        letzteMitarbeiterId = result.getInt("mitarbeiterId");
                        letztesDatum.clear();
                        letztesDatum = ZeitUtil.sqlDateZuCalendar(
                                result.getDate("datum"));
                    }                    
                    Kundentermin kundentermin = 
                    KundenterminDb.sucheMitId(result.getInt("ZeitfensterId"));
                    route.terminHinzufugen(kundentermin, 
                                           ZeitUtil.stringZuLocalTime(
                                                 result.getString("Uhrzeit")));
                  
                }
            wochenplanAusgabe = wochenplan;       
            result.close();
            st.close();
            } catch (Exception e) { 
            e.printStackTrace();
            }
        }
    public static Wochenplan gibWochenplanFuerMitarbeiter(
            int mitarbeiterId, 
            int planId)
    {
        selectWochenplan(-1, mitarbeiterId, planId);
        return wochenplanAusgabe;
    }      
    
    public static Wochenplan gibWochenplanFuerFahrzeug(
            int fahrzeugId, 
            int planId)
    {
        selectWochenplan(fahrzeugId, -1, planId);
        return wochenplanAusgabe;
    }
    
    /**
     * Methode führt den select aus für die 
     * Schluesselausgabe-Tabelle für ein bestimmtes Datum.
     * 
     * @param datum
     */
    public static Schluesselausgabe selectSchluesselAusgabe(Calendar datum)
    {
        Schluesselausgabe schluesselausgabe = new Schluesselausgabe(datum);
            try 
            {
                Connection con = DbConnection.connect();
                String query = "select t.Datum, t.mitarbeiterId, "
                        +      "t.fahrzeugId "
                        +      "from termin t join wochenplanvariante w "
                        +      "on t.varianteid = w.varianteid "                       
                        +      "where w.statusdef = 1 AND t.datum = ? "
                        +       "group by t.fahrzeugid, t.mitarbeiterid, " 
                        +       "t.datum"; 
           

                PreparedStatement st = con.prepareStatement(query);
                st.setDate(1, ZeitUtil.calendarZuSql(datum));
             
                ResultSet result = st.executeQuery();                                               
                
                while(result.next()) 
                {       
                    schluesselausgabe.addEintrag(
                       MitarbeiterDb.sucheMitId(result.getInt("mitarbeiterId")), 
                       FahrzeugDb.sucheMitId(result.getInt("fahrzeugId")));                      
 
                }
             
            result.close();
            st.close();
            } catch (Exception e) { 
            e.printStackTrace();
            }
        
            return schluesselausgabe;
    }    
}
