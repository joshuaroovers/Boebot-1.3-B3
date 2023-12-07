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
    public Motor(int pin)
    {
        //super(pin);

        this.pin = pin;
        this.servo = new Servo(pin);
        this.currentSpeed = 0;
        accellerateTimer = new Timer(1000);
    }
    public void setSpeed(int targetSpeed)
    {
        this.targetSpeed = targetSpeed;
    }
    @Override
    public void update()
    {
        if(currentSpeed == targetSpeed)
            return;

        if(!accellerateTimer.timeout())
            return;

        int speedDifference = targetSpeed - currentSpeed;
        if(speedDifference > 10)
            speedDifference = 10;
        else if (speedDifference < -10)
            speedDifference = -10;

        currentSpeed += speedDifference;

        this.servo.update(currentSpeed);


        System.out.println("Current speed: " + currentSpeed);
    }
}
