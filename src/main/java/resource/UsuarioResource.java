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
            if (connection != null) {
                this.usuarioBO = new UsuarioBO(connection); // Passa a conexão para o BO
            } else {
                throw new SQLException("Não foi possível obter uma conexão com o banco de dados.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Loga mais detalhes do erro
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace(); // Exibe a pilha de erro no console
        }
    }

    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(Usuario usuario) {
        if (usuarioBO == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: BO não inicializado")
                    .build();
        }

        try {
            // Verifica se o e-mail já existe
            if (usuarioBO.buscarUsuarioPorEmailESenha(usuario.getEmail(), usuario.getSenha()) != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("E-mail já cadastrado.")
                        .build();
            }

            // Tenta adicionar o usuário
            usuarioBO.adicionarUsuario(usuario);
            return Response.status(Response.Status.CREATED).entity("Usuário registrado com sucesso").build();
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime a stack trace para identificar detalhes do erro
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao registrar o usuário: " + e.getMessage() + "\n" + getStackTrace(e))
                    .build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // Imprime a stack trace para identificarmos erros de validação
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro de validação: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace(); // Captura qualquer outro erro não esperado
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage())
                    .build();
        }
    }

    // Método auxiliar para obter a stack trace como string
    private String getStackTrace(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }

    // Rota para login de usuário
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(LoginRequest loginRequest) {
        if (usuarioBO == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: BO não inicializado")
                    .build();
        }

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
        if (usuarioBO == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno: BO não inicializado")
                    .build();
        }

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
