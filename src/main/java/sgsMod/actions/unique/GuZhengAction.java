package sgsMod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GuZhengAction extends AbstractGameAction {

    public boolean isDone;
    private AbstractPower p;

    public GuZhengAction(AbstractPower p) {
        this.isDone = false;
        this.p = p;
    }

    public void update() {
        p.updateDescription();

        this.tickDuration();

    }
}