<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Kunde ändern</title>
  <link rel="stylesheet" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/css/bootstrap.css">
  <link rel="stylesheet" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/css/navbar-fixed-side.css">
  <link rel="shortcut icon" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/css/cars.css">
  <script src="http://srv-lab-t-911.zhaw.ch:8080/CARS/js/application.js"></script>
</head>

<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="menu.jsp" %>
 
    <div class="col-sm-9 col-lg-10">
        <h1 class="page-header">
        <img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/edit.png" class="header" alt="bearbeiten">Kunde ändern</h1>
        <div class="col-lg-9 table-responsive">
          <form action="selektion.html" method="post">
            <table class="table table-striped table-hover">
              <thead>
                <tr>
                  <td><input type="text" class="form-control" id="kundeId" placeholder="Kundennummer" name="kundeId" value="" /></td>
                  <td><input type="text" class="form-control" id="name" placeholder="Name" name="name" value="" title="Name enthält"/></td>
                  <td><input type="text" class="form-control" id="emailAdresse" placeholder="E-Mail Adresse" name="emailAdresse" value="" title="E-Mail Adresse enthält"/><br /><input type="tel" class="form-control" id="telNr" placeholder="Telefonnummer" name="telNr" value="" title="Telefonnummer beginnt mit" /></td>
				  <td>
				  	<select class="form-control" name="status" id="status">
                    	<option id="1" value="1">1</option>
                    	<option id="2" value="2">2</option>
                    	<option id="3" value="3">3</option>
                  	</select>
                  </td>
				  <td></td>
                  
				  <td>
                    <select class="form-control" name="status" id="status">
                      <option id="alle" value="1">alle</option>
                      <option id="aktive" value="2">aktive</option>
                      <option id="passive" value="3">passive</option>
                    </select>
                  </td>
                </tr>
                <tr class="titel"><td>Kundennummer</td><td>Name</td><td>E-Mail<br />Telefonnummer</td><td>Prio</td><td>Koordinaten</td><td>Status</td></tr>
              </thead>
              <tbody>                
 				<c:forEach var="item" items="${kunde}">
        			<tr valign="top">
          				<td><c:out value="${item.kundeId}" /></td>
          				<td><c:out value="${item.name}" /></td>
          				<td><c:out value="${item.emailAdresse}" /><br /><c:out value="${item.telefonNr}" /></td>
						<td><c:out value="${item.prioritaet}" /></td>
						<td><c:out value="${item.getKoordinaten().x.intValue()}" /> /
						<c:out value="${item.getKoordinaten().y.intValue()}" /></td>
	          			<td>
    				      <c:choose>
          					<c:when test="${item.istStatusAktiv()==true}">aktiv</c:when>
          					<c:otherwise>inaktiv</c:otherwise>
          				  </c:choose>
          				</td>
          				<% if(anmeldung.getSchreibrechte()) { %>
          					<td><a href="<c:url value='/Kunde' />?aktion=anzeigen&kundeId=<c:out value="${item.kundeId}" />">bearbeiten</a></td>
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