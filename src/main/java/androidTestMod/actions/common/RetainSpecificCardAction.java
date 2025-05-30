package androidTestMod.actions.common;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RetainSpecificCardAction extends AbstractGameAction {
    private AbstractPlayer p;
    private final AbstractCard card;

    public RetainSpecificCardAction(AbstractCard c) {
        //将抽牌堆指定的牌加入手牌
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_MED;
        this.card = c;
    }

    public void update() {


        this.isDone = true;
    }
}