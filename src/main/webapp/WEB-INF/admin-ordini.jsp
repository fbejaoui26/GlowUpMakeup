<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="admin-theme">
    
    <div class="admin-page-wrapper" style="max-width: 1200px;">
        
        <div class="admin-header-flex">
            <h1 class="titolo-admin"><i class="fa-solid fa-boxes-packing"></i> Report Ordini</h1>
        </div>
        
        <c:if test="${not empty erroreAdminOrdini}">
            <div class="messaggio-errore-box">${erroreAdminOrdini}</div>
        </c:if>

        <div class="admin-filter-bar">
            <form action="${pageContext.request.contextPath}/AdminOrdiniServlet" method="get" class="form-filtri-admin">
                
                <div class="filtro-gruppo">
                    <label for="dataInizio">Da data:</label>
                    <input type="date" id="dataInizio" name="dataInizio" value="${param.dataInizio}">
                </div>
                
                <div class="filtro-gruppo">
                    <label for="dataFine">A data:</label>
                    <input type="date" id="dataFine" name="dataFine" value="${param.dataFine}">
                </div>
                
                <div class="filtro-gruppo">
                    <label for="emailCliente">Email Cliente:</label>
                    <input type="text" id="emailCliente" name="emailCliente" placeholder="Cerca per email..." value="${param.emailCliente}">
                </div>
                
                <div class="filtri-azioni">
                    <button type="submit" class="btn-primary btn-filtro">
                        <i class="fa-solid fa-filter"></i> Filtra
                    </button>
                    <a href="${pageContext.request.contextPath}/AdminOrdiniServlet" class="btn-outline btn-filtro" title="Azzera Filtri">
                        <i class="fa-solid fa-rotate-right"></i>
                    </a>
                </div>
                
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
                            <tr>
                                <td colspan="6" class="nessun-dato">
                                    <i class="fa-solid fa-folder-open fa-2x"></i><br>
                                    Nessun ordine trovato con questi criteri.
                                </td>
                            </tr>
                        </c:when>
                        
                        <c:otherwise>
                            <c:forEach var="o" items="${listaOrdini}">
                                <tr>
                                    <td class="td-id">#${o.id}</td>
                                    <td><fmt:formatDate value="${o.dataOrdine}" pattern="dd/MM/yyyy HH:mm" /></td>
                                    <td><strong>${o.utenteEmail}</strong></td>
                                    <td class="td-prezzo"><fmt:formatNumber value="${o.totale}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;</td>
                                    
                                    <td>
                                        <form action="${pageContext.request.contextPath}/AggiornaStatoOrdineServlet" method="post" class="form-aggiorna-stato">
                                            <input type="hidden" name="idOrdine" value="${o.id}">
                                            
                                            <select name="nuovoStato" class="select-stato-admin">
                                                <option value="In elaborazione" ${o.stato == 'In elaborazione' ? 'selected' : ''}>In elaborazione</option>
                                                <option value="Spedito" ${o.stato == 'Spedito' ? 'selected' : ''}>Spedito</option>
                                                <option value="Consegnato" ${o.stato == 'Consegnato' ? 'selected' : ''}>Consegnato</option>
                                                <option value="Annullato" ${o.stato == 'Annullato' ? 'selected' : ''}>Annullato</option>
                                            </select>
                                            
                                            <button type="submit" class="btn-salva-stato" title="Salva nuovo stato">
                                                <i class="fa-solid fa-check"></i>
                                            </button>
                                        </form>
                                    </td>
                                    
                                    <td>
                                        <ul class="lista-prodotti-admin">
                                            <c:forEach var="el" items="${o.elementi}">
                                                <li><strong>${el.quantita}x</strong> ${el.prodotto.nome} (<fmt:formatNumber value="${el.prezzoAcquisto}" type="number" minFractionDigits="2" maxFractionDigits="2"/>&euro;)</li>
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
</div>

<jsp:include page="/WEB-INF/footer.jsp" />