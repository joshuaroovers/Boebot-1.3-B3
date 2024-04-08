package head;

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
    private int step;
//    private int backStep;
    private LineDetector lineDetector;
    public Splitter(MotorHelper motorHelper){
        this.motorHelper = motorHelper;
        this.splice="";
    }

    public void setSplice(String splice) {
        this.splice = splice;
        this.step = 0;
        System.out.println("splice init step:" + this.step);
//        this.spliceBack = spliceBack;
    }

    /**
     * Splitter
     * @author Morris Woestenburg
     * splits the command from the GUI into individual characters that call on the motor methods
     */
    public void commandStep(){
        if (step >= splice.length()){/*System.out.println("no commands left");*/}
        else {
            control = String.valueOf(splice.charAt(step));
            decoder(control);
            step++;
        }
    }

    public boolean firstCommand(){
        if(String.valueOf(splice.charAt(0)).equals("o") || String.valueOf(splice.charAt(0)).equals("c")){
            return step <= 1;
        }else{
            return step == 0;
        }

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
                this.motorHelper.turnAround();
                break;
            case "o":
                System.out.println("Command open claw!");
                this.motorHelper.clawOpen();
                break;
            case "c":
                System.out.println("Command close claw!");
                this.motorHelper.clawClose();
                break;
        }
//        spliceBack = control + spliceBack;
//            if (control.equals("b") && String.valueOf(splice.charAt(splice.length() - 1)).equals("b") && String.valueOf(splice.charAt(step + 1)).isEmpty()){splitterBack(spliceBack);}
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