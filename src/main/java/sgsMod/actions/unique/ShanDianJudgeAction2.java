package sgsMod.actions.unique;

import sgsMod.helper.SoundController;
import sgsMod.powers.guicai;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class ShanDianJudgeAction2 extends AbstractGameAction {
    public AbstractCard c;
    public AbstractPlayer p;
    public boolean isDone;
    private boolean firstUse;
    private int extra;
    private AbstractPower power;

    public ShanDianJudgeAction2(int extra, AbstractPower power) {
        this.isDone = false;
        this.extra = extra;
        this.power = power;
    }

    public void update() {
        this.p = AbstractDungeon.player;
        int r;

        if(p.hasPower(guicai.POWER_ID)){
            r = 0;
            this.addToBot(new LineAction("GuiCai", SoundController.SoundType.BattleAndRate));
        }
        else{
            r = AbstractDungeon.cardRandomRng.random(0, 7);
        }

        if(r == 0){
            AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
            if(m != null){
                AbstractDungeon.effectList.add(new BorderLongFlashEffect(Color.GOLD.cpy()));
                AbstractDungeon.effectList.add(new ShanDianFlashEffect(m));

                DamageInfo info = new DamageInfo(p, this.extra, DamageInfo.DamageType.THORNS);
                AbstractDungeon.effectList.add(new ShanDianFlashEffect(m));
                this.addToTop(new LightningDamageAction(m, info, AttackEffect.NONE));
                this.addToBot(new RemoveSpecificPowerAction(power.owner, power.owner, power));
            }

        }

        this.tickDuration();

    }
}