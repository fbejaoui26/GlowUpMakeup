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

    // il doGet mostra solo la pagina del carrello
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }


        request.getRequestDispatcher("/WEB-INF/carrello.jsp").forward(request, response);
    }

    // il doPost gestisce tutte le azioni di modifica del carrello
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        
        if (carrello == null) {
            carrello = new Carrello();
        }

        String action = request.getParameter("action");

        if (action != null) {
            try {
                if (action.equals("aggiungi")) {
                    int idProdotto = Integer.parseInt(request.getParameter("id"));
                    int quantita = 1;
                    if (request.getParameter("quantita") != null) {
                        quantita = Integer.parseInt(request.getParameter("quantita"));
                    }

                    ProdottoDAO dao = new ProdottoDAO();
                    Prodotto prodotto = dao.doRetrieveByKey(idProdotto);
                    
                    if (prodotto != null) {
                        carrello.aggiungiProdotto(prodotto, quantita);
                    }

                } else if (action.equals("rimuovi")) {
      
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

        session.setAttribute("carrello", carrello);

        // CONTROLLO AJAX
        boolean isAjax = "true".equals(request.getParameter("ajax"));
        
        if (isAjax) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            int nuovoTotale = carrello.getNumeroElementiTotali(); 
            response.getWriter().write("{\"totaleElementi\": " + nuovoTotale + "}");
            return; 
        }

        response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
    }
}