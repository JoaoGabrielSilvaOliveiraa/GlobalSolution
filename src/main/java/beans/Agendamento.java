package beans;

import java.time.LocalDateTime;

public class Agendamento {
    private int idAgendamento;
    private LocalDateTime dataHora;
    private int idUsuario; // Relacionamento com o usuário

    // Construtor vazio
    public Agendamento() {}

    // Construtor com parâmetros
    public Agendamento(int idAgendamento, LocalDateTime dataHora, int idUsuario) {
        this.idAgendamento = idAgendamento;
        this.dataHora = dataHora;
        this.idUsuario = idUsuario;
    }

    // Getters e Setters
    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
