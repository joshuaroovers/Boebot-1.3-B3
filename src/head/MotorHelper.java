package head;

import actuators.Motor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MotorHelper {
    private Motor s1;
    private Motor s2;

    public MotorHelper(Motor s1, Motor s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    /**
     * Splitter
     * @author Morris Woestenburg
     * @param command
     * splits the command from the GUI into individual characters that call on the motor methods
     */
    public void Splitter(String command){
        String splice = command;
        ArrayList<String> split = new ArrayList<>();
        split.add(String.valueOf(splice.charAt(0)));
        for (int i=0;i<command.length();i++) {
            String control = split.get(i);
            if (Objects.equals(control, "l")){turn_left();}
            if (Objects.equals(control, "r")){turn_right();}
            if (Objects.equals(control, "v")){forwards();}
            if (Objects.equals(control, "e,")){stop();}
        }
    }

    public void Wheels(int Wheel1, int Wheel2){
        this.s1.setSpeed(Wheel1);
        this.s2.setSpeed(Wheel2);
    }
    public void turn_right(){
        Wheels(1300,1525);
    }
    public void turn_left(){
        Wheels(1525,1300);
    }
    public void backwards(){
        Wheels(1300,1700);
    }
    public void forwards(){
        System.out.println("Wheels forwards");
        Wheels(1700,1300);
    }
    public void stop(){
        System.out.println("stop");
        Wheels(1500,1500);
    }

    public void hardStop(){
        System.out.println("Hard stop");
        this.s1.hardStop();
        this.s2.hardStop();
    }
}