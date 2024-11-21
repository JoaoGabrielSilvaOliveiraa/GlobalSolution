package dao;

import beans.Agendamento;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    private Connection connection;

    public AgendamentoDAO(Connection connection) {
        this.connection = connection;
    }

    public void adicionarAgendamento(Agendamento agendamento) throws SQLException {
        String sql = "INSERT INTO agendamentos (data_hora, id_usuario) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(agendamento.getDataHora()));
            stmt.setInt(2, agendamento.getIdUsuario());
            stmt.executeUpdate();
        }
    }

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

    public void atualizarAgendamento(Agendamento agendamento) throws SQLException {
        String sql = "UPDATE agendamentos SET data_hora = ?, id_usuario = ? WHERE id_agendamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(agendamento.getDataHora()));
            stmt.setInt(2, agendamento.getIdUsuario());
            stmt.setInt(3, agendamento.getIdAgendamento());
            stmt.executeUpdate();
        }
    }

    public void removerAgendamento(int idAgendamento) throws SQLException {
        String sql = "DELETE FROM agendamentos WHERE id_agendamento = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAgendamento);
            stmt.executeUpdate();
        }
    }

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
