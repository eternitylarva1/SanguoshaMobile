package androidTestMod.relics;

import androidTestMod.AndroidTestMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawPower;

public class RuneOctahedron extends CustomRelic {
    public static final String ID = AndroidTestMod.makeId("RuneOctahedron");
    public static final String IMG_PATH = AndroidTestMod.getResourcePath("relics/runeOctahedron.png");

    public RuneOctahedron() {
        super(AndroidTestMod.MOD_ID, ID, IMG_PATH, RelicTier.RARE, LandingSound.SOLID);
        this.counter = 0;
    }

    @Override
    public void onEquip() {
        onMasterDeckChange();
    }

    @Override
    public void onMasterDeckChange() {
        this.counter = 0;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.type == AbstractCard.CardType.CURSE) {
                this.counter++;
            }
        }
    }

    @Override
    public void atBattleStart() {
        onMasterDeckChange();
        if (this.counter > 0) {
            AbstractPlayer p = AbstractDungeon.player;
            this.flash();
            addToBot(new ApplyPowerAction(p, p, new DrawPower(p, this.counter)));
            addToBot(new DrawCardAction(this.counter));
            addToBot(new RelicAboveCreatureAction(p, this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
