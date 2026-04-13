<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/header.jsp" />

<div class="admin-theme">
    
    <div class="admin-page-wrapper" style="max-width: 800px;">
        
        <div class="admin-header-flex">
            <h1 class="titolo-admin"><i class="fa-solid fa-square-plus"></i> Aggiungi Prodotto</h1>
            <a href="${pageContext.request.contextPath}/AdminProdottiServlet" class="btn-outline">
                <i class="fa-solid fa-arrow-left"></i> Torna al Catalogo
            </a>
        </div>

        <c:if test="${not empty erroreInserimento}">
            <div class="messaggio-errore-box">${erroreInserimento}</div>
        </c:if>
        
        <div class="admin-form-card">
            <form action="${pageContext.request.contextPath}/AggiungiProdottoServlet" method="post" enctype="multipart/form-data" class="admin-styled-form">
                
                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="nome">Nome Prodotto *</label>
                        <input type="text" id="nome" name="nome" required placeholder="Es. Rossetto Matte Revolution">
                    </div>
                    <div class="form-group half-width">
                        <label for="marchio">Marchio *</label>
                        <input type="text" id="marchio" name="marchio" required placeholder="Es. Charlotte Tilbury">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="prezzo">Prezzo (&euro;) *</label>
                        <input type="number" id="prezzo" name="prezzo" step="0.01" min="0" required placeholder="Es. 29.90">
                    </div>
                    <div class="form-group half-width">
                        <label for="quantita">Quantità in Magazzino *</label>
                        <input type="number" id="quantita" name="quantita" min="0" required placeholder="Es. 50">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="colore">Colore / Shade</label>
                        <input type="text" id="colore" name="colore" placeholder="Es. Pillow Talk">
                    </div>
                    <div class="form-group half-width">
                        <label for="formato">Formato</label>
                        <input type="text" id="formato" name="formato" placeholder="Es. 3.5g / 30ml">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group half-width">
                        <label for="categoria">ID Categoria *</label>
                        <input type="number" id="categoria" name="categoria" required placeholder="Es. 1">
                    </div>
                    <div class="form-group half-width">
                        <label for="immagine">Immagine (Upload) *</label>
                        <input type="file" id="immagine" name="immagine" accept="image/*" required class="file-input-admin">
                    </div>
                </div>

                <div class="form-group">
                    <label for="descrizione">Descrizione *</label>
                    <textarea id="descrizione" name="descrizione" rows="5" required placeholder="Inserisci una descrizione dettagliata del prodotto..."></textarea>
                </div>

                <div class="form-azioni-admin">
                    <button type="submit" class="btn-primary btn-salva-admin">
                        <i class="fa-solid fa-floppy-disk"></i> Salva Prodotto
                    </button>
                </div>
                
            </form>
        </div>
        
    </div>
</div>

<jsp:include page="/WEB-INF/footer.jsp" />