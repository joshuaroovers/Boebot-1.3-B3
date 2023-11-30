import TI.BoeBot;

public class RobotMain {
    static Engine engine = new Engine();
    static boolean state = true;

    public static void main(String[] args) {
        while (true) {
            while (!engine.getRunning()) {
                if (!BoeBot.digitalRead(1))
                    engine.startUp();
                state = !state;
                BoeBot.digitalWrite(5, state);
                BoeBot.digitalWrite(4, !state);
                BoeBot.wait(400);
            }
            if (engine.emergencyStop.check())
                engine.setRunning(false);
            engine.update();
            BoeBot.wait(1);
        }
    }
}
