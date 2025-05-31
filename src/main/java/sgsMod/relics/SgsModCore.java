package sgsMod.relics;

import sgsMod.actions.unique.EndTurnTriggerAction;
import sgsMod.actions.unique.LianYingAction;
import sgsMod.actions.unique.LightningDamageAction;
import sgsMod.actions.unique.LimitedLineAction;
import sgsMod.cards.Basic.Shan;
import sgsMod.helper.SoundController;
import sgsMod.patch.SkinSelectScreen;
import sgsMod.powers.*;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SgsModCore extends CustomRelic {
    public static final String ID = "SgsModCore";
    private static final String IMG = "img/relics/SgsModCore.png";
    private static final String IMG_OTL = "img/relics/outline/SgsModCore.png";
    private boolean show = true;
    private boolean ShanSoundPlayed = false;

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public SgsModCore() {
        super(ID,  AssetLoader.getTexture(SgsMod.MOD_ID,IMG),  AssetLoader.getTexture(SgsMod.MOD_ID,IMG_OTL), RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public void onEquip(){
        AbstractPlayer p = AbstractDungeon.player;
        p.getCharStat().incrementVictory();
        int t = AbstractDungeon.ascensionLevel;
        for(int i = 0; i < 20; i++){
            AbstractDungeon.ascensionLevel = i;
            p.getCharStat().incrementAscension();
        }
        AbstractDungeon.ascensionLevel = t;
    }

    public void atBattleStart() {
        show = true;

        SoundController.clearAll();
    }

    public void atTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new RemoveSpecificPowerAction(p, p, ShaTimesPower.POWER_ID));
        AbstractPower power = new ShaTimesPower(p, 1);
        this.addToBot(new ApplyPowerAction(p, p, power));

        ShanSoundPlayed = false;
        SoundController.clearTurnBased();
    }

    public void atTurnStartPostDraw() {

    }

    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasPower(keji.POWER_ID)){
            this.addToBot(new SFXAction("KeJi"));
            return;
        }

        int hand_limit = (p.currentHealth - 1) / 10 + 1;
        if(p.hasPower(jieyingzi.POWER_ID)){
            hand_limit = (p.maxHealth - 1) / 10 + 1;
        }

        if(p.hasPower(quan.POWER_ID)){
            hand_limit += p.getPower(quan.POWER_ID).amount;
        }

        if(p.hasPower(jugu.POWER_ID)){
            hand_limit += p.getPower(jugu.POWER_ID).amount;
        }

        if(p.hasRelic(ShengGuangBaiYi.ID)){
            hand_limit += 2;
        }

        if(p.hand.size() > hand_limit){
            if(show){
                String s = "回合结束时，每10点生命可以保留一张手牌（向上取整）。";
                this.addToTop(new TalkAction(true, s, 4.0F, 4.0F));
                show = false;
            }
            this.addToBot(new DiscardAction(p, p, p.hand.size()-hand_limit, false));
            this.addToBot(new EndTurnTriggerAction());
        }

    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        //attacked的触发在changedamage之后
        AbstractPlayer p = AbstractDungeon.player;
        int outDamage = damageAmount;
        boolean usedShan = false;
        List<AbstractCard> toDiscard = new ArrayList<>();
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount >= 1) {
            for(AbstractCard c : AbstractDungeon.player.hand.group){
                if(Objects.equals(c.cardID, Shan.ID) && (outDamage > 0)){
                    usedShan = true;
                    toDiscard.add(c);


                    outDamage = outDamage - c.baseMagicNumber;
                    if(outDamage < 0) outDamage = 0;
                }
            }
        }

        if(p.hasPower(leiji.POWER_ID) && toDiscard.size() > 0){
            for(int i = 0; i < toDiscard.size(); i++){
                AbstractMonster target =  AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                this.addToTop(new LightningDamageAction(target, new DamageInfo(p, p.getPower(leiji.POWER_ID).amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            }
            this.addToTop(new LimitedLineAction("LeiJi", false));
        }

         for(int i = toDiscard.size()-1; i >= 0; i--){
             AbstractCard c = toDiscard.get(i);
             if(!c.exhaust){
                 this.addToTop(new DiscardSpecificCardAction(c));
             }
             else{
                 this.addToTop(new ExhaustSpecificCardAction(c, p.hand));
             }//如果是消耗性的闪，打出一次就会消失
         }

        //this.addToTop(new DiscardCardGroupAction(toDiscard));

        if(usedShan){
            if(!ShanSoundPlayed){
                if(SkinSelectScreen.getSkin().isMale){
                    this.addToTop(new SFXAction("Shan"));
                }
                else{
                    this.addToTop(new SFXAction("Shan_f"));
                }

                ShanSoundPlayed = true;
            }
        }

        if(p.hasPower(lianying.POWER_ID)){
            this.addToBot(new LianYingAction(p, p.getPower(lianying.POWER_ID).amount));
        }

        return outDamage;

    }



    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new SgsModCore();
    }
}
