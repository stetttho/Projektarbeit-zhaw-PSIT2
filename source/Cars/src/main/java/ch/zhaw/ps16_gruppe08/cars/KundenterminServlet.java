package ch.zhaw.ps16_gruppe08.cars;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet für Kundentermine, reagiert auf den Pfad /sucheKundentermine
 * 
 */
@WebServlet("/Kundentermine")
@ServletSecurity(
    httpMethodConstraints = {
      @HttpMethodConstraint(value = "GET"),
      @HttpMethodConstraint(value = "POST")
    })
public class KundenterminServlet extends HttpServlet {

  private static final long serialVersionUID = -5819739210052752326L;
  
  private String zielUrl;
  private static final String JNDI_DB_NAME = "jdbc/db-workshop";
  private static final String BASE_URL = "/WEB-INF/pages"; 
  private static final String DETAILANSICHT_URL = "/kundentermin.jsp";
  private static final String LISTENANSICHT_URL = "/liste_kundentermine.jsp";
  
  @Resource(name = JNDI_DB_NAME)
  private DataSource dataSource;

  /**
   * Behandelt Anfragen via Get-Methode
   *
   * @param request
   *          servlet request
   * @param response
   *          servlet response
   */
  @Override
  protected void doGet(HttpServletRequest request, 
               HttpServletResponse response)
      throws ServletException, IOException 
  {
    try 
    {
        processRequest(request, response);
    } catch (SQLException e) {
        throw new ServletException(e);
    }
    dispatch(request,response);
  }

  /**
   * Behandelt Anfragen via Post-Methode
   *
   * @param request
   *          servlet request
   * @param response
   *          servlet response
   */
  @Override
  protected void doPost(HttpServletRequest request, 
            HttpServletResponse response)
      throws ServletException, IOException 
  {
    try 
    {
        processRequest(request, response);
    } catch (SQLException e) {
        throw new ServletException(e);
    }
  
    dispatch(request,response);
  }

 /**
   * Leitet die verarbeitete Anfrage weiter
 * @throws IOException 
 * @throws ServletException 
   */
  protected void dispatch(HttpServletRequest request, 
          HttpServletResponse response) throws ServletException, IOException
  {
      RequestDispatcher dispatcher = getServletContext()
              .getRequestDispatcher(zielUrl);
        dispatcher.forward(request, response);      
  }  
  
  /**
   * Verarbeitet die Anfrage
   *
   * @param request
   *          servlet request
   * @param response
   *          servlet response
   * @throws ServletException if an internal error occurs in the servlet
   * @throws IOException if an I/O error occurs
   * @throws SQLException if a database error occurs
   */
  protected void processRequest(HttpServletRequest request,
                HttpServletResponse response)
          throws ServletException, IOException, SQLException 
  {

      request.setCharacterEncoding("UTF-8");    
      request.setAttribute("appMainContext","kundentermine");  
      ArrayList<Kundentermin> kundentermine;
      
      zielUrl = "404";
      Benutzer user = (Benutzer) request.getSession().getAttribute("anmeldung");
      boolean hatSchreibrechte = user.getSchreibrechte();
      String aktion = request.getParameter("aktion");
      if(!hatSchreibrechte)
      {
          aktion = "";
      }
      switch(aktion)
      {
          case "neu":
        	  zielUrl = BASE_URL + DETAILANSICHT_URL;
              break;
        
          case "anzeigen":
        	  zielUrl = BASE_URL + DETAILANSICHT_URL;  
              request.setAttribute("item", 
                         KundenterminDb.sucheMitId(
                            Integer.parseInt(
                                request
                                .getParameter("kundenterminId")
                                               )));
              break;

          case "speichern":
              speichern(request,response);
              zielUrl = BASE_URL + DETAILANSICHT_URL + "?aktion=";
              break;
              
          case "loeschen":
        	  loeschen(request,response);
              kundentermine = KundenterminDb.sucheMitFilter(null);
              	request.setAttribute("kundentermine", kundentermine);       
              		zielUrl = "/WEB-INF/pages/liste_kundentermine.jsp"; 
        	  break;
        	              

          default:
              kundentermine = KundenterminDb.sucheMitFilter(null);
              request.setAttribute("kundentermine", kundentermine);       
              zielUrl = BASE_URL + LISTENANSICHT_URL;         
      }
  }
  

