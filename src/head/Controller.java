package head;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import actuators.Motor;
import sensors.Button;
import sensors.ButtonCallback;
import sensors.LineDetector;
import sensors.LineDetectorCallback;
import actuators.Zoomer;
import sensors.Ultrasonic;
import sensors.UltrasonicCallback;

import java.util.ArrayList;

public class Controller implements Updateable, ButtonCallback, LineDetectorCallback, UltrasonicCallback  {

    public Boolean isRunning;
    private Zoomer zoomer;
    private LineDetector lineLeft;
    private LineDetector lineCenter;
    private LineDetector lineRight;
    private boolean lineDetectorStandby;
    private Splitter splitter;
    private Ultrasonic ultrasone;
    public EmergencyStop emergencyStop;
    private Button testButton;
    private Button testButton2;

    private Motor leftMotor;
    private Motor rightMotor;
    private MotorHelper motorHelper;
    private ArrayList<Updateable> updatables;
    private Timer timerLineDetector;

    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;

        //this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop( 0);
    };

//    public Controller() {
//        BoeBot.setMode(1, PinMode.Input);
//        this.isRunning = true;
//        //this.zoomer = new Zoomer(10, 11);
//        //this.emergencyStop = new EmergencyStop(0);
//    };


    public void startUp() {
        this.isRunning = true;
    }


    public void init() {
        updatables = new ArrayList<>();

        updatables.add(this.leftMotor = new Motor(12, 45));
        updatables.add(this.rightMotor = new Motor(13, 45));
        updatables.add(ultrasone = new Ultrasonic(10, 11, this));
        updatables.add(zoomer = new Zoomer(8));
        updatables.add(this.testButton = new Button(this,0));
        updatables.add(this.testButton2 = new Button(this,1));
        updatables.add(this.lineLeft = new LineDetector(2,this));
        updatables.add(this.lineCenter = new LineDetector(1,this));
        updatables.add(this.lineRight = new LineDetector(0,this));
        lineDetectorStandby = false;

        motorHelper = new MotorHelper(leftMotor,rightMotor);
        splitter = new Splitter(motorHelper);
        splitter.setSplice("vllr");
        timerLineDetector = new Timer(250);
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


    }

    public Boolean getRunning() {
        return isRunning;
    }


    public void setRunning(boolean b) {
        this.isRunning = b;
    }


    /**
     * @param distance this code should check if the distance that you are from an object is not to close.
     *                 the closer you are the more it checks how close you are.
     *                 if you get to close the buzzer wil start giving of a siren noise.
     * @author Stijn de vos
     * @since 04-12-2023
     */
    @Override
    public void onUltrasonic(double distance) {
        //System.out.println("Ultrasone distance: " + distance);
        if (distance >= 35) {
            //System.out.println("you are far enough");
            zoomer.setClose(false);

        } else if (distance >= 25 && distance < 35) {
            //System.out.println("you are getting closer");
            ultrasone.setTimer(25);
            zoomer.setClose(false);
            motorHelper.stop();

        } else if (distance >= 15 && distance < 25) {
            //System.out.println("very close");
            ultrasone.setTimer(30);
            zoomer.setClose(true);
            motorHelper.stop();


        } else if (distance >= 0) {
            //System.out.println("way to close");
            zoomer.setClose(true);
            motorHelper.hardStop();

        }
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





    /**
     * onLine
     * @author Joshua Roovers
     * @param lineDetector the lineDetector that calls this callback method.
     * callback method for a lineDetector Sensor that calls MotorHelper methods
     */
    @Override
    public void onLine(LineDetector lineDetector) {
        //if it's not waiting on a crossroad (which would be changed after following a given command)
        if(!lineDetectorStandby || timerLineDetector.timeout()){
            boolean left = lineLeft.checkForLine();
            boolean center = lineCenter.checkForLine();
            boolean right = lineRight.checkForLine();

            //System.out.println(lineLeft.getTestData()+" "+ lineCenter.getTestData()+" "+ lineRight.getTestData());


            //System.out.println(left+" "+center+" "+right);

            //when only center detects a black line
            if(!left && center && !right){
                motorHelper.forwards();
            }
            //when only right detects a black line
            else if(!left && !center && right || !left && center && right){
                //System.out.println("course correct to the left (slowing down right motor)");
                motorHelper.turn_left();
            }
            //when only left detects a black line
            else if(left && !center && !right || left && center && !right){
                //System.out.println("course correct to the right (slowing down left motor)");
                motorHelper.turn_right();
            }
            //when all detectors detect a black line
            else if(left && center && right){
                //System.out.println("crossroad");
                motorHelper.stop();
                lineDetectorStandby = true;
            }
            //when all detectors detect no black lines
            else if(!left && !center && !right){
                motorHelper.stop();
            }
        }
        else{
            this.lineDetectorStandby = false;
            timerLineDetector.setInterval(250);
            splitter.splitter();
        }

    }
}

