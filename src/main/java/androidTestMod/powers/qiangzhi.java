package androidTestMod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class qiangzhi extends AbstractPower {
    public static final String POWER_ID = qiangzhi.class.getSimpleName();;
    public static final String NAME ;
    public static final String[] DESCRIPTIONS;
    public static PowerStrings powerStrings;
    private static int IdOffset = 0;
    private final AbstractCard.CardType t;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    private AbstractCard card;

    public qiangzhi(AbstractCreature owner, int amount, AbstractCard.CardType t) {//参数：owner-能力施加对象、amount-施加能力层数。在cards的use里面用ApplyPowerAction调用进行传递。
        this.name = NAME;
        this.ID = POWER_ID + IdOffset;
        ++IdOffset;
        this.owner = owner;
        this.amount = amount;
        this.img = ImageMaster.loadImage("img/power_img/qiangzhi.png");
        //以上五句不可缺少，照抄即可。记得修改this.img的图片路径。
        this.t = t;
        this.type = PowerType.BUFF;//能力种类，可以不填写，会默认为PowerType.BUFF。PowerType.BUFF不会被人工制品抵消，PowerType.DEBUFF会被人工制品抵消。
        updateDescription();
    }

    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }//可通过添加if判定this.amount来限制层数上限。

    //触发时机，以下部分触发时机仅翻译英文，具体效果未知，如有错误，请见谅。如仍无法满足DIY需求，请详参desktop的各卡牌源码或AbstractCard类的源码。
    //小tips：在以下触发时机里，需要的闪烁的，可调用flash();让能力闪一下.

    public void updateDescription() {
        String s = "";
        switch (this.t){
            case ATTACK: s = "攻击"; break;
            case SKILL: s = "技能"; break;
            case POWER: s = "能力"; break;
            case CURSE: s = "诅咒"; break;
            case STATUS: s = "状态"; break;

        }
        if(DESCRIPTIONS.length == 1){
            this.description = (DESCRIPTIONS[0]);
        }

        else{
            this.description = (DESCRIPTIONS[0] + s + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]);
        }
    }

    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if(card.type == this.t){
            this.addToBot(new GainBlockAction(AbstractDungeon.player, this.amount, true));
        }
    }//触发时机：当一张卡被打出且卡牌效果生效前。

    public void atEndOfTurn(boolean isPlayer) {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

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

    public void onApplyPower(final AbstractPower power, final AbstractCreature target, final AbstractCreature source) {
        //参数：power-能力Id，target-被给予者，source-给予者
    }//触发时机：当给予能力时。

    public int onLoseHp(final int damageAmount) {
        return damageAmount;
    }//触发时机：当失去生命值时，返回伤害数值，可用来修改伤害数值。

    public void onVictory() {
    }//触发时机：当一个房间获胜时。
}
