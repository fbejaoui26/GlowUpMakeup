package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ProdottoDAO {

    // salvare un nuovo prodotto
    public void doSave(Prodotto product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO prodotto (nome, descrizione, prezzo, quantita, immagine, is_cancellato, categoria_id, colore, formato, marchio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            
            preparedStatement.setString(1, product.getNome());
            preparedStatement.setString(2, product.getDescrizione());
            preparedStatement.setDouble(3, product.getPrezzo());
            preparedStatement.setInt(4, product.getQuantita());
            preparedStatement.setString(5, product.getImmagine());
            preparedStatement.setBoolean(6, false); // di default non è cancellato
            preparedStatement.setInt(7, product.getCategoriaId());
            preparedStatement.setString(8, product.getColore());
            preparedStatement.setString(9, product.getFormato());
            preparedStatement.setString(10, product.getMarchio());

            preparedStatement.executeUpdate();

            // rende le modifiche permanenti
            connection.commit();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    // cerca prodotto per id
    public Prodotto doRetrieveByKey(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Prodotto bean = new Prodotto();

        // seleziona solo se is_cancellato è 0 (false)
        String selectSQL = "SELECT * FROM prodotto WHERE id = ? AND is_cancellato = 0";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                bean.setId(rs.getInt("id"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setQuantita(rs.getInt("quantita"));
                bean.setImmagine(rs.getString("immagine"));
                bean.setCategoriaId(rs.getInt("categoria_id"));
                bean.setColore(rs.getString("colore"));
                bean.setFormato(rs.getString("formato"));
                bean.setMarchio(rs.getString("marchio"));

            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return bean;
    }

    // preleva tutti i prodotti per il catalogo
    public Collection<Prodotto> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Prodotto> products = new ArrayList<Prodotto>();

        String selectSQL = "SELECT * FROM prodotto WHERE is_cancellato = 0";

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Prodotto bean = new Prodotto();

                bean.setId(rs.getInt("id"));
                bean.setNome(rs.getString("nome"));
                bean.setDescrizione(rs.getString("descrizione"));
                bean.setPrezzo(rs.getDouble("prezzo"));
                bean.setQuantita(rs.getInt("quantita"));
                bean.setImmagine(rs.getString("immagine"));
                bean.setCategoriaId(rs.getInt("categoria_id"));
                bean.setColore(rs.getString("colore"));
                bean.setFormato(rs.getString("formato"));
                bean.setMarchio(rs.getString("marchio"));

                products.add(bean);
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return products;
    }
}