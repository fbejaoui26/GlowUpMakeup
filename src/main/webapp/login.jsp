<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />

<div class="form-container">
    <h2>Accedi a GlowUp Makeup!</h2>
    
    <% if (request.getAttribute("erroreLogin") != null) { %>
        <p class="messaggio-errore errore-globale"><%= request.getAttribute("erroreLogin") %></p>
    <% } %>

    <form id="formLogin" action="LoginServlet" method="post">
        
        <div class="form-group">
            <label for="emailLogin">Email:</label>
            <input type="email" id="emailLogin" name="email" placeholder="La tua email">
            <span id="erroreEmailLogin" class="messaggio-errore"></span>
        </div>

        <div class="form-group">
            <label for="passwordLogin">Password:</label>
            <input type="password" id="passwordLogin" name="password" placeholder="La tua password">
            <span id="errorePasswordLogin" class="messaggio-errore"></span>
        </div>

        <button type="submit" class="btn-registrazione">Accedi</button>
    </form>
</div>

<script src="script/validazioneLogin.js"></script>

<jsp:include page="footer.jsp" />