package dao;

import beans.Contato;
import beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private Connection connection;

    public ContatoDAO(Connection connection) {
        this.connection = connection;
    }

    public void adicionarContato(Contato contato) throws SQLException {
        String sql = "INSERT INTO contatos (email, telefone, comentario) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contato.getEmail());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getComentario());
            stmt.executeUpdate();
        }
    }
    
    public List<Contato> listarContatos() throws SQLException {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM Contatos";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	contatos.add(new Contato( 
            		    rs.getString("email"), 
            		    rs.getString("telefone"), 
            		    rs.getString("comentario")
            		));
            }
        }
        return contatos;
    }
}
