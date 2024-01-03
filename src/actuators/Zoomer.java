package actuators;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import head.Updateable;

public class Zoomer implements Updateable {

    private int buz;
    private boolean state;
    private boolean close;
    Timer timer;

    /**
     * @author Stijn de Vos
     * @since 28-11-2023
     * the tones A and D minor because the sound more pleasant over the buzzer.
     */
    private final int A = 466;
    private final int D = 294;

    public Zoomer (int buz){
        this.buz = buz;
        this.timer = new Timer(400);
        this.state = false;
        this.close = false;

        BoeBot.setMode(this.buz, PinMode.Output);
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    /**
     * @author Stijn de Vos
     * @since 07-12-2023
     * the Zoomer will only send out a signal if the ultrasonicSensor gives the signal that there is an obstacle
     * if an obstacle is detected the Zoomer wil give a sound like a siren to signal the obstacle to move.
     */
    @Override
    public void update() {
        if (!timer.timeout()){
            return;
        }

        if (close){
            if (state){
                BoeBot.freqOut(this.buz, A, 400);
                state = !state;
            }
            else{
                BoeBot.freqOut(this.buz, D, 400);
                state = !state;
            }
        }

    }
}
