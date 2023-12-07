package Sensors;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import head.Updateable;
import sensors.UltraSonicCallback;

public class Ultrasonic implements Updateable
{
    Timer timer;
    UltraSonicCallback callback;
    private int triggerPin;
    private int echoPin;

    public Ultrasonic(int triggerPin, int echoPin, UltraSonicCallback callback)
    {
        this.triggerPin= triggerPin;
        this.echoPin = echoPin;
        this.callback = callback;
        this.timer = new Timer(1000 / 5);

        BoeBot.setMode(triggerPin, PinMode.Output);
        BoeBot.setMode(echoPin, PinMode.Input);
    }

    /**
     * @author Stijn de Vos
     * @since 02-12-2023
     * here is the methode the the ultrasonic sensor uses to measure distance between itself and an object.
     * this distance is then given to the ultraSonicCallback so that ik may later be used in
     * the rest of the program.
     *
     * there is a wait here in the code. despite that we had agreed that we wouldn't use wait commands.
     * I could not figure out a way to replace this with a timer without causing problems later in the code
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

        callback.onUltraSone(distance);
    }
}
