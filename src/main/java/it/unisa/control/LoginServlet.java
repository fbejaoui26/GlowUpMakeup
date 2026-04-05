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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtenteDAO dao = new UtenteDAO();
        try {
            // cerco l'utente nel DB
            Utente utente = dao.doRetrieveByEmailAndPassword(email, password);

            if (utente != null) {
                // l'utente esiste quindi creo il token di sessione
                HttpSession session = request.getSession();
                session.setAttribute("utenteLoggato", utente);
                
                // redirezione alla home
                response.sendRedirect("HomeServlet");
            } else {
                // credenziali errate: torniamo al login mostrando il messaggio di errore
                request.setAttribute("erroreLogin", "Email o password errati. Riprova.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erroreLogin", "Errore di connessione al database.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}