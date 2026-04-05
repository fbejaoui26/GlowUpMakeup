<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.model.Utente" %>

<%
    Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GlowUp Makeup</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>

<header>
    <div class="logo">✨ GlowUp Makeup</div>
    <nav>
       <div class="user-menu">
       <a href="HomeServlet">Home</a>
       <a href="catalogo">Catalogo</a> 
    <% if (utenteLoggato != null) { %>
        <span>Ciao, <%= utenteLoggato.getNome() %>!</span>
        <a href="carrello.jsp">Carrello 🛒</a>
        <a href="LogoutServlet">Logout</a>
    <% } else { %>
        <a href="carrello.jsp">Carrello 🛒</a>
        <a href="LoginServlet">Login</a>
        <a href="RegistrazioneServlet">Registrati</a>
    <% } %>
     </div>
    </nav>
</header>

<div class="container"> 