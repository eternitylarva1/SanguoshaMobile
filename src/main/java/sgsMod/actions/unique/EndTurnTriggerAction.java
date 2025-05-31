package sgsMod.actions.unique;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EndTurnTriggerAction extends AbstractGameAction {
    public AbstractCard c;
    public AbstractPlayer player;
    public boolean isDone;


    public EndTurnTriggerAction() {
        this.player = AbstractDungeon.player;
    }

    public void update() {
        for(AbstractCard c: player.hand.group){
            c.triggerOnEndOfTurnForPlayingCard();
        }


        this.tickDuration();

    }
}