package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {

 // registrazione di un nuovo utente nel database 
 public void doSave(Utente utente) throws SQLException {
  Connection connection = null;
  PreparedStatement preparedStatement = null;

  String insertSQL = "INSERT INTO utente (email, password, nome, cognome, ruolo) VALUES (?, ?, ?, ?, ?)";

  try {
   connection = DriverManagerConnectionPool.getConnection();
   preparedStatement = connection.prepareStatement(insertSQL);
   
   connection.setAutoCommit(false);
   
   preparedStatement.setString(1, utente.getEmail());
   preparedStatement.setString(2, utente.getPassword()); 
   preparedStatement.setString(3, utente.getNome());
   preparedStatement.setString(4, utente.getCognome());
   preparedStatement.setString(5, "cliente"); 

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
//metodo per il Login
 public Utente doRetrieveByEmailAndPassword(String email, String password) throws SQLException {
     Connection connection = null;
     PreparedStatement preparedStatement = null;
     Utente utente = null;

     String selectSQL = "SELECT * FROM utente WHERE email = ? AND password = ?";

     try {
         connection = DriverManagerConnectionPool.getConnection();
         preparedStatement = connection.prepareStatement(selectSQL);
         preparedStatement.setString(1, email);
         preparedStatement.setString(2, password);

         ResultSet rs = preparedStatement.executeQuery();

         if (rs.next()) {
             utente = new Utente();
             utente.setEmail(rs.getString("email"));
             utente.setPassword(rs.getString("password"));
             utente.setNome(rs.getString("nome"));
             utente.setCognome(rs.getString("cognome"));
             utente.setRuolo(rs.getString("ruolo"));
         }
     } finally {
         try {
             if (preparedStatement != null) preparedStatement.close();
         } finally {
             DriverManagerConnectionPool.releaseConnection(connection);
         }
     }
     return utente; // ritorna l'utente se lo trova, altrimenti ritorna null
 }
}