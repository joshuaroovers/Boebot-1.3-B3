package head;

import actuators.Claw;
import actuators.Motor;

import java.util.ArrayList;
import java.util.Objects;

public class Splitter implements Updateable{
    /**
     * Splitter
     * @author Morris Woestenburg
     * @param command
     * splits the command from the GUI into individual characters that call on the motor methods
     */
    public void splitter(String command){

        String splice = command;
        ArrayList<String> split = new ArrayList<>();
        for (int i=0;i<splice.length();i++) {
            split.add(String.valueOf(splice.charAt(0)));
            String control = split.get(0);
            split.remove(split.get(0));
            if (Objects.equals(control, "l")){MotorHelper.turn_left();}
            if (Objects.equals(control, "r")){MotorHelper.turn_right();}
            if (Objects.equals(control, "v")){MotorHelper.forwards();}
            if (Objects.equals(control, "n")){Motor.hardStop();}
            if (Objects.equals(control, "e,")){MotorHelper.stop();}
            if (Objects.equals(control, "o")){Claw.open();}
            if (Objects.equals(control, "c")){Claw.close();}
        }
    }

    @Override
    public void update() {

    }
}
