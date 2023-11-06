import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) throws IOException {
        // Parse environment variables
        String portEnv = System.getenv("PORT");
        int port = 8080;
        if (portEnv != null) {
            try {
                port = Integer.parseInt(portEnv);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number provided in PORT environment variable. Using default port:" + port);
            }
        }

        String logEnv = System.getenv("LOG");
        boolean log = false;
        if (logEnv != null) {
            try {
                log = Boolean.parseBoolean(logEnv);
            } catch (Exception e) {
                System.err.println("Invalid log boolean provided in LOG environment variable. Using default log option:" + log);
            }
        }

        // Build http server
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/ping", new PingHandler(log));
        server.createContext("/query", new QueryHandler(log, new pad_oracle()));
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port " + port);
    }

    static class PingHandler implements HttpHandler {
        private final boolean log;
        public PingHandler(boolean log) {
            this.log = log;
        }
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (log) {
                System.out.println(LocalDateTime.now() + ": ping requested");
            }
            String response = "pong";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class QueryHandler implements HttpHandler {
        private final boolean log;
        private final pad_oracle p;
        public QueryHandler(boolean log, pad_oracle p) {
            this.log = log;
            this.p = p;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            

            URI uri = t.getRequestURI();
            String query = uri.getQuery();
            Map<String, String> parameters = queryToMap(query);

            String str1 = parameters.getOrDefault("str1", "");
            String str2 = parameters.getOrDefault("str2", "");
            String response = String.valueOf(p.doOracle(str1, str2));

            // Log
            if (log) {
                System.out.printf("%s: query requested: (%s, %s) = %s\n", LocalDateTime.now().toString(), str1, str2, response);
            }

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private Map<String, String> queryToMap(String query) {
            Map<String, String> result = new HashMap<>();
            if (query == null || query.isEmpty()) {
                return result;
            }

            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }
}
