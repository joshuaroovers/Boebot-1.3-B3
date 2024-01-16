package sensors;

import TI.BoeBot;
import TI.PinMode;
import head.Updateable;

public class IRSensor implements Updateable {
    private IRSensorCallback irRemoteCallback;
    private String line;
    private int inputPin;

    public IRSensor(IRSensorCallback irRemoteCallback, int inputPin) {
        this.irRemoteCallback = irRemoteCallback;
        this.inputPin = inputPin;
        BoeBot.setMode(inputPin, PinMode.Input); //pin 9


    }

    @Override
    public void update() {
        //System.out.println("IR update");

        int pulseLen = BoeBot.pulseIn(inputPin, false, 6000);
        if (pulseLen > 2000) {
//            System.out.println("ir input!");

            int[] lengths = new int[12];
            for (int i = 0; i < 12; i++) {
                lengths[i] = BoeBot.pulseIn(inputPin, false, 20000);
            }

            //convert the signals to binary and add them to a StringBuilder excluding the last 5 signals because they're the remote id and not needed
            StringBuilder line = new StringBuilder();
            for (int i = 0; i <lengths.length-5 ; i++) {
                if (lengths[i] > 800) {
                    line.append(1);
                } else {
                    line.append(0);
                }
            }
//            System.out.println("raw binary");
//            System.out.println(line);

//            String reversedLine = line.reverse().toString();
//            System.out.println("reversedLine:");
//            System.out.println(reversedLine);

            //invert the StringBuilder string to get the correct binary and convert to an integer
            int value = Integer.parseInt(line.reverse().toString(),2);
//            System.out.println("int version");
//            System.out.println(value);

            irRemoteCallback.onSignal(value);
        }
    }
}
