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

@WebServlet("/AdminOrdiniServlet")
public class AdminOrdiniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente admin = (Utente) session.getAttribute("utenteLoggato");
        
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String dataInizio = request.getParameter("dataInizio");
        String dataFine = request.getParameter("dataFine");
        String email = request.getParameter("emailCliente");


        OrdineDAO dao = new OrdineDAO();
        try {
            List<Ordine> ordini = dao.doRetrieveAll(dataInizio, dataFine, email);
            request.setAttribute("listaOrdini", ordini);
            

            request.getRequestDispatcher("/WEB-INF/admin-ordini.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
 
            request.setAttribute("erroreAdminOrdini", "Si è verificato un errore durante il recupero degli ordini.");
            request.getRequestDispatcher("/WEB-INF/admin-ordini.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}