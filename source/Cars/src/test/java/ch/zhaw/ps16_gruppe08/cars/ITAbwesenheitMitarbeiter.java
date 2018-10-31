package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
/**
 * Führt diverse Integrationtests aus um die Klasse AbwesenheitMitarbeiter im
 * Zusammenspiel mit der Datenbank und dem Servlet zu prüfen.
 * @author Markus
 * 
 */

public class ITAbwesenheitMitarbeiter 
{
    private HttpServletResponseStub response;
    private HttpServletRequestStub request;
    private AbwesenheitMitarbeiterServlet servlet;
    private Benutzer benutzer;
    private HttpSessionStub session;
    
    
    @Before
    public void setUp() throws Exception 
    {
        response = new HttpServletResponseStub();
        request = new HttpServletRequestStub();
        servlet = new AbwesenheitMitarbeiterServlet();
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
            throws ServletException, IOException, SQLException 
    {
        request.setParameter("aktion", "neu");  
        servlet.processRequest(request, response);
        assertEquals("abwesenheitMitarbeitende",
        		request.getAttribute("appMainContext"));
    }
    
    
    /**
     * Teste ob bei Anzeigen einer Abwesenheit abgelegt wird
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
   @Test
   public void testeAnzeigen() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("aktion", "anzeigen");
        request.setParameter("abwesenheitId", "1425");
        servlet.processRequest(request, response);
        assertEquals("AbwesenheitMitarbeiter",request.getAttribute("item")
                .getClass().getSimpleName());        
    }
 
    /**
     * Teste ob eine Liste von Abwesenheiten geliefert wird, wenn keine
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
        assertEquals("ArrayList",request.getAttribute("abwesenheitMitarbeitende")
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
        assertEquals("ArrayList",request.getAttribute("abwesenheitMitarbeitende")
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
        assertEquals("/WEB-INF/pages/abwesenheitMitarbeiter.jsp",
        		servlet.getZielUrl());
            
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
        request.setParameter("abwesenheitId", "1");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/abwesenheitMitarbeiter.jsp",
        		servlet.getZielUrl());
             
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
        request.setParameter("mitarbeiterId","19");
        request.setParameter("abwesenheitVon", "06.04.2017");
        request.setParameter("abwesenheitBis", "08.04.2017");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/abwesenheitMitarbeiter.jsp?aktion=",
                     servlet.getZielUrl());        
    }   
    
    /**
     * Testet, ob die Benutzer eine positive Rückmeldung erhalten beim 
     * speichern einer neuen Abwesenheit
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenOKMSGNeu() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("mitarbeiterId","88");
        request.setParameter("abwesenheitVon", "06.11.2017");
        request.setParameter("abwesenheitBis", "08.11.2017");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Abwesenheit wurde " + 
                "erfolgreich gespeichert.",request.getAttribute("OKMSG"));
    }
    
    /**
     * Testet, ob die Benutzer eine positive Rückmeldung erhalten beim
     * speichern einer bestehenden Abwesenheit
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeAbwesenheitsMeldungOKMSGbestehend() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("mitarbeiterId","88");
        request.setParameter("abwesenheitVon", "03.08.2017");
        request.setParameter("abwesenheitBis", "06.08.2017");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Abwesenheit wurde "+
        		"erfolgreich gespeichert.",
		  		request.getAttribute("OKMSG"));        
    }
    
    @Test
    public void testeAbwesenheitFmsgDatumVorNachDatumBis()
    		throws ServletException, IOException, SQLException
    {
        request.setParameter("mitarbeiterId","88");
        request.setParameter("abwesenheitVon", "03.04.2017");
        request.setParameter("abwesenheitBis", "01.04.2017");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Die Abwesenheit BIS darf nicht "
    	  		+ "vor der Abwesenheit VON sein.",
		  		request.getAttribute("FMSG"));        
    }
    
    @Test
    public void testeAbwesenheitOkmsgDatumVorGleichDatumBis()
    		throws ServletException, IOException, SQLException
    {
        request.setParameter("mitarbeiterId","88");
        request.setParameter("abwesenheitVon", "06.09.2017");
        request.setParameter("abwesenheitBis", "06.09.2017");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Abwesenheit wurde "+
        		"erfolgreich gespeichert.",
		  		request.getAttribute("OKMSG"));    
    }
    
    @Test
    public void testeAbwesenheitFmsgDatumInVergangenheit()
    		throws ServletException, IOException, SQLException
    {
        request.setParameter("mitarbeiterId","88");
        request.setParameter("abwesenheitVon", "06.01.2017");
        request.setParameter("abwesenheitBis", "10.01.2017");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Das Datum bis darf nicht in der "
          		+ "Vergangenheit liegen",
		  		request.getAttribute("FMSG"));    
    }
}
