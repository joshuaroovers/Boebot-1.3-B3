import TI.BoeBot;
import actuators.Motor;
import head.Controller;
import head.MotorHelper;
import head.Updateable;
import sensors.Button;
import sensors.ButtonCallback;
import sensors.IRSensor;
import sensors.IRSensorCallback;

import java.util.ArrayList;

public class RobotMain {
    static Controller engine = new head.Controller();
    static boolean state = true;

    public static void main(String[] args) {
        engine.init();

        ArrayList<Updateable> mainUpdatables = new ArrayList<>();
        //todo should find a better way to initialize these components instead of doingint it twice (here and in controller)
        Motor leftMotor;
        mainUpdatables.add(leftMotor = new Motor(12, 15));
        Motor rightMotor;
        mainUpdatables.add(rightMotor = new Motor(13, 15));
        MotorHelper motorHelper = new MotorHelper(leftMotor, rightMotor, null, 60, null, null);

        IRSensor irSensor;
        mainUpdatables.add(irSensor = new IRSensor(engine, 6));


        while (true) {

            while (!engine.getRunning()) {
                System.out.println("system standby");
                if (!BoeBot.digitalRead(1))
                    engine.startUp();
                for (Updateable updateable: mainUpdatables) {
                    updateable.update();
                }
//                state = !state;
//                BoeBot.digitalWrite(5, state);
//                BoeBot.digitalWrite(4, !state);
//                BoeBot.wait(400);
            }
            if (engine.emergencyStop.check())
                engine.setRunning(false);
            engine.update();
            BoeBot.wait(1);
        }
    }
}
