package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
/**
 * Führt diverse Integrationtests aus um die Klasse Kundentermin im
 * Zusammenspiel mit der Datenbank und dem Servlet zu prüfen.
 * @author Markus
 * 
 */

public class ITKundentermin {
    private HttpServletResponseStub response;
    private HttpServletRequestStub request;
    private KundenterminServlet servlet;
    private Benutzer benutzer;
    private HttpSessionStub session;
    
    
    @Before
    public void setUp() throws Exception {
        response = new HttpServletResponseStub();
        request = new HttpServletRequestStub();
        servlet = new KundenterminServlet();
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
        assertEquals("kundentermine",request.getAttribute("appMainContext"));
    }
    
    /**
     * Teste ob bei Anzeigen ein Kundentermin abgelegt wird
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeAnzeigen() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("aktion", "anzeigen");
        request.setParameter("kundenterminId", "3");
        servlet.processRequest(request, response);
        assertEquals("Kundentermin",request.getAttribute("item")
                .getClass().getSimpleName());        
    }
    
    /**
     * Teste ob eine Liste von Kundenterminen geliefert wird, wenn keine
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
        assertEquals("ArrayList",request.getAttribute("kundentermine")
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
        assertEquals("ArrayList",request.getAttribute("kundentermine")
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
        assertEquals("/WEB-INF/pages/kundentermin.jsp",servlet.getZielUrl());
            
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
        request.setParameter("kundenterminId", "1");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/kundentermin.jsp",servlet.getZielUrl());
             
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
        request.setParameter("kundenterminId", "0");
        request.setParameter("kundeId","1");
        request.setParameter("startzeit","08:00");
        request.setParameter("endzeit", "10:00");
        request.setParameter("datum", "2017.04.03");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/kundentermin.jsp?aktion=",
                     servlet.getZielUrl());        
    }   
    
    /**
     * Testet, ob die Benutzer eine positive Rückmeldung erhalten beim 
     * speichern eines neuen Kundentermins
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenOKMSGNeu() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("kundenterminId", "0");
        request.setParameter("kundeId","1");
        request.setParameter("startzeit","08:00");
        request.setParameter("endzeit", "10:00");
        request.setParameter("datum", "2017.04.03");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Kundentermin wurde " + 
                "erfolgreich gespeichert.",request.getAttribute("OKMSG"));
    }
    
    /**
     * Testet, ob die Benutzer eine positive Rückmeldung erhalten beim
     * speichern eines bestehenden Kundentermins
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testeBenutzermeldungenOKMSGbestehend() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("kundenterminId", "1");
        request.setParameter("kundeId","1");
        request.setParameter("startzeit","08:00");
        request.setParameter("endzeit", "10:00");
        request.setParameter("datum", "2017.04.03");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);  
        assertEquals("Änderung des Kundentermines "+
                     "wurde erfolgreich gespeichert.",
                     request.getAttribute("OKMSG"));        
    }
    
    /**
     * Teste ob die Löschung des Eintrages über die korrekte 
     * ID funktioniert.
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public void testeLoeschungKorrektenEintrages()
    		 throws ServletException, IOException, SQLException
    {
    	request.setParameter("kundenterminId", "99");
    	assertEquals("Kundentermin erfolgreich gelöscht.",
    			request.getAttribute("OKMSG"));
    }
    
    /**
     * Teste ob die Löschung von Einträgen über ID Werten von 
     * -2, 0, null und einem Namen nicht geschieht und die 
     * korrekte Fehlermeldung zurückgegeben wird.
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public void testeLoeschungInkorrekterenEintrages()
    		 throws ServletException, IOException, SQLException
    {
    	request.setParameter("kundenterminId", "-2");
    	assertEquals( "Der Kundentermin konnte nicht gelöscht "+
                "werden: Falsche KundenterminId",
    			request.getAttribute("FMSG"));
    	
    	request.setParameter("kundenterminId", "0");
    	assertEquals( "Der Kundentermin konnte nicht gelöscht "+
                "werden: Falsche KundenterminId",
    			request.getAttribute("FMSG"));
    	
    	request.setParameter("kundenterminId", null);
    	assertEquals( "Der Kundentermin konnte nicht gelöscht "+
                "werden: Falsche KundenterminId",
    			request.getAttribute("FMSG"));
    	
    	request.setParameter("kundenterminId", "Hans Meier");
    	assertEquals( "Der Kundentermin konnte nicht gelöscht "+
                "werden: Falsche KundenterminId",
    			request.getAttribute("FMSG"));
    }
}
