package it.unisa.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Ordine {
    private int id;
    private Timestamp dataOrdine;
    private String stato;
    private String utenteEmail;
    private String indirizzoSpedizione;
    private String metodoPagamento;
    private double totale;
    
    // la "composizione" dell'ordine: tutti i prodotti acquistati
    private List<ElementoOrdine> elementi;

    public Ordine() {
        this.elementi = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Timestamp dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getUtenteEmail() {
        return utenteEmail;
    }

    public void setUtenteEmail(String utenteEmail) {
        this.utenteEmail = utenteEmail;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public void setIndirizzoSpedizione(String indirizzoSpedizione) {
        this.indirizzoSpedizione = indirizzoSpedizione;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public List<ElementoOrdine> getElementi() {
        return elementi;
    }

    public void setElementi(List<ElementoOrdine> elementi) {
        this.elementi = elementi;
    }
    
    public void aggiungiElemento(ElementoOrdine elemento) {
        this.elementi.add(elemento);
    }
}