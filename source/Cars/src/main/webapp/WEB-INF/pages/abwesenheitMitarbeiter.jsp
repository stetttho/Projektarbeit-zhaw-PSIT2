<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Abwesenheit des Mitarbeiters</title>
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
                <img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="header" alt="bearbeiten">Abwesenheit des Mitarbeiter erfassen </h1>
                <c:choose><c:when test="${OKMSG != null }"><div class="col-lg-7 alert alert-success"><c:out value="${OKMSG }"></c:out></div></c:when></c:choose>
				<c:choose><c:when test="${FMSG != null }"><div class="col-lg-7 alert alert-danger"><c:out value="${FMSG }"></c:out></div></c:when></c:choose>                
                <div class="col-lg-7">
                <form class="form-horizontal" action="<c:url value='/AbwesenheitMitarbeitende' />?aktion=speichern" method="post">
                    <div class="form-group">
                        <label for="abwesenheitId" class="col-sm-4 control-label hidden">Abwesenheitsnummer</label>
                        <div class="col-sm-6">
                            <input type="hidden" class="form-control" id="abwesenheitId" placeholder="AbwesenheitId" name="abwesenheitId" readonly="readonly" value="<c:out value="${item.abwesenheitId}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mitarbeiterId" class="col-sm-4 control-label">Mitarbeiternummer</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="mitarbeiterId" placeholder="Mitarbeiternummer" name="mitarbeiterId" value="<c:out value="${item.mitarbeiterId}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="abwesenheitVon" class="col-sm-4 control-label">Abwesenheit von</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="abwesenheitVon" placeholder="dd.mm.yyyy" name="abwesenheitVon" value="<c:out value="${item.getAbwesenheitVonFormatiert()}" />" />
                        </div>
                    </div>
                    <div class="form-group">
                       <label for="abwesenheitBis" class="col-sm-4 control-label">Abwesenheit bis</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="abwesenheitBis" placeholder="dd.mm.yyyy" name="abwesenheitBis" value="<c:out value="${item.getAbwesenheitBisFormatiert()}" />" />
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
