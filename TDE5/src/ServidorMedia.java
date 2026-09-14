// Erick

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ServidorMedia {
    private static final int PORTA_PADRAO = 5000;
    private static final int QUANTIDADE_MINIMA = 2;
    private static final int QUANTIDADE_MAXIMA = 127;
    private static final int BYTES_POR_DOUBLE = Double.BYTES;

    public static void main(String[] args) {
        int porta;

        try {
            porta = obterPorta(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        try (ServerSocket servidor = new ServerSocket(porta)) {
            System.out.println("Servidor aguardando conexoes na porta " + porta + "...");

            while (true) {
                try (Socket cliente = servidor.accept()) {
                    System.out.println("Cliente conectado: "
                            + cliente.getInetAddress().getHostAddress());
                    atenderCliente(cliente);
                } catch (IOException | IllegalArgumentException e) {
                    System.err.println("Falha ao atender cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Nao foi possivel iniciar o servidor: " + e.getMessage());
        }
    }

    private static void atenderCliente(Socket cliente) throws IOException {
        InputStream entrada = cliente.getInputStream();
        OutputStream saida = cliente.getOutputStream();

        int quantidade = entrada.read();
        if (quantidade == -1) {
            throw new EOFException("a conexao terminou antes do envio da quantidade");
        }
        if (quantidade < QUANTIDADE_MINIMA || quantidade > QUANTIDADE_MAXIMA) {
            throw new IllegalArgumentException(
                    "quantidade invalida: " + quantidade + " (esperado: 2 a 127)");
        }

        byte[] bytesDosNumeros = new byte[quantidade * BYTES_POR_DOUBLE];
        DataInputStream entradaCompleta = new DataInputStream(entrada);
        entradaCompleta.readFully(bytesDosNumeros);

        ByteBuffer buffer = ByteBuffer.wrap(bytesDosNumeros).order(ByteOrder.BIG_ENDIAN);
        double soma = 0.0;
        for (int i = 0; i < quantidade; i++) {
            soma += buffer.getDouble();
        }

        double media = soma / quantidade;
        byte[] resposta = ByteBuffer.allocate(Double.BYTES)
                .order(ByteOrder.BIG_ENDIAN)
                .putDouble(media)
                .array();

        saida.write(resposta);
        saida.flush();
        System.out.println("Media calculada: " + media);
    }

    private static int obterPorta(String[] args) {
        if (args.length == 0) {
            return PORTA_PADRAO;
        }
        if (args.length > 1) {
            throw new IllegalArgumentException("Uso: java ServidorMedia [porta]");
        }

        try {
            int porta = Integer.parseInt(args[0]);
            if (porta < 1 || porta > 65535) {
                throw new IllegalArgumentException("A porta deve estar entre 1 e 65535.");
            }
            return porta;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A porta deve ser um numero inteiro.");
        }
    }
}
