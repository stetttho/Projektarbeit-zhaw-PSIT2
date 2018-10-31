<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="anmeldung" class="ch.zhaw.ps16_gruppe08.cars.Benutzer" scope="session"/>
            <div class="col-sm-3 col-md-2">
                <nav class="navbar navbar-default navbar-fixed-side">
                    <div class="container">
                        <div class="navbar-header">
                            <button class="navbar-toggle" data-target=".navbar-collapse" data-toggle="collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <p class="navbar-brand"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/logo.svg" alt="Logo Cars" class="logo" /></p>
                        </div>
                        <div class="navbar-collapse collapse" aria-expanded="false" role="button">
                             <ul class="nav navbar-nav">
                                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Mitarbeitende<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                    <% if(anmeldung.getSchreibrechte()) { %>
                                        <li <c:choose><c:when test="${(appMainContext=='mitarbeitende' && param.aktion=='neu') || (appMainContext=='mitarbeitende' && param.aktion=='anzeigen') || (appMainContext=='mitarbeitende' && param.aktion=='speichern')}">class="active"</c:when></c:choose>><a href="<c:url value='/Mitarbeitende' />?aktion=neu"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="plus_menu" alt="hinzu" />Mitarbeitende erfassen</a></li>
                                    <% } %>
                                        <li <c:choose><c:when test="${(appMainContext=='mitarbeitende' && param.aktion=='') || (appMainContext=='mitarbeitende' && param.aktion=='liste')}">class="active"</c:when></c:choose>><a href="<c:url value='/Mitarbeitende' />?aktion=liste"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/edit.png" class="edit_menu" alt="editieren" />Mitarbeitende ändern</a></li>
                                    </ul>
                                </li>
                                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Abwesenheit Mitarbeitende<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                    <% if(anmeldung.getSchreibrechte()) { %>
                                        <li <c:choose><c:when test="${(appMainContext=='abwesenheitMitarbeitende' && param.aktion=='neu') || (appMainContext=='abwesenheitMitarbeitende' && param.aktion=='anzeigen') || (appMainContext=='abwesenheitMitarbeitende' && param.aktion=='speichern')}">class="active"</c:when></c:choose>><a href="<c:url value='/AbwesenheitMitarbeitende' />?aktion=neu"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="plus_menu" alt="hinzu" />Abwesenheit erfassen</a></li>
                                    <% } %>
                                        <li <c:choose><c:when test="${(appMainContext=='abwesenheitMitarbeitende' && param.aktion=='') || (appMainContext=='abwesenheitMitarbeitende' && param.aktion=='liste')}">class="active"</c:when></c:choose>><a href="<c:url value='/AbwesenheitMitarbeitende' />?aktion=liste"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/edit.png" class="edit_menu" alt="editieren" />Abwesenheit ändern</a></li>
                                    </ul>
                                </li>
                                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Fahrzeuge<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                    <% if(anmeldung.getSchreibrechte()) { %>
                                        <li <c:choose><c:when test="${(appMainContext=='fahrzeuge' && param.aktion=='neu') || (appMainContext=='fahrzeuge' && param.aktion=='anzeigen') || (appMainContext=='fahrzeuge' && param.aktion=='speichern')}">class="active"</c:when></c:choose> ><a href="<c:url value='/Fahrzeuge' />?aktion=neu"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="plus_menu" alt="hinzu" />Fahrzeuge erfassen</a></li>
                                    <% } %>   
                                        <li <c:choose><c:when test="${(appMainContext=='fahrzeuge' && param.aktion=='') || (appMainContext=='fahrzeuge' && param.aktion=='liste')}">class="active"</c:when></c:choose>><a href="<c:url value='/Fahrzeuge' />?aktion=liste"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/edit.png" class="edit_menu" alt="editieren" />Fahrzeuge ändern</a></li>
                                    </ul>
                                </li>
                                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Kunden<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                    <% if(anmeldung.getSchreibrechte()) { %>
                                        <li <c:choose><c:when test="${(appMainContext=='kunde' && param.aktion=='neu') || (appMainContext=='kunde' && param.aktion=='anzeigen') || (appMainContext=='kunde' && param.aktion=='speichern')}">class="active"</c:when></c:choose>><a href="<c:url value='/Kunde' />?aktion=neu"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="plus_menu" alt="hinzu" />Kunden erfassen</a></li>
                                    <% } %>    
                                        <li <c:choose><c:when test="${(appMainContext=='kunde' && param.aktion=='') || (appMainContext=='kunde' && param.aktion=='liste')}">class="active"</c:when></c:choose>><a href="<c:url value='/Kunde' />?aktion=liste"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/edit.png" class="edit_menu" alt="editieren" />Kunden ändern</a></li>
                                    </ul>
                                </li>
                                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Kundentermine<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                    <% if(anmeldung.getSchreibrechte()) { %>
                                    	<li <c:choose><c:when test="${(appMainContext=='kundentermine' && param.aktion=='neu') || (appMainContext=='kundentermine' && param.aktion=='anzeigen') || (appMainContext=='kundentermine' && param.aktion=='speichern')}">class="active"</c:when></c:choose>><a href="<c:url value='/Kundentermine' />?aktion=neu"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/plus.png" class="plus_menu" alt="hinzu" />Kundentermin erfassen</a></li>
                                    <% } %>    
                                        <li <c:choose><c:when test="${(appMainContext=='kundentermine' && param.aktion=='') || (appMainContext=='kundentermine' && param.aktion=='liste')}">class="active"</c:when></c:choose>><a href="<c:url value='/Kundentermine' />?aktion=liste"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/edit.png" class="edit_menu" alt="editieren" />Kundentermin ändern</a></li>
                                    </ul>
                                </li>
                                <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Wochenplan<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                    <% if(anmeldung.getSchreibrechte()) { %>
                                        <li <c:choose><c:when test="${(appMainContext=='berechnung')}">class="active"</c:when></c:choose>><a href="<c:url value='/Berechnung?aktion=anzeigen' />"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/refresh.png" class="calc_menu" alt="berechnen" />Berechnung starten</a></li>

                                        
                                    <% } %>    
                                       <li <c:choose><c:when test="${(appMainContext=='wochenplan' && param.aktion=='wochenplanausgabe')}">class="active"</c:when></c:choose>><a href="<c:url value='/Wochenplan' />?aktion=wochenplanausgabe"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/calendar.png" class="calendar_menu" alt="Kalender" />Wochenplan anzeigen</a></li>

										<li <c:choose><c:when test="${(appMainContext=='schluesselausgabe' && param.aktion=='schluesselausgabe')}">class="active"</c:when></c:choose>><a href="<c:url value='/Wochenplan' />?aktion=schluesselausgabe"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/calendar.png" class="calendar_menu" alt="Kalender" />Schlüsselausgabe anzeigen</a></li>                                    
                                    </ul>
                                </li>
                                <li><a href="index.jsp?aktion=abmelden"><b><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/shutdown.png" class="shutdown_menu" alt="ausschalten" />Abmelden</b></a></li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </div>