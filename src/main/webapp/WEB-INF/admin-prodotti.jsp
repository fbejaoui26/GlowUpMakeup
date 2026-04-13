<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="admin-theme">
    
    <div class="admin-page-wrapper" style="max-width: 1200px;">
        
        <div class="admin-header-flex">
            <h1 class="titolo-admin"><i class="fa-solid fa-tags"></i> Gestione Catalogo</h1>
            <a href="${pageContext.request.contextPath}/NuovoProdottoServlet" class="btn-primary">
                <i class="fa-solid fa-plus"></i> Nuovo Prodotto
            </a>
        </div>
        
        <c:if test="${not empty erroreAdmin}">
            <div class="messaggio-errore-box">${erroreAdmin}</div>
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
                                <td colspan="7" class="nessun-dato">
                                    <i class="fa-solid fa-box-open fa-2x"></i><br>
                                    Nessun prodotto presente nel catalogo.
                                </td>
                            </tr>
                        </c:when>
                        
                        <c:otherwise>
                            <c:forEach var="p" items="${prodotti}">
                                <tr class="${p.cancellato ? 'riga-disabilitata' : ''}">
                                    <td class="td-id">#${p.id}</td>
                                    <td>
                                        <div class="img-admin-thumb-box">
                                            <img src="${pageContext.request.contextPath}/images/${p.immagine}" alt="${p.nome}">
                                        </div>
                                    </td>
                                    <td class="testo-evidenza">${p.marchio}</td>
                                    <td class="td-nome">${p.nome}</td>
                                    <td class="td-prezzo">
                                        <fmt:formatNumber value="${p.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;
                                    </td>
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
                                    <td>
                                        <div class="colonna-azioni">
                                            <a href="${pageContext.request.contextPath}/ModificaProdottoServlet?id=${p.id}" class="btn-icona btn-modifica" title="Modifica Prodotto">
                                                <i class="fa-solid fa-pen-to-square"></i>
                                            </a>
                                            
                                            <form action="${pageContext.request.contextPath}/ToggleProdottoServlet" method="post" class="form-inline">
                                                <input type="hidden" name="idProdotto" value="${p.id}">
                                                <input type="hidden" name="statoAttuale" value="${p.cancellato}">
                                                
                                                <c:choose>
                                                    <c:when test="${p.cancellato}">
                                                        <button type="submit" class="btn-icona btn-ripristina" title="Ripristina nel catalogo">
                                                            <i class="fa-solid fa-eye"></i>
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="submit" class="btn-icona btn-nascondi" title="Nascondi dal catalogo">
                                                            <i class="fa-solid fa-eye-slash"></i>
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        
    </div>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />