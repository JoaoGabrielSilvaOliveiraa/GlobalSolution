package bo;

import dao.UsuarioDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import beans.Usuario;

public class UsuarioBO {

    private UsuarioDAO usuarioDAO;

    public UsuarioBO(Connection connection) {
        this.usuarioDAO = new UsuarioDAO(connection);
    }

    public void adicionarUsuario(Usuario usuario) throws SQLException {
        // Verifica se o nome não é nulo ou vazio
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
        }

        // Verifica se o e-mail não é nulo ou vazio
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail do usuário não pode ser vazio.");
        }

        // Verifica se o CPF tem 9 dígitos
        if (usuario.getDocumento() == null || usuario.getDocumento().length() != 9) {
            System.err.println("Erro de validação: CPF deve conter 9 dígitos. Documento informado: " + usuario.getDocumento());
            throw new IllegalArgumentException("O CPF deve conter 9 dígitos.");
        }

        // Log para confirmar que a validação foi bem-sucedida
        System.out.println("Validação bem-sucedida para o usuário: " + usuario.getNome());

        // Chama o método DAO para adicionar o usuário
        usuarioDAO.adicionarUsuario(usuario);
    }



    public List<Usuario> listarUsuarios() throws SQLException {
        return usuarioDAO.listarUsuarios();
    }

    public void atualizarUsuario(Usuario usuario) throws SQLException {
        if (usuario.getIdCadastro() <= 0) {
			throw new IllegalArgumentException("ID de cadastro inválido.");
		}
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail não pode ser vazio.");
        }
        usuarioDAO.atualizarUsuario(usuario);
    }

    public void removerUsuario(int idCadastro) throws SQLException {
        if (idCadastro <= 0) {
            throw new IllegalArgumentException("ID de cadastro inválido.");
        }
        usuarioDAO.removerUsuario(idCadastro);
    }

    public Usuario buscarUsuarioPorEmailESenha(String email, String senha) throws SQLException {
        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("E-mail e senha são obrigatórios.");
        }
        return usuarioDAO.buscarUsuarioPorEmailESenha(email, senha);
    }
}
