package sensors;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import head.Updateable;

public class LineDetector implements Updateable {
    Timer timer = new Timer(100);
    int adcPin;
    public LineDetector(int adcPin) {
        this.adcPin = adcPin;
        BoeBot.setMode(adcPin, PinMode.Input);
    }

    @Override
    public void update() {
        if (timer.timeout())
            System.out.println(adcPin + ": " + BoeBot.analogRead(adcPin));
    }
}
