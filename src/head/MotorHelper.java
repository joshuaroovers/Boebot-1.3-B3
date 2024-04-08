package head;

import TI.Timer;
import actuators.Claw;
import actuators.Motor;

public class MotorHelper {
    private final Timer timerLineDetector;
    private Motor motorLeft;
    private Motor motorRight;

    private Claw claw;
    private final int speedStill = 1500;
    private int speed;

    private NeoPixelHelper neoPixelHelper;
    /**
     * @param motorLeft
     * @param motorRight                sets the speed for the wheels.
     * @param claw
     * @param timerLineDetector
     * @author Morris Woestenburg, Joshua Roovers
     */

    public MotorHelper(Motor motorLeft, Motor motorRight, Claw claw, int speed, Timer timerLineDetector, NeoPixelHelper neo) {
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;
        this.claw = claw;
        this.speed = speed;
        this.timerLineDetector = timerLineDetector;
        this.neoPixelHelper = neo;
    }

//    public void Wheels(int Wheel1, int Wheel2){
//        this.s1.setSpeed(Wheel1);
//        this.s2.setSpeed(Wheel2);
//    }
    public void wheels(int Wheel1, int Wheel2, boolean gradualIncrement,String turnLight){
        neoPixelHelper.turnLight(turnLight);
        this.motorLeft.setGradualIncrement(gradualIncrement);
        this.motorRight.setGradualIncrement(gradualIncrement);
        this.motorLeft.setSpeed(speedStill + Wheel1);
        this.motorRight.setSpeed(speedStill - Wheel2);
    }
    public void turn_right(){
        wheels(speed,-speed/2, false,"right_Light");
        timerLineDetector.setInterval(550);
    }
    public void turn_left(){
        wheels(-speed/2,speed, false,"left_Light");
        timerLineDetector.setInterval(550);
    }

    public void adjust_right(){
        wheels(speed,0, false,"right_Light");}
    public void adjust_left(){
        wheels(0, speed, false,"left_Light");
    }

    public void turnAround() {
        wheels(speed, -speed, false,"right_Light");
        timerLineDetector.setInterval(850);
    }
    public void backwards(){
        wheels(-speed,-speed, false,"back_Light");
    }
    public void forwards(){
        wheels(speed,speed, true,"forward_Light");
        timerLineDetector.setInterval(50);
    }
    public void stop(){
        wheels(0,0, true,"stop_Light");
    }

    public void hardStop(){
        //System.out.println("Hard stop");
        wheels(0,0, false,"stop_Light");
    }

    public void clawOpen(){
        claw.setSpeed(1700);
        timerLineDetector.setInterval(2000);
    }

    public void clawClose(){
        claw.setSpeed(825);
        timerLineDetector.setInterval(4000);
    }


}