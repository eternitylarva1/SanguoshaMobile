package sgsMod.actions.unique;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class CardBackToHandAction extends AbstractGameAction {
    public AbstractCard c;
    public AbstractPlayer player;
    public boolean isDone;

    public CardBackToHandAction(AbstractPlayer p, AbstractCard c) {
        this.c = c;
        this.player = p;
        this.isDone = false;
    }

    public CardBackToHandAction(AbstractPlayer p, AbstractCard c, boolean isDone) {
        this.c = c;
        this.player = p;
        this.isDone = isDone;
    }

    public void update() {
        if(!isDone){
            this.addToBot(new WaitAction(0.5F));
            this.addToBot(new CardBackToHandAction(player, c, true));
        }
        else{
            if (this.player.hand.size() < 10) {
                this.player.hand.addToHand(c);
                this.player.discardPile.removeCard(c);
            }

            c.lighten(false);
            c.unhover();
            c.applyPowers();


            Iterator<AbstractCard> var1;
            for(var1 = this.player.discardPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {
                c = (AbstractCard)var1.next();
                c.unhover();
                c.target_x = (float) CardGroup.DISCARD_PILE_X;
            }

            AbstractDungeon.player.hand.refreshHandLayout();
        }


        this.tickDuration();

    }
}