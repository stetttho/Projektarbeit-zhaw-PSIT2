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
 * Servlet für Abwesenheit der Mitarbeitende, reagiert auf den Pfad /AbwesenheitMitarbeitende
 * @version 1.0
 * 
 */
@WebServlet("/AbwesenheitMitarbeitende")
@ServletSecurity(
    httpMethodConstraints = {
      @HttpMethodConstraint(value = "GET"),
      @HttpMethodConstraint(value = "POST")
    })
public class AbwesenheitMitarbeiterServlet extends HttpServlet 
{
  private static final long serialVersionUID = -5819739210052752326L;
  
  private String zielUrl;
  private static final String JNDI_DB_NAME = "PSIT2/Cars";
  private static final String BASE_URL = "/WEB-INF/pages"; 
  private static final String DETAILANSICHT_URL = "/abwesenheitMitarbeiter.jsp";
  private static final String LISTENANSICHT_URL = "/liste_abwesenheitMitarbeiter.jsp";

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
	  request.setAttribute("appMainContext","abwesenheitMitarbeitende"); 
	  ArrayList<AbwesenheitMitarbeiter> abwesenheitMitarbeitende;
	  
	  zielUrl = "404";
	  Benutzer user = (Benutzer) request.getSession().
			  getAttribute("anmeldung");
      String aktion = request.getParameter("aktion");
      if(!user.getSchreibrechte())
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
            			 AbwesenheitMitarbeiterDb.sucheMitId(
            				 Integer.parseInt(
            					 request.
            					 getParameter("abwesenheitId")
            								  )));
    			break;

    		case "speichern":
    			speichern(request,response);
    			zielUrl = BASE_URL + DETAILANSICHT_URL + "?aktion=";
    			break;
            
    		default:
    			abwesenheitMitarbeitende = 
    			AbwesenheitMitarbeiterDb.sucheMitFilter(null);
    			request.setAttribute("abwesenheitMitarbeitende",
    					abwesenheitMitarbeitende);    	
    			zielUrl = BASE_URL + LISTENANSICHT_URL;	
	  }
  }
  
  /**
   * Speichert eine Abwesenehit des Mitarbeiter Objekt in der Datenbank
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
      if(request.getParameter("abwesenheitId")!=null 
              && !request.getParameter("abwesenheitId").equals(""))
      {
          id = Integer.parseInt(request.getParameter("abwesenheitId"));
      }
            
      //Überprüft, ob die Eingaben bei DatumVon und DatumBis korrekte Werte sind.
      if(istKorrektesDatum(request.getParameter("abwesenheitVon")) ||
    		  istKorrektesDatum(request.getParameter("abwesenheitBis")))
      {
    	  AbwesenheitMitarbeiter abwesenheit = new AbwesenheitMitarbeiter(
    		  			  	id,
    		 	  		    Integer.parseInt(
    		  	  		    		request.getParameter("mitarbeiterId")),
    		  	  		    ZeitUtil.stringZuCalendar(
    		  	  		    		request.getParameter("abwesenheitVon")),
    		  	  		    ZeitUtil.stringZuCalendar(
    		  	  		    		request.getParameter("abwesenheitBis")));
      
    	  if(ZeitUtil.calendarZuString(
    			  abwesenheit.getAbwesenheitVon()) == "01.01.1970")
    	  {
    		  request.setAttribute("item", abwesenheit);        
    		  request.setAttribute("FMSG","Das eingegebene Datum muss im Format "
    				  + "TT.MM.JJJJ geschrieben werden.");
    	  }
    	  //Überprüft, ob DatumVon vor DatumBis liegt
    	  else if(abwesenheit.getAbwesenheitVon().compareTo(
    			  abwesenheit.getAbwesenheitBis())>0)
    	  {
    		  request.setAttribute("item", abwesenheit);        
    		  request.setAttribute("FMSG","Die Abwesenheit BIS darf nicht "
    				  + "vor der Abwesenheit VON sein.");
    	  }
    	  //Überprüft, ob Datum in der Zukunft liegt
    	  else if(abwesenheit.getAbwesenheitBis().compareTo(
    			  ZeitUtil.getDatumHeute()) < 0)
    	  {
    		  request.setAttribute("item", abwesenheit);        
    		  request.setAttribute("FMSG","Das Datum bis darf nicht in der "
    				  + "Vergangenheit liegen");
    	  }
    	  else
    	  {
    		  boolean kennungNeueAbwesenheit = false;
    		  if(id==0)
    		  {
    			  kennungNeueAbwesenheit = true;
    		  }
    		  id = AbwesenheitMitarbeiterDb.speichern(abwesenheit);
    		  request.setAttribute("item", 
    				  AbwesenheitMitarbeiterDb.sucheMitId(id));
    		  if(kennungNeueAbwesenheit)
    		  {
    			  request.setAttribute("OKMSG", 
    					  "Abwesenheit wurde erfolgreich "+
    					  "gespeichert.");    
    		  }
    		  else
    		  {
    			  request.setAttribute("OKMSG", 
                           "Änderung der Abwesenheit "+
                           "wurde erfolgreich gespeichert.");
    		  }
      
    	  }
      }
      else
      {
    	 AbwesenheitMitarbeiter abwesenheit = new AbwesenheitMitarbeiter(
		  			id,
		  			Integer.parseInt(
	  	  		    		request.getParameter("mitarbeiterId"))
		  			);
    	 
    	 request.setAttribute("item", abwesenheit);        
		 request.setAttribute("FMSG","Die Datumseingabe muss im "
		 		+ "Format TT.MM.YYYY sein.");
      }
  }
  
  private boolean istKorrektesDatum(String datum)
  {
	  	if(!(datum.trim() == "" || datum == null))
		{
	  		try
	  		{
	  			ZeitUtil.stringZuCalendar(datum);
	  			return true;
	  		}
	  		catch(Exception e)
	  		{
	  			return false;
	  		}
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
