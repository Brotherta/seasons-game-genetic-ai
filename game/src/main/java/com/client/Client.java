package com.client;

import com.utils.Util;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Class Client. Use to send to the server the data of the game played.
 */
public class Client {
    private Socket connection;
    private final Object waitingDisconnection = new Object();
    private final Object waitingConnection = new Object();
    private final Object waitingSending = new Object();

    private final Logger logger;
    private final static String LOG_FILE = "client.log";
    private final static String TYPE_LOG = "client";
    private final static String DIRECTORY_LOGS = "logs/";

    public Client(String urlServer) throws IOException {
        if (!Files.exists(Path.of(DIRECTORY_LOGS))) {
            Files.createDirectories(Path.of(DIRECTORY_LOGS));
        }
        FileHandler handler = new FileHandler(DIRECTORY_LOGS + LOG_FILE, true);
        logger = Logger.getLogger(TYPE_LOG);
        logger.addHandler(handler);

        try {
            connection = IO.socket(urlServer);
            connection.on(Util.CONNECT, objects -> {
                logger.info("connected to the server");
                synchronized (waitingConnection) {
                    waitingConnection.notifyAll();
                }
            });

            connection.on(Util.DISCONNECT, objects -> {
                logger.info("disconnected from the server");
                connection.off(Util.DISCONNECT);
                connection.disconnect();
                connection.close();

                synchronized (waitingDisconnection) {
                    waitingDisconnection.notifyAll();
                }
            });

            connection.on("dataReceived", objects -> {
                synchronized (waitingSending) {
                    waitingSending.notifyAll();
                }
            });

        } catch (URISyntaxException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void sendData(JSONArray stats) {
        logger.info("Sending data to the server");
        connection.emit("sendingStats", stats);
        synchronized (waitingSending) {
            try {
                waitingSending.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Try to connect to the server
     */
    public void setConnection() {
        connection.connect();
        synchronized (waitingConnection) {
            try {
                waitingConnection.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Notify to the server that the job is done. The client is disconnected.
     */
    public void unconnect() {
        connection.emit(Util.DISCONNECT, (Object) null);
        synchronized (waitingDisconnection) {
            try {
                waitingDisconnection.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
