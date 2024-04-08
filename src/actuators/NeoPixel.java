package actuators;

import TI.BoeBot;
import TI.Timer;
import head.Updateable;

import java.awt.*;

import static java.awt.Color.black;

public class NeoPixel implements Updateable {
    private int pixel;
    private boolean state;

    private boolean blink;
    private Color blinkOn;
    private Color blinkOff;

    private Timer timer;
    public NeoPixel(int pixel) {
        this.blink = false;
        this.pixel = pixel;

        this.timer = new Timer(1);
    }

    public void on(Color color){
        this.blink = false;
        setColor(color);
    }
    private void setColor(Color color){
        BoeBot.rgbSet(this.pixel, color);
    }

    public void setBlink(Color color1, Color color2) {
        this.blink = true;
        this.blinkOn = color1;
        this.blinkOff = color2;
        this.state = true;
        timer.setInterval(250);
    }

    /** @auteur Morris Woestenburg ,Joshua Roovers
     * turns the neoPixel on and off every 0.25 seconds, after which it resets the timer.
     */
    private void blink(){

        if (this.state){
            //System.out.println("light on");
            setColor(this.blinkOn);
            BoeBot.rgbShow();
            if(this.timer.timeout()) {
                //System.out.println("true state, toggle light");
                toggleState();
                this.timer.setInterval(250);
            }
        }else{

            //System.out.println("light off");
            setColor(this.blinkOff);
            BoeBot.rgbShow();
            if(this.timer.timeout()){
                //System.out.println("false state, toggle light");
                toggleState();
                this.timer.setInterval(250);
            }
        }
        setColor(this.blinkOff);
        BoeBot.rgbShow();

    }

    public boolean isState() {
        return state;
    }

    public void toggleState() {
        this.state = !this.state;
    }

    @Override
    public void update() {
        //System.out.println("pixel " + this.pixel + " update");
        if(blink){
            blink();
        }
        BoeBot.rgbShow();

    }
}
