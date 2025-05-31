package sgsMod.relics;

import sgsMod.helper.SgsHelper;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RandomGeneralCardPack extends CustomRelic {
    public static final String ID = "RandomGeneralCardPack";
    private static final String IMG = "img/relics/RandomGeneralCardPack.png";
    private static final String IMG_OTL = "img/relics/outline/RandomGeneralCardPack.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public RandomGeneralCardPack() {
        super(ID,  AssetLoader.getTexture(SgsMod.MOD_ID,IMG),  AssetLoader.getTexture(SgsMod.MOD_ID,IMG_OTL), RelicTier.STARTER, LandingSound.CLINK);
    }

    public void atBattleStartPreDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractCard c = SgsHelper.returnGeneralCardInCombat();
        this.addToBot(new MakeTempCardInHandAction(c.makeCopy()));
    }



    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new RandomGeneralCardPack();
    }
}
