package lv.sanelite.initium.util;

public class RGB {
    public static int color(int red, int green, int blue){
        return blue + ( green * 256 ) + ( red * 65536 );
    }
}
