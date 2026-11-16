/* Nome: Erick Ribeiro Graciano */

package cliente;

import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import servidor.BitsDouble;

public class BitsDoubleClient {

    public static void main(String[] args) throws Exception {
        URL url = URI.create("http://127.0.0.1:9876/bitsdouble?wsdl").toURL();
        QName qname = new QName("http://servidor/", "BitsDoubleImplService");
        Service servico = Service.create(url, qname);
        BitsDouble bitsDouble = servico.getPort(BitsDouble.class);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um valor double: ");
        double valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));

        System.out.println("Double: " + valor);
        System.out.println("Bits (sinal | expoente | mantissa):");
        System.out.println(bitsDouble.obterBits(valor));

        scanner.close();
    }
}
