package head;

import TI.BoeBot;
import TI.PinMode;
import actuators.Motor;

import java.util.ArrayList;

public class Controller implements Updateable {

    public Boolean isRunning;
    private Zoomer zoomer;

    public EmergencyStop emergencyStop;
    private Motor leftMotor;
    private Motor rightMotor;
    private ArrayList<Updateable> updatables;
    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;

        this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop(0);
    };

    public void startUp() {
        updatables  = new ArrayList<>();

        updatables.add(leftMotor = new Motor(12));
        updatables.add(rightMotor = new Motor(13));

        this.isRunning = true;
    }

    public void update() {
        while (true) {
            if (this.emergencyStop.check()){
                MotorAansturen.stop();
                this.isRunning = false;
                return;
            }
            for (Updateable updatable : updatables)
                updatable.update();
            BoeBot.wait(1);
        }


        //zoomer.update();
    }

    public Boolean getRunning() {
        return isRunning;
    }

    public Zoomer getZoomer() {
        return zoomer;
    }

    public void setRunning(boolean b) {
        this.isRunning = b;
    }
}


//    public static void main(String[] args) {
//        head.Zoomer zoomer = new head.Zoomer(12, 14);
//        head.EmergencyStop emergencyStop = new head.EmergencyStop(0);
//
//        while (true) {
//
//            //zoomer.update(12);
//            zoomer.update(14);
//            if(emergencyStop.check()){
//                //check for emergency stop press, stop the wheels and break the loop
//                head.MotorAansturen.stop();
//                break;
//            }
//            BoeBot.wait(1);
//        }