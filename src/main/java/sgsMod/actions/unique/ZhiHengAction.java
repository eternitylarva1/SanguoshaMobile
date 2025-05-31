package sgsMod.actions.unique;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ZhiHengAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final boolean huiwan;
    private final boolean jie;
    private final int count;

    public ZhiHengAction(AbstractCreature source, boolean jie, boolean huiwan) {
        this.setValues(AbstractDungeon.player, source, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.huiwan = huiwan;
        this.jie = jie;
        this.count = AbstractDungeon.player.hand.size() - 1;
    }

    public void update() {

        if (this.duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[1], 99, true, true);

            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {

                    if(huiwan){
                        this.addToTop(new SeekAction(AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));
                    }
                    else if(jie){
                        if(AbstractDungeon.handCardSelectScreen.selectedCards.group.size() >= count){
                            this.addToTop(new DrawCardAction(count+1));
                        }
                        else{
                            this.addToTop(new DrawCardAction(AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));
                        }
                    }
                    else{
                        this.addToTop(new DrawCardAction(AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));
                    }


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