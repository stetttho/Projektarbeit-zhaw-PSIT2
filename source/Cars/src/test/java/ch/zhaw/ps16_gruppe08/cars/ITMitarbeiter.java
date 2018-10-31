package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
/**
  * Führt diverse Integrationtests aus um die Klasse Mitarbeiter im
 * Zusammenspiel mit der Datenbank und dem Servlet zu prüfen.
 * @author Markus
 *
 */ 

public class ITMitarbeiter {
    private HttpServletResponseStub response;
    private HttpServletRequestStub request;
    private MitarbeiterServlet servlet;
    private Benutzer benutzer;
    private HttpSessionStub session;
    
    
    @Before
    public void setUp() throws Exception {
        response = new HttpServletResponseStub();
        request = new HttpServletRequestStub();
        servlet = new MitarbeiterServlet();
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
        assertEquals("mitarbeitende",request.getAttribute("appMainContext"));
    }
    
    /**
     * Teste ob bei Anzeigen ein Mitarbeiter abgelegt wird
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeAnzeigen() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("aktion", "anzeigen");
        request.setParameter("mitarbeiterId", "56");
        servlet.processRequest(request, response);
        assertEquals("Mitarbeiter",request.getAttribute("item")
                .getClass().getSimpleName());        
    }
    
    /**
     * Teste ob eine Liste von Mitarbeitenden geliefert wird, wenn keine
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
        assertEquals("ArrayList",request.getAttribute("mitarbeitende")
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
        assertEquals("ArrayList",request.getAttribute("mitarbeitende")
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
        assertEquals("/WEB-INF/pages/mitarbeiter.jsp",servlet.getZielUrl());
            
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
        request.setParameter("mitarbeiterId", "1");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/mitarbeiter.jsp",servlet.getZielUrl());
             
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
        request.setParameter("mitarbeiterId", "0");
        request.setParameter("name","Hugentobler");
        request.setParameter("vorname","Henry");
        request.setParameter("telNr","033 234 56 78");
        request.setParameter("status", "1");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/mitarbeiter.jsp?aktion=",
                     servlet.getZielUrl());        
    }   
    
    /**
     * Testet, ob die Benutzer eine positive Rückmeldung erhalten beim 
     * speichern eines neuen Mitarbeiters
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenOKMSGNeu() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("mitarbeiterId", "0");
        request.setParameter("name","Hugentobler");
        request.setParameter("vorname","Henry");
        request.setParameter("telNr","033 234 56 78");
        request.setParameter("email", "hugentobler@meier.ch");
        request.setParameter("status", "1");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Mitarbeiter wurde " + 
                "erfolgreich gespeichert.",request.getAttribute("OKMSG"));
    }
    
    /**
     * Testet, ob die Benutzer eine positive Rückmeldung erhalten beim
     * speichern eines bestehenden Mitarbeiters
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenOKMSGbestehend() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("mitarbeiterId", "1");
        request.setParameter("name","Hugentobler");
        request.setParameter("vorname","Henry");
        request.setParameter("telNr","033 234 56 78");
        request.setParameter("email", "hugentobler@test.ch");
        request.setParameter("status", "1");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Mitarbeiteränderung wurde " + 
                "erfolgreich gespeichert.",request.getAttribute("OKMSG"));        
    }
    

}
