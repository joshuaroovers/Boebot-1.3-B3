package head;

import TI.BoeBot;
import TI.PinMode;
import sensors.Button;
import sensors.Ultrasonic;
import sensors.UltrasonicCallback;

import java.util.ArrayList;

public class Controller implements UltrasonicCallback {

    public Boolean isRunning;
    private Zoomer zoomer;
    private Ultrasonic ultrasone;
    public EmergencyStop emergencyStop;
    private ArrayList<Updateable> updatables;

//    public Controller() {
//        BoeBot.setMode(1, PinMode.Input);
//        this.isRunning = true;
//        //this.zoomer = new Zoomer(10, 11);
//        //this.emergencyStop = new EmergencyStop(0);
//    };

    public void init(){
        updatables = new ArrayList<>();
        updatables.add(ultrasone = new Ultrasonic(10,11, this));
        updatables.add(zoomer = new Zoomer(8));
    }

    public void startUp() {
        this.isRunning = true;
    }

    /**
     * @author Stijn de vos
     * @since 14-12-2023
     * this code will update every sensor every millisecond
     */
    public void run() {
        if (this.emergencyStop.check()){
            MotorAansturen.stop();
            this.isRunning = false;
            return;
        }

        if(this.isRunning = true){
            for(Updateable update : updatables){
                update.update();
            }
        }
        BoeBot.wait(1);
    }

    public Boolean getRunning() {
        return isRunning;
    }


    public void setRunning(boolean b) {
        this.isRunning = b;
    }


    /**
     * @author Stijn de vos
     * @since 04-12-2023
     * @param distance
     * this code should check if the distance that you are from an object is not to close.
     * the closer you are the more it checks how close you are.
     * if you get to close the buzzer wil start giving of a siren noise.
     */
    @Override
    public void onUltrasonic(double distance) {
        System.out.println("Ultrasone distance: " + distance);
        if(distance >= 30)
        {
            System.out.println("you are far enough");
            zoomer.setClose(false);
        }
        else if (distance >= 20 && distance < 30) {
            System.out.println("you are getting closer");
            ultrasone.setTimer(10);
            zoomer.setClose(false);
        }
        else if (distance >= 10 && distance < 20){
            System.out.println("very close");
            ultrasone.setTimer(15);
            zoomer.setClose(true);
        }
        else{
            System.out.println("way to close");
            zoomer.setClose(true);
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