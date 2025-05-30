package androidTestMod.relics;

import androidTestMod.cards.Basic.HuoSha;
import androidTestMod.cards.Basic.Sha;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

public class ZhuQueYuShan extends CustomRelic {
    public static final String ID = "ZhuQueYuShan";
    private static final String IMG = "img/relics/ZhuQueYuShan.png";
    private static final String IMG_OTL = "img/relics/outline/ZhuQueYuShan.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public ZhuQueYuShan() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.MAGICAL);
    }


    public void onEquip() {
        ArrayList<AbstractCard> s = new ArrayList<>();
        ArrayList<AbstractCard> cardToRemove = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals(Sha.ID)) {
                AbstractCard c1 = new HuoSha();
                if(c.upgraded){
                    c1.upgrade();
                }
                s.add(c1.makeStatEquivalentCopy());
                cardToRemove.add(c);
            }
        }

        for(AbstractCard c : cardToRemove){
            AbstractDungeon.player.masterDeck.removeCard(c);
        }

        float displayCount = 0;
        for (AbstractCard c : s) {
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 6.0F + displayCount, (float)Settings.HEIGHT / 2.0F, false));
            displayCount = displayCount + (float)Settings.WIDTH / 6.0F;
        }

    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new ZhuQueYuShan();
    }
}
