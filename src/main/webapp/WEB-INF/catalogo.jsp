<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="page-header">
    <h1>Il nostro Catalogo</h1>
    <p>Trova i prodotti perfetti per la tua routine di bellezza.</p>
</div>

<div class="catalogo-container">
    
    <aside class="sidebar-categorie">
        <h3 class="sidebar-title">Filtra per:</h3>
        
        <ul class="lista-categorie">
            <li class="cat-item">
                <a href="${pageContext.request.contextPath}/CatalogoServlet" 
                   class="cat-link ${empty categoriaSelezionata ? 'active' : ''}">
                   Tutti i Prodotti
                </a>
            </li>
            
            <c:forEach var="cat" items="${categorie}">
                <li class="cat-item">
                    <a href="${pageContext.request.contextPath}/CatalogoServlet?categoria=${cat.id}" 
                       class="cat-link ${categoriaSelezionata == cat.id ? 'active' : ''}">
                       ${cat.nome}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </aside>

    <main class="prodotti-main">
        
        <c:if test="${not empty erroreCatalogo}">
            <p class="messaggio-errore">${erroreCatalogo}</p>
        </c:if>

        <div class="prodotti-grid">
            <c:choose>
                <c:when test="${not empty prodotti}">
                    <c:forEach items="${prodotti}" var="p">
                        <div class="prodotto-card">
                            <a href="${pageContext.request.contextPath}/DettaglioServlet?id=${p.id}">
                                <img src="${pageContext.request.contextPath}/images/${p.immagine}" alt="${p.nome}" class="prodotto-img">
                                <p class="marchio-prodotto">${not empty p.marchio ? p.marchio : ''}</p>
                                <h3>${p.nome}</h3>
                                <p class="dettagli-prodotto">
                                    ${not empty p.colore ? p.colore : ''} 
                                    ${not empty p.formato ? ' | '.concat(p.formato) : ''}
                                </p>
                                <p class="prezzo">
                                    <fmt:formatNumber value="${p.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;
                                </p>
                            </a>
                            
                            <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post" class="form-ajax" style="margin-top: auto;">
                                <input type="hidden" name="action" value="aggiungi">
                                <input type="hidden" name="id" value="${p.id}">
                                <input type="hidden" name="quantita" value="1">
                                <input type="hidden" name="ajax" value="true">
                                <button type="submit" class="btn-carrello">
                                    Aggiungi al Carrello
                                </button>
                            </form>
                        </div>
                    </c:forEach>
                </c:when>
                
                <c:otherwise>
                    <div class="empty-state">
                        <p>Nessun prodotto disponibile in questa categoria al momento.</p>
                        <a href="${pageContext.request.contextPath}/CatalogoServlet" class="btn-primary">Torna al catalogo completo</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />