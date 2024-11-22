package resource;

import bo.AgendamentoBO;
import beans.Agendamento;
import conexoes.ConexaoFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/agendamentos")
public class AgendamentoResource {

    private AgendamentoBO agendamentoBO;

    // Construtor onde instanciamos manualmente o AgendamentoBO
    public AgendamentoResource() {
        try {
            // Obtém a conexão com o banco de dados através da ConexaoFactory
            ConexaoFactory conexaoFactory = new ConexaoFactory();
            Connection connection = conexaoFactory.conexao();
            
            // Instancia o AgendamentoBO passando a conexão
            this.agendamentoBO = new AgendamentoBO(connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  // Em um cenário real, gerencie isso de maneira mais robusta
        }
    }

    // Endpoint para criar um novo agendamento
    @POST
    @Path("/criar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarAgendamento(Agendamento agendamento, @QueryParam("email") String email) {
        try {
            // Passa o email junto com o agendamento para a BO
            agendamentoBO.adicionarAgendamento(email, agendamento); 
            return Response.status(Response.Status.CREATED).entity(agendamento).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar agendamento: " + e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro: " + e.getMessage()).build();
        }
    }

    // Endpoint para atualizar um agendamento
    @PUT
    @Path("/atualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarAgendamento(@QueryParam("email") String email, @PathParam("id") int idAgendamento, Agendamento agendamento) {
        try {
            agendamento.setIdAgendamento(idAgendamento); // Setando o ID do agendamento
            // Passa o email junto com o agendamento para a BO
            agendamentoBO.atualizarAgendamento(email, agendamento); 
            return Response.status(Response.Status.OK).entity(agendamento).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar agendamento: " + e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro: " + e.getMessage()).build();
        }
    }

    // Endpoint para listar todos os agendamentos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAgendamentos() {
        try {
            List<Agendamento> agendamentos = agendamentoBO.listarAgendamentos();
            return Response.status(Response.Status.OK).entity(agendamentos).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar agendamentos: " + e.getMessage()).build();
        }
    }

    // Endpoint para remover um agendamento
    @DELETE
    @Path("/remover/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerAgendamento(@PathParam("id") int idAgendamento) {
        try {
            agendamentoBO.removerAgendamento(idAgendamento);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao remover agendamento: " + e.getMessage()).build();
        }
    }

    // Endpoint para buscar um agendamento por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarAgendamentoPorId(@PathParam("id") int idAgendamento) {
        try {
            Agendamento agendamento = agendamentoBO.buscarAgendamentoPorId(idAgendamento);
            if (agendamento != null) {
                return Response.status(Response.Status.OK).entity(agendamento).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Agendamento não encontrado").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar agendamento: " + e.getMessage()).build();
        }
    }
}
