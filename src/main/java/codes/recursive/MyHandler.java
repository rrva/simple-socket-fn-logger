package codes.recursive;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class MyHandler implements HttpHandler {
    Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public void handle(HttpExchange t) throws IOException {
        logger.info("{}", new String(t.getRequestBody().readAllBytes()));

        String response = "Thanks";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String dumpHeaders(HttpExchange t) {
        return t.getRequestHeaders().entrySet().stream().map((Function<Map.Entry<String, List<String>>, Object>) e -> String.format("%s: %s", e.getKey(), e.getValue().get(0))).collect(Collectors.toList()).toString();
    }
}