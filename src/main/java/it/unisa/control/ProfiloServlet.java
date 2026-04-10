package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.Ordine;
import it.unisa.model.OrdineDAO;
import it.unisa.model.Utente;

@WebServlet("/ProfiloServlet")
public class ProfiloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // controllo accessi
        Utente utenteLoggato = (Utente) session.getAttribute("utenteLoggato");
        if (utenteLoggato == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        // interrogazione del database
        OrdineDAO ordineDAO = new OrdineDAO();
        try {
            List<Ordine> storicoOrdini = ordineDAO.doRetrieveByUtente(utenteLoggato.getEmail());
            
            request.setAttribute("ordini", storicoOrdini);
            
            request.getRequestDispatcher("/WEB-INF/profilo.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erroreProfilo", "Si è verificato un errore nel caricamento dello storico.");
            request.getRequestDispatcher("/WEB-INF/profilo.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}