package sgsMod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class LianYingAction extends AbstractGameAction {

    public boolean isDone;
    private AbstractPlayer p;
    private final int num;

    public LianYingAction(AbstractPlayer p, int num) {
        this.isDone = false;
        this.p = p;
        this.num = num;
    }

    public void update() {
        if(p.hand.size() <= 0){
            this.addToTop(new DrawCardAction(num));
            this.addToTop(new GainEnergyAction(num));

            this.addToTop(new LimitedLineAction("LianYing", false));

        }

        this.tickDuration();

    }
}