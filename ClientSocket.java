import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.IOException;

public class ClientSocket {
    private final Socket socket; // Armazena o socket do cliente
    private final BufferedReader in; // objeto de entrada
    private final PrintWriter out; // objeto de saida

    // construtor
    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // receber a mensagem
        this.out = new PrintWriter(socket.getOutputStream(), true); // enviar a mensagem
    }

    // Método para retornar a mensagem
    public String getMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    // Método para saber se a mensagem foi enviado ou não
    public boolean sendMsg(String msg) {
        out.println(msg); // envia a mensagem
        return !out.checkError(); // se não houve erro, a mensagem foi enviada
    }

    // Método para retornar o endereço do socket do cliente
    public SocketAddress getRemoteSocketAdress() {
        return socket.getRemoteSocketAddress();
    }

    // Método para fechar os objetos abertos
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("Erro ao fechar socket: " + e.getMessage());
        }
    }

}