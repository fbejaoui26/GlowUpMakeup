package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrdineDAO {

    public void doSave(Carrello carrello, Utente utente, String indirizzoSpedizione, String metodoPagamento) throws SQLException {
        Connection connection = null;
        PreparedStatement psOrdine = null;
        PreparedStatement psComposizione = null;

        String insertOrdine = "INSERT INTO ordine (data_ordine, stato, utente_email, indirizzo_spedizione, metodo_pagamento, totale) VALUES (?, ?, ?, ?, ?, ?)";
        String insertComposizione = "INSERT INTO composizione (ordine_id, prodotto_id, quantita, prezzo_acquisto) VALUES (?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            connection.setAutoCommit(false);

            psOrdine = connection.prepareStatement(insertOrdine, Statement.RETURN_GENERATED_KEYS);
            psOrdine.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now())); 
            psOrdine.setString(2, "In elaborazione"); // stato iniziale di default
            psOrdine.setString(3, utente.getEmail());
            psOrdine.setString(4, indirizzoSpedizione);
            psOrdine.setString(5, metodoPagamento);
            psOrdine.setDouble(6, carrello.getPrezzoTotaleCarrello());
            
            psOrdine.executeUpdate();

            // recupero l'ID dell'ordine appena generato
            ResultSet rs = psOrdine.getGeneratedKeys();
            int idOrdine = 0;
            if (rs.next()) {
                idOrdine = rs.getInt(1);
            }

            // salvo la composizione
            psComposizione = connection.prepareStatement(insertComposizione);
            for (ElementoCarrello elemento : carrello.getElementi()) {
                psComposizione.setInt(1, idOrdine);
                psComposizione.setInt(2, elemento.getProdotto().getId());
                psComposizione.setInt(3, elemento.getQuantita());
                psComposizione.setDouble(4, elemento.getProdotto().getPrezzo());
                psComposizione.executeUpdate();
            }

            connection.commit(); 

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); 
            }
            throw e;
        } finally {
            try {
                if (psComposizione != null) psComposizione.close();
                if (psOrdine != null) psOrdine.close();
            } finally {
                if (connection != null) connection.setAutoCommit(true);
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }
}