package head;

import TI.BoeBot;
import java.awt.*;
import static java.awt.Color.*;
import  TI.Timer;
import actuators.NeoPixel;

public class NeoPixelHelper {
    private boolean state;
    private Timer time;
    private boolean blink=true;
    private int position=1;
    private NeoPixel pixelLeft;
    private NeoPixel pixelRight;
    private NeoPixel pixelBack;

    public NeoPixelHelper(NeoPixel pixelLeft, NeoPixel pixelRight, NeoPixel pixelBack){
        this.pixelLeft = pixelLeft;
        this.pixelRight = pixelRight;
        this.pixelBack = pixelBack;

        this.time = new Timer(1);
    }
    public void lights(){time.setInterval(500);
            if (!time.timeout()){
                for (int i=0;i<=5;i++){
                    if ((i%2==0)==blink){BoeBot.rgbSet(i,black);}
                    else {BoeBot.rgbSet(i,white);}
                }
                blink = !blink;
                BoeBot.rgbShow();
            }
        for (int i =0;i<=5;i++){
            BoeBot.rgbSet(i, black);
        }
        BoeBot.rgbShow();
    }
//    public void pix(int pixel,Color colour){
//        for (int i=0;i<=5;i++){BoeBot.rgbSet(i,black);}
//        BoeBot.rgbSet(pixel,colour);
//        BoeBot.rgbShow();
//    }
//    private void pixBlink(NeoPixel pixel,Color colour){
//
//            if (pixel.isState()){
//                pixel.setColor(colour);
//            }
//            else {
//                pixel.setColor(black);
//            }
//            pixel.toggleState();
//            pixel.setColor(black);
//
//    }
    public void reset(){
        time.setInterval(2000);
        if (!time.timeout()){
        if (position==0){BoeBot.rgbSet(position,green);BoeBot.rgbSet(5,black);}
        else {BoeBot.rgbSet(position,green);BoeBot.rgbSet(position-1,black);}
        BoeBot.rgbShow();
        position++;
        position %= 6;
        }
    }
    public void rightLight(){
        this.pixelRight.setBlink(red, black);
         }
    public void leftLight(){
        this.pixelLeft.setBlink(red,black);
    }
    public void backLight(){
        this.pixelBack.setBlink(white, black);
    }
    public void stopLight(){
        this.pixelLeft.on(black);
        this.pixelRight.on(black);
        this.pixelBack.on(black);
    }
}