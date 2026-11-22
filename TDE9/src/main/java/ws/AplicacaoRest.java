/* Aluno: Erick Ribeiro Graciano */

package ws;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/rest")
public class AplicacaoRest extends ResourceConfig {

    public AplicacaoRest() {
        register(PessoaWS.class);
        register(JacksonFeature.class);
    }
}
