package sensors;

import TI.BoeBot;
import TI.PinMode;

public class Button {

    public Button(int pin){
        BoeBot.setMode(pin, PinMode.Input);
    }
}
