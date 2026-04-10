<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pannello Amministratore - GlowUp</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>

<header class="header-admin">
    <div class="logo">⚙️ GlowUp Admin Panel</div>
    <nav>
       <div class="user-menu">
           <span>Benvenuta, ${utenteLoggato.nome}! (Admin)</span>
           
           <a href="${pageContext.request.contextPath}/HomeServlet">Vai al Sito</a>
           
           <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn-logout-admin">Logout</a>
       </div>
    </nav>
</header>

<div class="admin-container">
    <h1 class="titolo-admin">Dashboard di Gestione</h1>
    
    <div class="grid-admin">
        <div class="card-strumento">
            <h3>💄 Gestione Prodotti</h3>
            <p>Inserisci nuovi prodotti, modifica prezzi o rimuovi articoli dal catalogo.</p>
            <a href="${pageContext.request.contextPath}/AdminProdottiServlet" class="btn-admin">Gestisci Catalogo</a>
        </div>

        <div class="card-strumento">
            <h3>📦 Report Ordini</h3>
            <p>Visualizza gli ordini ricevuti, filtra per data e per clienti.</p>
            <a href="${pageContext.request.contextPath}/AdminOrdiniServlet" class="btn-admin">Visualizza Ordini</a>
        </div>
        
    </div>
</div>

</body>
</html>