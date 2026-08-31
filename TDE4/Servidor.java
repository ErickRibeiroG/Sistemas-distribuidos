// Aluno: Erick Ribeiro Graciano
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Servidor TCP/IP que escuta na porta 12345.
 * Recebe uma sequência de números inteiros enviada pelo cliente,
 * calcula a soma, a média e o desvio padrão dos números e envia o resultado de volta.
 */
public class Servidor {
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" Servidor TCP iniciado na porta " + PORTA);
        System.out.println(" Aguardando conexões de clientes...");
        System.out.println("==================================================");

        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            while (true) {
                // Aceita conexão de um novo cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("\n[+] Novo cliente conectado: " + clientSocket.getRemoteSocketAddress());

                // Trata a requisição em uma thread separada para suportar múltiplos clientes
                new Thread(() -> tratarCliente(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("[-] Erro no servidor: " + e.getMessage());
        }
    }

    private static void tratarCliente(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            List<Integer> numeros = new ArrayList<>();
            String linha;

            // Protocolo: Lê linhas enviadas pelo cliente até encontrar uma linha vazia "" ou "FIM"
            while ((linha = in.readLine()) != null) {
                linha = linha.trim();

                // Marca de término do envio do cliente
                if (linha.isEmpty() || linha.equalsIgnoreCase("FIM")) {
                    break;
                }

                // Processa a linha, permitindo múltiplos inteiros separados por espaço ou vírgula
                String[] partes = linha.split("[,\\s]+");
                for (String parte : partes) {
                    if (parte.isEmpty()) continue;
                    try {
                        int num = Integer.parseInt(parte);
                        numeros.add(num);
                    } catch (NumberFormatException e) {
                        out.println("ERRO: Valor inválido ignorado: '" + parte + "'");
                    }
                }
            }

            // Se nenhum número foi recebido
            if (numeros.isEmpty()) {
                out.println("ERRO: Nenhum número inteiro válido foi fornecido.");
                out.println("FIM_RESPOSTA");
                socket.close();
                System.out.println("[-] Cliente desconectado (nenhum número enviado).");
                return;
            }

            // Cálculos estatísticos
            int count = numeros.size();
            double soma = 0;
            for (int num : numeros) {
                soma += num;
            }
            double media = soma / count;

            double somaQuadradosDiferencas = 0;
            for (int num : numeros) {
                double diff = num - media;
                somaQuadradosDiferencas += diff * diff;
            }

            double desvioPadraoPopulacional = Math.sqrt(somaQuadradosDiferencas / count);
            double desvioPadraoAmostral = (count > 1) ? Math.sqrt(somaQuadradosDiferencas / (count - 1)) : 0.0;

            // Log no servidor
            System.out.println("[>] Processados " + count + " números do cliente " + socket.getRemoteSocketAddress());
            System.out.println("    Números recebidos: " + numeros);
            System.out.println("    Soma: " + soma);
            System.out.println("    Média: " + media);
            System.out.println("    Desvio Padrão (Populacional): " + desvioPadraoPopulacional);
            System.out.println("    Desvio Padrão (Amostral): " + desvioPadraoAmostral);

            // Resposta formatada para o cliente
            out.println("==================================================");
            out.println("         RESULTADOS ESTATÍSTICOS DA SEQUÊNCIA     ");
            out.println("==================================================");
            out.println("Quantidade de números recebidos : " + count);
            out.println("Números: " + numeros);
            out.println("--------------------------------------------------");
            out.println("Soma                            : " + String.format("%.2f", soma));
            out.println("Média                           : " + String.format("%.4f", media));
            out.println("Desvio Padrão (Populacional)    : " + String.format("%.4f", desvioPadraoPopulacional));
            out.println("Desvio Padrão (Amostral)        : " + String.format("%.4f", desvioPadraoAmostral));
            out.println("==================================================");
            out.println("FIM_RESPOSTA");

            socket.close();
            System.out.println("[-] Atendimento concluído. Conexão com " + socket.getRemoteSocketAddress() + " encerrada.");

        } catch (IOException e) {
            System.err.println("[-] Erro na comunicação com cliente: " + e.getMessage());
        }
    }
}
