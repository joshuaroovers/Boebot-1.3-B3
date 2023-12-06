package actuators;

import TI.BoeBot;
import TI.Timer;

import java.awt.*;

import static java.awt.Color.black;

public class NeoPixel {
    private Color colour;
    //    private int pixel;
    private boolean state;
    private Timer time;

    public NeoPixel(){
        this.time = new Timer(1);
    }
    public void lights(){time.setInterval(500);
        if (state) {
            while (!time.timeout()){
                for (int i=0;i<=5;i++){
                    if (i%2==0){BoeBot.rgbSet(i,black);}
                    else {BoeBot.rgbSet(i,colour);}
                }
                BoeBot.rgbShow();
            }
            while (!time.timeout()){
                for (int i=0;i<=5;i++){
                    if (i%2==1){BoeBot.rgbSet(i,black);}
                    else {BoeBot.rgbSet(i,colour);}
                }
                BoeBot.rgbShow();
            }
        }
        for (int i =0;i<=5;i++){
            BoeBot.rgbSet(i, black);
        }
        BoeBot.rgbShow();
    }
    public void setNeoPixel(Color colour, boolean state){
        this.colour = colour;
        this.state=state;
    }
    public Color getNeoPixelColor(){return this.colour;}
    //    public int getNeoPixelPin(){return this.pixel;}
    public boolean getNeoPixelState() {return this.state;}

}

