package Ejemplo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase principal que ahora funciona como un servidor web para demostrar el Patrón Proxy.
 */
public class Demo {

    private static final YouTubeDownloader naiveDownloader = new YouTubeDownloader(new ThirdPartyYouTubeClass());
    private static final YouTubeDownloader smartDownloader = new YouTubeDownloader(new YouTubeCacheProxy());

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/test-directo", new TestHandler(naiveDownloader, "directo"));
        server.createContext("/test-proxy", new TestHandler(smartDownloader, "proxy"));
        server.createContext("/", new StaticFileHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port 8080");
    }

    static class TestHandler implements HttpHandler {
        private final YouTubeDownloader downloader;
        private final String type;

        TestHandler(YouTubeDownloader downloader, String type) {
            this.downloader = downloader;
            this.type = type;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Redirigir la salida estándar para capturar la salida de la prueba
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);

            long time = test(downloader);

            System.out.flush();
            System.setOut(old);

            String output = baos.toString();
            String response = "Prueba con " + type + ":\n" + output + "Tiempo total: " + time + "ms\n";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private long test(YouTubeDownloader downloader) {
            long startTime = System.currentTimeMillis();

            // User behavior in our app:
            downloader.renderPopularVideos();
            downloader.renderVideoPage("catzzzzzzzzz");
            downloader.renderPopularVideos();
            downloader.renderVideoPage("dancesvideoo");

            // Users might visit the same page quite often.
            downloader.renderVideoPage("catzzzzzzzzz");
            downloader.renderVideoPage("someothervid");

            long estimatedTime = System.currentTimeMillis() - startTime;
            System.out.print("Time elapsed: " + estimatedTime + "ms\n");
            return estimatedTime;
        }
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uriPath = exchange.getRequestURI().getPath();
            if (uriPath.equals("/")) {
                uriPath = "/index.html";
            }
            String filePath = "src/main/resources/static" + uriPath;
            Path path = Paths.get(filePath);

            if (Files.exists(path)) {
                exchange.sendResponseHeaders(200, Files.size(path));
                try (OutputStream os = exchange.getResponseBody()) {
                    Files.copy(path, os);
                }
            } else {
                String response = "404 (Not Found)\n";
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }
}