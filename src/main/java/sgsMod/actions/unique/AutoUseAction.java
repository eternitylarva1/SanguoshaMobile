package sgsMod.actions.unique;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AutoUseAction extends AbstractGameAction {
    public AbstractCard c;
    public AbstractPlayer player;
    public boolean isDone;

    public AutoUseAction(AbstractPlayer p, AbstractCard c) {
        this.c = c;
        this.player = p;
        this.isDone = false;
    }

    public void update() {
        c.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c, true));

        this.tickDuration();

    }
}