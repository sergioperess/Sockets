import java.net.Socket;
//import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class ChatClient {
    private ClientSocket clientSocket;// Socket do cliente - para conectar ao servidor(socket do servidor)
    private static final String SERVER_ADRESS = "127.0.0.1"; // Configuração do endereço - endereço do IP local
    private Scanner scanner;

    public ChatClient() {
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException { // indica que pode havar um erro do tipo IOException(entrada e saida)
        clientSocket = new ClientSocket(new Socket(SERVER_ADRESS, ChatServer.PORT)); /*
                                                                                      * Iniciando o cliente com o
                                                                                      * endereço e o numero da
                                                                                      * porta
                                                                                      */

        /*
         * // Buffer Cria um espaço na memoria pre reservado para não ficar alocando e
         * // desalocando uma variável
         * // Utilizado para enviar a string com quebra de linha, para n utilizar o \n
         * this.out = new BufferedWriter(new
         * OutputStreamWriter(clientSocket.getOutputStream()));
         * // Objeto utilizado para enviar strings ao inves de bytes para o servidor
         * // new OutputStreamWriter(clientSocket.getOutputStream());
         * // clientSocket.getOutputStream();//objeto utilizado para enviar mensagens
         * para
         * // o servidor bytes(fluxo de dados)
         */

        System.out.println("Cliente conectado ao server" + SERVER_ADRESS + ":" + ChatServer.PORT);
        messageLoop();
    }/*
      * nesse caso, o cliente pode tentar conectar ao servidor, mas o endereço pode
      * estar errado ou a porta estar fechada,
      * porque a aplicação servidora não está em execução, dessa forma, será gerado
      * um erro de IOException que será tratado na main
      */

    // Método que ficara em um loop que enviará mensagens para o servidor até
    // finalizar
    // Nesse caso, o cliente fica conectado ate digitar sair
    private void messageLoop() throws IOException {
        String msg;
        do {

            System.out.print("Digite uma mensagem (sair para sair): ");
            msg = scanner.nextLine();
            clientSocket.sendMsg(msg);
            // out.write(msg); // enviar a mensagem
            // out.newLine(); // pular linha
            // out.flush(); // confirma o envio da mensage
            // para enviar uma mensagem com a quebra de linha utilizar 'out.println(msg)'

        } while (!msg.equalsIgnoreCase("sair"));// usado para n ter diferença de letra mai e minusc

    }

    public static void main(String[] args) {
        // tratamento de exceção para o cliente
        try {
            ChatClient client = new ChatClient();
            client.start();
        } catch (Exception e) {
            System.out.println("erro ao iniciar o cliente" + e.getMessage()); // menssagem de erro
        }

    }
}
