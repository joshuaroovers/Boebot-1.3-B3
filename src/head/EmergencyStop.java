package head;

import TI.BoeBot;
import TI.PinMode;
import sensors.Button;


public class EmergencyStop{

    private int triggerPin;
    private Button button;

    public EmergencyStop(int triggerPin){
        this.button = new Button(triggerPin);
        this.triggerPin = triggerPin;
    }

    /**
     * check()
     * check for emergency stop trigger from triggerPin (usually a button)
     * @return boolean whether the emergency stop trigger has been triggered
     */
    public boolean check(){

        if(!BoeBot.digitalRead(this.triggerPin)){
            return true;
        }
        return false;
    }
}
