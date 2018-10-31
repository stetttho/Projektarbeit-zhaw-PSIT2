<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Fahrzeug erfassen</title>
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
                <img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="header" alt="bearbeiten">Fahrzeug erfassen </h1>
                <c:choose><c:when test="${OKMSG != null }"><div class="col-lg-7 alert alert-success"><c:out value="${OKMSG }"></c:out></div></c:when></c:choose>
				<c:choose><c:when test="${FMSG != null }"><div class="col-lg-7 alert alert-danger"><c:out value="${FMSG }"></c:out></div></c:when></c:choose>                
                <div class="col-lg-7">
                <form class="form-horizontal" action="<c:url value='/Fahrzeuge' />?aktion=speichern" method="post">
                    <div class="form-group">
                        <label for="fahrzeugid" class="col-sm-4 control-label hidden">Fahrzeugnummer</label>
                        <div class="col-sm-6">
                            <input type="hidden" class="form-control" id="fahrzeugId" placeholder="FahrzeugId" name="fahrzeugId" readonly="readonly" value="<c:out value="${item.fahrzeugId}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mitarbeitername" class="col-sm-4 control-label">Marke und Modell</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="typ" placeholder="Marke und Modell" name="typ" value="<c:out value="${item.typ}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mitarbeitervorname" class="col-sm-4 control-label">Kontrollschild</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="kontrollschildNr" placeholder="Kontrollschild" name="kontrollschildNr" value="<c:out value="${item.kontrollschildNr}" />" />
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-10">
                            <div class="checkbox">
                              <label>
                                  <input type="checkbox" name="status" value="1" id="status" <c:choose><c:when test="${item.istStatusAktiv()==true || item==null}"><c:out value="checked=checked" /></c:when></c:choose> /> aktiv
                              </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-10">
                            <input type="submit" class="btn btn-primary" name="speichern" value="Speichern" />
                        </div>
                    </div>
                </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>