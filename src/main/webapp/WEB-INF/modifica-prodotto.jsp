<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica Prodotto - GlowUp Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>

<header class="header-admin">
    <div class="logo">⚙️ GlowUp Admin Panel</div>
    <nav>
       <div class="user-menu">
           <a href="${pageContext.request.contextPath}/AdminProdottiServlet">Torna al Catalogo</a>
           <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn-logout-admin">Logout</a>
       </div>
    </nav>
</header>

<div class="admin-container">
    <h1 class="titolo-admin">Modifica: ${prodottoDaModificare.nome}</h1>

    <c:if test="${not empty erroreModifica}">
        <p class="messaggio-errore errore-globale">${erroreModifica}</p>
    </c:if>
    
    <div class="form-container-admin">
        <form action="${pageContext.request.contextPath}/ModificaProdottoServlet" method="post" enctype="multipart/form-data" class="form-admin">
            
            <input type="hidden" name="id" value="${prodottoDaModificare.id}">

            <div class="form-riga">
                <div class="form-gruppo">
                    <label for="nome">Nome Prodotto *</label>
                    <input type="text" id="nome" name="nome" value="${prodottoDaModificare.nome}" required>
                </div>
                <div class="form-gruppo">
                    <label for="marchio">Marchio *</label>
                    <input type="text" id="marchio" name="marchio" value="${prodottoDaModificare.marchio}" required>
                </div>
            </div>

            <div class="form-riga">
                <div class="form-gruppo">
                    <label for="prezzo">Prezzo (&euro;) *</label>
                    <input type="number" id="prezzo" name="prezzo" step="0.01" min="0" value="${prodottoDaModificare.prezzo}" required>
                </div>
                <div class="form-gruppo">
                    <label for="quantita">Quantità in Magazzino *</label>
                    <input type="number" id="quantita" name="quantita" min="0" value="${prodottoDaModificare.quantita}" required>
                </div>
            </div>

            <div class="form-riga">
                <div class="form-gruppo">
                    <label for="colore">Colore/Shade</label>
                    <input type="text" id="colore" name="colore" value="${prodottoDaModificare.colore}">
                </div>
                <div class="form-gruppo">
                    <label for="formato">Formato</label>
                    <input type="text" id="formato" name="formato" value="${prodottoDaModificare.formato}">
                </div>
            </div>

            <div class="form-riga">
                <div class="form-gruppo">
                    <label for="categoria">ID Categoria *</label>
                    <input type="number" id="categoria" name="categoria" value="${prodottoDaModificare.categoriaId}" required>
                </div>
                <div class="form-gruppo">
                    <label for="immagine">Nuova Immagine (Opzionale)</label>
                    <input type="file" id="immagine" name="immagine" accept="image/*">
                    <small style="color: #7f8c8d; margin-top: 5px;">Lascia vuoto per mantenere: <strong>${prodottoDaModificare.immagine}</strong></small>
                </div>
            </div>

            <div class="form-gruppo">
                <label for="descrizione">Descrizione *</label>
                <textarea id="descrizione" name="descrizione" rows="4" required>${prodottoDaModificare.descrizione}</textarea>
            </div>

            <div class="form-azioni">
                <a href="${pageContext.request.contextPath}/AdminProdottiServlet" class="btn-annulla">Annulla</a>
                <button type="submit" class="btn-salva">Salva Modifiche</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>