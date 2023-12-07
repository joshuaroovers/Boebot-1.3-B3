package head;

import TI.BoeBot;
import TI.Servo;
import TI.Timer;
import actuators.Motor;

public class MotorAansturen {
    static Timer timer = new Timer(0);
    private Motor s1;
    private Motor s2;

    public MotorAansturen(Motor s1, Motor s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public void Wheels(int Wheel1, int Wheel2, int wait){
        timer.setInterval(wait);
        while (!timer.timeout()){
            this.s1.setSpeed(Wheel1);
            this.s2.setSpeed(Wheel2);
        }
    }
    public void turn_right(){
        Wheels(1300,25,750);
    }
    public void turn_left(){
        Wheels(25,1300,750);
    }
    public void backwards(){
        Wheels(1300,1700,1000);
    }
    public void forwards(){
        Wheels(1700,1300,1000);
    }
    public void stop(){
        Wheels(0,0,1000);
    }
    public void slowdown(){
        Wheels(1575,1425,500);
        Wheels(1550,1450,500);
        Wheels(1525,1475,500);
        Wheels(0,0,500);
    }
    public void startUp(){
        Wheels(0,0,500);
        Wheels(1525,1475,500);
        Wheels(1550,1450,500);
        Wheels(1575,1425,500);
    }
}