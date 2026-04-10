<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="form-container">
    <h2>Registrati a GlowUp Makeup!</h2>
    
    <c:if test="${not empty erroreRegistrazione}">
        <p class="messaggio-errore errore-globale">${erroreRegistrazione}</p>
    </c:if>

    <form id="formRegistrazione" action="${pageContext.request.contextPath}/RegistrazioneServlet" method="post">
        
        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" placeholder="Inserisci il tuo nome">
            <span id="erroreNome" class="messaggio-errore"></span>
        </div>

        <div class="form-group">
            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" placeholder="Inserisci il tuo cognome">
            <span id="erroreCognome" class="messaggio-errore"></span>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" placeholder="esempio@email.com">
            <span id="erroreEmail" class="messaggio-errore"></span>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Minimo 8 caratteri">
            <span id="errorePassword" class="messaggio-errore"></span>
        </div>

        <button type="submit" id="btnSubmitRegistrazione" class="btn-registrazione">Crea Account</button>
    </form>
</div>

<script src="${pageContext.request.contextPath}/script/validazione.js"></script>

<jsp:include page="/WEB-INF/footer.jsp" />