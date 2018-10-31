package ch.zhaw.ps16_gruppe08.cars;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;

/**
 * Führt diverse Integrationtests aus um die Klasse Terminberechnung im
* Zusammenspiel mit der Datenbank und dem Servlet zu prüfen.
* @author Markus
*
*/ 

public class ITTerminberechnung {
    private HttpServletResponseStub response;
    private HttpServletRequestStub request;
    private TerminberechnungServlet servlet;
    private Benutzer benutzer;
    private HttpSessionStub session;
    
    
    @Before
    public void setUp() throws Exception {
        response = new HttpServletResponseStub();
        request = new HttpServletRequestStub();
        servlet = new TerminberechnungServlet();
        benutzer = new Benutzer();
        session = new HttpSessionStub();
        request.setAttribute("session", session);
        session.setBenutzer(benutzer);
        benutzer.setName("admin");
        benutzer.setPasswort("admin");
        benutzer.setSchreibrechte();       
    }
    
    /**
     * Testet ob die korrekte ZielUrl zurückgeliefert wird
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testGetZielUrl() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("datumVon","08.05.2017");
        request.setParameter("datumBis", "12.05.2017");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("/WEB-INF/pages/berechnung.jsp", servlet.getZielUrl());
    }
    
    /**
     * Testet die Methode processRequest mit Aktion speichern und
     * gültigem Datum-von und Datum-bis, das es erfolgreich war
     * wird dem Benutzer mit einem Text angezeigt
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testProcessRequest() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("datumVon","08.05.2017");
        request.setParameter("datumBis", "12.05.2017");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("Berechnung wurde erfolgreich durchgeführt.",
                     request.getAttribute("OKMSG"));
    }
    
    /**
     * Testet die Methode processRequest mit Aktion speichern und
     * ungültigem Datum-bis, dies muss durch eine Fehlermeldung
     * (FMSG) angezeigt werden
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testProcessRequestUngueltigBisDatumLeer() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("datumVon","12.05.2017");   
        request.setParameter("datumBis", "");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("Das bis-Datum ist ungültig.",
                     request.getAttribute("FMSG"));
    }   
    
    /**
     * Testet die Methode processRequest mit Aktion speichern und
     * ungültigem Datum-von, dies muss durch eine Fehlermeldung
     * (FMSG) angezeigt werden
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     */
    @Test
    public void testProcessRequestUngueltigVonDatumLeer() 
            throws ServletException, IOException, SQLException
    {
        request.setParameter("datumBis","12.05.2017");  
        request.setParameter("datumVon", "");
        request.setParameter("aktion", "speichern");
        servlet.processRequest(request, response);
        assertEquals("Das von-Datum ist ungültig.",
                     request.getAttribute("FMSG"));
    }     
        
    /**
     * Testet die Methode berechnen, mit einem von-Datum das vor dem
     * bis Datum liegt
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     * @throws IllegalArgumentException 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBerechnenUngueltig() 
            throws IllegalArgumentException, 
            ServletException, 
            IOException, 
            SQLException
    {
        request.setParameter("datumVon", "12.05.2017");
        request.setParameter("datumBis", "08.05.2017");
        request.setParameter("aktion", "speichern");
        servlet.berechnen(request, response);        
    }
    
    /**
     * Testet die Methode berechnen, wenn kein Datum durch den Benutzer
     * eingegeben wurde, dabei wird eine Exception erwartet
     * @throws IllegalArgumentException
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBerechnenUngueltigKeinDatum() 
            throws IllegalArgumentException, 
            ServletException, 
            IOException, 
            SQLException
    {
        request.setParameter("datumVon", "");
        request.setParameter("datumBis", "");
        request.setParameter("aktion", "speichern");
        servlet.berechnen(request, response);
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
        assertEquals("berechnung",request.getAttribute("appMainContext"));
    }
    

    /**
     * Testet, ob die richtige Anzahl Kunden als verpasste Kunden gemeldet werden
     * mit der Testdatenbank sind dies für den 08.05.2017 ein Kunde
     * 
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     * @throws IllegalArgumentException 
     */
    @Test
    public void testeKorrekteVerpassteKunden() 
            throws IllegalArgumentException, 
            ServletException, 
            IOException, 
            SQLException
    {
        request.setParameter("datumVon", "08.05.2017");
        request.setParameter("datumBis", "08.05.2017");
        request.setParameter("aktion", "speichern");
        servlet.berechnen(request, response); 
        
        HashSet<Kunde> verpassteKunden = 
                (HashSet<Kunde>) request.getAttribute("verpassteKunden");
        assertEquals(1,verpassteKunden.size());
    }
    
    /**
     * Testet, ob in der statistischen Ausgabe 5 Varianten enthalten sind
     * (es werden innerhalb der Berechnung 5 Varianten aufbereitet
     *  und Details zu diesen Varianten sollen dem Benutzer nach der Berechnung
     *  angezeigt werden)
     * @throws SQLException 
     * @throws IOException 
     * @throws ServletException 
     * @throws IllegalArgumentException 
     */
    @Test
    public void testeStatistischeAusgabe() 
            throws IllegalArgumentException, 
            ServletException, 
            IOException, 
            SQLException
    {
        request.setParameter("datumVon", "08.05.2017");
        request.setParameter("datumBis", "08.05.2017");
        request.setParameter("aktion", "speichern");
        servlet.berechnen(request, response);       
        ArrayList<Variante> varianten = 
                (ArrayList<Variante>)request.getAttribute("varianten");
        assertEquals(5,varianten.size());
    }
}
