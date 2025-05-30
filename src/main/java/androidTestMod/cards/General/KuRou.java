package androidTestMod.cards.General;

import androidTestMod.cards.Others.SgsCard;
import androidTestMod.characters.SanGuoShaCharacter;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KuRou extends SgsCard {
    public static final String ID = KuRou.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/HuangGai.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public KuRou() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        //this.addToBot(new DiscardAction(p, p, 1, false));
        this.addToBot(new SFXAction("KuRou"));
        this.addToBot(new LoseHPAction(p, p, 2));
        this.addToBot(new DrawCardAction(this.baseMagicNumber));
        //this.addToBot(new CardBackToHandAction(p, this));
    }

    /*public void triggerOnEndOfTurnForPlayingCard() {
        //多次使用的牌在回合结束时自动丢弃
        this.addToTop(new DiscardSpecificCardAction(this));
    }*/

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return (AbstractCard) new KuRou();
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
