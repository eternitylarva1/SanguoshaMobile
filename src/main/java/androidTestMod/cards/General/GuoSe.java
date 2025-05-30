package androidTestMod.cards.General;

import androidTestMod.actions.unique.GuoSeAction;
import androidTestMod.actions.unique.LineAction;
import androidTestMod.actions.unique.MakeVirtualCardInHandAction;
import androidTestMod.cards.JinNang.LeBuSiShu;
import androidTestMod.cards.Others.SgsCard;
import androidTestMod.characters.SanGuoShaCharacter;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GuoSe extends SgsCard {
    public static final String ID = GuoSe.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/DaQiao.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public GuoSe() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.NONE);
        this.tags.add(AbstractCard.CardTags.General);
        this.cardsToPreview = new LeBuSiShu();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new LineAction("GuoSe"));
        this.addToBot(new GuoSeAction(p, 1));
        this.addToBot(new MakeVirtualCardInHandAction(new LeBuSiShu()));
    }


    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return (AbstractCard) new GuoSe();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else  {
            boolean hasCard = false;
            for(AbstractCard c : p.hand.group){
                if(c.type.equals(CardType.POWER)){
                    hasCard = true;
                    break;
                }
            }
            if(hasCard){
                return canUse;
            }
            else{
                this.cantUseMessage = "这张牌需要消耗一张能力牌才能打出。";
                return false;
            }
        }
    }
}
