package sgsMod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;

public class JueJinAction extends AbstractGameAction {
    private final AbstractPlayer p;

    public JueJinAction() {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            int armor = m.currentHealth - 1;
            m.currentHealth = 1;
            this.addToTop(new GainBlockAction(m, armor));
            this.addToTop(new ApplyPowerAction(m, m, new BarricadePower(m)));
        }

        int armor = p.currentHealth - 1;
        p.currentHealth = 1;
        this.addToTop(new GainBlockAction(p, armor));
        this.addToTop(new ApplyPowerAction(p, p, new BarricadePower(p)));

        this.isDone = true;
    }

}