<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="checkout-page">
    <div class="checkout-container">
        <h2 class="checkout-title">Completamento Ordine</h2>
        <p class="checkout-subtitle">Inserisci i tuoi dati in modo sicuro per finalizzare l'acquisto.</p>
        
        <c:if test="${not empty erroreCheckout}">
            <div class="messaggio-errore-box">${erroreCheckout}</div>
        </c:if>
        
        <form id="formCheckout" action="${pageContext.request.contextPath}/CheckoutServlet" method="post" class="styled-form">
            
            <div class="checkout-section">
                <h3 class="checkout-section-title"><i class="fa-solid fa-truck-fast"></i> 1. Indirizzo di Spedizione</h3>
                
                <div class="form-group">
                    <label for="indirizzo">Via/Piazza e Civico</label>
                    <input type="text" id="indirizzo" name="indirizzo" placeholder="Es. Via Roma, 10">
                    <span id="erroreIndirizzo" class="errore-testo"></span>
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="citta">Città</label>
                        <input type="text" id="citta" name="citta" placeholder="Es. Milano">
                        <span id="erroreCitta" class="errore-testo"></span>
                    </div>

                    <div class="form-group half-width">
                        <label for="cap">CAP</label>
                        <input type="text" id="cap" name="cap" placeholder="Es. 20100">
                        <span id="erroreCap" class="errore-testo"></span>
                    </div>
                </div>
            </div>

            <div class="checkout-section">
                <h3 class="checkout-section-title"><i class="fa-regular fa-credit-card"></i> 2. Metodo di Pagamento</h3>
                
                <div class="form-group">
                    <label for="carta">Numero Carta di Credito</label>
                    <input type="text" id="carta" name="carta" placeholder="16 cifre senza spazi">
                    <span id="erroreCarta" class="errore-testo"></span>
                </div>
            </div>

            <button type="submit" class="btn-primary btn-full checkout-submit-btn">
                <i class="fa-solid fa-lock"></i> Conferma e Paga
            </button>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/script/validazioneCheckout.js"></script>

<jsp:include page="/WEB-INF/footer.jsp" />