package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.Carrello;
import it.unisa.model.OrdineDAO;
import it.unisa.model.Utente;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // controllo accessi (solo utenti loggati)
        Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
        if (utenteLoggato == null) {
            request.setAttribute("erroreLogin", "Devi effettuare l'accesso per poter completare l'acquisto.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return; 
        }

        // recupero il carrello
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null || carrello.getElementi().isEmpty()) {
            response.sendRedirect("carrello.jsp");
            return;
        }

        // recupero i dati dal form checkout.jsp
        // la servlet legge i "name" degli input che l'utente ha compilato
        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String cap = request.getParameter("cap");
        String carta = request.getParameter("carta");

        // formattiamo i dati per il database
        String indirizzoSpedizione = indirizzo + ", " + cap + " " + citta;
        
        // salvo solo le ultime 4 cifre della carta
        String metodoPagamento = "Carta di credito";
        if (carta != null && carta.length() >= 4) {
            metodoPagamento = "Carta terminante in " + carta.substring(carta.length() - 4);
        }

        // salvataggio nel database
        OrdineDAO ordineDAO = new OrdineDAO();
        try {
  
            ordineDAO.doSave(carrello, utenteLoggato, indirizzoSpedizione, metodoPagamento);
            
            // svuoto il carrello
            carrello.svuota();
            session.setAttribute("carrello", carrello);
            
            response.sendRedirect("conferma.jsp");
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Si è verificato un errore durante il salvataggio dell'ordine. Riprova più tardi.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // se qualcuno prova ad accedere direttamente all'URL di questa servlet senza passare dal form, lo indirizzo in modo sicuro al carrello
        response.sendRedirect("carrello.jsp");
    }
}