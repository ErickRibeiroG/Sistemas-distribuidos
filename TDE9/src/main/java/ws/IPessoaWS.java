/* Aluno: Erick Ribeiro Graciano */

package ws;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Pessoa;

public interface IPessoaWS {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Pessoa> incluir(Pessoa pessoa);
}
