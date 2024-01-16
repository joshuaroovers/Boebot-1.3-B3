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

    /**
     * @author Stijn de Vos
     * @since 04-12-2023
     * dit is de code die kijkt hoe ver je van een obstacel af ben.
     * er zit helaas een wait in de code maar die kon ik niet vermeiden, als het mogenlijk is om dezen er
     * uit te halen zal ik later terug komen.
     */
    @Override
        public void update()
        {
            if(!timer.timeout()) {
                return;
            }
            BoeBot.digitalWrite(triggerPin, true);
            BoeBot.wait(1);
            BoeBot.digitalWrite(triggerPin, false);

            int pulse = BoeBot.pulseIn(echoPin, true, 10000);
            double distance = (double)pulse / 58;

            callback.onUltrasonic(distance);
        }
    }

