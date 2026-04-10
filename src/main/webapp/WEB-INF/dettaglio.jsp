<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="container">
    <div class="dettaglio-container">
        
        <div class="dettaglio-img-box">
            <%-- Context Path per l'immagine --%>
            <img src="${pageContext.request.contextPath}/images/${prodottoSingolo.immagine}" alt="${prodottoSingolo.nome}" class="img-grande">
        </div>

        <div class="dettaglio-info-box">
            <p class="marchio-prodotto">${not empty prodottoSingolo.marchio ? prodottoSingolo.marchio : ''}</p>
            <h1>${prodottoSingolo.nome}</h1>
            <p class="dettagli-extra">
                ${not empty prodottoSingolo.colore ? prodottoSingolo.colore : ''} 
                ${not empty prodottoSingolo.formato ? ' | '.concat(prodottoSingolo.formato) : ''}
            </p>

            <p class="prezzo-grande">
                <fmt:formatNumber value="${prodottoSingolo.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;
            </p>
            
            <hr>
            
            <h3>Descrizione:</h3>
            <p class="descrizione-testo">${not empty prodottoSingolo.descrizione ? prodottoSingolo.descrizione : 'Nessuna descrizione disponibile.'}</p>

            <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post" class="form-acquisto">
             <input type="hidden" name="action" value="aggiungi">
             <input type="hidden" name="id" value="${prodottoSingolo.id}">
    
             <label for="quantita">Quantità:</label>

             <input type="number" name="quantita" id="quantita" value="1" min="1" max="${prodottoSingolo.quantita}" class="input-quantita">
    
             <button type="submit" class="btn-carrello btn-grande">Aggiungi al Carrello</button>
            </form>
        </div>

    </div>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />