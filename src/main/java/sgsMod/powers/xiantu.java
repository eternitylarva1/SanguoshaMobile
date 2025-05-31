package sgsMod.powers;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class xiantu extends AbstractPower {
    public static final String POWER_ID = "xiantu";
    public static final String NAME ;
    public static final String[] DESCRIPTIONS;
    public static PowerStrings powerStrings;
    private static int IdOffset = 0;
    private final int extra;
    private final int startEnemyNum;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public xiantu(AbstractCreature owner, int extra) {//参数：owner-能力施加对象、amount-施加能力层数。在cards的use里面用ApplyPowerAction调用进行传递。
        this.name = NAME;
        this.ID = POWER_ID + IdOffset;
        ++IdOffset;
        this.owner = owner;
        this.img =  AssetLoader.getTexture(SgsMod.MOD_ID,"img/power_img/xiantu.png");
        //以上五句不可缺少，照抄即可。记得修改this.img的图片路径。
        updateDescription();//调用该方法（第36行）的文本更新函数,更新一次文本描叙，不可缺少。
        this.type = PowerType.DEBUFF;//能力种类，可以不填写，会默认为PowerType.BUFF。PowerType.BUFF不会被人工制品抵消，PowerType.DEBUFF会被人工制品抵消。
        this.amount = -1;
        this.extra = extra;

        int count = 0;
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!m.isDeadOrEscaped()){
                count++;
            }
        }
        startEnemyNum = count;

        this.updateDescription();
    }

    public void updateDescription() {
        if(DESCRIPTIONS.length == 1){
            this.description = (DESCRIPTIONS[0]);
        }
        else if(DESCRIPTIONS.length == 2){
            this.description = (DESCRIPTIONS[0] + extra + DESCRIPTIONS[1]);
        }
        else{
            this.description = (DESCRIPTIONS[0] + extra + DESCRIPTIONS[1]);
        }
    }

    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }//可通过添加if判定this.amount来限制层数上限。

    public void atEndOfTurn(final boolean isPlayer) {
        int count = 0;
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!m.isDeadOrEscaped()){
                count++;
            }
        }
        if(count >= startEnemyNum){
            //如果回合结束时怪物的数量没有变，说明没有杀死敌人；
            this.addToBot(new SFXAction("XianTu2"));
            this.addToBot(new LoseHPAction(this.owner, this.owner, 10));
        }

        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
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

    public int onLoseHp(final int damageAmount) {
        return damageAmount;
    }//触发时机：当失去生命值时，返回伤害数值，可用来修改伤害数值。

    public void onVictory() {
    }//触发时机：当一个房间获胜时。
}

