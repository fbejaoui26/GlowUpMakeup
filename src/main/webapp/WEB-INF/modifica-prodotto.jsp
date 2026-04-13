<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="admin-theme">
    
    <div class="admin-page-wrapper" style="max-width: 800px;">
        
        <div class="admin-header-flex">
            <h1 class="titolo-admin"><i class="fa-solid fa-pen-to-square"></i> Modifica Prodotto</h1>
            <a href="${pageContext.request.contextPath}/AdminProdottiServlet" class="btn-outline">
                <i class="fa-solid fa-arrow-left"></i> Torna al Catalogo
            </a>
        </div>

        <c:if test="${not empty erroreModifica}">
            <div class="messaggio-errore-box">${erroreModifica}</div>
        </c:if>
        
        <div class="admin-form-card">
            <form action="${pageContext.request.contextPath}/ModificaProdottoServlet" method="post" enctype="multipart/form-data" class="admin-styled-form">
                
                <input type="hidden" name="id" value="${prodottoDaModificare.id}">

                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="nome">Nome Prodotto *</label>
                        <input type="text" id="nome" name="nome" value="${prodottoDaModificare.nome}" required>
                    </div>
                    <div class="form-group half-width">
                        <label for="marchio">Marchio *</label>
                        <input type="text" id="marchio" name="marchio" value="${prodottoDaModificare.marchio}" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="prezzo">Prezzo (&euro;) *</label>
                        <input type="number" id="prezzo" name="prezzo" step="0.01" min="0" value="${prodottoDaModificare.prezzo}" required>
                    </div>
                    <div class="form-group half-width">
                        <label for="quantita">Quantità in Magazzino *</label>
                        <input type="number" id="quantita" name="quantita" min="0" value="${prodottoDaModificare.quantita}" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="colore">Colore / Shade</label>
                        <input type="text" id="colore" name="colore" value="${prodottoDaModificare.colore}">
                    </div>
                    <div class="form-group half-width">
                        <label for="formato">Formato</label>
                        <input type="text" id="formato" name="formato" value="${prodottoDaModificare.formato}">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="categoria">ID Categoria *</label>
                        <input type="number" id="categoria" name="categoria" value="${prodottoDaModificare.categoriaId}" required>
                    </div>
                    <div class="form-group half-width">
                        <label for="immagine">Nuova Immagine (Opzionale)</label>
                        <input type="file" id="immagine" name="immagine" accept="image/*" class="file-input-admin">
                        <small class="admin-hint">Attuale: <strong>${prodottoDaModificare.immagine}</strong> (Lascia vuoto per non modificare)</small>
                    </div>
                </div>

                <div class="form-group">
                    <label for="descrizione">Descrizione *</label>
                    <textarea id="descrizione" name="descrizione" rows="5" required>${prodottoDaModificare.descrizione}</textarea>
                </div>

                <div class="form-azioni-admin">
                    <a href="${pageContext.request.contextPath}/AdminProdottiServlet" class="btn-outline btn-annulla-admin">Annulla</a>
                    
                    <button type="submit" class="btn-primary btn-salva-admin">
                        <i class="fa-solid fa-floppy-disk"></i> Salva Modifiche
                    </button>
                </div>
                
            </form>
        </div>
        
    </div>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />