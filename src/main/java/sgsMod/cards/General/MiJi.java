package sgsMod.cards.General;

import sgsMod.cards.Others.SgsCard;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MiJi extends SgsCard {
    public static final String ID = MiJi.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/WangYi.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public MiJi() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseMagicNumber = 1;
        this.baseBlock = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.applyPowers();
       
        for (int i = 0; i < this.baseMagicNumber; i++) {
            this.addToBot(new GainBlockAction(p, p, this.block));
        }

    }

    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;
        this.baseMagicNumber = 1 + (p.maxHealth - p.currentHealth) / 20;
        super.applyPowers();
        //this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        //this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new MiJi();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeBlock(2);
            //this.exhaust = false;
            //this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            //this.initializeDescription();
        }
    }
}
