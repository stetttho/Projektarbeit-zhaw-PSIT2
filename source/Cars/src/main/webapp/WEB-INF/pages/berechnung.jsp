<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Berechnung durchführen</title>
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
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-9 col-lg-10">
                <h1 class="page-header">
                <img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="header" alt="bearbeiten">Wochenplan berechnen</h1>
                <c:choose><c:when test="${OKMSG != null }"><div class="col-lg-7 alert alert-success"><c:out value="${OKMSG }"></c:out></div></c:when></c:choose>
				<c:choose><c:when test="${FMSG != null }"><div class="col-lg-7 alert alert-danger"><c:out value="${FMSG }"></c:out></div></c:when></c:choose>                
                <div class="col-lg-7">
                <form class="form-horizontal" action="<c:url value='Berechnung' />?aktion=speichern" method="post">
                    <div class="form-group">
                        <label for="vonDatum" class="col-sm-4 control-label">von Datum</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="vonDatum" placeholder="von Datum" name="datumVon" value="<c:out value="${datumVon }"></c:out>" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bisDatum" class="col-sm-4 control-label">bis Datum</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="bisDatum" placeholder="bis Datum" name="datumBis" value="<c:out value="${datumBis }"></c:out>" />
                        </div>
                    </div>
                                
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-10">
                            <input type="submit" class="btn btn-primary" name="speichern" value="berechnen" />
                        </div>
                    </div>
                </form>
                 
                 <c:choose><c:when test="${OKMSG != null && verpassteKunden.size() > 0 }">                	    
                 		<h3>verpasste Kunden mit der erstellten Berechnung:</h3>
                	    <table id="verpassteKunden" class="table table-striped table-hover table-responsive">
                	    <thead>
                	    <tr><td>Kundennummer</td><td>Name</td><td>Priorität</td></tr>
						</thead>
						<tbody>
								<c:forEach var="item" items="${verpassteKunden}">  
									<tr>   
										<td><c:out value="${item.getKundeId()}" /></td>                                       
                          				<td><c:out value="${item.getName()}" /></td>
                          				<td><c:out value="${item.getPrioritaet()}" /></td>
                          				
                              		</tr>            
								</c:forEach>								
							</tbody>
						</table></c:when></c:choose>
                 <c:choose><c:when test="${OKMSG != null }">                	    
                 		<h3>Statistik der Varianten:</h3>
                	    <table id="variantenStatistik" class="table table-striped table-hover table-responsive">
                	    <thead>
                	    <tr><td>Variante</td><td>Anzahl verpasste Kunden</td></tr>
						</thead>
						<tbody>
						<%
						int zaehler = 0;
						%>
								<c:forEach var="item" items="${varianten}">
								<%
								zaehler++;%>  
									<tr <c:choose><c:when test="${item.getStatusDefinitiv() }">class=success</c:when></c:choose>>   
										<td>Variante <% out.print(zaehler); %></td>                                       
                          				<td><c:out value="${item.getAnzahlVerpassteKunde()}" /></td>
                          				
                              		</tr>            
								</c:forEach>								
							</tbody>
						</table></c:when></c:choose>						                          
                </div>
            </div>
        </div>
    </div>
</body>
</html>