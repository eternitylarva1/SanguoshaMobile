package androidTestMod.cards.General;

import androidTestMod.actions.unique.LineAction;
import androidTestMod.actions.unique.LuanJiAction;
import androidTestMod.cards.JinNang.WanJianQiFa;
import androidTestMod.cards.Others.SgsCard;
import androidTestMod.characters.SanGuoShaCharacter;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LuanJi extends SgsCard {
    public static final String ID = LuanJi.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/YuanShao.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private boolean ctr;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public LuanJi() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(AbstractCard.CardTags.General);
        this.cardsToPreview = new WanJianQiFa();
        ctr = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        if(ctr){
            this.addToBot(new LineAction("LuanJi1"));
        }
        else{
            this.addToBot(new LineAction("LuanJi2"));
        }
        ctr = !ctr;


        this.addToBot(new LuanJiAction(p, this.upgraded));

    }

    public void triggerOnEndOfTurnForPlayingCard() {
        //多次使用的牌在回合结束时自动丢弃
        //this.addToBot(new DiscardSpecificCardAction(this));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new LuanJi();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.cardsToPreview.upgrade();
        }
    }
}
