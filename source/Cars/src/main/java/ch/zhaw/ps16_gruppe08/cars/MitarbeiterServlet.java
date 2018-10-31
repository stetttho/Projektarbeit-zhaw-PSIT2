package ch.zhaw.ps16_gruppe08.cars;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
 * Servlet für Mitarbeitende, reagiert auf den Pfad /Mitarbeitende
 * @version 1.0
 * 
 */
@WebServlet("/Mitarbeitende")
@ServletSecurity(
    httpMethodConstraints = {
      @HttpMethodConstraint(value = "GET"),
      @HttpMethodConstraint(value = "POST")
    })
public class MitarbeiterServlet extends HttpServlet {

  private static final long serialVersionUID = -5819739210052752326L;
  
  private String zielUrl;
  private static final String JNDI_DB_NAME = "PSIT2/Cars";
  private static final String BASE_URL = "/WEB-INF/pages"; 
  private static final String DETAILANSICHT_URL = "/mitarbeiter.jsp";
  private static final String LISTENANSICHT_URL = "/liste_mitarbeitende.jsp";

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
    } 
    catch (SQLException e) 
    {
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
    } 
    catch (SQLException e) 
    {
      throw new ServletException(e);
    }
    dispatch(request,response);
  }
  
  /**
   * Leitet die verarbeitete Anfrage weiter
   * 
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
      String searchString = request.getParameter("searchString");
  
      request.setAttribute("appMainContext","mitarbeitende");  
 
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
            			 MitarbeiterDb.sucheMitId(
            				 Integer.parseInt(
            					 request.
            					 getParameter("mitarbeiterId")
            								  )));
              break;

          case "speichern":
              speichern(request,response);
              zielUrl = BASE_URL + DETAILANSICHT_URL + "?aktion=";
              break;
            
          default:
              ArrayList<Mitarbeiter> mitarbeitende = 
              MitarbeiterDb.sucheMitFilter(searchString);
              request.setAttribute("mitarbeitende", mitarbeitende);    	
              zielUrl = BASE_URL + LISTENANSICHT_URL;    		
      }
  }
  

  /**
   * Speichert ein Mitarbeiter Objekt in der Datenbank
   * 
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
       
      boolean status = false;
      String fehlermeldung = "";
      
      if(request.getParameter("status")!= null)
      {
      	status = true;
      }
      
      int id=0;
      if(request.getParameter("mitarbeiterId")!=null 
    		  && !request.getParameter("mitarbeiterId").equals(""))
      {
    	  id = Integer.parseInt(request.getParameter("mitarbeiterId"));
      }
      
      //Eingabeprüfungen
      String name = request.getParameter("name");
      if(istNullOderLeer(name))
      {
          fehlermeldung = fehlermeldung + "Fehler bei Name. ";
      }
      
      String vorname = request.getParameter("vorname");
      if(istNullOderLeer(vorname))
      {
          fehlermeldung = fehlermeldung + "Fehler bei Vorname. ";
      }
      
      String telNr = request.getParameter("telNr");
      if(istNullOderLeer(telNr))
      {
          fehlermeldung = fehlermeldung + "Fehler bei Telefonnummer. ";
      }
      
      String email = request.getParameter("email");
      if(istNullOderLeer(email))
      {
          fehlermeldung = fehlermeldung + "Fehler bei E-Mail Adresse. ";
      }
      
      if(fehlermeldung.equals("")) // Überprüfen ob Fehler bei Eingabe vorliegt
      {
          boolean kennungNeuerMitarbeiter = false;
          if(id==0)
          {
              kennungNeuerMitarbeiter = true;
          }
      
    	  id = MitarbeiterDb.speichern
    	          (new Mitarbeiter(
    	                           id,
    	                           status,
    	                           name,
    	                           vorname,
    	                           telNr,
    	                           email));
          request.setAttribute("item", 
        		  	MitarbeiterDb.sucheMitId(id));
          if(kennungNeuerMitarbeiter)
          {
              request.setAttribute("OKMSG", "Mitarbeiter wurde erfolgreich "+
            		  	  	    "gespeichert.");    
          }
          else
          {
        	  request.setAttribute("OKMSG", "Mitarbeiteränderung wurde "+
        			  		"erfolgreich gespeichert.");
          }
	  
      }
      else
      {
          request.setAttribute("item",
        		       new Mitarbeiter(
        		               id,
        		               status,
        		               name,
        		               vorname,
        		               telNr,
        		               email));    	  
          request.setAttribute("FMSG", fehlermeldung);

      } 

  }
  
  /**
   * prüft ob der übergebene String null oder leer "" ist, diese werden
   * als false zurückgemeldet
   * @param wert
   * @return
   */
  private boolean istNullOderLeer(String wert)
  {
      if(wert == null || wert.trim() == "")
      {
          return true; 
      }
      else
      {
          return false;
      }         
  }
  
  /**
   * Gibt die zielUrl zurück, auf die das Servlet verweisen wird nach
   * Durchführung, wird vorallem für die automatischen Tests verwendet
   */
  
  public String getZielUrl()
  {
      return zielUrl;
  }
 
}
