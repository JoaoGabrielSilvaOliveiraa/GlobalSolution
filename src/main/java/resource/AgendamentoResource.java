package resource;

import beans.Agendamento;
import bo.AgendamentoBO;
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

    public AgendamentoResource() {
        try {
            Connection connection = new ConexaoFactory().conexao();
            this.agendamentoBO = new AgendamentoBO(connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarAgendamento(Agendamento agendamento) {
        try {
            agendamentoBO.adicionarAgendamento(agendamento);
            return Response.status(Response.Status.CREATED).entity("Agendamento criado com sucesso").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAgendamentos() {
        try {
            List<Agendamento> agendamentos = agendamentoBO.listarAgendamentos();
            return Response.ok(agendamentos).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar agendamentos").build();
        }
    }
   
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarAgendamentoPorId(@PathParam("id") int idAgendamento) {
        try {
            Agendamento agendamento = agendamentoBO.buscarAgendamentoPorId(idAgendamento);
            if (agendamento == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Agendamento não encontrado.").build();
            }
            return Response.ok(agendamento).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar agendamento.").build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deletarAgendamento(@PathParam("id") int idAgendamento) {
        try {
            agendamentoBO.removerAgendamento(idAgendamento);
            return Response.ok("Agendamento removido com sucesso").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
