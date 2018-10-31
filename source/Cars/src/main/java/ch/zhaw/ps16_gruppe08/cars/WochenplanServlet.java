package ch.zhaw.ps16_gruppe08.cars;
import java.io.IOException;
import java.sql.SQLException;
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
 * Servlet für Wochenplan-Ausgabe, reagiert auf den Pfad /Wochenplan.
 * 
 * 
 */
@WebServlet("/Wochenplan")
@ServletSecurity(
    httpMethodConstraints = {
      @HttpMethodConstraint(value = "GET"),
      @HttpMethodConstraint(value = "POST")
    })
public class WochenplanServlet extends HttpServlet {

  private static final long serialVersionUID = -5819739210052752326L;
  
  private String zielUrl;

  private static final String JNDI_DB_NAME = "PSIT2/Cars";

  @Resource(name = JNDI_DB_NAME)
  private DataSource dataSource;

  /**
   * Behandelt Anfragen via Get-Methode.
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
   * Behandelt Anfragen via Post-Methode.
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
   * Leitet die verarbeitete Anfrage weiter.
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
   * Verarbeitet die Anfrage.
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
    String aktion = request.getParameter("aktion");
    if(aktion == null)
    {
        aktion = "";
    }            
    
    ArrayList<Mitarbeiter> mitarbeitende = 
            MitarbeiterDb.sucheMitFilter(searchString);
    ArrayList<Fahrzeug> fahrzeuge = FahrzeugDb.gibVerfuegbareFahrzeuge();
    request.setAttribute("plane", PlanDb.getAlle());
            request.setAttribute("mitarbeitende", mitarbeitende); 
    request.setAttribute("fahrzeuge", fahrzeuge);
    request.setAttribute("aktuellerMitarbeiter", 
            request.getParameter("mitarbeiterId"));   
    request.setAttribute("aktuellerPlan", 
            request.getParameter("planId"));  
    request.setAttribute("ansichtTyp", request.getParameter("ansichtTyp"));
    request.setAttribute("aktuellesFahrzeug", 
            request.getParameter("fahrzeugId"));
    request.setAttribute("startFahrzeug", 
            String.valueOf(fahrzeuge.get(0).getFahrzeugId()));
           
    switch(aktion)
    {
        case "wochenplanausgabe":
            ausgabeWochenplan(request, response);
            request.setAttribute("appMainContext","wochenplan");
            zielUrl = "/WEB-INF/pages/wochenplan.jsp";
            break;
      
        case "schluesselausgabe":
            schluesselausgabe(request,response);
            request.setAttribute("appMainContext","schluesselausgabe");
            zielUrl = "/WEB-INF/pages/schluesselausgabe.jsp"; 
            break;

        
        default:
              
            zielUrl = "/WEB-INF/pages/wochenplan.jsp";               
    }
        
  }
  
  public void schluesselausgabe(HttpServletRequest request,
          HttpServletResponse response)
  {
      if(request.getParameter("datum")!=null)
      {

          try
          {
              request.setAttribute("datum", 
                                   request.getParameter("datum"));
              Calendar datum = 
                      ZeitUtil.stringZuCalendar(request.getParameter("datum"));
              request.setAttribute("liste", 
                                   WochenplanDb.selectSchluesselAusgabe(datum));
          }
          catch(IllegalArgumentException e)
          {
              request.setAttribute("FMSG", "kein gültiges Datum");
          }
                   
      }
  }
  
  public void ausgabeWochenplan(HttpServletRequest request,
          HttpServletResponse response)
  {
      if((request.getParameter("mitarbeiterId") != null && 
              !request.getParameter("mitarbeiterId").equals("") ||
              request.getParameter("fahrzeugId")!=null&&
              !request.getParameter("fahrzeugId").equals(""))  &&
              request.getParameter("planId")!=null && 
              !request.getParameter("planId").equals("")&&
              request.getParameter("ansichtTyp")!=null)
           {

    	  		int ansichtTyp;     
    	  		try
    	  		{
    	  			ansichtTyp = 
                      Integer.parseInt(request.getParameter("ansichtTyp"));
    	  		}
    	  		catch(NumberFormatException e)
    	  		{
    	  			ansichtTyp = 2;
    	  		}
    	  		
               int planId = Integer.parseInt(request.getParameter("planId"));
               {
                   try
                   {
                       Wochenplan wochenplan;
                       if(ansichtTyp==1)
                       {
                           int mitarbeiterId = Integer.parseInt(
                                   request.getParameter("mitarbeiterId"));                    
                           wochenplan = 
                                   WochenplanDb.gibWochenplanFuerMitarbeiter(
                                           mitarbeiterId,planId);
                       }
                       else
                       {        
                           int fahrzeugId = Integer.parseInt(
                                   request.getParameter("fahrzeugId"));
                           wochenplan = 
                                   WochenplanDb
                                   .gibWochenplanFuerFahrzeug(fahrzeugId, 
                                                              planId);

                       }
                       
                       request.setAttribute("wochenplan", wochenplan);                    
                       request.setAttribute("aktuellerPlan", planId);
     
                   }
                   catch (IllegalArgumentException e)
                   {
                         //  erfolgreich = false;
                         //  fehlermeldung = e.getMessage();
                   }         
               }     
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
 
}
