package sgsMod.actions.unique;

import sgsMod.powers.shuangxiong;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;


import static com.megacrit.cardcrawl.cards.AbstractCard.CardTags.Sha;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class JueDouAction extends AbstractGameAction {
    private DamageInfo info;
    private float startingDuration;

    public JueDouAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.SLASH_HEAVY;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        int count = 1;
        for(AbstractCard c : player.hand.group){
            if(c.hasTag(Sha)){
                count++;
            }
        }

        int i;
        for(i = 0; i < count; ++i) {
            this.addToTop(new DamageAction(this.target, this.info, AttackEffect.SLASH_HEAVY));
        }

        if(!player.hasPower(shuangxiong.POWER_ID)){
            for(AbstractCard c : player.hand.group){
                if(c.hasTag(Sha)){
                    this.addToTop(new DiscardSpecificCardAction(c));
                }
            }
        }
        else {
            this.addToTop(new LineAction("ShuangXiong"));
        }

        this.isDone = true;
    }

}