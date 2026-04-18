package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.ProdottoDAO;
import it.unisa.model.Utente;

@WebServlet("/ToggleProdottoServlet")
public class ToggleProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Utente admin = (Utente) session.getAttribute("utenteLoggato");
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/HomeServlet");
            return;
            
        } try {
            
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            boolean statoAttuale = Boolean.parseBoolean(request.getParameter("statoAttuale"));
            
            // inverto lo stato: se era false diventa true, e viceversa)
            boolean nuovoStato = !statoAttuale;
            
            // aggiorno il DB
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doUpdateCancellato(idProdotto, nuovoStato);
            
            response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet");
            
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("erroreAdmin", "Si è verificato un errore durante la modifica dello stato del prodotto.");
            request.getRequestDispatcher("/AdminProdottiServlet").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet");
    }
}