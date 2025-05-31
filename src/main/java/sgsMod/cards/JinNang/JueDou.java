package sgsMod.cards.JinNang;

import sgsMod.actions.unique.JueDouAction;
import sgsMod.actions.unique.LimitedLineAction;

import sgsMod.powers.quedi;
import sgsMod.powers.wushuang;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class JueDou extends CustomCard{
    public static final String ID = JueDou.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/JueDou.png";
    private static final int COST = 1;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public JueDou() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.BASIC, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.JinNang);
        this.baseDamage = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new LimitedLineAction(ID, true, true));
        this.addToBot(new JueDouAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));

    }

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new JueDou();
    }

    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeDamage(3);
        }
    }

    public void applyPowers() {
        int base = 4;
        if(this.upgraded){
            base += 3;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasPower(wushuang.POWER_ID)){
            base += AbstractDungeon.player.getPower(wushuang.POWER_ID).amount;
        }
        if(p.hasPower(quedi.POWER_ID)){
            base += AbstractDungeon.player.getPower(quedi.POWER_ID).amount;
        }
        this.baseDamage = base;
        super.applyPowers();
    }



}
