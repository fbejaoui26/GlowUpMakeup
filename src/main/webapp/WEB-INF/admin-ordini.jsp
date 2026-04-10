<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GlowUp Admin - Report Ordini</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
    <header class="header-admin">
        <div class="logo">⚙️ Gestione Ordini</div>
        <a href="${pageContext.request.contextPath}/AdminDashboard">Torna alla Dashboard</a>
    </header>

    <div class="admin-container">
        
        <c:if test="${not empty erroreAdminOrdini}">
            <p class="messaggio-errore errore-globale">${erroreAdminOrdini}</p>
        </c:if>
    
        <div class="form-container-admin" style="margin-bottom: 30px;">
            <form action="${pageContext.request.contextPath}/AdminOrdiniServlet" method="get" class="form-admin" style="display:flex; gap:15px; align-items:flex-end;">
                <div class="form-gruppo">
                    <label>Da:</label>
                    <input type="date" name="dataInizio" value="${param.dataInizio}">
                </div>
                <div class="form-gruppo">
                    <label>A:</label>
                    <input type="date" name="dataFine" value="${param.dataFine}">
                </div>
                <div class="form-gruppo">
                    <label>Email Cliente:</label>
                    <input type="text" name="emailCliente" placeholder="Cerca email..." value="${param.emailCliente}">
                </div>
                <button type="submit" class="btn-salva">Filtra</button>
                <a href="${pageContext.request.contextPath}/AdminOrdiniServlet" class="btn-annulla" style="padding:10px;">Reset</a>
            </form>
        </div>

        <div class="table-responsive">
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID Ordine</th>
                        <th>Data</th>
                        <th>Cliente</th>
                        <th>Totale</th>
                        <th>Stato</th>
                        <th>Dettagli Prodotti</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty listaOrdini}">
                            <tr><td colspan="6" class="nessun-dato">Nessun ordine trovato con questi criteri.</td></tr>
                        </c:when>
                        
                        <c:otherwise>
                            <c:forEach var="o" items="${listaOrdini}">
                                <tr>
                                    <td>#${o.id}</td>
                                    <td><fmt:formatDate value="${o.dataOrdine}" pattern="dd/MM/yyyy HH:mm" /></td>
                                    <td><strong>${o.utenteEmail}</strong></td>
                                    <td style="white-space: nowrap;"><fmt:formatNumber value="${o.totale}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/AggiornaStatoOrdineServlet" method="post" style="display: flex; align-items: center; gap: 8px; margin: 0;">
                                            <input type="hidden" name="idOrdine" value="${o.id}">
                                            
                                            <select name="nuovoStato" style="padding: 5px; border-radius: 4px; border: 1px solid #ccc;">
                                                <option value="In elaborazione" ${o.stato == 'In elaborazione' ? 'selected' : ''}>In elaborazione</option>
                                                <option value="Spedito" ${o.stato == 'Spedito' ? 'selected' : ''}>Spedito</option>
                                                <option value="Consegnato" ${o.stato == 'Consegnato' ? 'selected' : ''}>Consegnato</option>
                                                <option value="Annullato" ${o.stato == 'Annullato' ? 'selected' : ''}>Annullato</option>
                                            </select>
                                            
                                            <button type="submit" class="btn-icona" title="Salva nuovo stato" style="background-color: #27ae60; color: white; padding: 4px 8px; border-radius: 4px; font-size: 0.9em; border: none; cursor: pointer;">
                                                Salva
                                            </button>
                                        </form>
                                    </td>
                                    <td>
                                        <ul style="list-style:none; padding:0; margin:0; font-size:0.85em;">
                                            <c:forEach var="el" items="${o.elementi}">
                                                <li>• ${el.quantita}x ${el.prodotto.nome} (<fmt:formatNumber value="${el.prezzoAcquisto}" type="number" minFractionDigits="2" maxFractionDigits="2"/>&euro;)</li>
                                            </c:forEach>
                                        </ul>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>