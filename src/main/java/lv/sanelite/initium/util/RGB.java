package lv.sanelite.initium.util;

public class RGB {
    public static int color(int red, int green, int blue){
        return blue + ( green * 256 ) + ( red * 65536 );
    }

    private final int redValue;
    private final int blueValue;
    private final int greenValue;

    public RGB(int red, int green, int blue){
        this.redValue = red;
        this.greenValue = green;
        this.blueValue = blue;
    }

    public int getRed() {
        return redValue;
    }
    public float getFloatRed(){
        return (float) getRed() / 255;
    }

    public int getGreen() {
        return greenValue;
    }
    public float getFloatGreen(){
        return (float) getGreen() / 255;
    }

    public int getBlue() {
        return blueValue;
    }
    public float getFloatBlue(){
        return (float) getBlue() / 255;
    }

    public int getIntegerColor(){
        return color(this.redValue,this.greenValue,this.blueValue);
    }

}
