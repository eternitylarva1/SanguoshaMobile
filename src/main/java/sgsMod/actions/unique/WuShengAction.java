package sgsMod.actions.unique;

import sgsMod.cards.Basic.Sha;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class WuShengAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean upgraded;

    public WuShengAction(AbstractCreature source, boolean upgraded) {
        this.setValues(AbstractDungeon.player, source, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[1], 99, true, true);

            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {

                    AbstractCard card = new Sha();
                    if(this.upgraded){
                        card.upgrade();
                    }
                    this.addToTop(new MakeVirtualCardInHandAction(card, AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));


                    for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                        AbstractDungeon.player.hand.moveToDiscardPile(c);
                        GameActionManager.incrementDiscard(false);
                        c.triggerOnManualDiscard();
                    }
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("GamblingChipAction");
        TEXT = uiStrings.TEXT;
    }
}