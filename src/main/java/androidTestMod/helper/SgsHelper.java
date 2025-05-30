package androidTestMod.helper;

import androidTestMod.actions.unique.TakenShaDamageAction;
import androidTestMod.powers.*;
import androidTestMod.relics.GuDingDao;
import androidTestMod.relics.QingGangJian;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class SgsHelper {
    public static void PreShaAction(AbstractPlayer p, AbstractMonster m){
        //使用杀指定目标前的效果
        if(p.hasRelic(QingGangJian.ID)){
            AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(m,p));
        }
    }

    public static float CountShaRate(AbstractPlayer p, AbstractMonster m){
        //计算杀的伤害倍率，如破军，古锭刀等
        float times = 1.0F;
        if(p.hasPower(pojun.POWER_ID) || p.hasRelic(GuDingDao.ID)){
            times = pojun(p, m, times);
            //破军和古锭刀的额外伤害倍率
        }
        return times;

    }

    public static void AfterShaAction(AbstractPlayer p, AbstractMonster m){
        //使用杀指定目标后的效果
        actionManager.addToBottom(new TakenShaDamageAction(p, m));

        if(p.hasPower(ShaTimesPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, ShaTimesPower.POWER_ID, 1));
        }
        if(AbstractDungeon.player.hasPower(jiu.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, jiu.POWER_ID));
        }
    }


    public static float pojun(AbstractPlayer p, AbstractMonster m, float originalRate){
        float extraRate = originalRate;
        int playerStrength = 0;
        if(p.hasPower(StrengthPower.POWER_ID)){
            playerStrength = p.getPower(StrengthPower.POWER_ID).amount;
        }

        int enemyStrength = 0;
        if(m.hasPower(StrengthPower.POWER_ID)){
            enemyStrength = m.getPower(StrengthPower.POWER_ID).amount;
        }


        if(p.hasPower(pojun.POWER_ID)){
            int change = p.getPower(pojun.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -change), -change));
            if (!m.hasPower("Artifact")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, change), change));
                enemyStrength = enemyStrength - change;
                //如果目标没有人工，则判定它被减少力量后的值
            }
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));

            if(playerStrength >= enemyStrength){
                extraRate = extraRate * 2.0F;
            }
        }

        if(p.hasRelic(GuDingDao.ID)){
            if(enemyStrength <= 0){
                p.getRelic(GuDingDao.ID).flash();
                extraRate = extraRate * 1.5F;
            }
        }

        return extraRate;
    }

    public static int GeneralShaDamage = 6;
    public static int ShaDamageUpgrade = 3;
    public static int ElementShaDamage = 10;
    public static int ElementShaDamageUpgrade = 5;
    public static int countShaDamage(boolean upgraded, boolean special){
        int base;
        if(!special){
            base = GeneralShaDamage;
            if(upgraded){
                base += ShaDamageUpgrade;
            }
        }
        else{
            base = ElementShaDamage;
            if(upgraded){
                base += ElementShaDamageUpgrade;
            }
        }

        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasPower(wushuang.POWER_ID)){
            base += AbstractDungeon.player.getPower(wushuang.POWER_ID).amount;

        }
        if(p.hasPower(jiu.POWER_ID)){
            int j = AbstractDungeon.player.getPower(jiu.POWER_ID).amount;
            base += j;
        }
        if(p.hasPower(liegongbiaoji.POWER_ID)){
            base += AbstractDungeon.player.getPower(liegongbiaoji.POWER_ID).amount;
        }
        if(p.hasPower(quedi.POWER_ID)){
            base += AbstractDungeon.player.getPower(quedi.POWER_ID).amount;
        }


        return base;
    }

    public static String noEnoughCards(){
        return "我的手牌数不足";
    }

    public static AbstractCard makeVirtualCopy(AbstractCard c) {
        AbstractCard card = c.makeCopy();

        for(int i = 0; i < c.timesUpgraded; ++i) {
            card.upgrade();
        }

        card.tags.add(AbstractCard.CardTags.Temporary);

        card.name = c.name;
        card.target = c.target;
        card.upgraded = c.upgraded;
        card.timesUpgraded = c.timesUpgraded;
        card.baseDamage = c.baseDamage;
        card.baseBlock = c.baseBlock;
        card.baseMagicNumber = c.baseMagicNumber;

        if(card.cost >= 0){
            card.cost = 0;
            card.costForTurn = 0;
        }

        card.isCostModified = true;
        card.isCostModifiedForTurn = true;
        card.exhaust = true;

        card.inBottleLightning = c.inBottleLightning;
        card.inBottleFlame = c.inBottleFlame;
        card.inBottleTornado = c.inBottleTornado;
        card.isSeen = c.isSeen;
        card.isLocked = c.isLocked;
        card.misc = c.misc;
        card.freeToPlayOnce = c.freeToPlayOnce;

        card.rawDescription = "转化 。 NL " + c.rawDescription;
        card.initializeDescription();
        return card;
    }

    public static boolean isPublicPower(String powerName){
        List<String> l = new ArrayList<>();
        l.add(StrengthPower.POWER_ID);
        l.add(PlatedArmorPower.POWER_ID);
        l.add(MetallicizePower.POWER_ID);
        l.add(BarricadePower.POWER_ID);

        for(String s : l){
            if(powerName.equals(s)){
                return true;
            }
        }

        return false;
    }

    public static AbstractCard returnGeneralCardInCombat() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        Iterator<AbstractCard> var2 = srcCommonCardPool.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.hasTag(AbstractCard.CardTags.General) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcUncommonCardPool.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.hasTag(AbstractCard.CardTags.General) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcRareCardPool.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.hasTag(AbstractCard.CardTags.General) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        return (AbstractCard)list.get(cardRandomRng.random(list.size() - 1));
    }

    private static final Logger logger = LogManager.getLogger(AbstractCreature.class.getName());
    public static void write_logger(String s){
        logger.info(s);
    }
}
