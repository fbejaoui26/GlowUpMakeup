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
import it.unisa.model.Prodotto;
import it.unisa.model.ProdottoDAO;

@WebServlet("/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // gestione sessione
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        
        if (carrello == null) {
            carrello = new Carrello();
        }

        String action = request.getParameter("action");

        if (action != null) {
            try {
                if (action.equals("aggiungi")) {
                    // recupero l'ID del prodotto da aggiungere
                    int idProdotto = Integer.parseInt(request.getParameter("id"));
                    
                    // se l'utente specifica la quantità (es. dalla pagina dettaglio), la uso, altrimenti di default è 1
                    int quantita = 1;
                    if (request.getParameter("quantita") != null) {
                        quantita = Integer.parseInt(request.getParameter("quantita"));
                    }

                    // prendo il prodotto vero e proprio dal database
                    ProdottoDAO dao = new ProdottoDAO();
                    Prodotto prodotto = dao.doRetrieveByKey(idProdotto);
                    
                    // se il prodotto esiste, lo metto nello carrello
                    if (prodotto != null) {
                        carrello.aggiungiProdotto(prodotto, quantita);
                    }

                } else if (action.equals("rimuovi")) {
                    // recuperiamo l'ID del prodotto da rimuovere e lo tolgo
                    int idProdotto = Integer.parseInt(request.getParameter("id"));
                    carrello.rimuoviProdotto(idProdotto);
                
            } else if (action.equals("aggiorna")) {
                int idProdotto = Integer.parseInt(request.getParameter("id"));
                int quantita = Integer.parseInt(request.getParameter("quantita"));
                carrello.aggiornaQuantita(idProdotto, quantita);
            }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // salvo il carrello aggiornato nella sessione
        session.setAttribute("carrello", carrello);

        // reinderizzo l'utente alla pagina per visualizzare il carrello
        response.sendRedirect("carrello.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}