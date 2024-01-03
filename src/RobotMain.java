import TI.BoeBot;
import head.Controller;

public class RobotMain {
    static Controller engine = new head.Controller();


    public static void main(String[] args) {

        Controller engine = new head.Controller();
        engine.init();
        while(true){
            engine.run();
        }

    }
}
