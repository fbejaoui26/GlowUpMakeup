package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

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

    public List<Ordine> doRetrieveByUtente(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // usiamo una Mappa per raggruppare i prodotti sotto lo stesso ordine.
        Map<Integer, Ordine> ordiniMap = new LinkedHashMap<>();

        String query = 
            "SELECT o.id AS id_ordine, o.data_ordine, o.stato, o.indirizzo_spedizione, o.metodo_pagamento, o.totale, " +
            "c.quantita, c.prezzo_acquisto, " +
            "p.id AS id_prodotto, p.nome, p.immagine, p.marchio " +
            "FROM ordine o " +
            "JOIN composizione c ON o.id = c.ordine_id " +
            "JOIN prodotto p ON c.prodotto_id = p.id " +
            "WHERE o.utente_email = ? " +
            "ORDER BY o.data_ordine DESC"; // mostriamo prima gli ordini più recenti

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idOrdine = rs.getInt("id_ordine");

                Ordine ordine = ordiniMap.get(idOrdine);

                if (ordine == null) {
                    ordine = new Ordine();
                    ordine.setId(idOrdine);
                    ordine.setDataOrdine(rs.getTimestamp("data_ordine"));
                    ordine.setStato(rs.getString("stato"));
                    ordine.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                    ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));
                    ordine.setTotale(rs.getDouble("totale"));
                    ordine.setUtenteEmail(email);

                    ordiniMap.put(idOrdine, ordine);
                }

                // estraiamo i dati del singolo prodotto di questa riga
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setImmagine(rs.getString("immagine"));
                prodotto.setMarchio(rs.getString("marchio"));

                ElementoOrdine elemento = new ElementoOrdine();
                elemento.setProdotto(prodotto);
                elemento.setQuantita(rs.getInt("quantita"));
                elemento.setPrezzoAcquisto(rs.getDouble("prezzo_acquisto"));

                ordine.aggiungiElemento(elemento);
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }

        // trasformiamo i valori della mappa in una lista e la restituiamo
        return new ArrayList<>(ordiniMap.values());
    }
    
    public List<Ordine> doRetrieveAll(String dataInizio, String dataFine, String emailCliente) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer, Ordine> ordiniMap = new LinkedHashMap<>();

        StringBuilder query = new StringBuilder(
            "SELECT o.id AS id_ordine, o.data_ordine, o.stato, o.indirizzo_spedizione, o.metodo_pagamento, o.totale, o.utente_email, " +
            "c.quantita, c.prezzo_acquisto, " +
            "p.id AS id_prodotto, p.nome, p.immagine, p.marchio " +
            "FROM ordine o " +
            "JOIN composizione c ON o.id = c.ordine_id " +
            "JOIN prodotto p ON c.prodotto_id = p.id WHERE 1=1 "
        );

        // aggiungiamo i filtri dinamicamente
        if (dataInizio != null && !dataInizio.isEmpty()) {
            query.append(" AND o.data_ordine >= ? ");
        }
        if (dataFine != null && !dataFine.isEmpty()) {
            query.append(" AND o.data_ordine <= ? ");
        }
        if (emailCliente != null && !emailCliente.isEmpty()) {
            query.append(" AND o.utente_email LIKE ? ");
        }

        query.append(" ORDER BY o.data_ordine DESC");

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(query.toString());

            int paramIndex = 1;
            if (dataInizio != null && !dataInizio.isEmpty()) {
                ps.setString(paramIndex++, dataInizio + " 00:00:00");
            }
            if (dataFine != null && !dataFine.isEmpty()) {
                ps.setString(paramIndex++, dataFine + " 23:59:59");
            }
            if (emailCliente != null && !emailCliente.isEmpty()) {
                ps.setString(paramIndex++, "%" + emailCliente + "%");
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                int idOrdine = rs.getInt("id_ordine");
                Ordine ordine = ordiniMap.get(idOrdine);

                if (ordine == null) {
                    ordine = new Ordine();
                    ordine.setId(idOrdine);
                    ordine.setDataOrdine(rs.getTimestamp("data_ordine"));
                    ordine.setStato(rs.getString("stato"));
                    ordine.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                    ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));
                    ordine.setTotale(rs.getDouble("totale"));
                    ordine.setUtenteEmail(rs.getString("utente_email"));
                    ordiniMap.put(idOrdine, ordine);
                }

                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setImmagine(rs.getString("immagine"));
                prodotto.setMarchio(rs.getString("marchio"));

                ElementoOrdine elemento = new ElementoOrdine();
                elemento.setProdotto(prodotto);
                elemento.setQuantita(rs.getInt("quantita"));
                elemento.setPrezzoAcquisto(rs.getDouble("prezzo_acquisto"));

                ordine.aggiungiElemento(elemento);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }
        return new ArrayList<>(ordiniMap.values());
    }
    
 // metodo per aggiornare lo stato di un ordine da parte dell'admin
    public void updateStato(int idOrdine, String nuovoStato) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE ordine SET stato = ? WHERE id = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, nuovoStato);
            preparedStatement.setInt(2, idOrdine);

            preparedStatement.executeUpdate();
            connection.commit();

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }
}