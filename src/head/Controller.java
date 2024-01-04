package head;

import TI.BoeBot;
import TI.PinMode;
import actuators.Motor;
import sensors.Button;
import sensors.ButtonCallback;
import sensors.IRSensor;
import sensors.IRSensorCallback;

import java.util.ArrayList;

public class Controller implements Updateable, ButtonCallback, IRSensorCallback {

    public Boolean isRunning;
    private Zoomer zoomer;
    private IRSensor irSensor;

    public EmergencyStop emergencyStop;
    private Button testButton;
    private Button testButton2;

    private Motor leftMotor;
    private Motor rightMotor;
    private MotorHelper motorHelper;
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
        updatables.add(this.irSensor = new IRSensor(this,6));
        motorHelper = new MotorHelper(leftMotor,rightMotor);
    }

    public void update() {

        if (this.emergencyStop.check()){
            System.out.println("Emergency stop from engine");
//            motorAansturen.stop();
            this.isRunning = false;
            return;
        }

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
            //System.out.println("test 0 button pressed!");
            motorHelper.forwards();
        }
        else if(whichButton == testButton2){
            //System.out.println("test 1 button pressed!");
            motorHelper.hardStop();
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


    @Override
    public void onSignal(int line) {
//        System.out.println("line callback value:");
//        System.out.println(line);
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
        }
    }
}
