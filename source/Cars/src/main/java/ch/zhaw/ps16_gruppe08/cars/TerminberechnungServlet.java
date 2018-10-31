package ch.zhaw.ps16_gruppe08.cars;
import java.io.IOException;
import java.sql.SQLException;
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
import java.util.Calendar;


/**
 * Servlet für Berechnung der Termine, reagiert auf den Pfad /Berechnung
 * 
 * 
 */
@WebServlet("/Berechnung")
@ServletSecurity(
    httpMethodConstraints = {
      @HttpMethodConstraint(value = "GET"),
      @HttpMethodConstraint(value = "POST")
    })
public class TerminberechnungServlet extends HttpServlet {

  private static final long serialVersionUID = -5819739210052752326L;
  
  private String zielUrl;

  private static final String JNDI_DB_NAME = "PSIT2/Cars";
  
  private static final int ANZAHL_VARIANTEN = 5;

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
      } catch (SQLException e) {
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
    } catch (SQLException e) {
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
                                        throws ServletException, 
                                               IOException,
                                               SQLException 
  {
      String fehlermeldung = "";
      boolean erfolgreich = true;
      request.setCharacterEncoding("UTF-8");	  

      request.setAttribute("appMainContext","berechnung"); 
      
      zielUrl = "/WEB-INF/pages/berechnung.jsp";     
      Benutzer user = (Benutzer) request.getSession().getAttribute("anmeldung");
      if(!user.getSchreibrechte())
      {
          zielUrl = "/WEB-INF/pages/start.jsp";   
      }
      if(request.getParameter("datumVon") != null)
      {
          request.setAttribute("datumVon",request.getParameter("datumVon"));   	  
      }
      if(request.getParameter("datumBis") != null)
      {
    	  request.setAttribute("datumBis",request.getParameter("datumBis"));
      }      
      
      if(request.getParameter("aktion").equals("speichern") && 
         user.getSchreibrechte())
      {
          try
          {
              this.berechnen(request, response);    	  
          }
          catch (IllegalArgumentException e)
          {
              erfolgreich = false;
              fehlermeldung = e.getMessage();
          } 
      
          if(erfolgreich)
          {
              request.setAttribute(
                      "OKMSG", 
                      "Berechnung wurde erfolgreich durchgeführt.");                  
          }
          else
          {
              request.setAttribute(
                      "FMSG", 
                      fehlermeldung);      	  
          }
      }

  }

  /**
   * Berechnet den Plan.
   * 
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   * @throws SQLException
   */
  protected void berechnen(HttpServletRequest request,
                           HttpServletResponse response) 
                                   throws ServletException, 
                                          IOException, 
                                          SQLException,
                                          IllegalArgumentException
  {
      Calendar datumVon = Calendar.getInstance();
      Calendar datumBis = Calendar.getInstance();
  
      try
      {
          datumVon = ZeitUtil.stringZuCalendar(
                  request.getParameter("datumVon").toString());
      }
      catch(IllegalArgumentException e)
      {
          throw new IllegalArgumentException("Das von-Datum ist ungültig.");
      }
	  
      try
      {
          datumBis = ZeitUtil.stringZuCalendar(
                  request.getParameter("datumBis").toString());
      }
      catch(IllegalArgumentException e)
      {
          throw new IllegalArgumentException("Das bis-Datum ist ungültig.");
      }	  

      try
      {
          Terminberechnung terminberechnung 
          = new Terminberechnung(datumVon,datumBis);
          int planId = PlanDb.speichern(datumVon, datumBis);
          Variante variante = 
                  terminberechnung.berechneTermine(planId, ANZAHL_VARIANTEN);
          request.setAttribute("verpassteKunden", 
                               variante.getVerpassteKunden());
          request.setAttribute("varianten", terminberechnung.getVarianten());
          for(Variante element : terminberechnung.getVarianten())
          {
              VarianteDb.speichern(element);
          }
      }
      catch(IllegalArgumentException e)
      {
          throw new IllegalArgumentException(e.getMessage());
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
