<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.model.Carrello" %>
<%@ page import="it.unisa.model.ElementoCarrello" %>
<jsp:include page="header.jsp" />

<div class="cart-container">
    <h2>Il tuo Carrello</h2>
    
    <%
        // recupero il carrello dalla sessione
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        
        // se non c'è il carrello o se non ci sono elementi dentro
        if (carrello == null || carrello.getElementi().isEmpty()) {
    %>
        <div class="cart-vuoto">
            <p>Il tuo carrello è attualmente vuoto.</p>
            <a href="HomeServlet" class="btn-continua">Continua lo shopping</a>
        </div>
    <%
        } else {
    %>
        <table class="cart-table">
            <thead>
                <tr>
                    <th>Prodotto</th>
                    <th>Prezzo Singolo</th>
                    <th>Quantità</th>
                    <th>Totale Riga</th>
                    <th>Azione</th>
                </tr>
            </thead>
            <tbody>
                <% for (ElementoCarrello item : carrello.getElementi()) { %>
                    <tr>
                        <td>
                            <img src="images/<%= item.getProdotto().getImmagine() %>" alt="<%= item.getProdotto().getNome() %>" class="cart-img">
                            <%= item.getProdotto().getNome() %>
                        </td>
                        <td><%= String.format("%.2f", item.getProdotto().getPrezzo()) %> &euro;</td>
                        <td>
                            <form action="CarrelloServlet" method="post" class="form-quantita">
                                <input type="hidden" name="action" value="aggiorna">
                                <input type="hidden" name="id" value="<%= item.getProdotto().getId() %>">
                                <input type="number" name="quantita" value="<%= item.getQuantita() %>" min="1" class="input-quantita auto-submit">
                            </form>
                        </td>
                        <td><%= String.format("%.2f", item.getPrezzoTotale()) %> &euro;</td>
                        <td>
                            <a href="CarrelloServlet?action=rimuovi&id=<%= item.getProdotto().getId() %>" class="btn-rimuovi">Rimuovi</a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        
        <div class="cart-summary">
            <h3>Totale da pagare: <%= String.format("%.2f", carrello.getPrezzoTotaleCarrello()) %> &euro;</h3>
            <a href="checkout.jsp" class="btn-checkout">Procedi all'Acquisto</a>
        </div>
    <%
        }
    %>
</div>

<script src="script/carrello.js"></script>

<jsp:include page="footer.jsp" />

<jsp:include page="footer.jsp" />