<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione Catalogo - GlowUp Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>

<header class="header-admin">
    <div class="logo">⚙️ GlowUp Admin Panel</div>
    <nav>
       <div class="user-menu">
           <a href="${pageContext.request.contextPath}/AdminDashboard">Dashboard</a>
           <a href="${pageContext.request.contextPath}/HomeServlet">Vai al Sito</a>
           <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn-logout-admin">Logout</a>
       </div>
    </nav>
</header>

<div class="admin-container">
    <div class="admin-header-flex">
        <h1 class="titolo-admin">Gestione Prodotti</h1>
        <a href="${pageContext.request.contextPath}/NuovoProdottoServlet" class="btn-admin-azione btn-aggiungi">+ Aggiungi Prodotto</a>
    </div>
    
    <c:if test="${not empty erroreAdmin}">
        <p class="messaggio-errore errore-globale">${erroreAdmin}</p>
    </c:if>
    
    <div class="table-responsive">
        <table class="admin-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Immagine</th>
                    <th>Marchio</th>
                    <th>Nome</th>
                    <th>Prezzo</th>
                    <th>Stato</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty prodotti}">
                        <tr>
                            <td colspan="7" class="nessun-dato">Nessun prodotto presente nel database.</td>
                        </tr>
                    </c:when>
                    
                    <c:otherwise>
                        <c:forEach var="p" items="${prodotti}">
                            <%-- Operatore ternario EL per la classe CSS --%>
                            <tr class="${p.cancellato ? 'riga-disabilitata' : ''}">
                                <td>#${p.id}</td>
                                <td>
                                    <img src="${pageContext.request.contextPath}/images/${p.immagine}" alt="${p.nome}" class="img-admin-thumb">
                                </td>
                                <td class="testo-evidenza">${p.marchio}</td>
                                <td>${p.nome}</td>
                                <td><fmt:formatNumber value="${p.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.cancellato}">
                                            <span class="badge-stato-admin badge-nascosto">Nascosto</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge-stato-admin badge-visibile">Visibile</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="colonna-azioni">
                                    <a href="${pageContext.request.contextPath}/ModificaProdottoServlet?id=${p.id}" class="btn-icona btn-modifica" title="Modifica">✏️</a>
                                    
                                    <form action="${pageContext.request.contextPath}/ToggleProdottoServlet" method="post" class="form-inline">
                                        <input type="hidden" name="idProdotto" value="${p.id}">
                                        <input type="hidden" name="statoAttuale" value="${p.cancellato}">
                                        
                                        <c:choose>
                                            <c:when test="${p.cancellato}">
                                                <button type="submit" class="btn-icona btn-ripristina" title="Ripristina nel catalogo">👁️</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn-icona btn-nascondi" title="Nascondi dal catalogo">🗑️</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>