package head;

import TI.BoeBot;
import TI.PinMode;
import actuators.Motor;
import sensors.Button;
import sensors.ButtonTestCallback;

import java.util.ArrayList;

public class Controller implements Updateable, ButtonTestCallback {

    public Boolean isRunning;
    private Zoomer zoomer;

    public EmergencyStop emergencyStop;
    public Button testButton;
    private Motor leftMotor;
    private Motor rightMotor;
    private MotorAansturen motorAansturen;
    private ArrayList<Updateable> updatables;
    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;

        //this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop(0);
    };

    public void startUp() {
        this.isRunning = true;
    }

    public void init(){
        updatables  = new ArrayList<>();

        updatables.add(this.leftMotor = new Motor(12));
        updatables.add(this.rightMotor = new Motor(13));
        updatables.add(this.testButton = new Button(this,2));

        motorAansturen = new MotorAansturen(leftMotor,rightMotor);
    }

    public void update() {

        if (this.emergencyStop.check()){
            motorAansturen.stop();
            this.isRunning = false;
            return;
        }

        leftMotor.update();
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
    public void onTestButton() {
        System.out.println("test button pressed!");
        leftMotor.setSpeed(100);
        rightMotor.setSpeed(100);
    }
}


//    public static void main(String[] args) {
//        head.Zoomer zoomer = new head.Zoomer(12, 14);
//        head.EmergencyStop emergencyStop = new head.EmergencyStop(0);
//
//        while (true) {
//
//            //zoomer.update(12);
//            zoomer.update(14);
//            if(emergencyStop.check()){
//                //check for emergency stop press, stop the wheels and break the loop
//                head.MotorAansturen.stop();
//                break;
//            }
//            BoeBot.wait(1);
//        }