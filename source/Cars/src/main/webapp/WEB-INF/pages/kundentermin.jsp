<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Kundentermin erfassen</title>
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
                <img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="header" alt="bearbeiten">Kundentermin erfassen </h1>
                <c:choose><c:when test="${OKMSG != null }"><div class="col-lg-7 alert alert-success"><c:out value="${OKMSG }"></c:out></div></c:when></c:choose>
				<c:choose><c:when test="${FMSG != null }"><div class="col-lg-7 alert alert-danger"><c:out value="${FMSG }"></c:out></div></c:when></c:choose>                
                <div class="col-lg-7">
                <form class="form-horizontal" action="<c:url value='/Kundentermine' />?aktion=speichern" method="post">
                    <div class="form-group">
                        <label for="kundenterminId" class="col-sm-4 control-label hidden">Kundentermin-ID</label>
                        <div class="col-sm-6">
                            <input type="hidden" class="form-control" id="kundenterminId" placeholder="Kundentermin-ID" readonly="readonly" name="kundenterminId" value="<c:out value="${item.kundenterminId}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="personName" class="col-sm-4 control-label">Kundennummer</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="kundeId" placeholder="Kundennummer" name="kundeId" value="<c:out value="${item.kundeId}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="datum" class="col-sm-4 control-label">Datum</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="datum" placeholder="Datum" name="datum" value="<c:out value="${item.getDatumFormatiert()}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="startzeit" class="col-sm-4 control-label">Startzeit</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="startzeit" placeholder="Startzeit" name="startzeit" value="<c:out value="${item.startzeit}" />" />
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="endzeit" class="col-sm-4 control-label">Endzeit</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="endzeit" placeholder="Endzeit" name="endzeit" value="<c:out value="${item.endzeit}" />" />
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