package it.unisa.model;

import java.util.ArrayList;
import java.util.List;

public class Carrello {
    private List<ElementoCarrello> elementi;

    public Carrello() {
        this.elementi = new ArrayList<>();
    }

    // aggiunge un prodotto o incrementa la quantità se già presente
    public void aggiungiProdotto(Prodotto prodotto, int quantita) {
        for (ElementoCarrello elemento : elementi) {
            if (elemento.getProdotto().getId() == prodotto.getId()) {
                // ll prodotto c'è già quindi sommo la nuova quantità a quella vecchia
                elemento.setQuantita(elemento.getQuantita() + quantita);
                return; 
            }
        }
        // se arriviamo qui, significa che il prodotto non c'era. lo aggiungiamo 
        elementi.add(new ElementoCarrello(prodotto, quantita));
    }
    
    public void aggiornaQuantita(int idProdotto, int nuovaQuantita) {
 
        if (nuovaQuantita <= 0) {
            rimuoviProdotto(idProdotto);
            return;
        }
        
        for (ElementoCarrello elemento : elementi) {
            if (elemento.getProdotto().getId() == idProdotto) {
                elemento.setQuantita(nuovaQuantita);
                return;
            }
        }
    }

    // rimuove completamente un prodotto dal carrello tramite il suo ID
    public void rimuoviProdotto(int idProdotto) {
        elementi.removeIf(elemento -> elemento.getProdotto().getId() == idProdotto);
    }

    public List<ElementoCarrello> getElementi() {
        return elementi;
    }

    // calcola il totale da pagare
    public double getPrezzoTotaleCarrello() {
        double totale = 0;
        for (ElementoCarrello elemento : elementi) {
            totale += elemento.getPrezzoTotale();
        }
        return totale;
    }
    
 // Aggiungi questo in it.unisa.model.Carrello
    public int getNumeroElementiTotali() {
        int totale = 0;
        for (ElementoCarrello elemento : elementi) {
            totale += elemento.getQuantita();
        }
        return totale;
    }
    
    // svuota completamente il carrello (da usare dopo l'acquisto o al logout)
    public void svuota() {
        elementi.clear();
    }
}