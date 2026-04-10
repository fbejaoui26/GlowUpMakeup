<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="cart-container">
    <h2>Il tuo Carrello</h2>
    
    <%-- Controllo se il carrello è assente o vuoto --%>
    <c:choose>
        <c:when test="${empty carrello or empty carrello.elementi}">
            <div class="cart-vuoto">
                <p>Il tuo carrello è attualmente vuoto.</p>
                <a href="${pageContext.request.contextPath}/HomeServlet" class="btn-continua">Continua lo shopping</a>
            </div>
        </c:when>
        
        <c:otherwise>
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Prodotto</th>
                        <th>Prezzo Singolo</th>
                        <th>Quantità</th>
                        <th>Totale Riga</th>
                        <th>Azione</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${carrello.elementi}">
                        <tr>
                            <td>
                                <img src="${pageContext.request.contextPath}/images/${item.prodotto.immagine}" alt="${item.prodotto.nome}" class="cart-img">
                                ${item.prodotto.nome}
                            </td>
                            <td><fmt:formatNumber value="${item.prodotto.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post" class="form-quantita">
                                    <input type="hidden" name="action" value="aggiorna">
                                    <input type="hidden" name="id" value="${item.prodotto.id}">
                                    <input type="number" name="quantita" value="${item.quantita}" min="1" class="input-quantita auto-submit">
                                    <button type="submit" class="btn-aggiorna" style="display:none;">Aggiorna</button>
                                </form>
                            </td>
                            <td><fmt:formatNumber value="${item.prezzoTotale}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="rimuovi">
                                    <input type="hidden" name="id" value="${item.prodotto.id}">
                                    <button type="submit" class="btn-rimuovi" style="border:none; cursor:pointer;">Rimuovi</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <div class="cart-summary">
                <h3>Totale da pagare: <fmt:formatNumber value="${carrello.prezzoTotaleCarrello}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;</h3>
                <a href="${pageContext.request.contextPath}/CheckoutServlet" class="btn-checkout">Procedi all'Acquisto</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/script/carrello.js"></script>

<jsp:include page="/WEB-INF/footer.jsp" />