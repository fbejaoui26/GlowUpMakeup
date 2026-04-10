package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.Prodotto;
import it.unisa.model.ProdottoDAO;
import it.unisa.model.Utente;

@WebServlet("/AdminProdottiServlet")
public class AdminProdottiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // CONTROLLO DI SICUREZZA 
        Utente admin = (Utente) session.getAttribute("utenteLoggato");
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/HomeServlet");
            return;
        }

        // RECUPERO DATI
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        try {
            Collection<Prodotto> catalogoCompleto = prodottoDAO.doRetrieveAllAdmin();
            
            request.setAttribute("prodotti", catalogoCompleto);
            request.getRequestDispatcher("/WEB-INF/admin-prodotti.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            // errore formattato per la View
            request.setAttribute("erroreAdmin", "Errore nel recupero del catalogo prodotti.");
            request.getRequestDispatcher("/WEB-INF/admin-prodotti.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}