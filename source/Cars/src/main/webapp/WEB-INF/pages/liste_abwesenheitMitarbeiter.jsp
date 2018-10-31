<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Abwesenheit der Mitarbeiter ändern</title>
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
        <img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/edit.png" class="header" alt="bearbeiten">Abwesenheit der Mitarbeiter ändern</h1>
        <c:choose><c:when test="${OKMSG != null }"><div class="col-lg-7 alert alert-success"><c:out value="${OKMSG }"></c:out></div></c:when></c:choose>
				<c:choose><c:when test="${FMSG != null }"><div class="col-lg-7 alert alert-danger"><c:out value="${FMSG }"></c:out></div></c:when></c:choose>
        <div class="col-lg-9 table-responsive">
          <form action="selektion.html" method="post">
            <table class="table table-striped table-hover">
              <thead>
                <tr>
                  <td><input type="text" class="form-control" id="mitarbeiterId" placeholder="Mitarbeiternummer" name="mitarbeiterId" value="" /></td>
                  <td><input type="text" class="form-control" id="abwesenheitVon" placeholder="Abwesend von" name="abwesendVon" value="" title="Abwesnheit von"/></td>
                  <td><input type="text" class="form-control" id="abwesenheitBis" placeholder="Abwesend bis" name="abwesendBis" value="" title="Abwesnheit bis"/></td>
                </tr>
                <tr class="titel"><td>Mitarbeitername</td><td>Abwesend von</td><td>Abwesend bis</td></tr>
              </thead>
              <tbody>                
 				<c:forEach var="item" items="${abwesenheitMitarbeitende}">
        			<tr valign="top">
          				<td><c:out value="${item.getMitarbeiterName()}" /></td>
          				<td><c:out value="${item.getAbwesenheitVonFormatiert()}" /></td>
          				<td><c:out value="${item.getAbwesenheitBisFormatiert()}" /></td>
          				<% if(anmeldung.getSchreibrechte()) { %>
          					<td><a href="<c:url value='/AbwesenheitMitarbeitende' />?aktion=anzeigen&abwesenheitId=<c:out value="${item.abwesenheitId}" />">bearbeiten</a></td>
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