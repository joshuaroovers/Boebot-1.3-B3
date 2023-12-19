package head;

import TI.BoeBot;
import TI.PinMode;
import sensors.LineDetector;
import sensors.LineDetectorCallback;

import javax.sound.sampled.Line;

public class Controller implements Updateable, LineDetectorCallback {

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
        this.line1 = new LineDetector(0,this);
        this.line2 = new LineDetector(1,this);
        this.line3 = new LineDetector(2,this);
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

    /**
     * onLine
     * @author Joshua Roovers
     * @param lineDetector the lineDetector that calls this callback method.
     * callback method for a lineDetector Sensor  //todo
     */
    @Override
    public void onLine(LineDetector lineDetector) {
        //System.out.println(line1.checkForLine()+" "+line2.checkForLine()+" "+line3.checkForLine());
        //System.out.println(line1.getTestData()+" "+line2.getTestData()+" "+line3.getTestData());

        //when all detectors detect a black line
        if(line1.checkForLine() && line2.checkForLine() && line3.checkForLine()){
            System.out.println("crossroad");
        }
        if(lineDetector == line1){
            if(line1.checkForLine()){
//                System.out.print("Black ");
            }
            else{
//                System.out.print("White ");
            }

        }
        else if(lineDetector == line2){
            if(line2.checkForLine()){
//                System.out.print("Black ");
            }
            else{
//                System.out.print("White ");
            }
        }
        else if(lineDetector == line3){
            if(line3.checkForLine()){
//                System.out.print("Black ");
            }
            else{
//                System.out.print("White ");
            }
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