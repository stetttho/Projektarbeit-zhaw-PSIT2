package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
/**
 * Führt diverse Integrationtests aus um die Klasse Fahrzeug im
 * Zusammenspiel mit der Datenbank und dem Servlet zu prüfen.
 * @author Markus
 * 
 */

public class ITFahrzeug {
    private HttpServletResponseStub response;
    private HttpServletRequestStub request;
    private FahrzeugServlet servlet;
    private Benutzer benutzer;
    private HttpSessionStub session;
    
    
    @Before
    public void setUp() throws Exception {
        response = new HttpServletResponseStub();
        request = new HttpServletRequestStub();
        servlet = new FahrzeugServlet();
        benutzer = new Benutzer();
        session = new HttpSessionStub();
        request.setAttribute("session", session);
        session.setBenutzer(benutzer);
        benutzer.setName("admin");
        benutzer.setPasswort("admin");
        benutzer.setSchreibrechte();
    }

    /**
     * Teste ob der appMainContext richtig gesetzt wird
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    @Test
    public void testeAppMainContext() 
            throws ServletException, IOException, SQLException {

        request.setParameter("aktion", "neu");  
        servlet.processRequest(request, response);
        assertEquals("fahrzeuge",request.getAttribute("appMainContext"));
    }
    
    /**
     * Teste ob bei Anzeigen ein Fahrzeug abgelegt wird
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeAnzeigen() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("aktion", "anzeigen");
        request.setParameter("fahrzeugId", "8");
        servlet.processRequest(request, response);
        assertEquals("Fahrzeug",request.getAttribute("item")
                .getClass().getSimpleName());        
    }
    
    /**
     * Teste ob eine Liste von Fahrzeugen geliefert wird, wenn keine
     * Parameter angegeben werden
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeListe() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("aktion", "");
        servlet.processRequest(request, response);
        assertEquals("ArrayList",request.getAttribute("fahrzeuge")
                .getClass().getSimpleName());        
    }
    
    /**
     * Teste ob bei einer ungültigen Aktion trotzdem die Liste angezeigt wird
     */
    @Test
    public void testeUngultigeAktion() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("aktion", "abc");
        servlet.processRequest(request, response);
        assertEquals("ArrayList",request.getAttribute("fahrzeuge")
                .getClass().getSimpleName());        
    }
    
    /**
     * Testet, ob die ZielUrl nach der Aktionsmöglichkeit neu die ZielUrl
     * für das JSP korrekt ist
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    
    @Test
    public void testeZielUrlNeu() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("aktion", "neu");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/fahrzeug.jsp",servlet.getZielUrl());
            
    }
    
    /**
     * Testet, ob die ZielUrl nach der Aktionsmöglichkeit anzeigen die ZielUrl
     * für das JSP korrekt ist
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    
    @Test
    public void testeZielUrlAnzeigen() 
            throws ServletException, IOException, SQLException
    {            
        request.setParameter("aktion", "anzeigen");
        request.setParameter("fahrzeugId", "1");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/fahrzeug.jsp",servlet.getZielUrl());
             
    } 
    
    /**
     * Testet, ob die ZielUrl nach der Aktionsmöglichkeit speichern die ZielUrl
     * für das JSP korrekt ist
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    
    @Test
    public void testeZielUrlSpeichern() 
            throws ServletException, IOException, SQLException
    {          
        request.setParameter("fahrzeugId", "0");
        request.setParameter("typ","VW Käfer");
        request.setParameter("kontrollschildNr","LU 123 445");
        request.setParameter("status", "1");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/fahrzeug.jsp?aktion=",
                     servlet.getZielUrl());        
    }   
    
    /**
     * Testet, ob die Benutzer eine positive Rückmeldung erhalten beim 
     * speichern eines neuen Fahrzeuges
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenOKMSGNeu() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("fahrzeugId", "0");
        request.setParameter("typ","VW Käfer");
        request.setParameter("kontrollschildNr","LU 123 445");
        request.setParameter("status", "1");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Fahrzeug wurde " + 
                "erfolgreich gespeichert.",request.getAttribute("OKMSG"));
    }
    
    /**
     * Testet, ob die Benutzer eine positive Rückmeldung erhalten beim
     * speichern eines bestehenden Fahrzeuges
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenOKMSGbestehend() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("fahrzeugId", "1");
        request.setParameter("typ","VW Käfer");
        request.setParameter("kontrollschildNr","LU 123 445");
        request.setParameter("status", "1");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Fahrzeugänderung wurde " + 
                "erfolgreich gespeichert.",request.getAttribute("OKMSG"));        
    }
    
    /**
     * Testet, ob die Benutzer eine negative Rückmeldung erhalten beim
     * speichern eines neuen Fahrzeuges mit fehlender Angabe zum Typ
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenFmsgFehlendeAngabeTyp() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("fahrzeugId", "1");
        request.setParameter("typ","");
        request.setParameter("kontrollschildNr","LU 123 445");
        request.setParameter("status", "1");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Wegen fehlenden Angaben konnte das "
        		+ "Fahrzeug nicht gespeichert werden."
        		,request.getAttribute("FMSG"));        
    }

    /**
     * Testet, ob die Benutzer eine negative Rückmeldung erhalten beim
     * speichern eines neuen Fahrzeuges mit fehlender Angabe zum 
     * TypKontrollschild
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenFmsgFehlendeAngabeKontrollschild() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("fahrzeugId", "1");
        request.setParameter("typ","VW Käfer");
        request.setParameter("kontrollschildNr","");
        request.setParameter("status", "1");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Wegen fehlenden Angaben konnte das "
        		+ "Fahrzeug nicht gespeichert werden."
        		,request.getAttribute("FMSG"));        
    }
}
