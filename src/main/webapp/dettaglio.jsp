<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.model.*" %>

<% 
    Prodotto p = (Prodotto) request.getAttribute("prodottoSingolo"); 
    if(p == null) { response.sendRedirect("HomeServlet"); return; }
%>

<jsp:include page="header.jsp" />

<div class="container">
    <div class="dettaglio-container">
        
        <div class="dettaglio-img-box">
            <img src="images/<%= p.getImmagine() %>" alt="<%= p.getNome() %>" class="img-grande">
        </div>

        <div class="dettaglio-info-box">
            <p class="marchio-prodotto"><%= p.getMarchio() != null ? p.getMarchio() : "" %></p>
            <h1><%= p.getNome() %></h1>
            <p class="dettagli-extra">
                <%= (p.getColore() != null) ? p.getColore() : "" %> | <%= (p.getFormato() != null) ? p.getFormato() : "" %>
            </p>
            
            <p class="prezzo-grande"><%= String.format("%.2f", p.getPrezzo()) %> &euro;</p>
            
            <hr>
            
            <h3>Descrizione:</h3>
            <p class="descrizione-testo"><%= p.getDescrizione() != null ? p.getDescrizione() : "Nessuna descrizione disponibile." %></p>
            
            <form action="CarrelloServlet" method="post" class="form-acquisto">
             <input type="hidden" name="action" value="aggiungi">
             <input type="hidden" name="id" value="<%= p.getId() %>">
    
             <label for="quantita">Quantità:</label>
             <input type="number" name="quantita" id="quantita" value="1" min="1" max="<%= p.getQuantita() %>" class="input-quantita">
    
             <button type="submit" class="btn-carrello btn-grande">Aggiungi al Carrello</button>
            </form>
        </div>

    </div>
</div>

<jsp:include page="footer.jsp" />