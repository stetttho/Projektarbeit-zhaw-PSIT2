<%@ page pageEncoding="utf-8" %>
<jsp:useBean id="wochenplan" class="ch.zhaw.ps16_gruppe08.cars.Wochenplan" scope="request"/>
<jsp:useBean id="ansichtTyp" class="java.lang.String" scope="request"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CARS - Wochenplan</title>

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
            <div class="col-sm-9 col-lg-9">
                <h1 class="page-header"><img src="http://srv-lab-t-911.zhaw.ch:8080/CARS/images/calendar.png" alt="Kalender" class="header" />Wochenplan anzeigen</h1>
                <div class="col-lg-12">
                	<section>
                		<h4>Ansicht</h4>
                        	<table id="ansichtTyp" class="table table-striped table-hover table-responsive">
                        	<tbody id="ansichtTypen">                 							
        					<tr valign="top"  >          				
          						<td <c:choose><c:when test="${ansichtTyp == 1}">class="bg-success"</c:when></c:choose>><a href="<c:url value='/Wochenplan' />?aktion=wochenplanausgabe&ansichtTyp=1&planId=<c:out value="${item.planId}" />&mitarbeiterId=<c:out value="${aktuellerMitarbeiter}" />">nach Mitarbeiter</a></td>          				          				    			    				      
          						<td <c:choose><c:when test="${ansichtTyp == 2}">class="bg-success"</c:when></c:choose>><a href="<c:url value='/Wochenplan' />?aktion=wochenplanausgabe&ansichtTyp=2&planId=<c:out value="${aktuellerPlan}" />&mitarbeiterId=<c:out value="${aktuellerMitarbeiter}" />">nach Fahrzeug</a></td>          				          				    			    				      
          					</tr>
              				</tbody>
                        </table>
                	</section>
                    <section>                       
                        <h4>verfügbare Pläne:</h4>
                        <table id="plane" class="table table-striped table-hover table-responsive">
                        <thead>
                                <tr><td>von - bis</td><td></td></tr>
                            </thead>
                        <tbody id="plane">                
 						<c:forEach var="item" items="${plane}">
        				<tr valign="top" <c:choose><c:when test="${aktuellerPlan == item.planId}">class="success"</c:when></c:choose> >          				
          				<td><c:out value="${item.getVonDatumFormatiert()}" /> - <c:out value="${item.getBisDatumFormatiert()}" /></td>          				          				    			    				      
          				<td><a href="<c:url value='/Wochenplan' />?aktion=wochenplanausgabe&planId=<c:out value="${item.planId}" />&mitarbeiterId=<c:out value="${aktuellerMitarbeiter}" />&ansichtTyp=<c:out value="${ansichtTyp}" />&fahrzeugId=<c:out value="${aktuellesFahrzeug}" />">auswählen</a></td>          				
        				</tr>
      					</c:forEach>              
              			</tbody>
                        </table>
                    </section>
                    <%
                    if(ansichtTyp.equals("1"))
                    {
                    %>                
                    <section>
                        <form method="post" action="wochenplan_liste.html">
                            <input type="text" value="" name="name" placeholder="Suchen ..." class="form-control" />
                        </form>
                        <h4>Suchergebnisse:</h4>
                        <table id="suchergebnisse" class="table table-striped table-hover table-responsive">
                        <thead>
                                <tr><td>Vorname Name</td><td></td></tr>
                            </thead>
                        <tbody id="listeMitarbeiternamen">                
 						<c:forEach var="item" items="${mitarbeitende}">
        				<tr valign="top" <c:choose><c:when test="${aktuellerMitarbeiter == item.mitarbeiterId}">class="success"</c:when></c:choose> >          				
          				<td><c:out value="${item.vorname}" /> <c:out value="${item.name}" /></td>          				          				    			    				      
          				<td><a href="<c:url value='/Wochenplan' />?aktion=wochenplanausgabe&mitarbeiterId=<c:out value="${item.mitarbeiterId}" />&ansichtTyp=<c:out value="${ansichtTyp}" />&planId=<c:out value="${aktuellerPlan}" />&fahrzeugId=<c:out value="${aktuellesFahrzeug}" />">auswählen</a></td>          				
        				</tr>
      					</c:forEach>              
              			</tbody>
                        </table>
                    </section>
                    <%
                    }
                    else
                    {
                        %>
                        <section>                       
                        <h4>Fahrzeuge:</h4>
                        <table id="plane" class="table table-striped table-hover table-responsive">
                        <tbody id="plane">                
 						<c:forEach var="item" items="${fahrzeuge}">
        				<tr valign="top" <c:choose><c:when test="${aktuellesFahrzeug == item.fahrzeugId}">class="success"</c:when></c:choose> >          				
          				<td><c:out value="${item.getTyp()}" /></td><td><c:out value="${item.getKontrollschildNr()}" /></td>          				     				          				    			    				  
          				<td><a href="<c:url value='/Wochenplan' />?aktion=wochenplanausgabe&planId=<c:out value="${aktuellerPlan}" />&mitarbeiterId=<c:out value="${aktuellerMitarbeiter}" />&ansichtTyp=<c:out value="${ansichtTyp}" />&fahrzeugId=<c:out value="${item.fahrzeugId}" />">auswählen</a></td>          				
        				</tr>
      					</c:forEach>              
              			</tbody>
                        </table>
                    </section>                         
                        <%
                    }
                    %>                    
                    <div class="col-lg-24 table-responsive">
                    <%
                    if(wochenplan.getRoutenAnzahl() > 0)
                    {
                        if(ansichtTyp.equals("1"))
                        {
                            %>
                            	<h2>Wochenplan für <c:out value="${wochenplan.getMitarbeiterName()}" /></h2>
                            <%
                        }
                        else
                        {
                            %>
                            	<h2>Wochenplan für <c:out value="${wochenplan.getFahrzeugKontrollSchild()}" /></h2>
                            <%                            
                        }
                        %>
                    	
						<table id="wochenplanMitarbeiter" class="table table-striped table-hover table-responsive">
							<thead>
							<!-- Hier folgt der Aufbau der Titelzeile mit Fahrzeug und Mitarbeiter -->
								<tr>
									<td></td>
									<c:forEach begin="1" end="${wochenplan.getRoutenAnzahl() }" step="1" var="index">                                            
                          				<td><c:out value="${wochenplan.getWochentag() }" /> <c:out value="${wochenplan.getDatumFormatiert() }" /> <br />
                          				<%
                          					if(ansichtTyp.equals("1"))
                          					{
                          				%>
                              					Fahrzeug: <c:out value="${wochenplan.getFahrzeugKontrollSchild()}" />
                              				<%
                          					}
                              				else
                              				{
                              				    %>
                              				    Mitarbeiter: <c:out value="${wochenplan.getMitarbeiterName()}" />
                              				    <%
                              				}
                              				%>
                              		    </td>
                              			<% wochenplan.moveNext(); %>                   
									</c:forEach>
 								</tr>
							</thead>
							<% wochenplan.resetIndex(); %>
							<!-- Hier wird pro Uhrzeit eine Zeile generiert -->
							<tbody>
							<%
								double uhrzeit = 8.0;
							%>
								<c:forEach begin="1" end="19" step="1" var="zeit">
									<tr>
										<!-- Zuerst die Spalte mit der Zeit -->										
										<td><%
									    	String strUhrzeit = "";
									        int stunden = (int)uhrzeit;
									        double minuten = uhrzeit - stunden;
									        minuten = minuten * 60;
									        if(stunden < 10)
									        {
									            strUhrzeit = "0";
									        }
									        strUhrzeit = strUhrzeit + stunden+":";
									        if(minuten < 10)
									        {
									            strUhrzeit = strUhrzeit + "0";
									        }
									        strUhrzeit = strUhrzeit + (int)minuten;									        
									    	out.print(strUhrzeit);
										%></td>
										<!-- für jeden Eintrag eine Spalte -->
										<c:forEach begin="1" end="${wochenplan.getRoutenAnzahl() }" step="1" var="index">
											<td>
											<% if(wochenplan.getKunde(uhrzeit)!=null)
											    {
													%>Kunde: <a href="<c:url value='/Kunde' />?aktion=anzeigen&kundeId=
														<% out.print(wochenplan.getKunde(uhrzeit).getKundeId()); 
														
														%>">
														
														<% out.print(wochenplan.getKunde(uhrzeit).getName()); 
														
														%></a> 
												   <%											    
											    }
											%>
											</td>
											<% wochenplan.moveNext(); %>
										</c:forEach>
										<% uhrzeit +=0.5; %>
										<% wochenplan.resetIndex(); %>
									</tr>	
								</c:forEach>

							</tbody>
						</table>
                        
                        
                        <%
                         
                    }
                    else
                    {
                     	%>
                            <h2>Kein Wochenplan vorhanden</h2>
                        <%
                     }
                    %>
					</div>                                                    
        </div>
    </div>
</body>
</html>
