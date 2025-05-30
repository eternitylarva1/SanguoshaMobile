package androidTestMod.actions.common;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Random;

public class SoundAction extends AbstractGameAction {
    private AbstractPlayer p;
    private final String key;
    private final int rate;

    public SoundAction(String key) {
        this(key, 10);
    }

    public SoundAction(String key, int rate) {
        this.key = key;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.rate = rate;
    }

    public void update() {
        int rate = new Random().nextInt(100);
        if(rate <= this.rate){
            CardCrawlGame.sound.play(key, 0F);
        }

        this.isDone = true;
    }

}