package app;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.utils.Util;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private int randomNegativeNumber;
    private int randomGreaterThanFourNumber;
    private int randomCleanPlayerAmountNumber;

    private String randomNegativeNumberString;
    private String randomGreaterThanFourNumberString;
    private String randomCleanPlayerAmountNumberString;

    AppTest() {
        this.randomNegativeNumber = -Util.getRandomInt();
        this.randomGreaterThanFourNumber = Util.getRandomInt() + 4;
        this.randomCleanPlayerAmountNumber = Util.getRandomInt(3) + 2;

        this.randomNegativeNumberString = String.valueOf(randomNegativeNumber);
        this.randomGreaterThanFourNumberString = String.valueOf(randomGreaterThanFourNumber);
        this.randomCleanPlayerAmountNumberString = String.valueOf(randomCleanPlayerAmountNumber);
    }

    @Test
    void testPlayerArgs() throws IOException {
        App a = new App(new String[] { Util.P_ARG, randomCleanPlayerAmountNumberString });
        assertEquals(randomCleanPlayerAmountNumber, a.getGameLoop().getEngine().getPlayersCentralManager().getAmountOfPlayers());

        assertThrows(IllegalArgumentException.class, () -> new App(new String[] { Util.P_ARG, "0"}));
        assertThrows(IllegalArgumentException.class, () -> new App(new String[] { Util.P_ARG, "1"}));
        assertThrows(IllegalArgumentException.class, () -> new App(new String[] { Util.P_ARG, randomGreaterThanFourNumberString}));
        assertThrows(IllegalArgumentException.class, () -> new App(new String[] { Util.P_ARG, randomNegativeNumberString }));
    }

    @Test
    void testVerbose() throws IOException {
        App app = new App(new String[] { Util.V_ARG });
        assertTrue(app.getGameLoop().isBoolRend());
        app = new App(new String[] { "" });
        assertFalse(app.getGameLoop().isBoolRend());
    }

    @Test
    void testGameAmount() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> new App(new String[] { Util.G_ARG, "0"}));
        assertThrows(IllegalArgumentException.class, () -> new App(new String[] { Util.G_ARG, randomNegativeNumberString }));

        int gameAmount = 1 + Util.getRandomInt(999);
        App app = new App(new String[] { Util.G_ARG, String.valueOf(gameAmount) });
        assertEquals(gameAmount, app.getGameLoop().getNbGames());
    }

    @Test
    void main() {
        try {
            int status = SystemLambda.catchSystemExit(() -> App.main(new String[]{""}));
            assertEquals(0, status);
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }
}