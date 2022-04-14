package app;

import com.client.Client;
import com.game.engine.GameLoop;
import com.utils.Util;
import server.Server;

import java.io.IOException;
import java.util.List;

public class App {

    private final GameLoop gameLoop;
    private final Client client;

    public App(String[] args) throws IOException {
        boolean boolRend = Util.checkArgsVerbose(args);
        int numberGames = Util.checkArgsGame(args);
        int numberPlayer = Util.checkArgsPlayer(args);
        String dnaFile = Util.checkArgsDNA(args);
        List<String> dnaList = Util.checkListArgsDNA(args);
        client = new Client("http://127.0.0.1:10101");
        if(dnaList != null){
            gameLoop = new GameLoop(numberGames,boolRend,dnaList);
        }
        else{
            gameLoop = new GameLoop(numberPlayer, numberGames, boolRend, dnaFile);
        }


    }

    public Client getClient() {
        return client;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public static void main(String[] args) throws IOException {
        App app = new App(args);
        Thread server = new Thread(() -> {
            try {
                Server.main(null);
            } catch (IOException e) {
                System.exit(1);
            }
        });
        server.start();

        app.getClient().setConnection();

        app.gameLoop.start();

        app.getClient().sendData(app.gameLoop.getStatsManager().sendData(app.gameLoop.getEngine()));
        app.getClient().unconnect();

        System.exit(0);
    }
}