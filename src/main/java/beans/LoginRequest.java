package beans;

//Decidi criar essa classe DTO para facilitar o desenvolvimento da API
//Assim fica mais facil pra mim puxar o email e senha no Resource, através do LoginRequest
//Também usei por motivos pessoais, quis explorar um pouco sobre DTO, e gostei de usar, além de ser funcional, facilita muito.

public class LoginRequest {
    private String email;
    private String senha;

    // Getters e setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
