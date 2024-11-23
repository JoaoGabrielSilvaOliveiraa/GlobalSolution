package bo;

import beans.Contato;
import dao.ContatoDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ContatoBO {
    private ContatoDAO contatoDAO;

    public ContatoBO(Connection connection) {
        this.contatoDAO = new ContatoDAO(connection);
    }
    
    public List<Contato>listarContatos() throws SQLException {
        return contatoDAO.listarContatos();

    }

    
    public void adicionarContato(Contato contato) throws SQLException {
        // Validar os dados do contato, por exemplo:
        if (contato.getEmail() == null || contato.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O email é obrigatório.");
        }
        if (contato.getTelefone() == null || contato.getTelefone().isEmpty()) {
            throw new IllegalArgumentException("O telefone é obrigatório.");
        }

        // Adicionar o contato no banco de dados
        contatoDAO.adicionarContato(contato);
    }
}
