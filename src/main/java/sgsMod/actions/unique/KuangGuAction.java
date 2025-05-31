package sgsMod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KuangGuAction extends AbstractGameAction {
    public int damage;
    public AbstractMonster m;

    public KuangGuAction(AbstractCreature source, int amount, DamageInfo.DamageType type, AttackEffect effect, AbstractMonster m) {
        this.setValues((AbstractCreature)null, source, amount);
        this.damage = amount;
        this.m = m;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        int i;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            boolean playedMusic = false;
            i = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        }

        this.tickDuration();
        if (this.isDone) {

            int healAmount = 0;

            AbstractMonster target = m;
            if (!target.isDying && target.currentHealth > 0 && !target.isEscaping) {
                target.damage(new DamageInfo(this.source, this.damage, this.damageType));
                if (target.lastDamageTaken > 0) {
                    healAmount += target.lastDamageTaken;

                    /*for (int j = 0; j < target.lastDamageTaken / 2 && j < 10; ++j) {
                        this.addToBot(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));
                    }*/
                }
            }

            if (healAmount > 0) {
                if (!Settings.FAST_MODE) {
                    this.addToBot(new WaitAction(0.3F));
                }

                this.addToBot(new HealAction(this.source, this.source, healAmount));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            this.addToTop(new WaitAction(0.1F));
        }

    }
}