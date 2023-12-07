package head;

import Sensors.Ultrasonic;
import TI.BoeBot;
import TI.PinMode;
import sensors.UltraSonicCallback;
import sun.management.Sensor;

import java.util.ArrayList;

public class Controller implements Updateable {

    public Boolean isRunning;
    private Zoomer zoomer;
    private Sensors.Ultrasonic ultrasonic;
    private ArrayList<Updateable> updatables;

    public EmergencyStop emergencyStop;
    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;

        updatables = new ArrayList<>();
        updatables.add(this.zoomer = new Zoomer(2));
        updatables.add(this.ultrasonic = new Ultrasonic(10,11, this::onUltraSone));

        this.emergencyStop = new EmergencyStop(0);
    };

    public void startUp() {
        this.isRunning = true;
    }

    public void update() {
        if (this.emergencyStop.check()){
            MotorAansturen.stop();
            this.isRunning = false;
            return;
        }

        for (Updateable updateable : updatables){
            updateable.update();
        }
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

    /**
     * @author Stijn de vos
     * @since 04-12-2023
     * @param distance
     * this code should check if the distance that you are from an object is not to close.
     * it will use this information to eventually act on certain distances and stop or slowdown
     */
    public void onUltraSone(double distance) {
        System.out.println("Ultrasone distance: " + distance);
        if(distance < 5)
        {
            System.out.println("you are to close");
            zoomer.setClose(true);
        }
        else {
            zoomer.setClose(false);
        }
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