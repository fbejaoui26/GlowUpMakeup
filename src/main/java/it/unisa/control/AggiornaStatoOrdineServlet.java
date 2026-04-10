package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.OrdineDAO;
import it.unisa.model.Utente;

@WebServlet("/AggiornaStatoOrdineServlet")
public class AggiornaStatoOrdineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // controllo di sicurezza
        HttpSession session = request.getSession();
        Utente admin = (Utente) session.getAttribute("utenteLoggato");
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/HomeServlet");
            return;
        }

        try {
            // recupero l'ID dell'ordine e il nuovo stato dal form
            int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
            String nuovoStato = request.getParameter("nuovoStato");

            // aggiorno il database
            OrdineDAO ordineDAO = new OrdineDAO();
            ordineDAO.updateStato(idOrdine, nuovoStato);

            // torno alla pagina degli ordini
            response.sendRedirect(request.getContextPath() + "/AdminOrdiniServlet");

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            
            request.setAttribute("erroreAdminOrdini", "Errore durante l'aggiornamento dello stato per l'ordine #" + request.getParameter("idOrdine"));
            request.getRequestDispatcher("/AdminOrdiniServlet").forward(request, response);
        }
    }
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/AdminOrdiniServlet");
    }
}