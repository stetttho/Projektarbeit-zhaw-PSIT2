package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;

public class ITKunde 
{
    /**
     * Führt diverse Integrationtests aus um die Klasse Kunde im
     * Zusammenspiel mit der Datenbank und dem Servlet zu prüfen.
     * 
     * 
     */
	private HttpServletResponseStub response;
	private HttpServletRequestStub request;
	private KundeServlet servlet;
    private Benutzer benutzer;
    private HttpSessionStub session;
	    
	    
	@Before
	public void setUp() throws Exception 
	{
		response = new HttpServletResponseStub();
		request = new HttpServletRequestStub();
		servlet = new KundeServlet();
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
		assertEquals("kunde",request.getAttribute("appMainContext"));
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
        request.setParameter("kundeId", "1");
        servlet.processRequest(request, response);
        assertEquals("Kunde",request.getAttribute("item")
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
        assertEquals("ArrayList",request.getAttribute("kunde")
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
        assertEquals("ArrayList",request.getAttribute("kunde")
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
        assertEquals("/WEB-INF/pages/kunde.jsp",servlet.getZielUrl());
            
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
        request.setParameter("kundeId", "1");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/kunde.jsp",servlet.getZielUrl());
             
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
        request.setParameter("kundeId", "0");
        request.setParameter("name","Max Muster");
        request.setParameter("xKoordinate","122000");
        request.setParameter("yKoordinate", "30000");
        request.setParameter("prioritaet", "3");
        request.setParameter("emailAdresse", "Max.Muster@beispiel.ch");
        request.setParameter("telNr", "+41 79 466 21 02");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/kunde.jsp?aktion=",servlet.getZielUrl());        
    }   
    
    /**
     * Testet, ob die Benutzer eine positive Rückmeldung erhalten beim 
     * speichern eines neuen Kunden
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenOkmsgNeu() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("kundeId", "3");
        request.setParameter("name","Max Muster");
        request.setParameter("xKoordinate","122000");
        request.setParameter("yKoordinate", "30000");
        request.setParameter("prioritaet", "3");
        request.setParameter("emailAdresse", "Max.Muster@beispiel.ch");
        request.setParameter("telNr", "+41 79 466 21 02");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Kundeänderung wurde " + 
                "erfolgreich gespeichert.",request.getAttribute("OKMSG"));
    }

  
    
    /**
     * Testet, ob die Benutzer eine negative Rückmeldung erhalten beim 
     * speichern eines Kunden mit falschen Koordinaten
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeKundeFmsgFalscheKoordinaten() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("kundeId", "6");
        request.setParameter("name","Hermann Meier");
        request.setParameter("xKoordinate","00aa18");
        request.setParameter("yKoordinate", "21d00");
        request.setParameter("prioritaet", "3");
        request.setParameter("emailAdresse", "MMeier@beispiel.ch");
        request.setParameter("telNr", "+41 79 654 55 02");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("Fehler bei xKoordinate. " + "Fehler bei yKoordinate. "
        		,request.getAttribute("FMSG"));
    }

}
