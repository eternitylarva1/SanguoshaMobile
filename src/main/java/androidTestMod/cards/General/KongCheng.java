package androidTestMod.cards.General;

//湮灭

import androidTestMod.cards.Others.SgsCard;
import androidTestMod.characters.SanGuoShaCharacter;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KongCheng extends SgsCard {
    public static final String ID = KongCheng.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/zhugeliang.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;

    public KongCheng() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(AbstractCard.CardTags.General);
    }

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return (AbstractCard) new KongCheng();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new SFXAction("KongCheng"));
        this.addToBot(new ApplyPowerAction(p, p, new androidTestMod.powers.KongCheng(p)));
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}