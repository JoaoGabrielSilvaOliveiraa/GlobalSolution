package resource;

import conexoes.ConexaoFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.LoginRequest;
import beans.Usuario;
import bo.UsuarioBO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/usuarios")
public class UsuarioResource {

    private UsuarioBO usuarioBO;

    // Construtor que inicializa o UsuarioBO com uma conexão
    public UsuarioResource() {
        try {
            ConexaoFactory conexaoFactory = new ConexaoFactory();
            Connection connection = conexaoFactory.conexao(); // Obtém a conexão com o banco
            this.usuarioBO = new UsuarioBO(connection); // Passa a conexão para o BO
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Tratar o erro de conexão aqui
        }
    }

    // Rota para registrar um novo usuário
    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(Usuario usuario) {
        try {
            // Verifica se o e-mail já existe
            if (usuarioBO.buscarUsuarioPorEmailESenha(usuario.getEmail(), usuario.getSenha()) != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("E-mail já cadastrado.")
                        .build();
            }
            usuarioBO.adicionarUsuario(usuario); // Chama o BO para adicionar o usuário
            return Response.status(Response.Status.CREATED).entity("Usuário registrado com sucesso").build();
        } catch (SQLException e) {
            e.printStackTrace(); // Tratando erros
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao registrar o usuário")
                    .build();
        }
    }

    // Rota para login de usuário
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(LoginRequest loginRequest) {
        try {
            // Verificar se o usuário existe com o e-mail e senha fornecidos
            Usuario usuario = usuarioBO.buscarUsuarioPorEmailESenha(
                    loginRequest.getEmail(), loginRequest.getSenha());

            if (usuario != null) {
                return Response.ok(usuario).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Credenciais inválidas")
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no servidor")
                    .build();
        }
    }

    // Rota para listar todos os usuários
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioBO.listarUsuarios();
            return Response.ok(usuarios).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar os usuários")
                    .build();
        }
    }
}
