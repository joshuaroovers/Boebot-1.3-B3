package head;

import actuators.Claw;
import actuators.Motor;

import java.util.ArrayList;
import java.util.Objects;

public class Splitter implements Updateable{

    private  String splice;
    private ArrayList<String> split;
    private String control;
    public Splitter(){
    }

    public void setSplice(String splice) {
        this.splice = splice;
    }

    //    public void returning(String command){
//        String splice = command;
//        ArrayList<String> split = new ArrayList<>();
//        for (int i=0;i<splice.length();i++) {
//            split.add(String.valueOf(splice.charAt(splice.length()-1)));
//            String control = split.get(0);
//            split.remove(split.get(0));
//            if (Objects.equals(control, "r")){MotorHelper.turn_left();}
//            if (Objects.equals(control, "l")){MotorHelper.turn_right();}
//            if (Objects.equals(control, "v")){MotorHelper.forwards();}
//            if (Objects.equals(control, "n")){Motor.hardStop();}
//            if (Objects.equals(control, "e,")){MotorHelper.stop();}
//            if (Objects.equals(control, "o")){Claw.open();}
//            if (Objects.equals(control, "c")){Claw.close();}
//        }
//    }
    /**
     * Splitter
     * @author Morris Woestenburg
     * splits the command from the GUI into individual characters that call on the motor methods
     */
    @Override
    public void update() {
        if (splice.isEmpty()){return;}
        split = new ArrayList<>();
        for (int i=0;i<splice.length();i++) {
            split.add(String.valueOf(splice.charAt(0)));
            control = split.get(0);
            split.remove(split.get(0));
            if (Objects.equals(control, "l")){MotorHelper.turn_left();}
            if (Objects.equals(control, "r")){MotorHelper.turn_right();}
            if (Objects.equals(control, "v")){MotorHelper.forwards();}
            if (Objects.equals(control, "n")){Motor.hardStop();}
            if (Objects.equals(control, "e,")){MotorHelper.stop();}
            if (Objects.equals(control, "o")){Claw.open();}
            if (Objects.equals(control, "c")){Claw.close();}
//            if (Objects.equals(control, "b") && String.valueOf(splice.charAt(splice.length() - 1)).equals("b") && (control + 1).isEmpty()){returning(splice);}
//            else{continue;}
        }
    }
}
