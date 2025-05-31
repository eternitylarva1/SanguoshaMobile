package sgsMod.cards.General;


import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class XunXun extends CustomCard {
    public static final String ID = XunXun.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/LiDian.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public XunXun() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.NONE);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
       
        this.addToBot(new ScryAction(this.baseMagicNumber));
        this.addToBot(new DrawCardAction(2));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new XunXun();
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
