package excecoes;

public class UsuarioException extends Exception {

	// Construtor com mensagem de erro
	public UsuarioException(String message) {
		super(message);
	}

	// Construtor com mensagem de erro e causa
	public UsuarioException(String message, Throwable cause) {
		super(message, cause);
	}
}
