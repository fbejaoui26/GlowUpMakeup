package it.unisa.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import it.unisa.model.Prodotto;
import it.unisa.model.ProdottoDAO;
import it.unisa.model.Utente;

@WebServlet("/ModificaProdottoServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ModificaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String PERCORSO_SORGENTE_PROGETTO = "C:\\Users\\fatma\\eclipse-workspace\\GlowUpMakeup\\src\\main\\webapp\\images";

    // mostra il form pre-compilato
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente admin = (Utente) session.getAttribute("utenteLoggato");
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/HomeServlet");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            ProdottoDAO dao = new ProdottoDAO();
            Prodotto prodotto = dao.doRetrieveByKey(id);
            
            request.setAttribute("prodottoDaModificare", prodotto);
            request.getRequestDispatcher("/WEB-INF/modifica-prodotto.jsp").forward(request, response);
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet");
        }
    }

    // salva le modifiche nel DB
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Utente admin = (Utente) session.getAttribute("utenteLoggato");
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/HomeServlet");
            return;
        }

        Prodotto prodottoAggiornato = new Prodotto();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String marchio = request.getParameter("marchio");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            String colore = request.getParameter("colore");
            String formato = request.getParameter("formato");
            int categoriaId = Integer.parseInt(request.getParameter("categoria"));

            prodottoAggiornato.setId(id);
            prodottoAggiornato.setNome(nome);
            prodottoAggiornato.setMarchio(marchio);
            prodottoAggiornato.setDescrizione(descrizione);
            prodottoAggiornato.setPrezzo(prezzo);
            prodottoAggiornato.setQuantita(quantita);
            prodottoAggiornato.setColore(colore);
            prodottoAggiornato.setFormato(formato);
            prodottoAggiornato.setCategoriaId(categoriaId);

            // gestione immagine
            Part filePart = request.getPart("immagine");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            
            if (fileName != null && !fileName.isEmpty()) {
                String tempPath = getServletContext().getRealPath("") + File.separator + "images";
                salvaSuDisco(filePart, tempPath, fileName);
                salvaSuDisco(filePart, PERCORSO_SORGENTE_PROGETTO, fileName);
                prodottoAggiornato.setImmagine(fileName);
            } else {
                prodottoAggiornato.setImmagine(null); 
            }

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doUpdate(prodottoAggiornato);

            response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet?success=true");

        } catch (NumberFormatException | SQLException | NullPointerException e) {
            e.printStackTrace();
            request.setAttribute("erroreModifica", "Errore durante l'aggiornamento. Ricontrolla i campi inseriti.");
            request.setAttribute("prodottoDaModificare", prodottoAggiornato);
            request.getRequestDispatcher("/WEB-INF/modifica-prodotto.jsp").forward(request, response);
        }
    }

    private void salvaSuDisco(Part part, String path, String fileName) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
        try (InputStream input = part.getInputStream();
             FileOutputStream output = new FileOutputStream(new File(dir, fileName))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) output.write(buffer, 0, length);
        }
    }
}