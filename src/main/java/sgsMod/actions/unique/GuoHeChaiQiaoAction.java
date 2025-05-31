package sgsMod.actions.unique;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;
import java.util.List;

public class GuoHeChaiQiaoAction extends AbstractGameAction {
    private final AbstractMonster target;
    private final boolean get;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public GuoHeChaiQiaoAction(AbstractMonster target, boolean get) {
        this.target = target;
        this.get = get;
    }

    public void update() {
        if(target.type == AbstractMonster.EnemyType.BOSS){
            this.tickDuration();
            return;
        }
        AbstractPlayer player = AbstractDungeon.player;
        List<AbstractPower> res = new ArrayList<>();
        for(AbstractPower p : target.powers){
            if(p.type == AbstractPower.PowerType.BUFF){
                if(p.ID.equals(StrengthPower.POWER_ID) && p.amount <= 0){
                    //如果是力量，只在为正时生效
                }
                else{
                    res.add(p);
                }
            }
        }

        if(res.size() >= 1){
            int r = AbstractDungeon.cardRandomRng.random(res.size() - 1);
            if (this.target.hasPower("Artifact")) {
                this.addToTop(new TextAboveCreatureAction(this.target, TEXT[0]));
                this.duration -= Gdx.graphics.getDeltaTime();
               
                this.target.getPower("Artifact").flashWithoutSound();
                this.target.getPower("Artifact").onSpecificTrigger();
            }
            else{
                int amount = res.get(r).amount;
                if(get){
                    switch (res.get(r).ID){
                        case StrengthPower.POWER_ID: this.addToTop(new ApplyPowerAction(player, player, new StrengthPower(player, amount))); break;
                        case PlatedArmorPower.POWER_ID: this.addToTop(new ApplyPowerAction(player, player, new PlatedArmorPower(player, amount))); break;
                        case MetallicizePower.POWER_ID: this.addToTop(new ApplyPowerAction(player, player, new MetallicizePower(player, amount))); break;
                        case BarricadePower.POWER_ID: this.addToTop(new ApplyPowerAction(player, player, new BarricadePower(player))); break;
                        case IntangiblePower.POWER_ID: this.addToTop(new ApplyPowerAction(player, player, new IntangiblePower(player, amount))); break;
                        default: break;
                    }
                }

                this.addToTop(new RemoveSpecificPowerAction(target, target, res.get(r)));
            }
        }



        this.tickDuration();

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ApplyPowerAction");
        TEXT = uiStrings.TEXT;
    }

}