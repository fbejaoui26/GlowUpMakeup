package it.unisa.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.Utente;

@WebServlet("/NuovoProdottoServlet")
public class NuovoProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Utente admin = (Utente) session.getAttribute("utenteLoggato");
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/HomeServlet");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/nuovo-prodotto.jsp").forward(request, response);
    }
}