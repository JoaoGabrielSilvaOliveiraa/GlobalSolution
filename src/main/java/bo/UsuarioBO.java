package bo;

import dao.UsuarioDAO;
import beans.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioBO {

    private UsuarioDAO usuarioDAO;

    // Construtor que recebe a conexão e inicializa o DAO
    public UsuarioBO(Connection connection) {
        this.usuarioDAO = new UsuarioDAO(connection);
    }

    // Método para adicionar um novo usuário
    public void adicionarUsuario(Usuario usuario) throws SQLException {
        // Aqui você pode adicionar lógicas de validação de negócios antes de adicionar o usuário
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail do usuário não pode ser vazio.");
        }

        // Chama o método do DAO para adicionar o usuário
        usuarioDAO.adicionarUsuario(usuario);
    }

    public UsuarioBO() {
        super();
    }

    // Método para listar todos os usuários
    public List<Usuario> listarUsuarios() throws SQLException {
        // Lógica de negócios, se necessário
        return usuarioDAO.listarUsuarios();
    }

    // Método para atualizar os dados de um usuário
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        // Validação dos dados do usuário
        if (usuario.getIdCadastro() <= 0) {
            throw new IllegalArgumentException("ID de cadastro inválido.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail não pode ser vazio.");
        }

        // Chama o método do DAO para atualizar o usuário
        usuarioDAO.atualizarUsuario(usuario);
    }

    // Método para remover um usuário pelo ID
    public void removerUsuario(int idCadastro) throws SQLException {
        // Validação antes de remover
        if (idCadastro <= 0) {
            throw new IllegalArgumentException("ID de cadastro inválido.");
        }

        // Chama o método do DAO para remover o usuário
        usuarioDAO.removerUsuario(idCadastro);
    }

    // Método para buscar um usuário pelo e-mail e senha (sem depender de TipoCliente)
    public Usuario buscarUsuarioPorEmailESenha(String email, String senha) throws SQLException {
        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("E-mail e senha são obrigatórios.");
        }

        return usuarioDAO.buscarUsuarioPorEmailESenha(email, senha);  // Chama o método no DAO
    }
}
