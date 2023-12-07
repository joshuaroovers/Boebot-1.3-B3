package head;

import TI.BoeBot;
import TI.PinMode;
import sensors.LineDetector;

import javax.sound.sampled.Line;

public class Controller implements Updateable {

    public Boolean isRunning;
    private Zoomer zoomer;

    public EmergencyStop emergencyStop;

    private LineDetector line1;
    private LineDetector line2;
    private LineDetector line3;
    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;
        this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop(0);
        this.line1 = new LineDetector(0);
        this.line2 = new LineDetector(1);
        this.line3 = new LineDetector(2);
    }

    public void startUp() {
        this.isRunning = true;
    }

    public void update() {
        if (this.emergencyStop.check()){
            MotorAansturen.stop();
            this.isRunning = false;
            return;
        }

        zoomer.update();
        line1.update();
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