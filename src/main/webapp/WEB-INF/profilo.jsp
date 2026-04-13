<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="profilo-page">
    <div class="profilo-header-box">
        <div class="avatar-box">
            <i class="fa-regular fa-user"></i>
        </div>
        <h1 class="titolo-profilo">Ciao, ${utenteLoggato.nome}!</h1>
        <p class="sottotitolo-profilo">Qui puoi visualizzare lo storico dei tuoi acquisti.</p>
    </div>

    <c:if test="${not empty erroreProfilo}">
        <div class="messaggio-errore-box">${erroreProfilo}</div>
    </c:if>

    <div class="ordini-container">
        <h2 class="sezione-ordini-titolo"><i class="fa-solid fa-box-open"></i> I Miei Ordini</h2>

        <c:choose>
            <c:when test="${empty ordini}">
                <div class="nessun-ordine-box">
                    <i class="fa-solid fa-receipt fa-3x icon-vuota"></i>
                    <p>Non hai ancora effettuato nessun ordine.</p>
                    <a href="${pageContext.request.contextPath}/CatalogoServlet" class="btn-primary">Inizia lo shopping</a>
                </div>
            </c:when>
            
            <c:otherwise>
                <div class="lista-ordini">
                    <c:forEach var="ordine" items="${ordini}">
                        <div class="card-ordine">
                            
                            <div class="ordine-header">
                                <div class="ordine-info-base">
                                    <span class="ordine-id">Ordine #${ordine.id}</span>
                                    <span class="ordine-data">
                                        <i class="fa-regular fa-calendar"></i> <fmt:formatDate value="${ordine.dataOrdine}" pattern="dd/MM/yyyy HH:mm" />
                                    </span>
                                </div>
                                <div class="ordine-stato-box">
                                    <span class="badge-stato stato-${ordine.stato.toLowerCase()}">${ordine.stato}</span>
                                </div>
                            </div>

                            <div class="ordine-dettagli-grid">
                                <div class="dettaglio-col">
                                    <span class="dettaglio-label">Spedito a:</span>
                                    <span class="dettaglio-valore">${ordine.indirizzoSpedizione}</span>
                                </div>
                                <div class="dettaglio-col">
                                    <span class="dettaglio-label">Pagamento:</span>
                                    <span class="dettaglio-valore">${ordine.metodoPagamento}</span>
                                </div>
                            </div>

                            <div class="ordine-prodotti-list">
                                <c:forEach var="elemento" items="${ordine.elementi}">
                                    <div class="prodotto-storico-row">
                                        <div class="img-storico-box">
                                            <img src="${pageContext.request.contextPath}/images/${elemento.prodotto.immagine}" alt="${elemento.prodotto.nome}">
                                        </div>
                                        
                                        <div class="info-storico-col">
                                            <span class="marchio-storico">${elemento.prodotto.marchio}</span>
                                            <a href="${pageContext.request.contextPath}/DettaglioServlet?id=${elemento.prodotto.id}" class="nome-storico">${elemento.prodotto.nome}</a>
                                        </div>
                                        
                                        <div class="quantita-storico-col">
                                            <span class="qta-label">Qtà:</span> ${elemento.quantita}
                                        </div>
                                        
                                        <div class="prezzo-storico-col">
                                            <fmt:formatNumber value="${elemento.prezzoAcquisto}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <div class="ordine-footer">
                                <span class="totale-label">Totale Pagato:</span>
                                <span class="totale-valore">
                                    <fmt:formatNumber value="${ordine.totale}" type="number" minFractionDigits="2" maxFractionDigits="2"/> &euro;
                                </span>
                            </div>
                            
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />