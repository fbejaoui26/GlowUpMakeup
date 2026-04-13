<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>GlowUp Makeup</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>

<header class="site-header">
    <div class="header-container">
        
        <div class="logo">
            <a href="${pageContext.request.contextPath}/HomeServlet">GlowUp Makeup</a>
        </div>

        <nav class="main-nav">
            <a href="${pageContext.request.contextPath}/HomeServlet">Home</a>
            <a href="${pageContext.request.contextPath}/CatalogoServlet">Catalogo</a>
        </nav>

        <div class="user-actions">
            <%-- GESTIONE LOGIN/PROFILO --%>
            <c:choose>
                <c:when test="${not empty utenteLoggato}">
                    <c:choose>
                        <c:when test="${utenteLoggato.admin}">
                            <a href="${pageContext.request.contextPath}/AdminDashboard" title="Dashboard">
                                <i class="fa-solid fa-gear"></i> Admin
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/ProfiloServlet" title="Profilo">
                                <i class="fa-regular fa-user"></i> ${utenteLoggato.nome}
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/LogoutServlet" class="logout-link" title="Logout">
                        <i class="fa-solid fa-arrow-right-from-bracket"></i>
                    </a>
                </c:when>
               <c:otherwise>
                    <a href="${pageContext.request.contextPath}/LoginServlet" title="Accedi">
                        <i class="fa-regular fa-user"></i> Accedi
                    </a>
                    <a href="${pageContext.request.contextPath}/RegistrazioneServlet" title="Registrati">
                        <i class="fa-solid fa-user-plus"></i> Registrati
                    </a>
                </c:otherwise>
            </c:choose>

            <%-- IL CARRELLO (nascosto per gli admin) --%>
   <c:if test="${empty utenteLoggato or not utenteLoggato.admin}">
    <a href="${pageContext.request.contextPath}/CarrelloServlet" class="cart-link" title="Carrello">
        <i class="fa-solid fa-bag-shopping"></i>
        
        <%-- JSTL inietta la classe 'badge-nascosto' solo se necessario --%>
        <span id="badge-carrello" class="badge-carrello ${empty carrello or carrello.numeroElementiTotali == 0 ? 'badge-nascosto' : ''}">
            ${not empty carrello and carrello.numeroElementiTotali > 0 ? carrello.numeroElementiTotali : '0'}
        </span>
        
    </a>
</c:if>
        </div>

    </div>
</header>

<div class="container">