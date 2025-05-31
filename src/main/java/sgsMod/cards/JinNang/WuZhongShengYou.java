package sgsMod.cards.JinNang;

import sgsMod.actions.unique.LimitedLineAction;

import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class WuZhongShengYou extends CustomCard{
    public static final String ID = WuZhongShengYou.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WuZhongShengYou.png";
    private static final int COST = 0;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public WuZhongShengYou() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
        this.baseMagicNumber = 2;
        this.tags.add(AbstractCard.CardTags.JinNang);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new LimitedLineAction(ID, true, true));
        this.addToBot(new DrawCardAction(this.baseMagicNumber));

    }

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new WuZhongShengYou();
    }

    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeMagicNumber(1);
            //this.exhaust = false;
            //this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            //this.initializeDescription();
        }
    }



}
