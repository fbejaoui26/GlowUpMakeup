<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.model.*, java.util.*" %>

<% 
    Collection<Prodotto> prodotti = (Collection<Prodotto>) request.getAttribute("prodotti");
%>

<jsp:include page="header.jsp" />

    <hr>
    
    <h2>Prodotti in Vetrina</h2>

    <div class="prodotti-grid">
        <% 
     
        if (prodotti != null && !prodotti.isEmpty()) {
            for(Prodotto p : prodotti) { 
        %>
            <div class="prodotto-card">
              <a href="DettaglioServlet?id=<%= p.getId() %>";>
                <img src="images/<%= p.getImmagine() %>" alt="<%= p.getNome() %>" class="prodotto-img">
                
                <p class="marchio-prodotto"><%= p.getMarchio() != null ? p.getMarchio() : "" %></p>
                
                <h3><%= p.getNome() %></h3>
                
                <p class="dettagli-prodotto">
                   <%= (p.getColore() != null && !p.getColore().isEmpty()) ? p.getColore() : "" %> 
                   <%= (p.getFormato() != null && !p.getFormato().isEmpty()) ? " | " + p.getFormato() : "" %></p>
                
                
                <p class="prezzo"><%= String.format("%.2f", p.getPrezzo()) %> &euro;</p>
                
                <button class="btn-carrello">Aggiungi al Carrello</button>
            </div>
        <% 
            } 
        } else { 
        %>
            <p style="text-align:center;">Nessun prodotto disponibile al momento.</p>
        <% } %>
    </div>

<jsp:include page="footer.jsp" />