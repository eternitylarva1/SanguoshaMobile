package sgsMod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RandomLightningDamageAction extends AbstractGameAction {
    public DamageInfo info;
    public AbstractPlayer player;
    public boolean isDone;
    public boolean Strongest;

    public RandomLightningDamageAction(AbstractPlayer p, DamageInfo info) {
        this(p, info, false);
    }

    public RandomLightningDamageAction(AbstractPlayer p, DamageInfo info, boolean StrongestOne) {
        this.player = p;
        this.isDone = false;
        this.info = info;
        this.Strongest = StrongestOne;
    }

    public void update() {
        if(!Strongest){
            this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        }
        else{
            this.target = null;
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!m.isDeadOrEscaped()){
                    if(this.target == null){
                        this.target = m;
                    }
                    else{
                        if(m.currentHealth > this.target.currentHealth){
                            this.target = m;
                        }
                    }
                }
            }
        }
        if(this.target != null){
            this.addToTop(new LightningDamageAction(this.target, info, AttackEffect.NONE));
        }


        this.tickDuration();

    }
}