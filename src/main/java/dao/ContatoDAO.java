package dao;

import beans.Contato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
