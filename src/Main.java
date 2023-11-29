import TI.BoeBot;

public class Main {
    static RobotMain robotMain = new RobotMain();

    public static void main(String[] args) {
        while (true) {
            while (!robotMain.getRunning()) {
                if (BoeBot.digitalRead(1))
                    robotMain.startUp();
            }
            robotMain.update();
            BoeBot.wait(1);
        }
    }
}
