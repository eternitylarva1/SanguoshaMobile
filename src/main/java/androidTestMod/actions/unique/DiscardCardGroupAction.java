package androidTestMod.actions.unique;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;

public class DiscardCardGroupAction extends AbstractGameAction {
    private final List<AbstractCard> c;

    public DiscardCardGroupAction(List<AbstractCard> c) {
        AbstractPlayer p = AbstractDungeon.player;
        this.c = c;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard card : c) {
                AbstractDungeon.player.hand.moveToDiscardPile(card);
                GameActionManager.incrementDiscard(false);
                card.triggerOnManualDiscard();
            }

        }

        this.tickDuration();
    }

}