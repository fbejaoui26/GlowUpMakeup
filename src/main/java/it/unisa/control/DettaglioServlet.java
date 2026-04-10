package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.Prodotto;
import it.unisa.model.ProdottoDAO;

@WebServlet("/DettaglioServlet")
public class DettaglioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            // controllo che l'ID sia stato passato e sia un numero valido
            String idString = request.getParameter("id");
            if (idString == null || idString.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/HomeServlet");
                return;
            }
            
            int id = Integer.parseInt(idString);
            ProdottoDAO dao = new ProdottoDAO();
            
            Prodotto prodotto = dao.doRetrieveByKey(id);
            
            // se il prodotto non esiste, torna alla home
            if (prodotto == null) {
                response.sendRedirect(request.getContextPath() + "/HomeServlet");
                return;
            }
            
            request.setAttribute("prodottoSingolo", prodotto);
            request.getRequestDispatcher("/WEB-INF/dettaglio.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/HomeServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/HomeServlet"); 
        }
    }
}