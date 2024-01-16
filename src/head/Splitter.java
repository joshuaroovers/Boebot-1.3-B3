package head;

import TI.Timer;
import actuators.Claw;
import sensors.LineDetector;

import java.util.ArrayList;

public class Splitter {

    private  String splice;
//    private String spliceBack;
//    private String backControl;
    private ArrayList<String> split;
    private String control;
    private MotorHelper motorHelper;
    private Claw claw;
    private int step;
//    private int backStep;
    private LineDetector lineDetector;
    public Splitter(MotorHelper motorHelper){
        this.motorHelper = motorHelper;
    }

    public void setSplice(String splice) {
        this.splice = splice;
        this.step = 0;
        System.out.println("splice init step:" + this.step);
    }

    //    public void returning(String command){
//            switch(control){
//            case "r":
//                motorHelper.turn_left();
//                break;
//            case "l":
//                motorHelper.turn_right();
//                break;
//            case "v":
//                motorHelper.forwards();
//                break;
//            case "n":
//                motorHelper.hardStop();
//                break;
//            case "e":
//                motorHelper.stop();
//                break;
//            case "o":
//                claw.open();
//                break;
//            case "c":
//                claw.close();
//                break;
//            case "t":
//                    motorHelper.turnAround();
//                    break;
//        }
//        }
    /**
     * Splitter
     * @author Morris Woestenburg
     * splits the command from the GUI into individual characters that call on the motor methods
     */
    public void commandStep(){
        System.out.println("splice step1:" + this.step);
        if (step >= splice.length()){System.out.println("no commands left");}
        else {
            control = String.valueOf(splice.charAt(step));
            decoder(control);
            step++;
        }
        System.out.println("splice step2:" + this.step);
    }

    public boolean firstCommand(){
        return step == 0;
    }
    public boolean noMoreCommands(){
        return step >= splice.length();
    }
    private void decoder(String control){
//        if (splice.isEmpty()){return;}
        switch(control){
            case "l":
                System.out.println("Command turn left!");
                this.motorHelper.turn_left();
                break;
            case "r":
                System.out.println("Command turn right!");
                this.motorHelper.turn_right();
                break;
            case "v":
                System.out.println("Command go forward!");
                this.motorHelper.forwards();
                break;
//            case "n":
//                motorHelper.hardStop();
//                break;
            case "t":
                motorHelper.turnAround();
                break;
            case "o":
                claw.open();
                break;
            case "c":
                claw.close();
                break;
        }
//        spliceBack += control;
//            if (Objects.equals(control, "b") && String.valueOf(splice.charAt(splice.length() - 1)).equals("b") && (control + 1).isEmpty()){splitterBack(spliceBack);}
    }

    public int getStep() {
        return this.step;
    }
//    public void splitterBack(String spliceBack){
//        if (backStep >= this.spliceBack.length()){System.out.println("no commands left");}
//        else {
//            backControl = String.valueOf(this.spliceBack.charAt(backStep));
//            returning(backControl);
//            backStep++;
//        }
//    }

}