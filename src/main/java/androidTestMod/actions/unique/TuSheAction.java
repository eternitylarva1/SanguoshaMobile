package androidTestMod.actions.unique;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.Random;

public class TuSheAction extends AbstractGameAction {

    public boolean isDone;
    private AbstractPlayer p;
    private int num;

    public TuSheAction(AbstractPlayer p, int num) {
        this.isDone = false;
        this.p = p;
        this.num = num;
    }

    public void update() {
        boolean draw = true;
        for(AbstractCard c: p.hand.group){
            if(c.hasTag(AbstractCard.CardTags.Basic)){
                draw = false;
                break;
            }
        }
        if(draw){
            this.addToTop(new DrawCardAction(num));
            int i = new Random().nextInt() % 100;
            if(i < 50){
                this.addToTop(new LineAction("TuShe1"));
            }
            else{
                this.addToTop(new LineAction("TuShe2"));
            }

        }

        this.tickDuration();

    }
}