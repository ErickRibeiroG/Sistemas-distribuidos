/* Nome: Erick Ribeiro Graciano */

package servidor;

import javax.jws.WebService;

@WebService(endpointInterface = "servidor.BitsDouble")
public class BitsDoubleImpl implements BitsDouble {

    @Override
    public String obterBits(double valor) {
        long bits = Double.doubleToRawLongBits(valor);
        StringBuilder resultado = new StringBuilder();

        for (int i = 63; i >= 0; i--) {
            long bit = (bits >> i) & 1;
            resultado.append(bit);

            if (i == 63 || i == 52) {
                resultado.append(" | ");
            }
        }

        return resultado.toString();
    }
}
