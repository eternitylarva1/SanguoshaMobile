package androidTestMod.cards.General;

import androidTestMod.characters.SanGuoShaCharacter;
import androidTestMod.powers.unableusesha;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class MuMu extends CustomCard {
    public static final String ID = MuMu.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/SunLuYu.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public MuMu() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseBlock = 10;
        this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new SFXAction(ID));
        /*boolean usedSha = false;
        for(AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisTurn){
            if(c.hasTag(AbstractCard.CardTags.Sha)){
                usedSha = true;
                break;
            }
        }
        if(usedSha){
            this.addToBot(new GainBlockAction(p, this.block));
        }
        else{
            this.addToBot(new GainBlockAction(p, this.block));
            this.addToBot(new GainBlockAction(p, this.block));
            this.addToBot(new ApplyPowerAction(p, p, new unableusesha(p, 1)));
        }*/
        this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.baseMagicNumber, false)));
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new ApplyPowerAction(p, p, new unableusesha(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new MuMu();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(1);
        }
    }
}
