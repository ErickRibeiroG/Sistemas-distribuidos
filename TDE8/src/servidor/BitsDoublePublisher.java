/* Nome: Erick Ribeiro Graciano */

package servidor;

import javax.xml.ws.Endpoint;

public class BitsDoublePublisher {

    public static void main(String[] args) {
        String endereco = "http://127.0.0.1:9876/bitsdouble";
        Endpoint.publish(endereco, new BitsDoubleImpl());
        System.out.println("Servidor publicado em " + endereco + "?wsdl");
    }
}
