package sgsMod.relics;

import sgsMod.actions.unique.ChooseOneGeneralAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LuxuryGeneralCardPack extends CustomRelic {
    public static final String ID = "LuxuryGeneralCardPack";
    private static final String IMG = "img/relics/RandomGeneralCardPack.png";
    private static final String IMG_OTL = "img/relics/outline/RandomGeneralCardPack.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public LuxuryGeneralCardPack() {
        super(ID,  AssetLoader.getTexture(SgsMod.MOD_ID,IMG),  AssetLoader.getTexture(SgsMod.MOD_ID,IMG_OTL), RelicTier.BOSS, LandingSound.CLINK);
    }

    public void atBattleStartPreDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new ChooseOneGeneralAction());
    }

    public void obtain() {
        instantObtain(AbstractDungeon.player, 0, true);
        flash();
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(RandomGeneralCardPack.ID);
    }

    public void onEquip() {

    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new LuxuryGeneralCardPack();
    }
}
