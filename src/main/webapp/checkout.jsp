<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.model.Utente" %>
<%
    // controllo accessi
    Utente utente = (Utente) session.getAttribute("utenteLoggato");
    if (utente == null) {
        request.setAttribute("erroreLogin", "Devi effettuare l'accesso per poter completare l'acquisto.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }
%>
<jsp:include page="header.jsp" />

<div class="form-container">
    <h2>Dettagli di Spedizione e Pagamento</h2>
    
    <form id="formCheckout" action="CheckoutServlet" method="post">
        
        <h3>Indirizzo di Spedizione</h3>
        <div class="form-group">
            <label for="indirizzo">Via/Piazza e Civico:</label>
            <input type="text" id="indirizzo" name="indirizzo" placeholder="Es. Via Roma, 10">
            <span id="erroreIndirizzo" class="messaggio-errore"></span>
        </div>

        <div class="form-group">
            <label for="citta">Città:</label>
            <input type="text" id="citta" name="citta" placeholder="Es. Milano">
            <span id="erroreCitta" class="messaggio-errore"></span>
        </div>

        <div class="form-group">
            <label for="cap">CAP:</label>
            <input type="text" id="cap" name="cap" placeholder="Es. 20100">
            <span id="erroreCap" class="messaggio-errore"></span>
        </div>

        <h3>Metodo di Pagamento</h3>
        <div class="form-group">
            <label for="carta">Numero Carta di Credito:</label>
            <input type="text" id="carta" name="carta" placeholder="16 cifre senza spazi">
            <span id="erroreCarta" class="messaggio-errore"></span>
        </div>

        <button type="submit" class="btn-registrazione">Conferma e Paga</button>
    </form>
</div>

<script src="script/validazioneCheckout.js"></script>

<jsp:include page="footer.jsp" />