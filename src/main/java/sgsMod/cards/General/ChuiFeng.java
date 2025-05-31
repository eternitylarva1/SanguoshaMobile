package sgsMod.cards.General;

import sgsMod.actions.unique.MakeVirtualCardInHandAction;
import sgsMod.cards.JinNang.JueDou;
import sgsMod.cards.Others.QueDi1;
import sgsMod.cards.Others.QueDi2;
import sgsMod.cards.Others.QueDi3;
import sgsMod.cards.Others.SgsCard;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ChuiFeng extends SgsCard {
    public static final String ID = ChuiFeng.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/WenYang.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public ChuiFeng() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.NONE);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseMagicNumber = 2;
        this.cardsToPreview = new JueDou();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
       
        this.addToBot(new LoseHPAction(p, p, this.baseMagicNumber));
        this.addToBot(new MakeVirtualCardInHandAction(new JueDou()));

        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new QueDi1());
        stanceChoices.add(new QueDi2());
        stanceChoices.add(new QueDi3());
        if (this.upgraded) {

            for (AbstractCard c : stanceChoices) {
                c.upgrade();
            }
        }

        this.addToBot(new ChooseOneAction(stanceChoices));


    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ChuiFeng();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeMagicNumber(-1);
        }
    }
}
