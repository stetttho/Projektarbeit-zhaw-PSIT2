<%@ page pageEncoding="utf-8" %>
<jsp:useBean id="liste" class="ch.zhaw.ps16_gruppe08.cars.Schluesselausgabe" scope="request"/>
<jsp:useBean id="datum" class="java.lang.String" scope="request"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Schlüssel-Verteilliste</title>

  <link rel="stylesheet" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/css/bootstrap.css">
  <link rel="stylesheet" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/css/navbar-fixed-side.css">
  <link rel="shortcut icon" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/css/cars.css">
  <script src="http://srv-lab-t-911.zhaw.ch:8080/CARS/js/application.js"></script>
</head>
<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="menu.jsp" %>
            <div class="col-sm-9 col-lg-9">
                <h1 class="page-header"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/calendar.png" alt="Kalender" class="header" />Schlüssel-Verteilliste</h1>                
                <c:choose><c:when test="${OKMSG != null }"><div class="col-lg-7 alert alert-success"><c:out value="${OKMSG }"></c:out></div></c:when></c:choose>
				<c:choose><c:when test="${FMSG != null }"><div class="col-lg-7 alert alert-danger"><c:out value="${FMSG }"></c:out></div></c:when></c:choose>                
                <div class="col-lg-7">
                <form class="form-horizontal" action="<c:url value='Wochenplan' />?aktion=schluesselausgabe" method="post">
                    <div class="form-group">
                        <label for="vonDatum" class="col-sm-4 control-label">Datum</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="Datum" placeholder="Datum" name="datum" value="<c:out value="${datum }"></c:out>" />
                        </div>
                    </div>                  
                               
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-10">
                            <input type="submit" class="btn btn-primary" name="speichern" value="anzeigen" />
                        </div>
                    </div>
                </form>	                  
                 <%
                    if(liste.getAnzahlEintraege() > 0)
                    {
                    %>     
                    <h2>für <c:out value="${liste.getDatumFormatiert() }"></c:out></h2>               
                    <div class="col-lg-24 table-responsive">
      					<table id="schluesselplan" class="table table-striped table-hover table-responsive">
							<thead>
							<!-- Hier folgt der Aufbau der Titelzeile mit Fahrzeug und Mitarbeiter -->	
							</thead>
							
							<!-- Hier wird pro Fahrzeug eine Zeile generiert -->
							<tbody>
								<c:forEach begin="1" step="1" end="${liste.getAnzahlEintraege() }">  
									<tr>                                          
                          				<td><c:out value="${liste.getFahrzeug().getKontrollschildNr()}" /></td>
                          				<td><c:out value="${liste.getMitarbeiter().getVorname()}" /> <c:out value="${liste.getMitarbeiter().getName()}" /></td>
                              			<% liste.moveNext(); %>       
                              			</tr>            
								</c:forEach>								
							</tbody>
						</table>
					</div> 					                                     
        </div>
    </div>
    <%
                    }
                    else {
                        if(!datum.equals(""))
                        {
                            %>    
                            <h2>kein gültiger Plan ausgewählt</h2>
                            <%       
                        }
                   } %>
</body>
</html>
