package codes.recursive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class MyHandler implements HttpHandler {
    Logger logger = LoggerFactory.getLogger(Main.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] bytes = exchange.getRequestBody().readAllBytes();
        try  {
            try {
                logger.info("{}", objectMapper.readTree(bytes));
            } catch (JsonProcessingException e) {
                logger.info("{}", new String(bytes));
            }
        } catch (Exception e) {
            logger.error("error: {}", e.getMessage(), e);
        } finally {
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        }
    }

    private String dumpHeaders(HttpExchange t) {
        return t.getRequestHeaders().entrySet().stream().map((Function<Map.Entry<String, List<String>>, Object>) e -> String.format("%s: %s", e.getKey(), e.getValue().get(0))).collect(Collectors.toList()).toString();
    }
}