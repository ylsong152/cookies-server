package cookie;

import java.io.*;
import java.net.Socket;

public class CookieClient {
    public static void main(String[] args) throws Exception {
        System.out.println("Attempting to connect to localhost @ 5000");
        Socket socket = new Socket("localhost", 5000);

        System.out.println("Connected to port 5000");
        
        OutputStream os = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        Console cons = System.console();
        String line = cons.readLine("Please enter a message: ");

        bw.write(line + "\n");
        bw.flush();

        while((line = br.readLine()) != null) {
            System.out.printf("Server response: %s\n", line);
        }

        // socket.close();
        System.out.println("Socket closed");
    }
}
