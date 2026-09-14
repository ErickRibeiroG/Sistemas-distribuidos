// Erick Ribeiro Graciano

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClienteMedia {
    private static final String SERVIDOR_PADRAO = "localhost";
    private static final int PORTA_PADRAO = 5000;
    private static final int QUANTIDADE_MINIMA = 2;
    private static final int QUANTIDADE_MAXIMA = 127;

    public static void main(String[] args) {
        String servidor;
        int porta;

        try {
            servidor = obterServidor(args);
            porta = obterPorta(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        try (Scanner teclado = new Scanner(System.in)) {
            int quantidade = lerQuantidade(teclado);
            double[] numeros = lerNumeros(teclado, quantidade);
            byte[] requisicao = montarRequisicao(numeros);

            try (Socket socket = new Socket(servidor, porta)) {
                OutputStream saida = socket.getOutputStream();
                InputStream entrada = socket.getInputStream();

                saida.write(requisicao);
                saida.flush();

                byte[] resposta = new byte[Double.BYTES];
                new DataInputStream(entrada).readFully(resposta);
                double media = ByteBuffer.wrap(resposta)
                        .order(ByteOrder.BIG_ENDIAN)
                        .getDouble();

                System.out.println("Media calculada pelo servidor: " + media);
            }
        } catch (IOException e) {
            System.err.println("Erro de comunicacao com o servidor: " + e.getMessage());
        }
    }

    private static int lerQuantidade(Scanner teclado) {
        while (true) {
            System.out.print("Quantos numeros deseja transmitir (2 a 127)? ");
            try {
                int quantidade = teclado.nextInt();
                if (quantidade >= QUANTIDADE_MINIMA && quantidade <= QUANTIDADE_MAXIMA) {
                    return quantidade;
                }
                System.out.println("Informe uma quantidade entre 2 e 127.");
            } catch (InputMismatchException e) {
                System.out.println("Informe um numero inteiro valido.");
                teclado.next();
            }
        }
    }

    private static double[] lerNumeros(Scanner teclado, int quantidade) {
        double[] numeros = new double[quantidade];

        for (int i = 0; i < quantidade; i++) {
            while (true) {
                System.out.print("Digite o " + (i + 1) + "o numero: ");
                try {
                    numeros[i] = teclado.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Informe um numero double valido.");
                    teclado.next();
                }
            }
        }
        return numeros;
    }

    private static byte[] montarRequisicao(double[] numeros) {
        ByteBuffer buffer = ByteBuffer.allocate(1 + numeros.length * Double.BYTES)
                .order(ByteOrder.BIG_ENDIAN);
        buffer.put((byte) numeros.length);

        for (double numero : numeros) {
            buffer.putDouble(numero);
        }
        return buffer.array();
    }

    private static String obterServidor(String[] args) {
        if (args.length > 2) {
            throw new IllegalArgumentException("Uso: java ClienteMedia [servidor] [porta]");
        }
        return args.length >= 1 ? args[0] : SERVIDOR_PADRAO;
    }

    private static int obterPorta(String[] args) {
        if (args.length < 2) {
            return PORTA_PADRAO;
        }

        try {
            int porta = Integer.parseInt(args[1]);
            if (porta < 1 || porta > 65535) {
                throw new IllegalArgumentException("A porta deve estar entre 1 e 65535.");
            }
            return porta;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A porta deve ser um numero inteiro.");
        }
    }
}
