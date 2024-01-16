package head;

import actuators.Motor;

public class MotorHelper {
    private Motor s1;
    private Motor s2;

    private final int speedStill = 1500;
    private int speed;
    /**
     * @author Morris Woestenburg, Joshua Roovers
     * @param s1
     * @param s2
     * sets the speed for the wheels.
     */

    public MotorHelper(Motor s1, Motor s2, int speed) {
        this.s1 = s1;
        this.s2 = s2;
        this.speed = speed;
    }

//    public void Wheels(int Wheel1, int Wheel2){
//        this.s1.setSpeed(Wheel1);
//        this.s2.setSpeed(Wheel2);
//    }
    public void wheels(int Wheel1, int Wheel2, boolean gradualIncrement){
        this.s1.setGradualIncrement(gradualIncrement);
        this.s2.setGradualIncrement(gradualIncrement);
        this.s1.setSpeed(speedStill - Wheel1);
        this.s2.setSpeed(speedStill + Wheel2);
    }
    public void turn_left(){
        wheels(speed,-speed/2, false);
    }
    public void turn_right(){
        wheels(-speed/2,speed, false);
    }

    public void adjust_left(){
        wheels(speed,0, false);}
    public void adjust_right(){
        wheels(0, speed, false);
    }

    public void small_adjust_left(){
        wheels(speed/2,speed/4, false);} //todo might not be needed anymore
    public void small_adjust_right(){
        wheels(speed/4,speed/2, false);
    } //todo might not be needed anymore

    public void backwards(){

        wheels(-speed,-speed, false);
    }
    public void forwards(){
        //System.out.println("Wheels forwards");
        wheels(speed,speed, true);
    }
    public void stop(){
        //System.out.println("stop");
        wheels(0,0, true);
    }

    public void hardStop(){
        //System.out.println("Hard stop");
        wheels(0,0, false);
    }
    public void turnAround(){
        wheels(speed,-speed,false);
    }
}