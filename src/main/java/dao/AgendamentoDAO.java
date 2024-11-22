package dao;

import beans.Agendamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    private Connection connection;
    private UsuarioDAO usuarioDAO;

    public AgendamentoDAO(Connection connection) {
        this.connection = connection;
        this.usuarioDAO = new UsuarioDAO(connection); // Inicializando o UsuarioDAO
    }

    // Método para adicionar agendamento
    public void adicionarAgendamento(Agendamento agendamento) throws SQLException {
        // Busca o ID do usuário com base no email
        int idUsuario = usuarioDAO.buscarIdUsuarioPorEmail(agendamento.getEmailUsuario());

        if (idUsuario == -1) {
            throw new IllegalArgumentException("Usuário não encontrado com o email fornecido.");
        }

        // Agora, podemos adicionar o agendamento com o idUsuario
        String sql = "INSERT INTO agendamentos (data_hora, id_usuario) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(agendamento.getDataHora()));
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();
        }
    }

    // Método para listar agendamentos
    public List<Agendamento> listarAgendamentos() throws SQLException {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT * FROM agendamentos";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                agendamentos.add(new Agendamento(
                        rs.getInt("id_agendamento"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getInt("id_usuario")
                ));
            }
        }
        return agendamentos;
    }

    // Método para atualizar agendamento
    public void atualizarAgendamento(Agendamento agendamento) throws SQLException {
        String sql = "UPDATE agendamentos SET data_hora = ?, id_usuario = ? WHERE id_agendamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(agendamento.getDataHora()));
            stmt.setInt(2, agendamento.getIdUsuario());
            stmt.setInt(3, agendamento.getIdAgendamento());
            stmt.executeUpdate();
        }
    }

    // Método para remover agendamento
    public void removerAgendamento(int idAgendamento) throws SQLException {
        String sql = "DELETE FROM agendamentos WHERE id_agendamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAgendamento);
            stmt.executeUpdate();
        }
    }

    // Método para buscar agendamento por ID
    public Agendamento buscarAgendamentoPorId(int idAgendamento) throws SQLException {
        String sql = "SELECT * FROM agendamentos WHERE id_agendamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAgendamento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Agendamento(
                            rs.getInt("id_agendamento"),
                            rs.getTimestamp("data_hora").toLocalDateTime(),
                            rs.getInt("id_usuario")
                    );
                }
            }
        }
        return null;
    }
}
