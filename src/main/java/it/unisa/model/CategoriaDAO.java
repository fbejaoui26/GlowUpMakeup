package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    // recupera tutte le categorie dal database
    public List<Categoria> doRetrieveAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Categoria> categorie = new ArrayList<>();

        String selectSQL = "SELECT * FROM categoria";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Categoria cat = new Categoria();
                cat.setId(resultSet.getInt("id"));
                cat.setNome(resultSet.getString("nome"));
                cat.setDescrizione(resultSet.getString("descrizione"));
                categorie.add(cat);
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return categorie;
    }
}