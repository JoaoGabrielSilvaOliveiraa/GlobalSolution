package bo;

import beans.Agendamento;
import dao.AgendamentoDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoBO {

    private AgendamentoDAO agendamentoDAO;

    public AgendamentoBO(Connection connection) {
        this.agendamentoDAO = new AgendamentoDAO(connection);
    }

    public void adicionarAgendamento(Agendamento agendamento) throws SQLException {
        if (agendamento.getDataHora() == null || agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data e hora do agendamento devem ser futuras.");
        }
        agendamentoDAO.adicionarAgendamento(agendamento);
    }

    public List<Agendamento> listarAgendamentos() throws SQLException {
        return agendamentoDAO.listarAgendamentos();
    }

    public void atualizarAgendamento(Agendamento agendamento) throws SQLException {
        if (agendamento.getDataHora() == null || agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data e hora do agendamento devem ser futuras.");
        }
        agendamentoDAO.atualizarAgendamento(agendamento);
    }

    public void removerAgendamento(int idAgendamento) throws SQLException {
        if (idAgendamento <= 0) {
            throw new IllegalArgumentException("ID de agendamento inválido.");
        }
        agendamentoDAO.removerAgendamento(idAgendamento);
    }

    public Agendamento buscarAgendamentoPorId(int idAgendamento) throws SQLException {
        if (idAgendamento <= 0) {
            throw new IllegalArgumentException("ID de agendamento inválido.");
        }
        return agendamentoDAO.buscarAgendamentoPorId(idAgendamento);
    }
    
    

}
