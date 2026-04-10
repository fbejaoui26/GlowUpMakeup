package it.unisa.model;

public class ElementoOrdine {
    private Prodotto prodotto;
    private int quantita;
    private double prezzoAcquisto; // il prezzo "congelato" al momento del checkout

    public ElementoOrdine() {}

    public ElementoOrdine(Prodotto prodotto, int quantita, double prezzoAcquisto) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzoAcquisto = prezzoAcquisto;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrezzoAcquisto() {
        return prezzoAcquisto;
    }

    public void setPrezzoAcquisto(double prezzoAcquisto) {
        this.prezzoAcquisto = prezzoAcquisto;
    }
    
    // calcolo il totale della riga usando il prezzo "storico"
    public double getTotaleRiga() {
        return prezzoAcquisto * quantita;
    }
}