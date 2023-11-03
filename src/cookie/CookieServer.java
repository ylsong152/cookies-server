package cookie;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class CookieServer {
    
    public static final int PORT = 5000;

    public static List<String> getRandomCookie(int number) throws Exception {
        FileReader cookieFileReader = new FileReader("fortunecookie.txt");
        BufferedReader cookieReader = new BufferedReader(cookieFileReader);

        List<String> cookies = new ArrayList<>();
        cookies = cookieReader.lines()
        .collect(Collectors.toList());

        Collections.shuffle(cookies);
        cookieReader.close();
        return cookies.subList(0, number);
    }
    public static void main(String[] args) throws Exception {
        
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("Server created");

        while(true) {
            System.out.printf("Waiting for client connection from: %d\n", PORT);   
            Socket client = ss.accept();

            InputStream is = client.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            OutputStream os = client.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            String line = br.readLine();
            System.out.printf("Client: %s\n", line);
            if (line != null) {
                String[] words = line.split(" ");

                if (!words[0].equals("cookie")) {
                    bw.write("Please type 'cookie' followed by a desired number");
                    System.out.println("Did not type cookie");
                } else if (words.length < 2) {
                    bw.write("Please provide a number after 'cookie'");
                    System.out.println("Did not specify number"); 
                } 
                else {
                    List<String> listOfCookies = getRandomCookie(Integer.parseInt(words[1]));
                    for (String cookie : listOfCookies) {
                        System.out.printf("Sent: %s\n", cookie);
                        bw.write(cookie);
                        bw.newLine();
                    }
                }     
            bw.flush();
            is.close();
            os.close();    
            }
            System.out.printf("Got a connection for %d\n", PORT);
        }

    }
}
