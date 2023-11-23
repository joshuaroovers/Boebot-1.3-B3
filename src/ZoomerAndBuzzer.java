import TI.BoeBot;
import TI.PinMode;

public class ZoomerAndBuzzer {

    public static void main(String[] args) {

        int button = 0;
        int buz1 = 2;
        int led = 6;

        BoeBot.setMode(button, PinMode.Input);
        BoeBot.setMode(buz1, PinMode.Output);
        BoeBot.setMode(led, PinMode.Output);

        Boolean buttonstate;
        Boolean state = true;
        int A = 466;
        int B = 494;
        int C = 262;
        int D = 294;
        int E = 330;
        int F = 349;
        int G = 392;

        while (true) {
            buttonstate = !BoeBot.digitalRead(button);

            if (buttonstate){
                for (int i = 0; i < 5; i++){
                    state = !state;
                    BoeBot.digitalWrite(led, state);
                    BoeBot.wait(200);
                }

                BoeBot.freqOut(buz1, A, 200);
                BoeBot.wait(10);
                BoeBot.freqOut(buz1, B, 200);
                BoeBot.wait(50);
                BoeBot.freqOut(buz1, D, 100);
                BoeBot.wait(10);
                BoeBot.freqOut(buz1, B, 200);
                BoeBot.wait(50);
                BoeBot.freqOut(buz1, F, 100);
                BoeBot.wait(25);
                BoeBot.freqOut(buz1, F, 100);
                BoeBot.wait(25);
                BoeBot.freqOut(buz1, E, 200);
            }

            BoeBot.wait(500);
        }
    }
}
