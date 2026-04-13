<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="auth-wrapper">
    <div class="auth-image-side">
    </div>

    <div class="auth-form-side">
        <h2>Bentornato!</h2>
        <p class="auth-subtitle">Accedi al tuo account per continuare lo shopping.</p>
     
        <c:if test="${not empty erroreLogin}">
            <div class="messaggio-errore-box">${erroreLogin}</div>
        </c:if>

        <form id="formLogin" action="${pageContext.request.contextPath}/LoginServlet" method="post" class="styled-form">
            
            <div class="form-group">
                <label for="emailLogin">Email</label>
                <input type="email" id="emailLogin" name="email" placeholder="La tua email">
                <span id="erroreEmailLogin" class="errore-testo"></span>
            </div>

            <div class="form-group">
                <label for="passwordLogin">Password</label>
                <input type="password" id="passwordLogin" name="password" placeholder="La tua password">
                <span id="errorePasswordLogin" class="errore-testo"></span>
            </div>

            <button type="submit" class="btn-primary btn-full">Accedi</button>
            
            <p class="auth-switch">
                Non hai ancora un account? <a href="${pageContext.request.contextPath}/RegistrazioneServlet">Registrati ora</a>
            </p>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/script/validazioneLogin.js"></script>

<jsp:include page="/WEB-INF/footer.jsp" />