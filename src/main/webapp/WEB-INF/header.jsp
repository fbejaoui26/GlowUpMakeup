<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>GlowUp Makeup</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>

<header>
    <div class="logo">✨ GlowUp Makeup</div>
<nav>
       <div class="user-menu">
           <a href="${pageContext.request.contextPath}/HomeServlet">Home</a>
           <a href="${pageContext.request.contextPath}/CatalogoServlet">Catalogo</a>
           
           <%-- IL CARRELLO (nascosto per gli admin) --%>
           <c:if test="${empty utenteLoggato or not utenteLoggato.admin}">
               <a href="${pageContext.request.contextPath}/CarrelloServlet" class="cart-link">
                   Carrello 🛒
                   <c:choose>
                       <c:when test="${not empty carrello and carrello.numeroElementiTotali > 0}">
                           <span id="badge-carrello" class="badge-carrello">${carrello.numeroElementiTotali}</span>
                       </c:when>
                       <c:otherwise>
                           <span id="badge-carrello" class="badge-carrello" style="display: none;">0</span>
                       </c:otherwise>
                   </c:choose>
               </a>
           </c:if>

           <%-- GESTIONE LOGIN/PROFILO --%>
           <c:choose>
               <c:when test="${not empty utenteLoggato}">
                   <c:choose>
                       <c:when test="${utenteLoggato.admin}">
                           <a href="${pageContext.request.contextPath}/AdminDashboard" class="link-profilo" title="Vai alla Dashboard">⚙️ Admin: ${utenteLoggato.nome}</a>
                       </c:when>
                       <c:otherwise>
                           <a href="${pageContext.request.contextPath}/ProfiloServlet" class="link-profilo" title="Vai al tuo profilo">Ciao, ${utenteLoggato.nome}!</a>
                       </c:otherwise>
                   </c:choose>
                   <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
               </c:when>
               <c:otherwise>
                   <a href="${pageContext.request.contextPath}/LoginServlet">Login</a>
                   <a href="${pageContext.request.contextPath}/RegistrazioneServlet">Registrati</a>
               </c:otherwise>
           </c:choose>
       </div>
    </nav>
</header>

<div class="container">