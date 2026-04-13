<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="admin-theme">
    <div class="admin-page-wrapper">
        
        <div class="admin-header-box">
            <h1 class="titolo-admin"><i class="fa-solid fa-gauge-high"></i> Dashboard di Gestione</h1>
            <p>Benvenuta nell'area riservata, ${utenteLoggato.nome}. Usa gli strumenti qui sotto per gestire lo store.</p>
        </div>
        
        <div class="grid-admin">
            <div class="card-strumento">
                <div class="card-admin-icona">
                    <i class="fa-solid fa-tags"></i>
                </div>
                <h3>Gestione Prodotti</h3>
                <p>Inserisci nuovi prodotti, modifica i prezzi, le scorte o rimuovi articoli dal catalogo in tempo reale.</p>
                <a href="${pageContext.request.contextPath}/AdminProdottiServlet" class="btn-primary btn-full">Gestisci Catalogo</a>
            </div>

            <div class="card-strumento">
                <div class="card-admin-icona">
                    <i class="fa-solid fa-boxes-packing"></i>
                </div>
                <h3>Report Ordini</h3>
                <p>Visualizza gli ordini ricevuti dai clienti, controlla i pagamenti e aggiorna lo stato delle spedizioni.</p>
                <a href="${pageContext.request.contextPath}/AdminOrdiniServlet" class="btn-primary btn-full">Visualizza Ordini</a>
            </div>
        </div>

    </div>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />