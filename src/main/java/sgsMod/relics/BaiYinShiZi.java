package sgsMod.relics;

import sgsMod.SgsMod;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BaiYinShiZi extends CustomRelic {
    public static final String ID = "BaiYinShiZi";
    private static final String IMG = "img/relics/BaiYinShiZi.png";
    private static final String IMG_OTL = "img/relics/outline/BaiYinShiZi.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public BaiYinShiZi() {
        super(ID,  AssetLoader.getTexture(SgsMod.MOD_ID,IMG),  AssetLoader.getTexture(SgsMod.MOD_ID,IMG_OTL), RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > 10) {
            this.flash();
            return 10;
        } else {
            return damageAmount;
        }
    }



    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new BaiYinShiZi();
    }
}
