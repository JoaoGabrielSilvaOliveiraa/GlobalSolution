package dao;

import beans.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para inserir um novo usuário
    public void adicionarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, documento, email, telefone, cep, rua, cidade, estado, senha) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        if (connection == null || connection.isClosed()) {
            throw new SQLException("Conexão com o banco de dados não estabelecida ou está fechada.");
        }

        connection.setAutoCommit(false);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getDocumento());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());
            stmt.setString(5, usuario.getCep());
            stmt.setString(6, usuario.getRua());
            stmt.setString(7, usuario.getCidade());
            stmt.setString(8, usuario.getEstado());
            stmt.setString(9, usuario.getSenha());

            stmt.executeUpdate();

            connection.commit();
            System.out.println("Usuário adicionado com sucesso: " + usuario.getNome());
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("Erro ao adicionar o usuário: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Método para buscar um usuário pelo ID
    public Usuario buscarUsuarioPorId(int idCadastro) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id_cadastro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCadastro);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getInt("id_cadastro"),
                            rs.getString("nome"), rs.getString("documento"), rs.getString("email"),
                            rs.getString("telefone"), rs.getString("cep"), rs.getString("rua"), rs.getString("cidade"),
                            rs.getString("estado"), rs.getString("senha"));
                }
            }
        }
        return null;
    }

    // Método para listar todos os usuários
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id_cadastro"),
                        rs.getString("nome"), rs.getString("documento"), rs.getString("email"),
                        rs.getString("telefone"), rs.getString("cep"), rs.getString("rua"),
                        rs.getString("cidade"), rs.getString("estado"), rs.getString("senha"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    // Método para atualizar os dados de um usuário
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, documento = ?, email = ?, telefone = ?, cep = ?, rua = ?, cidade = ?, estado = ?, senha = ? "
                + "WHERE id_cadastro = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getDocumento());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());
            stmt.setString(5, usuario.getCep());
            stmt.setString(6, usuario.getRua());
            stmt.setString(7, usuario.getCidade());
            stmt.setString(8, usuario.getEstado());
            stmt.setString(9, usuario.getSenha());
            stmt.setInt(10, usuario.getIdCadastro());

            stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso: " + usuario.getNome());
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o usuário: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Método para remover um usuário pelo ID
    public void removerUsuario(int idCadastro) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_cadastro = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCadastro);
            stmt.executeUpdate();
            System.out.println("Usuário removido com sucesso: ID " + idCadastro);
        } catch (SQLException e) {
            System.out.println("Erro ao remover o usuário: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Método para buscar um usuário por email e senha
    public Usuario buscarUsuarioPorEmailESenha(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getInt("id_cadastro"),
                            rs.getString("nome"), rs.getString("documento"), rs.getString("email"),
                            rs.getString("telefone"), rs.getString("cep"), rs.getString("rua"), rs.getString("cidade"),
                            rs.getString("estado"), rs.getString("senha"));
                }
            }
        }
        return null;
    }

    // Novo método para validar se o email já existe
    public boolean validarUsuarioExistente(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
}
