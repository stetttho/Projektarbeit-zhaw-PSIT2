<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>

<%@ page import = "ch.zhaw.ps16_gruppe08.cars.*" %>
<jsp:useBean id="anmeldung" class="ch.zhaw.ps16_gruppe08.cars.Benutzer" scope="session"/>


<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Herzlich Willkommen</title>

  <link rel="stylesheet" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/css/bootstrap.css">
  <link rel="stylesheet" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/css/navbar-fixed-side.css">
  <link rel="shortcut icon" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="http://srv-lab-t-911.zhaw.ch:8080/CARS/css/cars.css">
  <script src="http://srv-lab-t-911.zhaw.ch:8080/CARS/js/application.js"></script>

</head>
<body>
<% if(request.getParameter("aktion") != null && request.getParameter("aktion").equals("abmelden")){
		anmeldung.setAnzahlVersuche(0);
		session.invalidate();}%>
<% anmeldung.setName(request.getParameter("name")); %>      
<% anmeldung.setPasswort(request.getParameter("passwort")); %>      
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% if(!anmeldung.ueberpruefeBenutzer() || !anmeldung.ueberpruefePasswort()){ %>

<div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 col-md-2">
                <nav class="navbar navbar-default navbar-fixed-side">
                    <div class="container">
                        <div class="navbar-header">
                            <p class="navbar-brand"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/logo.svg" alt="Logo Cars" class="logo" /></p>
                        </div>
                    </div>
                </nav>
            </div>
            <div class="col-sm-9 col-lg-10">
                <h1 class="page-header">Willkommen bei CARS</h1>
                <p>Bitte melden Sie sich an:</p>
                <%if(anmeldung.getAnzahlVersuche()>1){ %>
                <div class="col-lg-7 alert alert-danger"><p>Benutzer- oder Passwortangaben sind nicht korrekt!</p></div>
                <%} %>
                <%if(request.getParameter("aktion")!= null && request.getParameter("aktion").equals("abmelden")){ %>
                <div class="col-lg-7 alert alert-success"><p>Benutzer erfolgreich abgemeldet.</p></div>
                <%} %>
                <div class="col-lg-7">
                    <form action="index.jsp" method="post" class="form-horizontal">
                        <div class="form-group">
                            <label for="benutzer" class="col-sm-2 control-label">Benutzername</label>
                            <div class="col-sm-6">
                                <input type="text" name="name" class="form-control" id="name" placeholder="Benutzername"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="benutzername" class="col-sm-2 control-label">Passwort</label>
                            <div class="col-sm-6">
                                <input type="password" name="passwort" class="form-control" id="passwort" placeholder="Passwort"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <input type="hidden" name="aktion" value="" />
                                <input type="submit" class="btn btn-primary" name="anmelden" value="anmelden" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="row">
            <footer class="col-sm-3 col-md-2">
                &copy; Projektgruppe 8<br />Daniel Huonder<br /> Markus Wüest<br />Thomas Stettler<br /> Philipp Sporrer<br />Version 1.0
            </footer>
        </div>
    </div>
    
    <% } else if (anmeldung.ueberpruefeBenutzer() && anmeldung.ueberpruefePasswort()){ %>
		<jsp:forward page="/WEB-INF/pages/start.jsp" />
		
	<% } %>
</body>
</html>
