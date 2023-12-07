package actuators;

import TI.BoeBot;
import java.awt.*;
import static java.awt.Color.*;
import  TI.Timer;

public class NeoPixel {
    private Color colour;
    private int pixel;
    private boolean state;
    private Timer time;
    private boolean blink=true;

    public NeoPixel(){
        this.time = new Timer(1);
    }
    public void lights(){time.setInterval(500);
        if (state) {
            if (!time.timeout()){
                for (int i=0;i<=5;i++){
                    if ((i%2==0)==blink){BoeBot.rgbSet(i,black);}
                    else {BoeBot.rgbSet(i,colour);}
                }
                blink = !blink;
                BoeBot.rgbShow();
            }
        }
        for (int i =0;i<=5;i++){
            BoeBot.rgbSet(i, black);
        }
        BoeBot.rgbShow();
    }
    public void pix(){
        for (int i=0;i<=5;i++){BoeBot.rgbSet(i,black);}
        BoeBot.rgbSet(pixel,colour);
        BoeBot.rgbShow();
    }
    public void setNeoPixel(Color colour, boolean state){
        this.colour = colour;
        this.state = state;
    }
    public Color getNeoPixelColor(){return this.colour;}
    public void setSingleNeoPixelPin(int pixel,Color colour){
        this.pixel = pixel;
        this.colour = colour;
    }
    public int getPixel(){return  this.pixel;}
    public boolean getNeoPixelState() {return this.state;}

}