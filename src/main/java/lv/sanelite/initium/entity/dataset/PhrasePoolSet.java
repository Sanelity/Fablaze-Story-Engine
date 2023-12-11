package lv.sanelite.initium.entity.dataset;

public class PhrasePoolSet {
    private final PhrasePool talkPool;
    private final PhrasePool onHurtPool;

    public PhrasePoolSet(PhrasePool defaultPool, PhrasePool onHurt){
        this.talkPool = defaultPool;
        this.onHurtPool = onHurt;
    }
    public static PhrasePoolSet loadPool(String default_name, String hurt_name){
        return new PhrasePoolSet(
                PhrasePool.getPoolByName(default_name),
                PhrasePool.getPoolByName(hurt_name)
        );
    }

    public String getTalk(){
        return this.talkPool.getPhraseByPoolType();
    }
    public String getHurt(){
        return this.onHurtPool.getPhraseByPoolType();
    }

}
