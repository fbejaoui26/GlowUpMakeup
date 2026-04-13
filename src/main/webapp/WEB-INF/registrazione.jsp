<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="auth-wrapper">
    <div class="auth-image-side">
    </div>

    <div class="auth-form-side">
        <h2>Crea il tuo Account</h2>
        <p class="auth-subtitle">Unisciti a GlowUp per scoprire la tua nuova routine di bellezza.</p>
        
        <c:if test="${not empty erroreRegistrazione}">
            <div class="messaggio-errore-box">${erroreRegistrazione}</div>
        </c:if>

        <form id="formRegistrazione" action="${pageContext.request.contextPath}/RegistrazioneServlet" method="post" class="styled-form">
            
            <div class="form-row">
                <div class="form-group half-width">
                    <label for="nome">Nome</label>
                    <input type="text" id="nome" name="nome" placeholder="Il tuo nome">
                    <span id="erroreNome" class="errore-testo"></span>
                </div>

                <div class="form-group half-width">
                    <label for="cognome">Cognome</label>
                    <input type="text" id="cognome" name="cognome" placeholder="Il tuo cognome">
                    <span id="erroreCognome" class="errore-testo"></span>
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="esempio@email.com">
                <span id="erroreEmail" class="errore-testo"></span>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Minimo 8 caratteri">
                <span id="errorePassword" class="errore-testo"></span>
            </div>

            <button type="submit" id="btnSubmitRegistrazione" class="btn-primary btn-full">Registrati</button>
            
            <p class="auth-switch">
                Hai già un account? <a href="${pageContext.request.contextPath}/LoginServlet">Accedi qui</a>
            </p>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/script/validazione.js"></script>

<jsp:include page="/WEB-INF/footer.jsp" />