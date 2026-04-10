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

@WebServlet("/AggiungiProdottoServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, 
    maxFileSize = 1024 * 1024 * 10,      
    maxRequestSize = 1024 * 1024 * 50    
)
public class AggiungiProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String PERCORSO_SORGENTE_PROGETTO = "C:\\Users\\fatma\\eclipse-workspace\\GlowUpMakeup\\src\\main\\webapp\\images";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Utente admin = (Utente) session.getAttribute("utenteLoggato");
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/HomeServlet");
            return;
        }

        try {
            // recupero i parametri 
            String nome = request.getParameter("nome");
            String marchio = request.getParameter("marchio");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            String colore = request.getParameter("colore");
            String formato = request.getParameter("formato");
            int categoriaId = Integer.parseInt(request.getParameter("categoria"));

            // gestione Immagine
            Part filePart = request.getPart("immagine");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // salvataggio nella cartella temporanea di Tomcat ---
            String tempPath = getServletContext().getRealPath("") + File.separator + "images";
            salvaSuDisco(filePart, tempPath, fileName);

            // salvataggio nella cartella del progetto
            salvaSuDisco(filePart, PERCORSO_SORGENTE_PROGETTO, fileName);

            // salvo nel DB
            Prodotto nuovoProdotto = new Prodotto();
            nuovoProdotto.setNome(nome);
            nuovoProdotto.setMarchio(marchio);
            nuovoProdotto.setDescrizione(descrizione);
            nuovoProdotto.setPrezzo(prezzo);
            nuovoProdotto.setQuantita(quantita);
            nuovoProdotto.setColore(colore);
            nuovoProdotto.setFormato(formato);
            nuovoProdotto.setCategoriaId(categoriaId);
            nuovoProdotto.setImmagine(fileName);

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.doSave(nuovoProdotto);

            response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet");

        } catch (NumberFormatException | SQLException | NullPointerException e) {
            e.printStackTrace();      
            request.setAttribute("erroreInserimento", "Errore durante il salvataggio del prodotto. Controlla i dati inseriti.");
            request.getRequestDispatcher("/WEB-INF/nuovo-prodotto.jsp").forward(request, response);
        }
    }

    // metodo di utility per scrivere il file fisicamente (lasciato inalterato)
    private void salvaSuDisco(Part part, String path, String fileName) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
        
        try (InputStream input = part.getInputStream();
             FileOutputStream output = new FileOutputStream(new File(dir, fileName))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }
    }
}