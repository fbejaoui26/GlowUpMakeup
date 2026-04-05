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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // recupero i dati, già validati, dalla form 
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // creo il bean e lo riempio
        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(password);
        nuovoUtente.setRuolo("cliente"); // di default chi si registra è un cliente normale

        // salvo nel database tramite il DAO
        UtenteDAO dao = new UtenteDAO();
        try {
            dao.doSave(nuovoUtente);
            
            // creo la sessione (o recupero quella esistente del carrello)
            HttpSession session = request.getSession();
            
            // salvo l'intero oggetto utente nella sessione come "token" di riconoscimento
            session.setAttribute("utenteLoggato", nuovoUtente);
            
            // reinderizzo l'utente alla home da loggato
            response.sendRedirect("HomeServlet");
            
        } catch (SQLException e) {
            e.printStackTrace();
            // se c'è un errore (es. email già esistente nel DB) torno indietro
            request.setAttribute("erroreServer", "Errore durante la registrazione. Email forse già in uso.");
            request.getRequestDispatcher("registrazione.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}