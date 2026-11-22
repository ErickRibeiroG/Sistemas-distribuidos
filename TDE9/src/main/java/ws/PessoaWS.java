/* Aluno: Erick Ribeiro Graciano */

package ws;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Path;
import model.Pessoa;

@Path("/pessoas")
public class PessoaWS implements IPessoaWS {

    private static final List<Pessoa> pessoas = new ArrayList<>();

    @Override
    public List<Pessoa> incluir(Pessoa pessoa) {
        synchronized (pessoas) {
            pessoas.add(pessoa);
            return new ArrayList<>(pessoas);
        }
    }
}
