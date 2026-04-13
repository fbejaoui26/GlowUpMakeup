<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="conferma-wrapper">
    <div class="conferma-card">
        
        <div class="icona-successo">
            <i class="fa-regular fa-circle-check"></i>
        </div>
        
        <h2 class="titolo-conferma">Ordine Confermato!</h2>
        
        <p class="testo-conferma">
            Grazie per il tuo acquisto su GlowUp.<br>
            
            Il tuo ordine è stato registrato con successo nel nostro sistema e lo stiamo elaborando.
        </p>

        <a href="${pageContext.request.contextPath}/HomeServlet" class="btn-primary">Torna allo Shopping</a>
        
    </div>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />