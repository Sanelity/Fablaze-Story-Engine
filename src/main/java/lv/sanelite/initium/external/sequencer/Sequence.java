package lv.sanelite.initium.external.sequencer;

import java.util.LinkedList;
import java.util.function.Consumer;

public class Sequence {
    public static LinkedList<Action> actions = new LinkedList<>();

    public void addAction(Consumer<Object> function){
        actions.add(new Action(function));
    }
    public void perform(){
        if(!actions.isEmpty()){
            actions.pop().play();
        }
    }
    public void clear(){
        actions.clear();
    }


}
