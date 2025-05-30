package androidTestMod.actions.unique;

import androidTestMod.powers.tiesuolianhuan;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;

public class LightningDamageAction extends AbstractGameAction {
    private final AbstractCreature target;
    private final DamageInfo info;
    private final AttackEffect effect;

    public LightningDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect) {
        this.target = target;
        this.info = info;
        this.effect = effect;
    }

    public void update() {
        if(target.hasPower(tiesuolianhuan.POWER_ID)){
            ArrayList<AbstractMonster> l = new ArrayList<>();
            for(AbstractMonster m: getCurrRoom().monsters.monsters){
                if(m != target && m.hasPower(tiesuolianhuan.POWER_ID)){
                    l.add(m);
                }
            }

            for(int i = l.size()-1; i >= 0; i--){
                AbstractMonster m = l.get(i);
                this.addToTop(new LightningDamageAction(m, info, effect));
                this.addToTop(new RemoveSpecificPowerAction(m, m, tiesuolianhuan.POWER_ID));
            }

            //AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.LIGHTNING, false));
            this.addToTop(new RemoveSpecificPowerAction(target, target, tiesuolianhuan.POWER_ID));
        }


        this.addToTop(new DamageAction(target, info, effect));
        this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
        this.addToTop(new VFXAction(new LightningEffect(this.target.hb.cX, this.target.hb.cY)));

        this.tickDuration();

    }

}