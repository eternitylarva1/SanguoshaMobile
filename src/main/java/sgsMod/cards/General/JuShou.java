package sgsMod.cards.General;

import sgsMod.cards.Others.SgsCard;

import sgsMod.powers.lebusishu;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;

public class JuShou extends SgsCard {
    public static final String ID = JuShou.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/CaoRen.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public JuShou() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseBlock = 30;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new SFXAction("JuShou"));
        this.addToBot(new ApplyPowerAction(p, p, new BarricadePower(p)));
        this.addToBot(new ApplyPowerAction(p, p, new lebusishu(p)));
        this.addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new JuShou();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeBlock(10);
        }
    }
}
