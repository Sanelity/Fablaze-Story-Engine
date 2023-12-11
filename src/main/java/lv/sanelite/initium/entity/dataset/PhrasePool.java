package lv.sanelite.initium.entity.dataset;

import java.util.Map;
import java.util.Random;

public class PhrasePool {
    public static Map<String, PhrasePool> phrasePoolMap = Map.of(
            "default", new PhrasePool(Map.of(
                    0,"О, привет!",
                    1,"Неплохая сегодня погодка, верно?",
                    2,"Как думаешь, завтра будет солнечно?",
                    3,"Хм, у тебя что-то случилось?"),
                    SequenceType.RANDOM),
            "angeloid",new PhrasePool(Map.of(
                    0,"Мне кажется, что тебе лучше уйти сейчас.",
                    1,"Отстань по-хорошему!",
                    2,"§l*Громкая тишина*§r",
                    3,"Никакие \"Божественные\" силы нам не свойственны, я просто технология",
                    4,"Пожалуйста, не раздражай!"),
                    SequenceType.RANDOM),
            "hurt", new PhrasePool(Map.of(
                    0,"Эй, мне как бы больно!",
                    1,"Хватит издеваться!",
                    2,"Бей тех, кто Тебе сделал больно, но не меня!"
            ), SequenceType.RANDOM)
    );

    public static PhrasePool getPoolByName(String poolName){
        if(phrasePoolMap.containsKey(poolName)){
            return phrasePoolMap.get(poolName);
        }else return phrasePoolMap.get("default");
    }

    private final Map<Integer,String> phraseBase;
    private final SequenceType phraseSelector;
    private int useCount = 0;

    private final Random randomSource = new Random();

    private PhrasePool(Map<Integer, String> pool, SequenceType type){
        this.phraseBase = pool;
        this.phraseSelector = type;
    }
    public static void newPhrasePool(String poolName,Map<Integer, String> pool, SequenceType type){
        PhrasePool phrasePool = new PhrasePool(pool, type);
        phrasePoolMap.put(poolName, phrasePool);
    }
    public static void addPhrase(String poolName, String phrase){
        PhrasePool thisPool = getPoolByName(poolName);
        thisPool.phraseBase.put(thisPool.phraseBase.size(),phrase);
    }

    public int getPoolSize(){
        return this.phraseBase.size();
    }
    public SequenceType getPoolType() {
        return this.phraseSelector;
    }


    public String getPhraseByPoolType(){


        int k = this.getPoolSize();
        if(this.getPoolType() == SequenceType.RANDOM){
            return this.phraseBase.get(randomSource.nextInt(k));
        }else {
            String phrase = this.phraseBase.get(useCount % k); this.useCount++;
            return phrase;
        }
    }
    public String getPhraseByID(PhrasePool pool, int ID){
        return pool.phraseBase.get(ID % pool.getPoolSize());
    }


}
