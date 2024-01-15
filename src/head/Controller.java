package head;

import TI.BoeBot;
import TI.PinMode;
import actuators.Motor;
import actuators.NeoPixel;
import actuators.Zoomer;
import sensors.Button;
import sensors.ButtonCallback;

import java.awt.*;
import java.util.ArrayList;

public class Controller implements Updateable, ButtonCallback {

    public Boolean isRunning;
    private Zoomer zoomer;

    public EmergencyStop emergencyStop;
    private Button testButton;
    private Button testButton2;

    private Motor leftMotor;
    private Motor rightMotor;
    private MotorHelper motorAansturen;
    private NeoPixel pixelLeft;
    private NeoPixel pixelRight;
    private NeoPixel pixelBack;
    private NeoPixel pixelForward;

    private NeoPixelHelper neoPixelHelper;
    private ArrayList<Updateable> updatables;
    public Controller() {
        BoeBot.setMode(1, PinMode.Input);
        this.isRunning = true;

        //this.zoomer = new Zoomer(10, 11);
        this.emergencyStop = new EmergencyStop(5);
    };

    public void startUp() {
        this.isRunning = true;
    }

    public void init(){
        updatables  = new ArrayList<>();

        updatables.add(this.leftMotor = new Motor(12,15));
        updatables.add(this.rightMotor = new Motor(13,15));
        updatables.add(this.testButton = new Button(this,0));
        updatables.add(this.testButton2 = new Button(this,1));
        updatables.add(this.pixelLeft = new NeoPixel(3));
        updatables.add(this.pixelRight = new NeoPixel(5));
        updatables.add(this.pixelBack = new NeoPixel(1));
        updatables.add(this.pixelForward = new NeoPixel(4));

        neoPixelHelper = new NeoPixelHelper(this.pixelLeft,this.pixelRight, this.pixelBack, this.pixelForward);

        motorAansturen = new MotorHelper(leftMotor,rightMotor);
    }

    public void update() {

//        if (this.emergencyStop.check()){
////            motorAansturen.stop();
//            this.isRunning = false;
//            return;
//        }

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
            //this.pixelBack.setColor(Color.blue);
            //System.out.println("test 0 button pressed!");
            //motorAansturen.forwards();

//            this.pixelBack.setBlink(Color.red, Color.black);
            neoPixelHelper.stopLight();
        }
        else if(whichButton == testButton2){
            //this.pixelBack.setColor(Color.black);
            //System.out.println("test 1 button pressed!");
           // motorAansturen.hardStop();
//            this.pixelBack.setBlink(Color.white, Color.black);
            neoPixelHelper.rightLight();
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