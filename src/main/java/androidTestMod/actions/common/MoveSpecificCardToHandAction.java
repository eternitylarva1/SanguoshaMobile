package androidTestMod.actions.common;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MoveSpecificCardToHandAction extends AbstractGameAction {
    private AbstractPlayer p;
    private final AbstractCard card;

    public MoveSpecificCardToHandAction(AbstractCard c) {
        //将抽牌堆指定的牌加入手牌
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_MED;
        this.card = c;
    }

    public void update() {
        if (this.p.hand.size() == 10) {
            this.p.drawPile.moveToDiscardPile(card);
            this.p.createHandIsFullDialog();
        } else {
            card.unhover();
            card.lighten(true);
            card.setAngle(0.0F);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.current_x = CardGroup.DRAW_PILE_X;
            card.current_y = CardGroup.DRAW_PILE_Y;
            this.p.drawPile.removeCard(card);
            AbstractDungeon.player.hand.addToTop(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
        }

        this.isDone = true;
    }
}