package sgsMod.cards.General;

import sgsMod.cards.Others.SgsCard;

import sgsMod.powers.ShaTimesPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class XiongLuan extends SgsCard {
    public static final String ID = XiongLuan.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/ZhangXiu.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public XiongLuan() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.General);
        this.exhaust = true;
        this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
       
        this.addToBot(new RemoveAllBlockAction(m, p));
        this.addToBot(new ApplyPowerAction(p, p, new ShaTimesPower(p, 999)));
        this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.baseMagicNumber, false)));
        //this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.baseMagicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new XiongLuan();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
