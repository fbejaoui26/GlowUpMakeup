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
        
        int id = Integer.parseInt(request.getParameter("id"));
        ProdottoDAO dao = new ProdottoDAO();
        
        try {
            
            Prodotto prodotto = dao.doRetrieveByKey(id);
            
            request.setAttribute("prodottoSingolo", prodotto);
            request.getRequestDispatcher("dettaglio.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("HomeServlet"); // se c'è un errore, torna alla home
        }
    }
}