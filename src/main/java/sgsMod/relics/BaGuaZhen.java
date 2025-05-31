package sgsMod.relics;

import sgsMod.actions.unique.MakeVirtualCardInHandAction;
import sgsMod.cards.Basic.Shan;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BaGuaZhen extends CustomRelic {
    public static final String ID = "BaGuaZhen";
    private static final String IMG = "img/relics/BaGuaZhen.png";
    private static final String IMG_OTL = "img/relics/outline/BaGuaZhen.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public BaGuaZhen() {
        super(ID,  AssetLoader.getTexture(SgsMod.MOD_ID,IMG),  AssetLoader.getTexture(SgsMod.MOD_ID,IMG_OTL), RelicTier.UNCOMMON, LandingSound.SOLID);
        this.counter = 0;
    }


    public void atTurnStart() {

    }

    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        if(counter == 1){
            this.flash();

            AbstractCard c = new Shan();
            this.addToBot(new MakeVirtualCardInHandAction(c));
            counter = 0;
        }
        else{
            counter = 1;
        }

    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new BaGuaZhen();
    }
}
