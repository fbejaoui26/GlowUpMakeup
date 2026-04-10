<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Aggiungi Prodotto - GlowUp Admin</title>
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
    <h1 class="titolo-admin">Aggiungi Nuovo Prodotto</h1>

    <c:if test="${not empty erroreInserimento}">
        <p class="messaggio-errore errore-globale">${erroreInserimento}</p>
    </c:if>
    
    <div class="form-container-admin">
    
        <form action="${pageContext.request.contextPath}/AggiungiProdottoServlet" method="post" enctype="multipart/form-data" class="form-admin">
            
            <div class="form-riga">
                <div class="form-gruppo">
                    <label for="nome">Nome Prodotto *</label>
                    <input type="text" id="nome" name="nome" required placeholder="Es. Rossetto Matte Revolution">
                </div>
                <div class="form-gruppo">
                    <label for="marchio">Marchio *</label>
                    <input type="text" id="marchio" name="marchio" required placeholder="Es. Charlotte Tilbury">
                </div>
            </div>

            <div class="form-riga">
                <div class="form-gruppo">
                    <label for="prezzo">Prezzo (&euro;) *</label>
                    <input type="number" id="prezzo" name="prezzo" step="0.01" min="0" required placeholder="Es. 29.90">
                </div>
                <div class="form-gruppo">
                    <label for="quantita">Quantità in Magazzino *</label>
                    <input type="number" id="quantita" name="quantita" min="0" required placeholder="Es. 50">
                </div>
            </div>

            <div class="form-riga">
                <div class="form-gruppo">
                    <label for="colore">Colore/Shade</label>
                    <input type="text" id="colore" name="colore" placeholder="Es. Pillow Talk">
                </div>
                <div class="form-gruppo">
                    <label for="formato">Formato</label>
                    <input type="text" id="formato" name="formato" placeholder="Es. 3.5g">
                </div>
            </div>

            <div class="form-riga">
                <div class="form-gruppo">
                    <label for="categoria">ID Categoria *</label>
                    <input type="number" id="categoria" name="categoria" required placeholder="Es. 1">
                </div>
                <div class="form-gruppo">
                    <label for="immagine">Immagine (Upload) *</label>
                    <input type="file" id="immagine" name="immagine" accept="image/*" required>
                </div>
            </div>

            <div class="form-gruppo">
                <label for="descrizione">Descrizione *</label>
                <textarea id="descrizione" name="descrizione" rows="4" required placeholder="Inserisci una descrizione del prodotto"></textarea>
            </div>

            <div class="form-azioni">
                <a href="${pageContext.request.contextPath}/AdminProdottiServlet" class="btn-annulla">Annulla</a>
                <button type="submit" class="btn-salva">Salva Prodotto</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>