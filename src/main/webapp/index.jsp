<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.model.*, java.util.*" %>

<jsp:include page="header.jsp" />

    <h1>Benvenuto/a su GlowUp Makeup 💄</h1>
    <p>Scopri subito i nostri prodotti migliori!</p>

    <hr>
    
    <h2>Prodotti in Vetrina</h2>
    
    <%
        // codice temporaneo per testare le prime funzionalità
        ProdottoDAO dao = new ProdottoDAO();
        Collection<Prodotto> prodotti = dao.doRetrieveAll(null);
    %>

    <div style="display: flex; gap: 20px; flex-wrap: wrap;">
        <% for(Prodotto p : prodotti) { %>
            <div style="border: 1px solid #ccc; background: white; padding: 15px; border-radius: 10px; width: 200px; box-shadow: 2px 2px 10px rgba(0,0,0,0.1);">
                <img src="images/<%= p.getImmagine() %>" alt="<%= p.getNome() %>" style="width: 100%; height: 150px; object-fit: cover; border-radius: 5px;">
                
                <h3><%= p.getNome() %></h3>
                <p style="color: #d63384; font-weight: bold;"><%= p.getPrezzo() %> &euro;</p>
                <button style="background-color: #d63384; color: white; border: none; padding: 10px; width: 100%; cursor: pointer;">Aggiungi al Carrello</button>
            </div>
        <% } %>
    </div>

<jsp:include page="footer.jsp" />