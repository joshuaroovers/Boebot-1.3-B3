package head;

import TI.BoeBot;
import TI.Servo;
public class MotorAansturen {
    static Servo s1 = new Servo(12);
    static Servo s2 = new Servo(13);
    public static void main(String[] args) {
//        while(true){
//            startUp();
//            turn_left();
//            stop();
//            forwards();
//            turn_right();
//            forwards();
//            stop();
//            turn_right();
//            stop();
//            turn_right();
//            backwards();
//            turn_right();
//            forwards();
//            turn_left();
//            forwards();
//            slowdown();
//            stop();
//        }
    }
    /** @basis_motor
     * Servo s1 = new Servo(12);
     * Servo s2 = new Servo(13);
     *@forward
     * s1.update(1300);
     * s2.update(1700);
     *@backwards
     *  s1.update(1700);
     *  s2.update(1300);
     *@stop
     * s1.update(0);
     * s2.update(0);
     * @Links90Graden
     * s1.update(1300);
     * s2.update(10);
     * BoeBot.wait(750);
     */

//    /**
//     *
//     * @param Direction
//     *  String all lowercqse to select direction relative to current orientation
//     * @param s1
//     * @param s2
//     */
//    public void useTurn(String Direction, Servo s1, Servo s2) {
//        switch (Direction) {
//            case "left":
//                s1.update(1300);
//                s2.update(10);
//                BoeBot.wait(750);
//                s1.update(0);
//                s2.update(0);
//                break;
//            case "right" :
//                s1.update(10);
//                s2.update(1300);
//                BoeBot.wait(750);
//                s1.update(0);
//                s2.update(0);
//                break;
//            case "forward" :
//                s1.update(1300);
//                s2.update(1700);
//                BoeBot.wait(1000);
//                break;
//            case "backwards" :
//                s1.update(1700);
//                s2.update(1300);
//                BoeBot.wait(1000);
//                break;
//            case "stop" :
//                s1.update(0);
//                s2.update(0);
//                BoeBot.wait(1000);
//                break;
//        }
//    }
    public static void Wheels(int Wheel1, int Wheel2, int wait){
        s1.update(Wheel1);
        s2.update(Wheel2);
        BoeBot.wait(wait);
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
//    public static int getSpeed(){
//        Scanner reader = new Scanner(System.in);
//        int speed = -1;
//        while(speed < 0 || speed >3){
//            System.out.println("set speed");
//            speed = Integer.parseInt(reader.nextLine());
//        }
//        return speed;
//    }
//
//    public static int SpeedMod(int speed){
//        ArrayList<Integer> SpeedMod = new ArrayList<>();
//        SpeedMod.add(0);
//        SpeedMod.add(-100);
//        SpeedMod.add(100);
//        SpeedMod.add(200);
//        return SpeedMod.get(speed);
//    }
}
