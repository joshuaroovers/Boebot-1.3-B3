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
import actuators.Zoomer;
import sensors.Ultrasonic;
import sensors.UltrasonicCallback;
import sensors.IRSensor;
import sensors.IRSensorCallback;

import java.util.ArrayList;

public class Controller implements Updateable, ButtonCallback, LineDetectorCallback, UltrasonicCallback, IRSensorCallback  {

    public Boolean isRunning;
    private Zoomer zoomer;
    private Ultrasonic ultrasone;
    public EmergencyStop emergencyStop;
    private ArrayList<Updateable> updatables;
    private Splitter splitter;
    private LineDetector lineLeft;
    private LineDetector lineCenter;
    private LineDetector lineRight;
    private boolean lineDetectorStandby;
    private boolean obstackleDetected;
    private Button testButton;
    private Button testButton2;

    private Motor leftMotor;
    private Motor rightMotor;
    private Claw claw;
    private MotorHelper motorHelper;
    private Timer timerLineDetector;
  
    private IRSensor irSensor;
    private boolean emergencyBreakActive;
    private boolean IRtakeOver;


    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;

        //this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop( 5);
    };

//    public Controller() {
//        BoeBot.setMode(1, PinMode.Input);
//        this.isRunning = true;
//        //this.zoomer = new Zoomer(10, 11);
//        //this.emergencyStop = new EmergencyStop(0);
//    };

    public void startUp() {
        this.isRunning = true;
        emergencyBreakActive = false;
        IRtakeOver = false;
        lineDetectorStandby = false;
    }


    public void init(){
        emergencyBreakActive = false;
        IRtakeOver = false;
      
        updatables.add(this.leftMotor = new Motor(12,12));
        updatables.add(this.rightMotor = new Motor(13,12));
        updatables.add(this.claw = new Claw(14,10));
        updatables.add(ultrasone = new Ultrasonic(10,11, this));
        updatables.add(zoomer = new Zoomer(8));
        updatables.add(this.testButton = new Button(this,0));
        updatables.add(this.testButton2 = new Button(this,1));
        updatables.add(this.irSensor = new IRSensor(this,4));
        updatables.add(this.lineLeft = new LineDetector(2,150,this));
        updatables.add(this.lineCenter = new LineDetector(1,100,this));
        updatables.add(this.lineRight = new LineDetector(0,350,this));


        timerLineDetector = new Timer(1);

        motorHelper = new MotorHelper(leftMotor,rightMotor, claw, 60, timerLineDetector);
        splitter = new Splitter(motorHelper,claw);
        splitter.setSplice("cvlrlrlrvv");


        lineDetectorStandby = false;

        //motorHelper.stop();
    }
    //temp default

    public void update() {

       if (emergencyBreakActive){
           motorHelper.hardStop();
           this.isRunning = false;
           return;
       }

//            claw.update();
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
            obstackleDetected = false;
            zoomer.setClose(false);

        } else {
            obstackleDetected = true;

            if (distance >= 25 && distance < 35) {
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
    }


    @Override
    public void onButton(Button whichButton) {

        if (whichButton == testButton) {
            //System.out.println("test 0 button pressed!");
            motorHelper.hardStop();
            //motorHelper.clawOpen();
        }
        else if(whichButton == testButton2){
            //System.out.println("test 1 button pressed!");
            //motorHelper.forwards();
            //splitter.setSplice("l");
            //splitter.setSplice("tvvlvrvl");
//            claw.close();
           // splitter.setSplice("cvlrlrlrvv");
          motorHelper.hardStop();
          emergencyBreakActive = true;
        }
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
        if(!obstackleDetected || !IRtakeOver) {
            if (timerLineDetector.timeout()) {
                boolean left = lineLeft.checkForLine();
                boolean center = lineCenter.checkForLine();
                boolean right = lineRight.checkForLine();

                System.out.println(lineLeft.getTestData()+" "+ lineCenter.getTestData()+" "+ lineRight.getTestData());


                //System.out.println(left+" "+center+" "+right);

                //when only center detects a black line
                if (!left && center && !right) {
                    motorHelper.forwards();
                }
                //when only right detects a black line
                else if(!left && !center && right){
                    motorHelper.adjust_left();
                }
                //when only left detects a black line
                else if(left && !center && !right){
                    motorHelper.adjust_right();
                }
                //when all detectors detect a black line
                else if(left && center && right){
                    motorHelper.stop();
                    lineDetectorStandby = true;
                    splitter.commandStep();
                }
                //when all detectors detect no black lines
                else if(!left && !center && !right){

                    if(splitter.noMoreCommands()){
                        motorHelper.stop();
                    }else if(splitter.firstCommand()){
                        lineDetectorStandby = true;
                        splitter.commandStep();
                    }

                }
            } else {
                this.lineDetectorStandby = false;
                timerLineDetector.setInterval(250);
                splitter.commandStep();
            }
        }

    }
  
    @Override
      public void onSignal(int line) {
  //        System.out.println("line callback value:");
  //        System.out.println(line);
          if(line != -1){
              this.IRtakeOver = true;
          }
          switch(line){

              case 0: //button-1
                  System.out.println("button 1 pressed!");
                  break;
              case 1: //button-2
                  System.out.println("button 2 pressed!");
                  motorHelper.stop();
                  break;
              case 2: //button-3
                  System.out.println("button 3 pressed!");
                  break;
              case 3: //button-4
                  System.out.println("button 4 pressed!");
                  break;
              case 4: //button-5
                  System.out.println("button 5 pressed!");
                  break;
              case 5: //button-6
                  System.out.println("button 6 pressed!");
                  break;
              case 6: //button-7
                  System.out.println("button 7 pressed!");
                  break;
              case 7: //button-8
                  System.out.println("button 8 pressed!");
                  break;
              case 8: //button-9
                  System.out.println("button 9 pressed!");
                  break;
              case 16: //button-CH+
                  System.out.println("button CH+ pressed!");

                  motorHelper.forwards();
                  break;
              case 17: //button-CH-
                  System.out.println("button CH- pressed!");
                  motorHelper.backwards();
                  break;
              case 18: //button-Vol+  //todo might be 19 instead
                  System.out.println("button Vol+ pressed!");
                  motorHelper.turn_right();
                  break;
              case 19: //button-Vol-  //todo might be 18 instead
                  System.out.println("button Vol- pressed!");
                  motorHelper.turn_left();
                  break;
              case 21: //button power off  //todo might be 18 instead
                  System.out.println("button Vol- pressed!");
                  motorHelper.hardStop();
                  emergencyBreakActive = true;
                  break;
          }
      }
  
}
