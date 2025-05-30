package androidTestMod.cards.General;

import androidTestMod.characters.SanGuoShaCharacter;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WangXi extends CustomCard {
    public static final String ID = WangXi.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/LiDian.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public WangXi() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseBlock = 12;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        boolean use = true;
        for(AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisTurn){
            if(c.type == CardType.ATTACK){
                use = false;
                break;
            }
        }
        if(use){
            this.addToBot(new SFXAction(ID));
            this.addToBot(new GainBlockAction(p, p, this.block));
            this.addToBot(new GainBlockAction(m, p, this.block));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new WangXi();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        boolean use = true;
        for(AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisTurn){
            if(c.type == CardType.ATTACK){
                use = false;
                break;
            }
        }
        if (use) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        }

    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeBlock(3);
        }
    }
}
