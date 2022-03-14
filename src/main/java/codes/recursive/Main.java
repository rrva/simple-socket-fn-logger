package codes.recursive;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(Main.class);
        int port = args.length > 0 ? Integer.parseInt(args[0]) : Integer.parseInt( System.getProperty("port", "8080") );
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();

        logger.info("Listening on {}...", port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Server shutting down. Goodbye...");
            try {
                server.stop(1000);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }));

    }

}