package resource;

import bo.ContatoBO;
import beans.Contato;
import conexoes.ConexaoFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/contatos")
public class ContatoResource {
    private ContatoBO contatoBO;

    public ContatoResource() {
        try {
            ConexaoFactory conexaoFactory = new ConexaoFactory();
            Connection connection = conexaoFactory.conexao();
            this.contatoBO = new ContatoBO(connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Endpoint para criar um novo contato
    @POST
    @Path("/criar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarContato(Contato contato) {
        try {
            contatoBO.adicionarContato(contato);
            return Response.status(Response.Status.CREATED).entity(contato).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar contato: " + e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro: " + e.getMessage()).build();
        }
    }

    // Método para listar todos os contatos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarContatos() {
        try {
            // Obtém a lista de contatos do BO
            List<Contato> contatos = contatoBO.listarContatos();
            return Response.status(Response.Status.OK).entity(contatos).build();
        } catch (SQLException e) {
            // Retorna erro 500 em caso de problemas no banco de dados
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar contatos: " + e.getMessage()).build();
        }
    }
}
