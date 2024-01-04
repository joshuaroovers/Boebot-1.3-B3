package head;

import actuators.Claw;
import actuators.Motor;
import sensors.LineDetector;

import java.util.ArrayList;
import java.util.Objects;

public class Splitter implements Updateable{

    private  String splice;
    private ArrayList<String> split;
    private String control;
    private MotorHelper motorHelper;
    private Claw claw;
    private int step;
    private LineDetector lineDetector;
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

//            if (Objects.equals(control, "r")){motorHelper.turn_left();}
//            if (Objects.equals(control, "l")){motorHelper.turn_right();}
//            if (Objects.equals(control, "v")){motorHelper.forwards();}
//            if (Objects.equals(control, "n")){motorHelper.hardStop();}
//            if (Objects.equals(control, "e,")){motorHelper.stop();}
//            if (Objects.equals(control, "o")){claw.open();}
//            if (Objects.equals(control, "c")){claw.close();}
//        }
//    }
    /**
     * Splitter
     * @author Morris Woestenburg
     * splits the command from the GUI into individual characters that call on the motor methods
     */
    private void splitter(){
        for (int i=0;i<splice.length();i++) {

            control = String.valueOf(splice.charAt(i));
            decoder(control);
        }
    }
    private void decoder(String control){
//        if (splice.isEmpty()){return;}
        if (Objects.equals(control, "l")){motorHelper.turn_left();}
        if (Objects.equals(control, "r")){motorHelper.turn_right();}
        if (Objects.equals(control, "v")){motorHelper.forwards();}
        if (Objects.equals(control, "n")){motorHelper.hardStop();}
        if (Objects.equals(control, "e,")){motorHelper.stop();}
        if (Objects.equals(control, "o")){claw.open();}
        if (Objects.equals(control, "c")){claw.close();}
//            if (Objects.equals(control, "b") && String.valueOf(splice.charAt(splice.length() - 1)).equals("b") && (control + 1).isEmpty()){returning(splice);}
//            else{continue;}
    }

    @Override
    public void update() {

    }
}

