package sgsMod.actions.unique;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class YouDiAction extends AbstractGameAction {
    public AbstractPlayer p;
    private final int amount;

    public YouDiAction(AbstractPlayer p, int amount) {
        this.p = p;
        this.amount = amount;
    }

    public void update() {
        if(!p.hand.isEmpty()){
            int res = AbstractDungeon.cardRandomRng.random(0, p.hand.size() - 1);
            AbstractCard c = p.hand.group.get(res);

            if(! (c.type == AbstractCard.CardType.POWER)){
                this.addToTop(new DrawCardAction(3));
            }

            if(!c.hasTag(AbstractCard.CardTags.Sha)){
                this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, amount)));
            }



            this.p.hand.moveToDiscardPile(c);
            c.triggerOnManualDiscard();
            GameActionManager.incrementDiscard(false);
        }


        this.tickDuration();

    }
}