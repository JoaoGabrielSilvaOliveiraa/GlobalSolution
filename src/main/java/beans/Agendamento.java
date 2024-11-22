package beans;

import java.time.LocalDateTime;

public class Agendamento {
    private int idAgendamento;
    private LocalDateTime dataHora;
    private int idUsuario;
    private String emailUsuario;  // Campo para o email do usuário

    // Construtor padrão
    public Agendamento() {}

    // Construtor com data e email
    public Agendamento(LocalDateTime dataHora, String emailUsuario) {
        this.dataHora = dataHora;
        this.emailUsuario = emailUsuario;
    }

    // Construtor completo com idAgendamento, dataHora, idUsuario
    public Agendamento(int idAgendamento, LocalDateTime dataHora, int idUsuario) {
        this.idAgendamento = idAgendamento;
        this.dataHora = dataHora;
        this.idUsuario = idUsuario;
    }

    // Getters e setters
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

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
