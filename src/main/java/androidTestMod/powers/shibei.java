package androidTestMod.powers;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class shibei extends AbstractPower {
    public static final String POWER_ID = shibei.class.getSimpleName();
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static PowerStrings powerStrings;
    private int times;
    public int extraLose;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public shibei(AbstractCreature owner, int extraLose) {//参数：owner-能力施加对象、amount-施加能力层数。在cards的use里面用ApplyPowerAction调用进行传递。
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.times = 0;
        this.extraLose = extraLose;
        this.img = ImageMaster.loadImage("img/power_img/shibei.png");
        //以上五句不可缺少，照抄即可。记得修改this.img的图片路径。
        updateDescription();//调用该方法（第36行）的文本更新函数,更新一次文本描叙，不可缺少。
        this.type = PowerType.BUFF;//能力种类，可以不填写，会默认为PowerType.BUFF。PowerType.BUFF不会被人工制品抵消，PowerType.DEBUFF会被人工制品抵消。

    }

    public void updateDescription() {
        if(DESCRIPTIONS.length == 1){
            this.description = (DESCRIPTIONS[0]);
        }
        else if(DESCRIPTIONS.length == 2){
            this.description = (DESCRIPTIONS[0] + extraLose + DESCRIPTIONS[1]);
        }
        else{
            this.description = (DESCRIPTIONS[0] + extraLose + DESCRIPTIONS[1]);
        }
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0 && this.times <= 0) {
            this.times++;
            this.addToBot(new SFXAction("ShiBei"));
            return 0;
        }

        return damageAmount;
    }

    public void atStartOfTurn(){
        this.times = 0;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (this.times > 0 && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && damageAmount > 0) {
            this.addToBot(new SFXAction("ShiBei"));
            this.addToBot(new LoseHPAction(this.owner, this.owner, this.extraLose));
        }

        return damageAmount;
    }

    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }//可通过添加if判定this.amount来限制层数上限。

    //触发时机，以下部分触发时机仅翻译英文，具体效果未知，如有错误，请见谅。如仍无法满足DIY需求，请详参desktop的各卡牌源码或AbstractCard类的源码。
    //小tips：在以下触发时机里，需要的闪烁的，可调用flash();让能力闪一下.

    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {//参数：damage-伤害数值，type-伤害种类
        return damage;//该damage即卡牌类里面的this.damage。
    }//触发时机：给予伤害值时，返回伤害值，可用来修改伤害数值，会重复触发，请不要在该函数里调用ApplyPowerAction、DamageAction等一系列Action，只可对damage进行运算（可以进行条件判定）。
    //如需调用Action，请使用下文写出的onAttack。

    public void onAttack(final DamageInfo info, final int damageAmount, final AbstractCreature target) {//参数： info-伤害信息，damageAmount-伤害数值，target-伤害目标
    }//触发时机：当玩家攻击时。info.可调用伤害信息。

    public float atDamageReceive(final float damage, final DamageInfo.DamageType damageType) {//参数：damage-伤害数值，damageType-伤害种类
        return damage;//该damage指被格挡前的damage。
    }//触发时机：当玩家受到伤害时，返回伤害数值，可用来修改伤害数值，会重复触发，请不要在该函数里调用ApplyPowerAction、DamageAction等一系列Action，只可对damage进行运算（可以进行条件判定）。
    //如需调用Action，请使用下文的onAttacked。

    public void atEndOfTurn(final boolean isPlayer) {
    }//触发时机：当玩家回合结束时触发。

    public void atEndOfRound() {
    }//触发时机：当怪物回合结束时触发。

    public void onDamageAllEnemies(final int[] damage) {
    }//触发时机：当对敌人全体造成伤害时触发。（待商榷，未使用过）

    public int onHeal(final int healAmount) {
        return healAmount;
    }//触发时机：当玩家回复生命时，返回生命回复数值，可以用来修改生命回复数值。

    public void onUseCard(final AbstractCard card, final UseCardAction action) {
    }//触发时机：当一张卡被打出且卡牌效果生效前。

    public void onPlayCard(final AbstractCard card, final AbstractMonster m) {
    }//触发时机：当一张卡被打出且卡牌效果生效前。

    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) {
    }//触发时机：当一张卡被打出后进入弃牌堆/消耗堆时。

    public void onAfterCardPlayed(final AbstractCard usedCard) {
    }//触发时机：当一张卡被打出且卡牌效果生效后。

    public void atEnergyGain() {
    }//触发时机：当玩家获得能量时。

    public void onExhaust(final AbstractCard card) {
    }//触发时机：当玩家消耗卡牌时。

    public void onGainedBlock(final float blockAmount) {
    }//触发时机：当玩家获得格挡时。

    public void onRemove() {
    }//触发时机：当能力被移除时。

    public void onApplyPower(final AbstractPower power, final AbstractCreature target, final AbstractCreature source) {
        //参数：power-能力Id，target-被给予者，source-给予者
    }//触发时机：当给予能力时。

    public int onLoseHp(final int damageAmount) {
        return damageAmount;
    }//触发时机：当失去生命值时，返回伤害数值，可用来修改伤害数值。

    public void onVictory() {
    }//触发时机：当一个房间获胜时。


}

