<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="cart-page-wrapper">
    <h2 class="cart-page-title">Il tuo Carrello</h2>
    
    <%-- Controllo se il carrello è assente o vuoto --%>
    <c:choose>
        <c:when test="${empty carrello or empty carrello.elementi}">
            <div class="cart-vuoto-box">
                <i class="fa-solid fa-bag-shopping fa-3x cart-vuoto-icon"></i>
                <p>Il tuo carrello è attualmente vuoto.</p>
                <a href="${pageContext.request.contextPath}/CatalogoServlet" class="btn-primary">Continua lo shopping</a>
            </div>
        </c:when>
        
        <c:otherwise>
            <div class="cart-layout">
                
                <div class="cart-items-list">
                    <c:forEach var="item" items="${carrello.elementi}">
                        <div class="cart-item-card">
                            
                            <div class="cart-item-img-box">
                                <a href="${pageContext.request.contextPath}/DettaglioServlet?id=${item.prodotto.id}">
                                    <img src="${pageContext.request.contextPath}/images/${item.prodotto.immagine}" alt="${item.prodotto.nome}">
                                </a>
                            </div>
                            
                            <div class="cart-item-details">
                                <a href="${pageContext.request.contextPath}/DettaglioServlet?id=${item.prodotto.id}" class="cart-item-name">
                                    ${item.prodotto.nome}
                                </a>
                                <p class="cart-item-price">
                                    <fmt:formatNumber value="${item.prodotto.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;
                                </p>
                            </div>
                            
                            <div class="cart-item-actions">
                                <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post" class="form-quantita cart-qty-form">
                                    <input type="hidden" name="action" value="aggiorna">
                                    <input type="hidden" name="id" value="${item.prodotto.id}">
                                    <input type="number" name="quantita" value="${item.quantita}" min="1" class="input-quantita-cart auto-submit">
                                    <button type="submit" style="display:none;">Aggiorna</button>
                                </form>
                                
                                <p class="cart-item-total">
                                    <fmt:formatNumber value="${item.prezzoTotale}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;
                                </p>
                                
                                <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post" class="cart-remove-form">
                                    <input type="hidden" name="action" value="rimuovi">
                                    <input type="hidden" name="id" value="${item.prodotto.id}">
                                    <button type="submit" class="btn-rimuovi-icon" title="Rimuovi prodotto">
                                        <i class="fa-regular fa-trash-can"></i>
                                    </button>
                                </form>
                            </div>
                            
                        </div>
                    </c:forEach>
                </div>
                
                <div class="cart-summary-box">
                    <h3>Riepilogo Ordine</h3>
                    
                    <div class="summary-row">
                        <span>Subtotale (${carrello.numeroElementiTotali} articoli)</span>
                        <span><fmt:formatNumber value="${carrello.prezzoTotaleCarrello}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;</span>
                    </div>
                    
                    <div class="summary-row">
                        <span>Spedizione</span>
                        <span>Gratuita</span>
                    </div>
                    
                    <div class="summary-total">
                        <span>Totale</span>
                        <span><fmt:formatNumber value="${carrello.prezzoTotaleCarrello}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;</span>
                    </div>
                    
                    <a href="${pageContext.request.contextPath}/CheckoutServlet" class="btn-primary btn-full checkout-btn">Procedi all'Acquisto</a>
                </div>
                
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/script/carrello.js"></script>

<jsp:include page="/WEB-INF/footer.jsp" />