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

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute("utenteLoggato") != null) {
            response.sendRedirect("HomeServlet");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/registrazione.jsp").forward(request, response);
    }

    // il doPost gestisce la ricezione e il salvataggio dei dati
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(password);
        nuovoUtente.setRuolo("cliente"); 

        UtenteDAO dao = new UtenteDAO();
        try {
            dao.doSave(nuovoUtente);
            
            HttpSession session = request.getSession();
            session.setAttribute("utenteLoggato", nuovoUtente);
            
            response.sendRedirect("HomeServlet");
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("erroreRegistrazione", "Errore durante la registrazione. Email forse già in uso.");
            request.getRequestDispatcher("/WEB-INF/registrazione.jsp").forward(request, response);
        }
    }
}