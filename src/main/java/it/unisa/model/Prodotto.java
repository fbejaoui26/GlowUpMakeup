package it.unisa.model;

import java.io.Serializable;

public class Prodotto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantita;
    private String immagine;     
    private boolean isCancellato; 
    private int categoriaId;      // chiave esterna

    public Prodotto() {
    }

    // getter e setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public boolean isCancellato() {
        return isCancellato;
    }

    public void setCancellato(boolean isCancellato) {
        this.isCancellato = isCancellato;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public String toString() {
        return "Prodotto [id=" + id + ", nome=" + nome + ", prezzo=" + prezzo + "]";
    }
}