package sensors;

import TI.BoeBot;
import TI.PinMode;
import head.Updateable;

public class IRSensor implements Updateable {
    private IRSensorCallback irRemoteCallback;
    private String line;
    private int inputPin;

    public IRSensor(IRSensorCallback irRemoteCallback, int inputPin) {
        this.irRemoteCallback = irRemoteCallback;
        this.inputPin = inputPin;
        BoeBot.setMode(inputPin, PinMode.Input); //pin 9


    }

    @Override
    public void update() {
        line = "";
        int value = 0;
        int pulseLen = BoeBot.pulseIn(inputPin, false, 6000);
        if (pulseLen > 2000) {
            int[] lengths = new int[12];
            for (int i = 0; i < 12; i++) {
                lengths[i] = BoeBot.pulseIn(inputPin, false, 20000);
            }
            for (int length : lengths) {
                if (length > 800) {
                    line += 1;
                } else {
                    line += 0;

                }
            }


            irRemoteCallback.onSignal(line);
        }
    }
}
