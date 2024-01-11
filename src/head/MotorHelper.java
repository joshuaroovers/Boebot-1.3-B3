package head;

import actuators.Motor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MotorHelper {
    private Motor s1;
    private Motor s2;

    /**
     * @author Morris Woestenburg, Joshua Roovers
     * @param s1
     * @param s2
     * sets the speed for the wheels.
     */

    public MotorHelper(Motor s1, Motor s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

//    public void Wheels(int Wheel1, int Wheel2){
//        this.s1.setSpeed(Wheel1);
//        this.s2.setSpeed(Wheel2);
//    }
    public void Wheels(int Wheel1, int Wheel2, boolean gradualIncrement){
        this.s1.setGradualIncrement(gradualIncrement);
        this.s2.setGradualIncrement(gradualIncrement);
        this.s1.setSpeed(Wheel1);
        this.s2.setSpeed(Wheel2);
    }
    public void turn_left(){
        Wheels(1450,1450, false);
    }
    public void turn_right(){
        Wheels(1550,1550, false);
    }

    public void adjust_right(){Wheels(1450,1525, false);} //todo too slow
    public void adjust_left(){
        Wheels(1475,1550, false);
    } //todo too slow

    public void backwards(){

        Wheels(1550,1450, false);
    }
    public void forwards(){
        //System.out.println("Wheels forwards");
        Wheels(1450,1550, true);
    }
    public void stop(){
        //System.out.println("stop");
        Wheels(1500,1500, true);
    }

    public void hardStop(){
        //System.out.println("Hard stop");
        this.s1.hardStop();
        this.s2.hardStop();
    }
}