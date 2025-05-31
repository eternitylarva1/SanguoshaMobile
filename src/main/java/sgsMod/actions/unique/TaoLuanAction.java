package sgsMod.actions.unique;

import sgsMod.cards.Basic.Jiu;
import sgsMod.cards.Basic.Sha;
import sgsMod.cards.Basic.Shan;
import sgsMod.cards.Basic.Tao;
import sgsMod.cards.JinNang.JueDou;
import sgsMod.cards.JinNang.TaoYuanJieYi;
import sgsMod.helper.SgsHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class TaoLuanAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private final boolean upgrade;
    private AbstractCard taoluan;

    public TaoLuanAction(boolean upgrade, AbstractCard taoluan) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgrade = upgrade;
        this.taoluan = taoluan;
    }

    public void update() {
        ArrayList<AbstractCard> generatedCards = this.generateCardChoices(this.upgrade);
        if(generatedCards.size() < 1){
            this.isDone = true;
            return;
        }

        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], false);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = SgsHelper.makeVirtualCopy(AbstractDungeon.cardRewardScreen.discoveryCard);
                    AbstractCard disCard2 = SgsHelper.makeVirtualCopy(AbstractDungeon.cardRewardScreen.discoveryCard);
                    if (AbstractDungeon.player.hasPower("MasterRealityPower")) {
                        disCard.upgrade();
                        disCard2.upgrade();
                    }

                    disCard.setCostForTurn(0);
                    disCard2.setCostForTurn(0);
                    disCard.current_x = -1000.0F * Settings.xScale;
                    disCard2.current_x = -1000.0F * Settings.xScale + AbstractCard.IMG_HEIGHT_S;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    //this.addToTop(new ZhangRangAction(disCard.type, taoluan));

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }



    private ArrayList<AbstractCard> generateCardChoices(boolean upgraded) {
        ArrayList<AbstractCard> totalList = new ArrayList<>();

        totalList.add(new Sha());
        totalList.add(new Shan());
        totalList.add(new Jiu());
        totalList.add(new JueDou());

        Iterator<AbstractCard> var2 = srcCommonCardPool.group.iterator();
        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.hasTag(AbstractCard.CardTags.JinNang) || c.hasTag(AbstractCard.CardTags.Basic)) {
                totalList.add(c);
            }
        }

        var2 = srcUncommonCardPool.group.iterator();
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.hasTag(AbstractCard.CardTags.JinNang) || c.hasTag(AbstractCard.CardTags.Basic)) {
                totalList.add(c);
            }
        }

        var2 = srcRareCardPool.group.iterator();
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.hasTag(AbstractCard.CardTags.JinNang) || c.hasTag(AbstractCard.CardTags.Basic)) {
                totalList.add(c);
            }
        }

        for(AbstractCard c2 : actionManager.cardsPlayedThisCombat){
            //如果本场战斗已经使用过某张牌，将它移除
            Iterator<AbstractCard> iterator = totalList.iterator();
            while (iterator.hasNext()) {
                AbstractCard c3 = iterator.next();
                if (c2.cardID.equals(c3.cardID)) {
                    iterator.remove();
                }
            }
        }

     Iterator<AbstractCard> iterator = totalList.iterator();
while (iterator.hasNext()) {
    AbstractCard c2 = iterator.next();
    if (c2.cardID.equals(Shan.ID) || c2.cardID.equals(Tao.ID) || c2.cardID.equals(TaoYuanJieYi.ID)) {
        iterator.remove();
    }
}

        ArrayList<AbstractCard> res = new ArrayList<>();
        for(AbstractCard c1 : totalList){
            if(upgraded){
                c1.upgrade();
            }
            res.add(c1);
        }

        return res;
    }
}