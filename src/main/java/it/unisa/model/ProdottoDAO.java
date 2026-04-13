package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ProdottoDAO {

    // salvare un nuovo prodotto
    public void doSave(Prodotto product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO prodotto (nome, descrizione, prezzo, quantita, immagine, is_cancellato, categoria_id, colore, formato, marchio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            connection.setAutoCommit(false);
            
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
                bean.setCancellato(rs.getBoolean("is_cancellato"));
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
                bean.setCancellato(rs.getBoolean("is_cancellato"));
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
 // preleva TUTTI i prodotti (sia visibili che nascosti) per il pannello Admin
    public Collection<Prodotto> doRetrieveAllAdmin() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<Prodotto> products = new ArrayList<Prodotto>();

        String selectSQL = "SELECT * FROM prodotto ORDER BY id DESC";

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
                bean.setCancellato(rs.getBoolean("is_cancellato"));

                products.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return products;
    }
    
 // cambia lo stato di visibilità di un prodotto
    public void doUpdateCancellato(int id, boolean isCancellato) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE prodotto SET is_cancellato = ? WHERE id = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            connection.setAutoCommit(false);
            
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setBoolean(1, isCancellato);
            preparedStatement.setInt(2, id);

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
    
 // modifica un prodotto esistente
    public void doUpdate(Prodotto product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        //query per gestire l'immagine opzionale
        StringBuilder query = new StringBuilder("UPDATE prodotto SET nome=?, descrizione=?, prezzo=?, quantita=?, categoria_id=?, colore=?, formato=?, marchio=?");
        
        boolean aggiornaImmagine = product.getImmagine() != null && !product.getImmagine().trim().isEmpty();
        if (aggiornaImmagine) {
            query.append(", immagine=?");
        }
        query.append(" WHERE id=?");

        try {
            connection = DriverManagerConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query.toString());
            
            preparedStatement.setString(1, product.getNome());
            preparedStatement.setString(2, product.getDescrizione());
            preparedStatement.setDouble(3, product.getPrezzo());
            preparedStatement.setInt(4, product.getQuantita());
            preparedStatement.setInt(5, product.getCategoriaId());
            preparedStatement.setString(6, product.getColore());
            preparedStatement.setString(7, product.getFormato());
            preparedStatement.setString(8, product.getMarchio());

            int paramIndex = 9;
            if (aggiornaImmagine) {
                preparedStatement.setString(paramIndex++, product.getImmagine());
            }
            preparedStatement.setInt(paramIndex, product.getId()); // L'ID va sempre alla fine

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
    
    public Collection<Prodotto> doRetrieveByCategoria(int idCategoria) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        Collection<Prodotto> prodotti = new LinkedList<>();

        String selectSQL = "SELECT * FROM prodotto WHERE categoria_id = ? AND is_cancellato = false";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idCategoria);
            
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Prodotto bean = new Prodotto();
                bean.setId(resultSet.getInt("id"));
                bean.setNome(resultSet.getString("nome"));
                bean.setMarchio(resultSet.getString("marchio"));
                bean.setDescrizione(resultSet.getString("descrizione"));
                bean.setPrezzo(resultSet.getDouble("prezzo"));
                bean.setQuantita(resultSet.getInt("quantita"));
                bean.setColore(resultSet.getString("colore"));
                bean.setFormato(resultSet.getString("formato"));
                bean.setImmagine(resultSet.getString("immagine"));
                bean.setCategoriaId(resultSet.getInt("categoria_id"));
                bean.setCancellato(resultSet.getBoolean("is_cancellato"));
                
                prodotti.add(bean);
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return prodotti;
    }
}