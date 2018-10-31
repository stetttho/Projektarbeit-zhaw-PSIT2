<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Kundentermin ändern</title>
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
 
    <div class="col-sm-9 col-lg-10">
        <h1 class="page-header">
        <img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/edit.png" class="header" alt="bearbeiten">Kundentermin ändern</h1>
        <c:choose><c:when test="${OKMSG != null }"><div class="col-lg-7 alert alert-success"><c:out value="${OKMSG }"></c:out></div></c:when></c:choose>
				<c:choose><c:when test="${FMSG != null }"><div class="col-lg-7 alert alert-danger"><c:out value="${FMSG }"></c:out></div></c:when></c:choose>                
        <div class="col-lg-9 table-responsive">
          <form action="selektion.html" method="post">
            <table class="table table-striped table-hover">
              <thead>
                <tr>                 
                  <td><input type="text" class="form-control" id="personName" placeholder="Kundennamen" name="personName" value="" title="Name enthält"/></td>
                  <td><input type="tel" class="form-control" id="datum" placeholder="Datum" name="datum" value="" title="tt.mm.jjjj" /></td>
                  <td><input type="tel" class="form-control" id="startzeit" placeholder="Startzeit" name="startzeit" value="" title="" /></td>
                  <td><input type="tel" class="form-control" id="endzeit" placeholder="Endzeit" name="endzeit" value="" title="" /></td>
                </tr>
                <tr class="titel"><td>Kundenname</td><td>Datum</td><td>Startzeit</td><td>Endzeit</td></tr>
              </thead>
              <tbody>                
 				<c:forEach var="item" items="${kundentermine}">
        			<tr valign="top">          				
          				<td><c:out value="${item.getKundeName()}" /></td>
          				<td><c:out value="${item.getDatumFormatiert()}" /></td>
          				<td><c:out value="${item.startzeit}" /></td>
          				<td><c:out value="${item.endzeit}" /></td>
          				<% if(anmeldung.getSchreibrechte()) { %>
          					<td><a href="<c:url value='/Kundentermine' />?aktion=anzeigen&kundenterminId=<c:out value="${item.kundenterminId}" />">bearbeiten</a></td>          				
        					<td><a href="<c:url value='/Kundentermine' />?aktion=loeschen&kundenterminId=<c:out value="${item.kundenterminId}" />">löschen</a></td>
        				<% } %>
        			</tr>
      			</c:forEach>              
              </tbody>
            </table>
          </form>
        </div>
    </div>
  </body>
</html>