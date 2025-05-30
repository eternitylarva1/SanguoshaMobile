package androidTestMod.actions.unique;

import androidTestMod.patch.SkinSelectScreen;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Random;

public class LimitedLineAction extends AbstractGameAction {
    public String ID;
    public AbstractPlayer player;
    public boolean times;
    private final boolean gender;

    public LimitedLineAction(String ID, boolean times) {
        this.ID = ID;
        this.times = times;
        gender = false;
    }

    public LimitedLineAction(String ID, boolean times, boolean gender) {
        this.ID = ID;
        this.times = times;
        this.gender = gender;
    }

    public LimitedLineAction(String ID) {
        this(ID, true);
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        boolean sound = false;
        if(times){
            int count = 0;
            for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn){
                if(c.cardID.equals(ID)){
                    count++;
                }
            }
            if(count < 2){
                sound = true;
            }
            //如果是按次数发动的，每回合只能响起1次
        }
        else{
            int r = new Random().nextInt();
            r = r % 100;
            if(r <= 20){
                sound = true;
            }
        }

        if(sound){
            if(gender){
                if(SkinSelectScreen.getSkin().isMale){
                    this.addToTop(new SFXAction(ID));
                }
                else {
                    this.addToTop(new SFXAction(ID + "_f"));
                }

            }
            else {
                this.addToTop(new SFXAction(ID));
            }

        }
        this.tickDuration();

    }
}