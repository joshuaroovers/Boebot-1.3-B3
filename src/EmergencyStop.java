import TI.BoeBot;
import TI.PinMode;


public class EmergencyStop{

    private int triggerPin;

    public EmergencyStop(int triggerPin){
        this.triggerPin = triggerPin;
        BoeBot.setMode(triggerPin, PinMode.Input);
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
