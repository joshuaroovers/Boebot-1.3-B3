import TI.BoeBot;
import TI.PinMode;

public class Engine implements Updateable{

    public Boolean isRunning;
    private Zoomer zoomer;

    public EmergencyStop emergencyStop;
    Engine() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;
        this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop(0);
    };

    public void startUp() {
        this.isRunning = true;
    }

    public void update() {
        if (this.emergencyStop.check()){
            MotorAansturen.stop();
            this.isRunning = false;
            return;
        }

        zoomer.update();
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
}


//    public static void main(String[] args) {
//        Zoomer zoomer = new Zoomer(12, 14);
//        EmergencyStop emergencyStop = new EmergencyStop(0);
//
//        while (true) {
//
//            //zoomer.update(12);
//            zoomer.update(14);
//            if(emergencyStop.check()){
//                //check for emergency stop press, stop the wheels and break the loop
//                MotorAansturen.stop();
//                break;
//            }
//            BoeBot.wait(1);
//        }