  /**
   * Speichert ein Kundentermin Objekt in der Datenbank.
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   * @throws SQLException
   */
  protected void speichern(HttpServletRequest request,
               HttpServletResponse response) 
                       throws ServletException, 
                          IOException, 
                          SQLException
  {
             
      int id=0;
      if(request.getParameter("kundenterminId")!=null 
              && !request.getParameter("kundenterminId").equals(""))
      {
          id = Integer.parseInt(request.getParameter("kundenterminId"));
      }

      LocalTime startzeit;
      LocalTime endzeit;
      Calendar datum;
      int kundeId;
      String fehlermeldung = "";
      try
      {
          startzeit = ZeitUtil.stringZuLocalTime(
                  request.getParameter("startzeit"));       
      }
      catch(Exception e)
      {
          startzeit = LocalTime.of(0, 0);
          fehlermeldung += "- Startzeit ";
      }
      try
      {       
          endzeit = ZeitUtil.stringZuLocalTime(
                  request.getParameter("endzeit"));                      
      }
      catch(Exception e)
      {
          endzeit = LocalTime.of(23, 59);
          fehlermeldung += "- Endzeit ";          
      }   
      try
      {               
          datum = ZeitUtil.stringZuCalendar(
                  request.getParameter("datum"));               
      }
      catch(Exception e)
      {
          datum = Calendar.getInstance();
          fehlermeldung += "- Datum ";          
      }       
      try
      {                         
          kundeId = Integer.parseInt(request.getParameter("kundeId"));          
      }
      catch(Exception e)
      {
          kundeId = 0;
          fehlermeldung += "- KundenId ";          
      } 
      if(fehlermeldung.equals("") && startzeit.isAfter(endzeit))
      {
          fehlermeldung += "- die Startzeit muss vor der Endzeit liegen ";
      }
      
      if(fehlermeldung.equals(""))
      {
          boolean kennungNeuerKundentermin = false;
          if(id==0)
          {
              kennungNeuerKundentermin = true;
          }
          id = KundenterminDb.speichern(
                  new Kundentermin(id,
                                   kundeId,
                                   startzeit,
                                   endzeit,
                                   datum));
          request.setAttribute("item", 
                  KundenterminDb.sucheMitId(id));
          if(kennungNeuerKundentermin)
          {
              request.setAttribute("OKMSG", 
                           "Kundentermin wurde erfolgreich "+
                           "gespeichert.");    
          }
          else
          {
              request.setAttribute("OKMSG", 
                           "Änderung des Kundentermines "+
                           "wurde erfolgreich gespeichert.");
          }
      
      }
      else
      {
          request.setAttribute("item",
                  new Kundentermin(id,
                                   kundeId,
                                   startzeit,
                                   endzeit,
                                   datum));
          zielUrl = BASE_URL + DETAILANSICHT_URL + "?aktion=neu";
          request.setAttribute("FMSG", 
                       "Folgende Eingaben sind ungültig: "+fehlermeldung);
      }


  }
  /**
   * Löscht ein Kundentermin Objekt in der Datenbank.
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   * @throws SQLException
   */  
  protected void loeschen(HttpServletRequest request,
          HttpServletResponse response) 
                  throws ServletException, 
                     IOException, 
                     SQLException
  {
	  if(istKorrekteId(request.getParameter("kundenterminId")))
	  {
	      if(KundenterminDb.loescheZeitfenster(
                      Integer.parseInt(
                              request.getParameter("kundenterminId"))) >0)
	      {
	          request.setAttribute("OKMSG", 
	                  "Kundentermin erfolgreich gelöscht.");  	          
	      }
	      else
	      {
	          request.setAttribute("FMSG", "Der Eintrag kann nicht "+
	                                       "gelöscht werden.");
	      }

	  }
	  else
	  {		       
          request.setAttribute("FMSG", 
                       "Der Kundentermin konnte nicht gelöscht "+
                       "werden: Falsche KundenterminId");
	  }
	  
	  
  }
  
  /**
   * Gibt die zielUrl zurück, auf die das Servlet verweisen wird nach
   * Durchführung, wird vorallem für die automatischen Tests verwendet.
   */
  public String getZielUrl()
  {
      return zielUrl;
  }  
 
  /**
   * Überprüft, ob der angegebene String eine korrekte Id ist.
   * @param wert
   */
  private boolean istKorrekteId(String wert)
  {
	  try
	  {
		  int id = Integer.parseInt(wert);
		  if(id <= 0)
		  {
			  return false;
		  }
		  else
		  {
			  return true;
		  }
	  }
	  catch(Exception e)
	  {
		  return false;
	  }
  }
}
