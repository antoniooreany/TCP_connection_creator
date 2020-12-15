import java.net.*;
import java.io.*;

/**
 * This program demonstrates a client socket application that connects to
 * a web server and send a HTTP HEAD request.
 *
 * @author www.codejava.net
 */
public class HttpClient {
    private static String response = "\r\n";
    private static String method;
    private static final int PORT = 80;

    public static void ConnectTCP(String context) {
        URL url;
        try {
            url = new URL(context);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return;
        }
        String hostname = url.getHost();
        try (Socket socket = new Socket(hostname, PORT)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            method = "HEAD ";
            writer.println(method + " " + url.getPath() + " HTTP/1.1");        // TODO Create Label with the current Request
            writer.println("Host: " + hostname);
            writer.println("User-Agent: Simple Http Client");
            writer.println("Accept: text/html");
            writer.println("Accept-Language: en-US");
            writer.println("Connection: close");
            writer.println();
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                response += line + "\r\n";
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
    public static String getResponse() {
        return response;
    }
    public static String getMethod() {
        return method;
    }

}