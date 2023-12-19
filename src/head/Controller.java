package head;

import TI.BoeBot;
import TI.PinMode;
import actuators.Motor;
import sensors.Button;
import sensors.ButtonCallback;
import sensors.LineDetector;
import sensors.LineDetectorCallback;

import java.util.ArrayList;

public class Controller implements Updateable, ButtonCallback {

    public Boolean isRunning;
    private Zoomer zoomer;

    public EmergencyStop emergencyStop;

    private LineDetector line1;
    private LineDetector line2;
    private LineDetector line3;
    private Button testButton;
    private Button testButton2;

    private Motor leftMotor;
    private Motor rightMotor;
    private MotorHelper motorAansturen;
    private ArrayList<Updateable> updatables;
    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;

        //this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop(5);
    };

    public void startUp() {
        this.isRunning = true;
    }

    public void init(){
        updatables  = new ArrayList<>();

        updatables.add(this.leftMotor = new Motor(12,15));
        updatables.add(this.rightMotor = new Motor(13,15));
        updatables.add(this.testButton = new Button(this,0));
        updatables.add(this.testButton2 = new Button(this,1));

        motorAansturen = new MotorHelper(leftMotor,rightMotor);
    }

    public void update() {

//        if (this.emergencyStop.check()){
////            motorAansturen.stop();
//            this.isRunning = false;
//            return;
//        }

        for (Updateable updatable : updatables)
            updatable.update();
        BoeBot.wait(1);


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

    @Override
    public void onButton(Button whichButton) {

        if(whichButton == testButton){
            //System.out.println("test 0 button pressed!");
            motorAansturen.forwards();
        }
        else if(whichButton == testButton2){
            //System.out.println("test 1 button pressed!");
            motorAansturen.hardStop();
        }

//        switch (whichButton){
//            case testButton:
//                System.out.println();
//            case testButton2:
//            default:
//                System.out.println("");;
//        }

//        leftMotor.setSpeed(100);
//        rightMotor.setSpeed(100);
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