package lv.sanelite.initium.external.sequencer;


import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class TimerThread extends Thread{
    private final long sleepTime;

    public TimerThread(long miliseconds){
        this.sleepTime = miliseconds;
    }

    public void run(){
        try {
            sleep(sleepTime);
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }finally {
            Sequence.actions.pop().play();
        }
    }
}
