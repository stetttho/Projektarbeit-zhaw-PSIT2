<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Kunde erfassen</title>
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
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-9 col-lg-10">
                <h1 class="page-header">
                <img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="header" alt="bearbeiten">Kunde erfassen </h1>
                <c:choose><c:when test="${OKMSG != null }"><div class="col-lg-7 alert alert-success"><c:out value="${OKMSG }"></c:out></div></c:when></c:choose>
				<c:choose><c:when test="${FMSG != null }"><div class="col-lg-7 alert alert-danger"><c:out value="${FMSG }"></c:out></div></c:when></c:choose>                
                <div class="col-lg-7">
                <form class="form-horizontal" action="<c:url value='/Kunde' />?aktion=speichern" method="post">
                    <div class="form-group">
                        <label for="kundeID" class="col-sm-4 control-label">Kundennummer</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="kundeId" placeholder="Kundennummer" name="kundeId" readonly="readonly" value="<c:out value="${item.kundeId}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="Kundename" class="col-sm-4 control-label">Name</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" placeholder="Name" name="name" value="<c:out value="${item.name}" />" />
                        </div>
                    </div>
					<div class="form-group">
                        <label for="emailAdresse" class="col-sm-4 control-label">E-Mail Adresse</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="emailAdresse" placeholder="E-Mail Adresse" name="emailAdresse" value="<c:out value="${item.emailAdresse}" />" />
                        </div>
                    </div>
					<div class="form-group">
                        <label for="telNr" class="col-sm-4 control-label">Telefonnummer</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="telNr" placeholder="Telefonnummer" name="telNr" value="<c:out value="${item.telefonNr}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="prioritaet" class="col-sm-4 control-label">Prioritaet</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="prioritaet" id="prioritaet">
                      			<option <c:choose><c:when test="${item.prioritaet==1}"><c:out value="selected=selected" /></c:when></c:choose> id="1" value="1">tief</option>
                      			<option <c:choose><c:when test="${item.prioritaet==2}"><c:out value="selected=selected" /></c:when></c:choose> id="2" value="2">normal</option>
                      			<option <c:choose><c:when test="${item.prioritaet==3}"><c:out value="selected=selected" /></c:when></c:choose> id="3" value="3">hoch</option>
                    		</select>
                        </div>
                    </div>					
					
                    <div class="form-group">
						<label class="col-sm-4 control-label">Standort</label>
                        <div class="col-sm-3">
                            
                            <input type="text" class="form-control" id="xKoordinate" placeholder="X-Koordinate" name="xKoordinate" value="<c:out value="${item.getKoordinaten().x.intValue()}" />" />
						</div>
						<div class="col-sm-3">	
							<input type="text" class="form-control" id="yKoordinate" placeholder="Y-Koordinate" name="yKoordinate" value="<c:out value="${item.getKoordinaten().y.intValue()}" />" />
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
                        <div class="col-sm-offset-4 col-sm-4">
                        	<input type="button" class="btn btn-primary" name="zurueck" value="Zurück" onClick="history.go(-1);return true;">
                        </div>
                        <div class="col-sm-4">
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