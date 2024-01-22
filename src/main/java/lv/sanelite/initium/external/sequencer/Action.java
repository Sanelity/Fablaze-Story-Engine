package lv.sanelite.initium.external.sequencer;

import java.util.function.Consumer;

public class Action {
    Consumer<Object> act;

    public Action(Consumer<Object> function){
        this.act = function;
    }

    public void play(){
        this.act.accept(act);

    }

}
