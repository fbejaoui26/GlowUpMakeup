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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // controllo 
        Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
        if (utenteLoggato == null) {
            request.setAttribute("erroreLogin", "Devi effettuare l'accesso per poter completare l'acquisto.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null || carrello.getElementi().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
        if (utenteLoggato == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return; 
        }

        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null || carrello.getElementi().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
            return;
        }

        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String cap = request.getParameter("cap");
        String carta = request.getParameter("carta");

        String indirizzoSpedizione = indirizzo + ", " + cap + " " + citta;
        
        String metodoPagamento = "Carta di credito";
        if (carta != null && carta.length() >= 4) {
            metodoPagamento = "Carta terminante in " + carta.substring(carta.length() - 4);
        }

        OrdineDAO ordineDAO = new OrdineDAO();
        try {
            ordineDAO.doSave(carrello, utenteLoggato, indirizzoSpedizione, metodoPagamento);
            
            carrello.svuota();
            session.setAttribute("carrello", carrello);
            
            response.sendRedirect(request.getContextPath() + "/ConfermaServlet");
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erroreCheckout", "Si è verificato un errore durante il salvataggio dell'ordine. Riprova più tardi.");
            request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request, response);
        }
    }
}