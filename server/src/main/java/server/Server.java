package server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import stats.PlayerStats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * The server will collect the data from the games played. It will be useful to analyse the behavior of the
 * different AI implemented.
 */
public class Server {

    private final SocketIOServer server;
    private final Object waitingConnection = new Object();

    private final Logger logger;
    private static final String LOG_FILE = "server.log";
    private static final String TYPE_LOG = "server";
    private static final String JSON_FILE = "server_statistics.json";
    private static final String DIRECTORY_LOGS = "logs/";

    public Server(Configuration config) throws IOException {
        if (!Files.exists(Path.of(DIRECTORY_LOGS))) {
            Files.createDirectories(Path.of(DIRECTORY_LOGS));
        }
        FileHandler handler = new FileHandler(DIRECTORY_LOGS + LOG_FILE, true);
        logger = Logger.getLogger(TYPE_LOG);
        logger.addHandler(handler);

        server = new SocketIOServer(config);
        server.addConnectListener(socketIOClient ->
                logger.info("Server: connection of " + socketIOClient.getRemoteAddress())
        );

        server.addDisconnectListener(socketIOClient -> {
            logger.info("client disconnected, server stopping.");
            synchronized (waitingConnection) {
                waitingConnection.notifyAll();
            }
        });

        server.addEventListener("sendingStats", PlayerStats[].class, (socketIOClient, stats, ackRequest) -> {
            logger.info("Data received from client.");
            for (PlayerStats stat : stats) {
                logger.info(stat.toString());
                StatsJSONFileManager sjfm = new StatsJSONFileManager(JSON_FILE);
                sjfm.updatePlayerStatsToFile(stat);
                sjfm.saveGlobalJson();
            }
            socketIOClient.sendEvent("dataReceived");
        });
    }

    /**
     * Start the server and wait for connection to be closed. When the client end his job, the server will be closes.
     */
    public void start() {
        server.start();

        logger.info("waiting for connection");
        synchronized (waitingConnection) {
            try {
                waitingConnection.wait();
            } catch (InterruptedException e) {
                logger.severe("Error while trying to connect");
                Thread.currentThread().interrupt();
            }
        }
        logger.info("server stopping");
        server.stop();
    }

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);

        Server server = new Server(config);
        server.start();
    }
}
