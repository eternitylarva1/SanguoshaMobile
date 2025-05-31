package sgsMod.cards.JinNang;

import sgsMod.actions.unique.LimitedLineAction;

import sgsMod.powers.EnergyLosePower;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class WuGuFengDeng extends CustomCard{
    public static final String ID = WuGuFengDeng.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WuGuFengDeng.png";
    private static final int COST = 1;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public WuGuFengDeng() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.JinNang);
        this.exhaust = true;
        this.baseMagicNumber = 2;
        AbstractCard c = new Miracle();
        c.upgrade();
        this.cardsToPreview = c;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new LimitedLineAction(ID, true, true));
        AbstractCard c = new Miracle();
        c.upgrade();

        int count = 0;
        for(AbstractMonster m1: AbstractDungeon.getMonsters().monsters){
            if(!m1.isDeadOrEscaped()){
                count++;
            }
        }

        this.addToBot(new MakeTempCardInHandAction(c, count));
        this.addToBot(new ApplyPowerAction(p, p, new EnergyLosePower(p, count)));
    }

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new WuGuFengDeng();
    }

    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
