import TI.BoeBot;
import TI.PinMode;

public class RobotMain {
    public static void main(String[] args) {
        Zoomer zoomer = new Zoomer(12, 14);
        EmergencyStop emergencyStop = new EmergencyStop(0);
        
        while (true) {

            //zoomer.update(12);
            zoomer.update(14);
            if(emergencyStop.check()){
                //check for emergency stop press, stop the wheels and break the loop
                MotorAansturen.stop();
                break;
            }
            BoeBot.wait(1);
        }
    }
}
