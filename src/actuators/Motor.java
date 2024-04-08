package actuators;

import TI.Servo;
import TI.Timer;
import head.NeoPixelHelper;
import head.Updateable;

public class Motor implements Updateable {

    private int pin;
    private int currentSpeed;
    private int targetSpeed;
    private Timer accellerateTimer;
    private Servo servo;
    private int speedStep;
    private boolean gradualIncrement;
    private String currentTurn;
    private NeoPixelHelper neoPixelHelper;

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
        this.accellerateTimer = new Timer(200);
        this.targetSpeed = targetSpeed;
    }

    public void setGradualIncrement(boolean state) {
        this.gradualIncrement = state;
    }
    public void setTurnLight(String turnLight){
        currentTurn = turnLight;
    }

    //public void hardStop(){this.targetSpeed = 1500; this.currentSpeed = 1500 + this.speedStep;}
    @Override
    public void update()
    {
        if(currentSpeed == targetSpeed){
            this.gradualIncrement = false;
            return;
        }


        if(!accellerateTimer.timeout())
            return;

        if(gradualIncrement){
            neoPixelHelper.turnLight(currentTurn);
            int speedDifference = targetSpeed - currentSpeed;
            if(speedDifference > speedStep)
                speedDifference = speedStep;
            else if (speedDifference < -speedStep)
                speedDifference = -speedStep;

            currentSpeed += speedDifference;
        }else{
            currentSpeed = targetSpeed;
        }


        this.servo.update(currentSpeed);


        //System.out.println("Current speed ("+this.pin+"): " + currentSpeed);
    }
}
