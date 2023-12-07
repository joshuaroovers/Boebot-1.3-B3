package actuators;

import TI.BoeBot;
import head.Updateable;

public class Motor extends TI.Servo implements Updateable {

    public Motor(int pin){
        super(pin);
    }

    @Override
    public void update() {

    }
}
