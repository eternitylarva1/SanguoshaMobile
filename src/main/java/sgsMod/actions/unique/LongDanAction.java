package sgsMod.actions.unique;

import sgsMod.cards.Basic.Sha;
import sgsMod.cards.Basic.Shan;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class LongDanAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DURATION_PER_CARD = 0.25F;
    private AbstractPlayer p;
    private boolean upgraded;
    private final ArrayList<AbstractCard> cannotPick = new ArrayList();

    public LongDanAction(AbstractCreature source, boolean upgraded) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.upgraded = upgraded;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            var1 = this.p.hand.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (!this.canPick(c)) {
                    this.cannotPick.add(c);
                }
            }

            if (this.cannotPick.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            this.p.hand.group.removeAll(this.cannotPick);
            if (this.p.hand.group.size() > 0) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                this.p.hand.moveToDiscardPile(c);
                GameActionManager.incrementDiscard(false);
                c.triggerOnManualDiscard();
                AbstractCard newCard;
                if(c.hasTag(AbstractCard.CardTags.Sha)){
                    newCard = new Shan();
                }
                else{
                    newCard = new Sha();
                }
                if(this.upgraded){
                    newCard.upgrade();
                }
                this.addToTop(new MakeVirtualCardInHandAction(newCard));
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotPick) {
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }

    private boolean canPick(AbstractCard card) {
        return card.hasTag(AbstractCard.CardTags.Sha) || card.cardID.equals(Shan.ID);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
    }
}