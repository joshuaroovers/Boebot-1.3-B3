package actuators;

import TI.BoeBot;
import TI.Servo;
import TI.Timer;
import head.Updateable;

public class Motor implements Updateable {

    private int pin;
    private int currentSpeed;
    private int targetSpeed;
    private Timer accellerateTimer;
    private Servo servo;
    private int speedStep;
    //private int stepPerSec;
    public Motor(int pin, int speedStep)
    {
        //super(pin);

        this.pin = pin;
        this.speedStep = speedStep;
        this.servo = new Servo(pin);
        this.currentSpeed = 1500; //still
        this.targetSpeed = this.currentSpeed;
        accellerateTimer = new Timer(200);
    }
    public void setSpeed(int targetSpeed)
    {
        this.targetSpeed = targetSpeed;
    }

    public void hardStop(){this.targetSpeed = 1500; this.currentSpeed = 1500 + this.speedStep;}
    @Override
    public void update()
    {
        if(currentSpeed == targetSpeed)
            return;

        if(!accellerateTimer.timeout())
            return;

        int speedDifference = targetSpeed - currentSpeed;
        if(speedDifference > speedStep)
            speedDifference = speedStep;
        else if (speedDifference < -speedStep)
            speedDifference = -speedStep;

        currentSpeed += speedDifference;

        this.servo.update(currentSpeed);


        System.out.println("Current speed ("+this.pin+"): " + currentSpeed);
    }
}
