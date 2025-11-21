import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
// Somar o total e somar o count e fazer a divisão no final
class ServerWorker implements Runnable {
    Socket client_socket;
    int total = 0;
    int count = 0;
    ServerWorker(Socket socket) {
        this.client_socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
            PrintWriter out = new PrintWriter(client_socket.getOutputStream());

            String line;

            while ((line = in.readLine()) != null) {
                count++;
                total += Integer.parseInt(line);
                out.println(total);
                out.flush();
            }

            client_socket.shutdownOutput();
            client_socket.shutdownInput();
            client_socket.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
public class EchoServer {
    private int total = 0;
    private int count = 0;
    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(11511);

            while (true) {
                Socket socket = ss.accept();
                (new Thread(new ServerWorker(socket))).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

