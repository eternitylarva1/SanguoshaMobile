package sgsMod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class jizhi extends AbstractPower {
    public static final String POWER_ID = jizhi.class.getSimpleName();
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public jizhi(AbstractCreature owner, int amount) {//参数：owner-能力施加对象、amount-施加能力层数。在cards的use里面用ApplyPowerAction调用进行传递。
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.img =  AssetLoader.getTexture(SgsMod.MOD_ID,"img/power_img/jizhi.png");
        //以上五句不可缺少，照抄即可。记得修改this.img的图片路径。
        updateDescription();//调用该方法（第36行）的文本更新函数,更新一次文本描叙，不可缺少。
        this.type = PowerType.BUFF;//能力种类，可以不填写，会默认为PowerType.BUFF。PowerType.BUFF不会被人工制品抵消，PowerType.DEBUFF会被人工制品抵消。

    }

    public void updateDescription() {
        if(DESCRIPTIONS.length == 1){
            this.description = (DESCRIPTIONS[0]);
        }
        else if(DESCRIPTIONS.length == 2){
            this.description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]);
        }
        else{
            this.description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]);
        }
    }

    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }//可通过添加if判定this.amount来限制层数上限。

    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if(card.hasTag(AbstractCard.CardTags.JinNang)){
            this.addToBot(new SFXAction("JiZhi"));
            this.addToBot(new DrawCardAction(this.amount));
        }
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

