package sgsMod.actions.unique;

import sgsMod.powers.kuangfeng;
import sgsMod.powers.tiesuolianhuan;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;

public class FireDamageAction extends AbstractGameAction {
    private final AbstractCreature target;
    private final DamageInfo info;
    private final AttackEffect effect;

    public FireDamageAction(AbstractCreature target, DamageInfo info, AttackEffect effect) {
        this.target = target;
        this.info = info;
        this.effect = effect;
    }

    public void update() {
        if(target.hasPower(kuangfeng.POWER_ID)){
            this.info.output = this.info.output * 2;
        }

        if(target.hasPower(tiesuolianhuan.POWER_ID)){
            ArrayList<AbstractMonster> l = new ArrayList<>();
            for(AbstractMonster m: getCurrRoom().monsters.monsters){
                if(m != target && m.hasPower(tiesuolianhuan.POWER_ID)){
                    l.add(m);
                }
            }

            for(int i = l.size()-1; i >= 0; i--){
                AbstractMonster m = l.get(i);
                this.addToTop(new FireDamageAction(m, info, effect));
                this.addToTop(new RemoveSpecificPowerAction(m, m, tiesuolianhuan.POWER_ID));
            }

            this.addToTop(new RemoveSpecificPowerAction(target, target, tiesuolianhuan.POWER_ID));
        }



        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.FIRE, false));
        this.addToTop(new DamageAction(target, info, effect));

        this.tickDuration();

    }

}