package sensors;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import head.Updateable;

public class Ultrasonic implements Updateable {
        Timer timer;
        UltrasonicCallback callback;
        private final int triggerPin;
        private final int echoPin;

        public Ultrasonic(int triggerPin, int echoPin, UltrasonicCallback callback)
        {
            this.triggerPin= triggerPin;
            this.echoPin = echoPin;
            this.callback = callback;
            this.timer = new Timer(1000 / 5);

            BoeBot.setMode(triggerPin, PinMode.Output);
            BoeBot.setMode(echoPin, PinMode.Input);
        }

    public void setTimer(int timer) {
        this.timer = new Timer(1000/timer);
    }

    @Override
        public void update()
        {
            if(!timer.timeout()) {
                return;
            }
            //er word hier gebruik gemaakt van BoeBot.wait want ik wou geen timer functie in de timer aan roepen
            //daar zag ik te veel problemen ontstaan.
            BoeBot.digitalWrite(triggerPin, true);
            BoeBot.wait(1);
            BoeBot.digitalWrite(triggerPin, false);

            int pulse = BoeBot.pulseIn(echoPin, true, 10000);
            double distance = (double)pulse / 58;

            callback.onUltrasonic(distance);
        }
    }

