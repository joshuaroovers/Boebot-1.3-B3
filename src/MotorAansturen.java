import TI.BoeBot;
import TI.Servo;
import TI.Timer;

public class MotorAansturen {
    static Timer timer = new Timer(0);
    static Servo s1 = new Servo(12);
    static Servo s2 = new Servo(13);

    public static void Wheels(int Wheel1, int Wheel2, int wait){
        timer.setInterval(wait);
        while (!timer.timeout()){
            s1.update(Wheel1);
            s2.update(Wheel2);
        }
    }
    public static void turn_right(){
        Wheels(1300,10,750);
    }
    public static void turn_left(){
        Wheels(10,1300,750);
    }
    public static void backwards(){
        Wheels(1300,1700,1000);
    }
    public static void forwards(){
        Wheels(1700,1300,1000);
    }
    public static void stop(){
        Wheels(0,0,1000);
    }
    public static void slowdown(){

        Wheels(1575,1425,500);
        Wheels(1550,1450,500);
        Wheels(1525,1475,500);
        Wheels(0,0,500);
    }
    public static void startUp(){
        Wheels(0,0,500);
        Wheels(1525,1475,500);
        Wheels(1550,1450,500);
        Wheels(1575,1425,500);
    }
}
