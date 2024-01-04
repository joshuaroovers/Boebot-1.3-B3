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
    private int position=1;

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
    public void pix(int pixel,Color colour){
        for (int i=0;i<=5;i++){BoeBot.rgbSet(i,black);}
        BoeBot.rgbSet(pixel,colour);
        BoeBot.rgbShow();
    }
    private void pixBlink(int pixel,Color colour){
        time.setInterval(250);
        for (int i=0;i<=5;i++){BoeBot.rgbSet(i,black);}
        if (!time.timeout()){
            if (state){
                BoeBot.rgbSet(pixel,colour);
                BoeBot.rgbShow();}
            else {
                BoeBot.rgbSet(pixel,black);
                BoeBot.rgbShow();
            }
            state=!state;
            BoeBot.rgbSet(pixel,black);
            BoeBot.rgbShow();
        }
    }
    public void reset(){
        if (position==1){BoeBot.rgbSet(position,green);BoeBot.rgbSet(6,black);}
        else {BoeBot.rgbSet(position,green);BoeBot.rgbSet(position-1,black);}
        BoeBot.rgbShow();
        position++;
        if (position > 7){position = 1;}
    }
    public void rightLight(){pixBlink(6,red);}
    public void leftLight(){pixBlink(4,red);}
    public void backLight(){pixBlink(1,white);pixBlink(3,white);}
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
