package sgsMod.cards.General;

import sgsMod.cards.Others.CuiKe;
import sgsMod.cards.Others.SgsCard;
import sgsMod.cards.Others.ZhanHuo;

import sgsMod.powers.junlue;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JunLue extends SgsCard {
    public static final String ID = JunLue.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/ShenLuXun.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public JunLue() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(AbstractCard.CardTags.General);
        this.exhaust = true;
        this.cardsToPreview = new CuiKe();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //this.addToBot(new SFXAction(ID));
        this.addToBot(new ApplyPowerAction(p, p, new junlue(p)));
        this.addToBot(new MakeTempCardInHandAction(new CuiKe()));
        this.addToBot(new MakeTempCardInHandAction(new ZhanHuo()));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new JunLue();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeBaseCost(1);
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
