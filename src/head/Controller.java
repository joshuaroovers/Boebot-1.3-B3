package head;

import TI.BoeBot;
import TI.PinMode;
import actuators.Motor;
import sensors.Button;
import sensors.ButtonCallback;
import sensors.IRSensor;
import sensors.IRSensorCallback;

import java.util.ArrayList;

public class Controller implements Updateable, ButtonCallback, IRSensorCallback {

    public Boolean isRunning;
    private Zoomer zoomer;
    private IRSensor irSensor;

    public EmergencyStop emergencyStop;
    private Button testButton;
    private Button testButton2;

    private Motor leftMotor;
    private Motor rightMotor;
    private MotorHelper motorHelper;
    private ArrayList<Updateable> updatables;
    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;
        //this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop(0);

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
        updatables.add(this.irSensor = new IRSensor(this,9));
        motorHelper = new MotorHelper(leftMotor,rightMotor);
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
            motorHelper.forwards();
        }
        else if(whichButton == testButton2){
            //System.out.println("test 1 button pressed!");
            motorHelper.hardStop();
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


    @Override
    public void onSignal(String line) {
//        switch(line){
//            case "000000010000":
//
//        }
        //System.out.println(line);
        if (line.equals("000000010000")) {
            System.out.println("Moving left forward");
            motorHelper.turn_left();


        }

        if (line.equals("100000010000")) {
            System.out.println("Moving forward");
            motorHelper.forwards();
//            servoL.update(1600);
//            servoR.update(1400);

        }

        if (line.equals("010000010000")) {
            System.out.println("Moving right forward");
            motorHelper.turn_right();
            //servoR.update(1400);
        }

        if (line.equals("110000010000")) {
            System.out.println("Turning left");

        }

        if (line.equals("001000010000")) {
            System.out.println("Stopping");
            motorHelper.stop();
//            servoL.update(1500);
//            servoR.update(1500);

        }

        if (line.equals("101000010000")) {
            System.out.println("Turning right");

        }

        if (line.equals("011000010000")) {
            System.out.println("Moving left backward");
            //servoL.update(1400);
        }

        if (line.equals("111000010000")) {
            System.out.println("Moving backward");
            motorHelper.backwards();
//            servoL.update(1400);
//            servoR.update(1600);
        }

        if (line.equals("000100010000")) {
            System.out.println("Moving right backward");
            //servoR.update(1600);
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