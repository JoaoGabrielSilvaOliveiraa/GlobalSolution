package beans;

public class Contato {
    private String email;
    private String telefone;
    private String comentario;

    // Getters e setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contato() {
		super();
	}

	public Contato(String email, String telefone, String comentario) {
		super();
		this.email = email;
		this.telefone = telefone;
		this.comentario = comentario;
	}

	public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
