import TI.BoeBot;
import head.Controller;

public class RobotMain {
    static Controller engine = new head.Controller();


    public static void main(String[] args) {
        engine.init();
        while (true) {
            while (!engine.getRunning()) {
                if (!BoeBot.digitalRead(1))
                    engine.startUp();
//                state = !state;
//                BoeBot.digitalWrite(5, state);
//                BoeBot.digitalWrite(4, !state);
//                BoeBot.wait(400);
            }
            if (engine.emergencyStop.check())
                engine.setRunning(false);
            engine.update();
            BoeBot.wait(1);
        }

    }
}
