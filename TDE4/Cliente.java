// Aluno: Erick Ribeiro Graciano
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Cliente TCP/IP que se conecta ao servidor na porta 12345.
 * Permite ao usuário digitar uma quantidade indefinida de números inteiros
 * e envia-os ao servidor para calcular soma, média e desvio padrão.
 */
public class Cliente {
    private static final String HOST = "127.0.0.1";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("            CLIENTE TCP - CÁLCULO ESTATÍSTICO     ");
        System.out.println("==================================================");
        System.out.println("Tentando conectar ao servidor em " + HOST + ":" + PORTA + "...");

        try (
            Socket socket = new Socket(HOST, PORTA);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("[+] Conexão estabelecida com sucesso com o servidor!");
            System.out.println("\nInstruções:");
            System.out.println(" - Digite um número inteiro por linha (ou múltiplos separados por espaço/vírgula).");
            System.out.println(" - Para finalizar o envio e calcular, pressione ENTER em uma linha vazia ou digite 'FIM'.");
            System.out.println("--------------------------------------------------");

            while (true) {
                System.out.print("Digite um número (ou ENTER para finalizar): ");
                String entrada = scanner.nextLine();

                // Envia a linha para o servidor
                out.println(entrada);

                // Se a entrada for vazia ou "FIM", sinaliza o encerramento da transmissão de dados pelo cliente
                if (entrada.trim().isEmpty() || entrada.trim().equalsIgnoreCase("FIM")) {
                    break;
                }
            }

            System.out.println("\n[>] Dados enviados! Aguardando resposta do servidor...\n");

            // Recebe e exibe a resposta do servidor
            String respostaLinha;
            while ((respostaLinha = in.readLine()) != null) {
                if (respostaLinha.equalsIgnoreCase("FIM_RESPOSTA")) {
                    break;
                }
                System.out.println(respostaLinha);
            }

            System.out.println("\n[+] Processamento concluído. Conexão encerrada.");

        } catch (IOException e) {
            System.err.println("[-] Erro ao conectar ou comunicar com o servidor: " + e.getMessage());
            System.err.println("    Certifique-se de que o servidor (Servidor.java) esteja em execução.");
        }
    }
}
