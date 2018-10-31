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
 * Servlet für Fahrzeuge, reagiert auf den Pfad /sucheFahrzeuge
 * 
 */
@WebServlet("/Fahrzeuge")
@ServletSecurity(
    httpMethodConstraints = {
      @HttpMethodConstraint(value = "GET"),
      @HttpMethodConstraint(value = "POST")
    })
public class FahrzeugServlet extends HttpServlet {

  private String zielUrl;
  private static final long serialVersionUID = -5819739210052752326L;
  
  private static final String JNDI_DB_NAME = "PSIT2/Cars";
  private static final String BASE_URL = "/WEB-INF/pages"; 
  private static final String DETAILANSICHT_URL = "/fahrzeug.jsp";
  private static final String LISTENANSICHT_URL = "/liste_fahrzeuge.jsp";

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
    try {
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
    try {
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
		  			throws ServletException, 
		  			       IOException, 
		  			       SQLException 
  {

      request.setCharacterEncoding("UTF-8");	  
      request.setAttribute("appMainContext","fahrzeuge");  
 
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
    		  request.setAttribute("item", FahrzeugDb.sucheMitId(
    					Integer.parseInt(request.getParameter
    						("fahrzeugId"))));
    		  break;

    	  case "speichern":
    		  speichern(request,response);
    		  zielUrl = BASE_URL + DETAILANSICHT_URL + "?aktion=";
    		  break;
            
    	  default:
    		  ArrayList<Fahrzeug> fahrzeuge = FahrzeugDb.sucheMitFilter(null);
    		  request.setAttribute("fahrzeuge", fahrzeuge);    	
    		  zielUrl = BASE_URL + LISTENANSICHT_URL;    		
      }
      

  }
  

  /**
   * Speichert ein Fahrzeug Objekt in der Datenbank
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
      if(request.getParameter("status")!= null)
      {
      	  status = true;
      }
      
      int id=0;
      if(request.getParameter("fahrzeugId")!=null && 
    		  !request.getParameter("fahrzeugId").equals(""))
      {
    	  id = Integer.parseInt(request.getParameter("fahrzeugId"));
      }

	  
      if(istEingabeVorhanden(request.getParameter("typ"))
    	&& istEingabeVorhanden(request.getParameter("kontrollschildNr"))
    	&& request.getParameter("kontrollschildNr").length()<=10)
      {
    	  boolean kennungNeuesFahrzeug = false;
    	  if(id==0)
    	  {
    		  kennungNeuesFahrzeug = true;
    	  }
    	  id = FahrzeugDb.speichern(
    			  new Fahrzeug(id,
    				       status,
    				       request.getParameter("typ"),
    				       request.getParameter("kontrollschildNr")
    				       ));
          request.setAttribute("item", 
        		       FahrzeugDb.sucheMitId(id));
          if(kennungNeuesFahrzeug)
          {
              request.setAttribute(
            		  "OKMSG", 
            		  "Fahrzeug wurde erfolgreich gespeichert.");    
          }
          else
          {
              request.setAttribute(
                          "OKMSG", 
                          "Fahrzeugänderung wurde " + 
                          "erfolgreich gespeichert.");
          }
      }
      else
      {
    	  request.setAttribute(
        		  "item",
        		  new Fahrzeug(id,
        			       status,request.getParameter("typ"),
        			       request.getParameter("kontrollschildNr")
        			       ));    	  
    	  request.setAttribute("FMSG", "Wegen fehlenden Angaben "
  		  		+ "konnte das Fahrzeug nicht gespeichert "
  		  		+ "werden.");
      }
  }
  
  /**
   * Kontrolliert ob die Eingabe einen Wert hat.
   * 
   * @param eingabe
   * @return istEingabeVorhanden
   */
  private boolean istEingabeVorhanden(String eingabe)
  {
	  String eingabeModifiziert = eingabe.replaceAll(" ", "");
	  
	  if(eingabeModifiziert == "" ||eingabe == null)
	  {
		  return false;
	  }
	  else
	  {
		  return true;
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
