package ch.zhaw.ps16_gruppe08.cars;
import java.awt.Point;
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
 * Servlet für Kunde, reagiert auf den Pfad /Kunde
 * @version 1.0
 * 
 */
@WebServlet("/Kunde")
@ServletSecurity(
    httpMethodConstraints = {
      @HttpMethodConstraint(value = "GET"),
      @HttpMethodConstraint(value = "POST")
    })
public class KundeServlet extends HttpServlet 
{
  private static final long serialVersionUID = -5819739210052752326L;

  private String zielUrl;
  private static final String JNDI_DB_NAME = "PSIT2/Cars";
  private static final String BASE_URL = "/WEB-INF/pages"; 
  private static final String DETAILANSICHT_URL = "/kunde.jsp";
  private static final String LISTENANSICHT_URL = "/liste_kunde.jsp";

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
    String searchString = request.getParameter("searchString");
  
    request.setAttribute("appMainContext","kunde");  
 
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
                    KundeDb.sucheMitId(
                        Integer.parseInt(
                                request.getParameter("kundeId"))));
            break;

        case "speichern":
            speichern(request,response);
            zielUrl = BASE_URL + DETAILANSICHT_URL + "?aktion=";
            break;
            
        default:
            ArrayList<Kunde> kunde = KundeDb.sucheMitFilter(searchString);
            request.setAttribute("kunde", kunde);       
            zielUrl = BASE_URL + LISTENANSICHT_URL;
    }
  }
  

  /**
   * Speichert eines Kunde Objekt in der Datenbank
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
      //Übertragen der Attribute
	  String fehlermeldung = "";
	  
      int id=0;
      if(request.getParameter("kundeId")!=null 
             && !request.getParameter("kundeId").equals(""))
      {
          id = Integer.parseInt(request.getParameter("kundeId"));
      }
     
	  boolean status = false;
	  if(request.getParameter("status")!= null)
      {
        status = true;
      }
      
      String name = request.getParameter("name");
      if(isIncorrectString(name))
      {
    	  fehlermeldung = fehlermeldung + "Fehler bei Name. ";
      }
      
      String emailAdresse = request.getParameter("emailAdresse");
      if(isIncorrectString(emailAdresse))
      {
    	  fehlermeldung = fehlermeldung + "Fehler bei E-Mail Adresse. ";
      }
      
	  String telNr = request.getParameter("telNr");
      if(isIncorrectString(telNr))
      {
    	  fehlermeldung = fehlermeldung + "Fehler bei Telefonnummer. ";
      }
      
      int prioritaet = 0;
      if(isCorrectNumber(request.getParameter("prioritaet")))
      {
    	  prioritaet = Integer.valueOf(request.getParameter("prioritaet"));
      }
      else
      {
    	  fehlermeldung = fehlermeldung + "Fehler bei Priorität. ";
      }
      
      int xKoordinate = 0;
      if(isCorrectNumber(request.getParameter("xKoordinate")))
      {
    	  xKoordinate = Integer.valueOf(request.getParameter("xKoordinate"));
      }
      else
      {
    	  fehlermeldung = fehlermeldung + "Fehler bei xKoordinate. ";
      }
      
      int yKoordinate = 0;
      if(isCorrectNumber(request.getParameter("yKoordinate")))
      {
    	  yKoordinate = Integer.valueOf(request.getParameter("yKoordinate"));
      }
      else
      {
    	  fehlermeldung = fehlermeldung + "Fehler bei yKoordinate. ";
      }
     
              
      if(fehlermeldung == "") // Überprüfen ob Fehler bei Eingabe vorliegt
      {
          boolean kennungNeuerKunde = false;
          if(id==0)
          {
              kennungNeuerKunde = true;
          }
                  
          id = KundeDb.speichern(
                new Kunde(
                        id,
                        status,
                        name,
                        new Point(xKoordinate,yKoordinate),
                        prioritaet,
                        emailAdresse,
                        telNr
                        )
                );
          
          request.setAttribute("item", KundeDb.sucheMitId(id));
          if(kennungNeuerKunde)
          {
              request.setAttribute("OKMSG", "Kunde wurde "+
                           "erfolgreich gespeichert.");    
          }
          else
          {
              request.setAttribute("OKMSG", 
                                   "Kundeänderung wurde erfolgreich "+
                           "gespeichert.");
          }
      
      }
      else
      {
          request.setAttribute("item",
                                new Kunde(id,
                                        status,
                                        name,
                                        new Point(xKoordinate,yKoordinate),
                                        prioritaet,
                                        emailAdresse,
                                        telNr
                                        )
                                );        
          request.setAttribute("FMSG", fehlermeldung);
      }

    }
  
//Fehlerüberprüfungsmethode
  private static boolean isCorrectNumber(String value)
  {
	  try 
	  {
		  Integer.parseInt(value);
		  return true;
	  }
	  catch(NumberFormatException e) 
	  {
		  return false;
	  }
  }
  private static boolean isIncorrectString(String value){
	  try 
	  {
		  if(value == null || value.trim() == "")
		  {
			  return true; 
	      }
		  else return false;
	  }
	  catch(IllegalArgumentException e) 
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
