<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="conferma-container">
    <div class="icona-successo">🎉</div>
    <h2 class="titolo-conferma">Ordine confermato!</h2>
    <p class="testo-conferma">
        Grazie per il tuo acquisto.<br>
        Il tuo ordine è stato registrato con successo nel nostro sistema e verrà elaborato al più presto.
    </p>

    <a href="${pageContext.request.contextPath}/HomeServlet" class="btn-ritorno">Torna alla Home</a>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />