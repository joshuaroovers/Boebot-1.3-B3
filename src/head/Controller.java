package head;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import actuators.Claw;
import actuators.Motor;
import sensors.Button;
import sensors.ButtonCallback;
import sensors.LineDetector;
import sensors.LineDetectorCallback;

import java.util.ArrayList;

public class Controller implements Updateable, ButtonCallback, LineDetectorCallback  {

    public Boolean isRunning;
    private Zoomer zoomer;
    private Splitter splitter;
    private LineDetector lineLeft;
    private LineDetector lineCenter;
    private LineDetector lineRight;
    private boolean lineDetectorStandby;
    public EmergencyStop emergencyStop;
    private Button testButton;
    private Button testButton2;

    private Motor leftMotor;
    private Motor rightMotor;
    private Claw claw;
    private MotorHelper motorHelper;
    private ArrayList<Updateable> updatables;
    private Timer timerLineDetector;

    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;

        //this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop( 5);
    };

    public void startUp() {
        this.isRunning = true;
    }

    public void init(){
        updatables  = new ArrayList<>();

        updatables.add(this.leftMotor = new Motor(12,12));
        updatables.add(this.rightMotor = new Motor(13,12));
        updatables.add(this.claw = new Claw(14,25));
        updatables.add(this.testButton = new Button(this,0));
        updatables.add(this.testButton2 = new Button(this,1));
        updatables.add(this.lineLeft = new LineDetector(2,350,this));
        updatables.add(this.lineCenter = new LineDetector(1,100,this));
        updatables.add(this.lineRight = new LineDetector(0,600,this));


        timerLineDetector = new Timer(1);

        motorHelper = new MotorHelper(leftMotor,rightMotor, 60, timerLineDetector);
        splitter = new Splitter(motorHelper);
        splitter.setSplice("vvvv");

        lineDetectorStandby = false;
    }
    //temp default

    public void update() {

        if (this.emergencyStop.check()){
            motorHelper.hardStop();
            this.isRunning = false;
            return;
        }

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
            motorHelper.hardStop();
        }
        else if(whichButton == testButton2){
            //System.out.println("test 1 button pressed!");
            //motorHelper.forwards();
            //splitter.setSplice("l");
            splitter.setSplice("vvvqqqqqqqq");
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

        //System.out.println("lineDetectorStandby: " +timerLineDetector.timeout());

        if(timerLineDetector.timeout()){
            timerLineDetector.setInterval(1); //to keep it 1 timeout tick instead of being every timeout tick of the crossroad set interval
            boolean left = lineLeft.checkForLine();
            boolean center = lineCenter.checkForLine();
            boolean right = lineRight.checkForLine();

            System.out.println(lineLeft.getTestData()+" "+ lineCenter.getTestData()+" "+ lineRight.getTestData());


            //System.out.println(left+" "+center+" "+right);

            //when only center detects a black line
            if(!left && center && !right){
                motorHelper.forwards();
            }
            //when only right detects a black line
            else if(!left && !center && right){
                //System.out.println("course correct to the left (slowing down right motor)");
                motorHelper.adjust_left();
            }else if(!left && center && right){
               // motorHelper.small_adjust_left(); //small
            }
            //when only left detects a black line
            else if(left && !center && !right){
                //System.out.println("course correct to the right (slowing down left motor)");
                motorHelper.adjust_right();
            }else if(left && center && !right){
               // motorHelper.small_adjust_right(); //small
            }
            //when all detectors detect a black line
            else if(left && center && right){
                System.out.println("crossroad");
                motorHelper.stop();
                lineDetectorStandby = true;
                System.out.println("new lineDetector Timer");
                //timerLineDetector.setInterval(550); //todo timing not quite done yet
                splitter.commandStep();
            }
            //when all detectors detect no black lines
            else if(!left && !center && !right){
                System.out.println("white space");

                if(splitter.noMoreCommands()){
                    System.out.println("whitespace stopping");
                    motorHelper.stop();
                }else if(splitter.getStep() == 0){
                    System.out.println("white and first command");
                    lineDetectorStandby = true;
                    splitter.commandStep();
                }

            }
            //System.out.println(splitter.firstCommand() +" " + splitter.getStep());
        }

    }
}