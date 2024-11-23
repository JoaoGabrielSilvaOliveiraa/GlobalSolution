package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import beans.Usuario;

public class UsuarioDAO {

    // Conexão
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }
    
    public Usuario buscarUsuarioPorId(int idCadastro) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id_cadastro = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCadastro);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Criação do objeto Usuario a partir dos dados do banco
                Usuario usuario = new Usuario();
                usuario.setIdCadastro(rs.getInt("id_cadastro"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                
                return usuario; // Retorna o usuário encontrado
            } else {
                return null; // Retorna null se o usuário não for encontrado
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar usuário por ID: " + e.getMessage(), e);
        }
    }

 

    

    public void adicionarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, documento, email, telefone, cep, rua, cidade, estado, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connection.setAutoCommit(false);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            System.out.println("Preparando para adicionar o usuário com os seguintes dados:");
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Documento: " + usuario.getDocumento());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Telefone: " + usuario.getTelefone());

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
            System.out.println("Usuário adicionado com sucesso.");
        } catch (SQLException e) {
            connection.rollback();
            System.err.println("Erro ao adicionar usuário: " + e.getMessage());
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    // Fazendo a listagem de todos usuarios caso seja necessário 
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt("id_cadastro"), rs.getString("nome"), rs.getString("documento"),
                        rs.getString("email"), rs.getString("telefone"), rs.getString("cep"), rs.getString("rua"),
                        rs.getString("cidade"), rs.getString("estado"), rs.getString("senha")));
            }
        }
        return usuarios;
    }

    // Atualizar as credenciais para atualizar as credenciais do Usuario
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, documento = ?, email = ?, telefone = ?, cep = ?, rua = ?, cidade = ?, estado = ?, senha = ? WHERE id_cadastro = ?";

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
        }
    }

    // Remover o Usuario (Exclusão de conta)
    public void removerUsuario(int idCadastro) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_cadastro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCadastro);
            stmt.executeUpdate();
        }
    }

    // Buscar por EmailESenha (Método do login)
    public Usuario buscarUsuarioPorEmailESenha(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getInt("id_cadastro"), rs.getString("nome"), rs.getString("documento"),
                            rs.getString("email"), rs.getString("telefone"), rs.getString("cep"), rs.getString("rua"),
                            rs.getString("cidade"), rs.getString("estado"), rs.getString("senha"));
                }
            }
        }
        return null;
    }

    // Validação para ver se já existe o usuario
    public boolean validarUsuarioExistente(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // Método que retorna o idUsuario baseado no email
    public Usuario buscarUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_cadastro"),
                            rs.getString("nome"),
                            rs.getString("documento"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("cep"),
                            rs.getString("rua"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("senha")
                    );
                }
            }
        }
        return null;
    }
    
 // Método para buscar o ID do usuário por email
    public int buscarIdUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT id_cadastro FROM usuarios WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_cadastro");
                }
            }
        }
        return -1; // Retorna -1 caso o usuário não seja encontrado
    }


 
}
