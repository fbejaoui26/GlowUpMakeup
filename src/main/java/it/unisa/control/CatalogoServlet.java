package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.Categoria;
import it.unisa.model.CategoriaDAO;
import it.unisa.model.Prodotto;
import it.unisa.model.ProdottoDAO;

@WebServlet("/CatalogoServlet") 
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaDAO catDAO = new CategoriaDAO();
        ProdottoDAO prodDAO = new ProdottoDAO();

        try {
            // recupero sempre le categorie per disegnare il menu laterale
            List<Categoria> categorie = catDAO.doRetrieveAll();
            request.setAttribute("categorie", categorie);

            // controllo se l'utente ha cliccato su una categoria specifica
            String idCategoriaStr = request.getParameter("categoria");
            Collection<Prodotto> prodotti;

            if (idCategoriaStr != null && !idCategoriaStr.isEmpty()) {
              
                int idCategoria = Integer.parseInt(idCategoriaStr);
                prodotti = prodDAO.doRetrieveByCategoria(idCategoria);
                
             
                request.setAttribute("categoriaSelezionata", idCategoria); 
            } else {
               
                prodotti = prodDAO.doRetrieveAll(""); 
            }

            request.setAttribute("prodotti", prodotti);

            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            
            request.setAttribute("erroreCatalogo", "Si è verificato un errore nel caricamento del catalogo.");
            request.getRequestDispatcher("/WEB-INF/catalogo.jsp").forward(request, response);
        }
    }
}