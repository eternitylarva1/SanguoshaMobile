package sgsMod.actions.unique;

import sgsMod.powers.guicai;
import sgsMod.powers.shandian;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class ShanDianJudgeAction extends AbstractGameAction {
    public AbstractCard c;
    public AbstractPlayer p;
    public boolean isDone;
    private boolean firstUse;
    private int extra;
    private AbstractPower power;

    public ShanDianJudgeAction(boolean firstUse, int extra) {
        this.firstUse = firstUse;
        this.isDone = false;
        this.extra = extra;
        this.actionType = ActionType.BLOCK;
    }

    public ShanDianJudgeAction(boolean firstUse, int extra, AbstractPower power) {
        this.firstUse = firstUse;
        this.extra = extra;
        this.power = power;
        this.actionType = ActionType.BLOCK;
    }

    public void update() {
        this.p = AbstractDungeon.player;
        int r;

        if(firstUse){
            boolean done = false;
            ArrayList<AbstractMonster> liveEnemy = new ArrayList<>();
            for (AbstractMonster m :AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()){
                    liveEnemy.add(m);
                }
            }

            for(AbstractMonster m : liveEnemy){
                if(p.hasPower(guicai.POWER_ID)){
                    r = 0;
                    this.addToBot(new SFXAction("GuiCai"));
                }
                else{
                    r = AbstractDungeon.cardRandomRng.random(0, 3);
                }

                if(r == 0){
                    DamageInfo info = new DamageInfo(p, this.extra, DamageInfo.DamageType.THORNS);
                    AbstractDungeon.effectList.add(new ShanDianFlashEffect(m));
                    this.addToTop(new LightningDamageAction(m, info, AttackEffect.NONE));
                    done = true;
                    break;
                }
                else{
                    AbstractDungeon.effectList.add(new ShanDianFlashEffect(m));
                    AbstractDungeon.effectList.add(new WordFloatEffect(m.hb.cX, m.hb.cY, "未命中"));
                }
            }

            if(!done){
                this.addToBot(new ApplyPowerAction(p, p, new shandian(p, this.extra)));
            }
        }
        else{
            boolean done = false;
            ArrayList<AbstractMonster> liveEnemy = new ArrayList<>();
            for (AbstractMonster m :AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()){
                    liveEnemy.add(m);
                }
            }

            for(AbstractMonster m : liveEnemy){
                if(p.hasPower(guicai.POWER_ID)){
                    r = 0;
                    this.addToBot(new LimitedLineAction("GuiCai", false));
                }
                else{
                    r = AbstractDungeon.cardRandomRng.random(0, 3);
                }
                if(r == 0){
                    DamageInfo info = new DamageInfo(p, this.extra, DamageInfo.DamageType.THORNS);
                    AbstractDungeon.effectList.add(new ShanDianFlashEffect(m));
                    this.addToTop(new LightningDamageAction(m, info, AttackEffect.NONE));
                    done = true;
                    break;
                }
                else{
                    AbstractDungeon.effectList.add(new ShanDianFlashEffect(m));
                    AbstractDungeon.effectList.add(new WordFloatEffect(m.hb.cX, m.hb.cY, "未命中"));
                }
            }

            if(done){
                this.addToBot(new RemoveSpecificPowerAction(power.owner, power.owner, power));
            }
        }


        this.tickDuration();

    }
}