import java.net.ServerSocket; //Biblioteca para criar um server socket
//import java.io.BufferedReader;
import java.io.IOException; //Biblioteca para tratamentos de erro de entrada e saida 

public class ChatServer {

    public static final int PORT = 12345; // numero da porta(está como publico para podermos utilizar no chat cliente)
    // private BufferedReader in; // Atributo para receber mensagem
    private ServerSocket serverSocket; /*
                                        * instanciar um socket em uma aplicação servidora que ficará escutando na porta
                                        * indicada
                                        */
    // Socket do servidor, serve como uma conexão ao cliente

    // Método para instanciar o serverSocket (Inicia o servidor - socket)
    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT); // Inicia o servidor com o numero da porta
        clientConnectLoop();
    }// Ao criar o metodo start, precisamos tratar um erro de entrada e saida por
     // try/catch, com a biblioteca IOException, vamos delegar esse tratamento
     // a hora de chamar o metodo star

    // Método para aguardar as conexões dos clientes
    private void clientConnectLoop() throws IOException {
        while (true) {// Executa infinitamente
            /*
             * // para se comunicar com o cliente, esse socket é utilizado
             * Socket clientSocket = serverSocket.accept(); // aguarda o client conectar,
             * após isso, cria-se um socket
             * // local para
             * // que o servidor possa se comunicar
             * System.out.println("Cliente " + clientSocket.getRemoteSocketAddress() +
             * " conectou."); // mostra que o
             * // cliente conectou
             */

            // como a classe ClientSocket espera um server por paramento, pode passar o
            // método accept, pois este retorna um socket
            // criar um objeto ClientSocket para fazer a comunicação
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());

            /*
             * // Método para receber mensagem, mesma forma do cliente
             * this.in = new BufferedReader(new
             * InputStreamReader(clientSocket.getInputStream()));
             * // clientSocket.getInputStream(); //Método para receber mensagem
             */

            /*
             * // Le a mensagem até que um \n seja recebido
             * String msg = in.readLine();
             */

            // Criando uma interface anonima e instanciando um objeto dessa classe, desse
            // modo,
            // Poderemos utilizar os atributos desse método

            // Thread para o cliente poder mandar mais de uma mensagem
            // Método com uma unica linha
            // Método anonimo como run que pode chamar outro método para passar um
            // parametro, o que não é possivel no método run
            new Thread(() -> clientMessageLoop(clientSocket)).start();

            // System.out.println("Mensagem recebida do cliente: " + msg);
        }
    }

    // Método para loop de mensagens
    public void clientMessageLoop(ClientSocket clientSocket) {
        String msg;
        // garante que o socket seja fechado
        try {
            while ((msg = clientSocket.getMessage()) != null) {
                // Caso a mensagem for diferente de sair, o cliente continua mandando mensagem
                if (!"sair".equalsIgnoreCase(msg)) {
                    System.out.println("Mensagem recebida do cliente: " + msg + " - do endereço: "
                            + clientSocket.getRemoteSocketAdress());
                } else {
                    return;
                }
            }
        } finally {
            clientSocket.close();
        }
    }

    public static void main(String[] args) {
        // Tratamento para exceção de erro de entrada e saída
        try {
            ChatServer server = new ChatServer(); // Instanciando o servidor
            server.start(); // Iniciando o servidor
        } catch (IOException e) {
            System.out.print("Erro ao iniciar o servidor" + e.getMessage()); // mensagem de erro
        }
    }
}
