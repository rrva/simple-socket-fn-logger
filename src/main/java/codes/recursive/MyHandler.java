package codes.recursive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange t) throws IOException {
        byte[] bytes = t.getRequestBody().readAllBytes();
        String arg = new String(bytes);
        try {
            JsonNode node = objectMapper.readTree(arg);
            logger.info("{}", node);
        } catch (JsonProcessingException e) {
            logger.info("{}", arg);
        }
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