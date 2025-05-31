package sgsMod.relics;

import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ShengGuangBaiYi extends CustomRelic {
    public static final String ID = "ShengGuangBaiYi";
    private static final String IMG = "img/relics/ShengGuangBaiYi.png";
    private static final String IMG_OTL = "img/relics/outline/ShengGuangBaiYi.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public ShengGuangBaiYi() {
        super(ID,  AssetLoader.getTexture(SgsMod.MOD_ID,IMG),  AssetLoader.getTexture(SgsMod.MOD_ID,IMG_OTL), RelicTier.COMMON, LandingSound.SOLID);
    }


    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 6));
    }



    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new ShengGuangBaiYi();
    }
}
