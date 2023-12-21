package actuators;

import TI.BoeBot;
import TI.Servo;
import TI.Timer;
import head.Updateable;

public class Claw implements Updateable {
    private int pin;
    private int currentSpeed;
    private int targetSpeed;
    private int speedStep;
    private Timer timer;
    private Servo s3;
    public Claw(int pin, int speedStep){
        this.speedStep = speedStep;
        this.pin = pin;
        this.s3 = new Servo(pin);
    }
    public void open(){
        this.targetSpeed = 1800;
    }

    public void close(){
        this.targetSpeed = 825;
    }

    @Override
    public void update() {
        timer = new Timer(100);
        if(currentSpeed == targetSpeed)
            return;

        if(!timer.timeout())
            return;

        int speedDifference = targetSpeed - currentSpeed;
        if(speedDifference > speedStep)
            speedDifference = speedStep;
        else if (speedDifference < -speedStep)
            speedDifference = -speedStep;

        currentSpeed += speedDifference;

        this.s3.update(currentSpeed);


        System.out.println("Current speed "+ this.pin +": "+ currentSpeed);
    }
}
