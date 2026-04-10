<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="profilo-container">
    <h1 class="titolo-profilo">Il mio Profilo</h1>
    <h2 class="sottotitolo-profilo">Storico Ordini</h2>

    <c:if test="${not empty erroreProfilo}">
        <p class="messaggio-errore">${erroreProfilo}</p>
    </c:if>

    <c:choose>
        <c:when test="${empty ordini}">
            <div class="nessun-ordine">
                <p>Non hai ancora effettuato nessun ordine.</p>
                <a href="${pageContext.request.contextPath}/HomeServlet" class="btn-carrello btn-grande">Inizia lo shopping</a>
            </div>
        </c:when>
        
        <c:otherwise>

            <c:forEach var="ordine" items="${ordini}">
                <div class="card-ordine">
                    <div class="ordine-header">
                        <div class="ordine-info-base">
                            <span class="ordine-id">Ordine #${ordine.id}</span>
                            <span class="ordine-data">
                                <fmt:formatDate value="${ordine.dataOrdine}" pattern="dd/MM/yyyy HH:mm" />
                            </span>
                        </div>
                        <div class="ordine-stato">
                            Stato: <span class="badge-stato">${ordine.stato}</span>
                        </div>
                    </div>

                    <div class="ordine-dettagli">
                        <p><strong>Spedizione a:</strong> ${ordine.indirizzoSpedizione}</p>
                        <p><strong>Pagamento:</strong> ${ordine.metodoPagamento}</p>
                    </div>

                    <div class="ordine-prodotti">
                        <%-- ciclo annidato sui prodotti (elementi) di quel singolo ordine --%>
                        <c:forEach var="elemento" items="${ordine.elementi}">
                            <div class="prodotto-storico">
                                <img src="${pageContext.request.contextPath}/images/${elemento.prodotto.immagine}" alt="${elemento.prodotto.nome}" class="img-storico">
                                
                                <div class="info-storico">
                                    <span class="marchio-storico">${elemento.prodotto.marchio}</span>
                                    <span class="nome-storico">${elemento.prodotto.nome}</span>
                                </div>
                                
                                <div class="quantita-storico">
                                    Quantità: ${elemento.quantita}
                                </div>
                                
                                <div class="prezzo-storico">
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
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />