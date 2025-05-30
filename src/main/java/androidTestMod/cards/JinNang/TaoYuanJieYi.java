package androidTestMod.cards.JinNang;

import androidTestMod.actions.unique.LimitedLineAction;
import androidTestMod.characters.SanGuoShaCharacter;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class TaoYuanJieYi extends CustomCard{
    public static final String ID = TaoYuanJieYi.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/TaoYuanJieYi.png";
    private static final int COST = 0;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public TaoYuanJieYi() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.RARE, CardTarget.NONE);
        this.tags.add(AbstractCard.CardTags.JinNang);
        this.exhaust = true;
        this.isEthereal = true;
        this.baseMagicNumber = 7;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new LimitedLineAction(ID, true, true));
        this.addToBot(new HealAction(p, p, this.baseMagicNumber));
        for(AbstractMonster m1: AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!m1.isDeadOrEscaped()){
                this.addToBot(new HealAction(m1, p, this.baseMagicNumber));
            }
        }
    }

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new TaoYuanJieYi();
    }

    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeMagicNumber(3);
        }
    }
}
