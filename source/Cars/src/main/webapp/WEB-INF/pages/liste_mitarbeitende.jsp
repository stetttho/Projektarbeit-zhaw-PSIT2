<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Mitarbeiter ändern</title>
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
        <img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/edit.png" class="header" alt="bearbeiten">Mitarbeiter ändern</h1>
        <div class="col-lg-9 table-responsive">
          <form action="selektion.html" method="post">
            <table class="table table-striped table-hover">
              <thead>
                <tr>                  
                  <td><input type="text" class="form-control" id="name" placeholder="Name" name="name" value="" title="Name enthält"/></td>
                  <td><input type="text" class="form-control" id="vorname" placeholder="Vorname" name="vorname" value="" title="Vorname enthält"/></td>
                  <td><input type="tel" class="form-control" id="telNr" placeholder="Telefonnummer" name="telNr" value="" title="Telefonnummer beginnt mit" /></td>
                  <td><input type="text" class="form-control" id="email" placeholder="E-Mail" name="email" value="" title="E-Mail beginnt mit" /></td>
                  <td>
                    <select class="form-control" name="status" id="status">
                      <option id="alle" value="1">alle</option>
                      <option id="aktive" value="2">aktive</option>
                      <option id="passive" value="3">passive</option>
                    </select>
                  </td>
                </tr>
                <tr class="titel"><td>Name</td><td>Vorname</td><td>Telefonnummer</td><td>E-Mail</td><td>Status</td></tr>
              </thead>
              <tbody>                
 				<c:forEach var="item" items="${mitarbeitende}">
        			<tr valign="top">          				
          				<td><c:out value="${item.name}" /></td>
          				<td><c:out value="${item.vorname}" /></td>
          				<td><c:out value="${item.telNr}" /></td>
          				<td><c:out value="${item.email}" /></td>
	          			<td>
    				      <c:choose>
          					<c:when test="${item.istStatusAktiv()==true}">aktiv</c:when>
          					<c:otherwise>inaktiv</c:otherwise>
          				  </c:choose>
          				</td>
          				<% if(anmeldung.getSchreibrechte()) { %>
          					<td><a href="<c:url value='/Mitarbeitende' />?aktion=anzeigen&mitarbeiterId=<c:out value="${item.mitarbeiterId}" />">bearbeiten</a></td>
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