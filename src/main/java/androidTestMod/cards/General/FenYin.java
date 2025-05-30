package androidTestMod.cards.General;

//湮灭

import androidTestMod.cards.Others.SgsCard;
import androidTestMod.characters.SanGuoShaCharacter;
import androidTestMod.powers.fenyin;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Random;

public class FenYin extends SgsCard {
    public static final String ID = FenYin.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/LiuZan.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;

    public FenYin() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(AbstractCard.CardTags.General);
    }

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return (AbstractCard) new FenYin();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        Random r = new Random();
        int rate = r.nextInt() % 100;
        if(rate < 50){
            this.addToBot(new SFXAction("FenYin1"));
        }
        else {
            this.addToBot(new SFXAction("FenYin2"));
        }
        this.addToBot(new ApplyPowerAction(p, p, new fenyin(p, 1)));
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeBaseCost(2);
            //this.isInnate = true;
            //this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            //this.initializeDescription();
        }
    }
}