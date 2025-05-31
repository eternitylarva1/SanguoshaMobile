package sgsMod.relics;

import sgsMod.powers.ShaTimesPower;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class QingLongYanYueDao extends CustomRelic {
    public static final String ID = "QingLongYanYueDao";
    private static final String IMG = "img/relics/QingLongYanYueDao.png";
    private static final String IMG_OTL = "img/relics/outline/QingLongYanYueDao.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public QingLongYanYueDao() {
        super(ID,  AssetLoader.getTexture(SgsMod.MOD_ID,IMG),  AssetLoader.getTexture(SgsMod.MOD_ID,IMG_OTL), RelicTier.COMMON, LandingSound.HEAVY);
    }


    public void atTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower power = new ShaTimesPower(p, 1);
        this.addToBot(new ApplyPowerAction(p, p, power));
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new QingLongYanYueDao();
    }
}
