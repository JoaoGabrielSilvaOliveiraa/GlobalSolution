package bo;

import beans.Agendamento;
import beans.Usuario;
import dao.AgendamentoDAO;
import dao.UsuarioDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoBO {

    private AgendamentoDAO agendamentoDAO;
    private UsuarioDAO usuarioDAO;

    // Construtor com a Connection passada manualmente
    public AgendamentoBO(Connection connection) {
        this.agendamentoDAO = new AgendamentoDAO(connection);
        this.usuarioDAO = new UsuarioDAO(connection);
    }

    // Método para adicionar agendamento baseado no email do usuário
    public void adicionarAgendamento(String email, Agendamento agendamento) throws SQLException {
        if (agendamento.getDataHora() == null || agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data e hora do agendamento devem ser futuras.");
        }

        // Busca o usuário com o email fornecido
        Usuario usuario = usuarioDAO.buscarUsuarioPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado com o email fornecido.");
        }

        // Associa o idUsuario ao agendamento
        agendamento.setIdUsuario(usuario.getIdCadastro());
        agendamento.setEmailUsuario(usuario.getEmail()); // Associando o email do usuário ao agendamento
        
        // Salva o agendamento com o idUsuario
        agendamentoDAO.adicionarAgendamento(agendamento);
    }


    public List<Agendamento> listarAgendamentos() throws SQLException {
        return agendamentoDAO.listarAgendamentos();
    }

    public void atualizarAgendamento(String email, Agendamento agendamento) throws SQLException {
        if (agendamento.getDataHora() == null || agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data e hora do agendamento devem ser futuras.");
        }

        // Busca o usuário com o email fornecido
        Usuario usuario = usuarioDAO.buscarUsuarioPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado com o email fornecido.");
        }

        // Associa o idUsuario ao agendamento
        agendamento.setIdUsuario(usuario.getIdCadastro());
        
        // Atualiza o agendamento com o idUsuario
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
