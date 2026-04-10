package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.Utente;
import it.unisa.model.UtenteDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // il doGet serve solo per mostrare la pagina di login
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // se l'utente è già loggato, lo rimandiamo alla Home
        if (session != null && session.getAttribute("utenteLoggato") != null) {
            response.sendRedirect("HomeServlet");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    // Il doPost gestisce l'invio del form
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtenteDAO dao = new UtenteDAO();
        try {
            Utente utente = dao.doRetrieveByEmailAndPassword(email, password);

            if (utente != null) {
                HttpSession session = request.getSession();
                session.setAttribute("utenteLoggato", utente);
                
                if (utente.isAdmin()) {
                    // ATTENZIONE: Se anche dashboard-admin.jsp finisce in WEB-INF, non puoi usare sendRedirect.
                    // Per ora usiamo un forward, ma l'ideale sarà creare una AdminDashboardServlet!
                    response.sendRedirect("AdminDashboard");
                } else {
                    response.sendRedirect("HomeServlet"); 
                }
            } else {
                request.setAttribute("erroreLogin", "Email o password errati. Riprova.");
                // Rimandiamo alla JSP protetta
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erroreLogin", "Errore di connessione al database.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}