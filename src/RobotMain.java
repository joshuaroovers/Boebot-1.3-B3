import TI.BoeBot;
import TI.PinMode;

public class RobotMain {
    public static void main(String[] args) {
        Zoomer zoomer = new Zoomer(12, 14);
        while (true) {
            zoomer.update(12);
            zoomer.update(14);
            BoeBot.wait(1);
        }
    }
}
