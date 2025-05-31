package sgsMod.cards.Basic;

import sgsMod.cards.Others.SgsCard;

import sgsMod.powers.yizhong;
import sgsMod.relics.RenWangDun;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Shan extends SgsCard {
    public static final String ID = Shan.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -2;

    public static final String IMG_PATH = "img/cards/Shan.png";

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Shan() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.BASIC, CardTarget.SELF);
        //添加基础防御标签和将格挡设为5
        this.tags.add(CardTags.STARTER_DEFEND);
        this.baseMagicNumber = 5;
        this.tags.add(AbstractCard.CardTags.Basic);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new GainBlockAction((AbstractCreature) p, (AbstractCreature) p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return (AbstractCard) new Shan();
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;

    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    public void applyPowers() {
        this.baseMagicNumber = 5;
        if(this.upgraded) this.baseMagicNumber += 3;
        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasRelic(RenWangDun.ID)){
            this.baseMagicNumber += 2;
        }
        if(AbstractDungeon.player.hasPower(yizhong.POWER_ID)){
            this.baseMagicNumber += AbstractDungeon.player.getPower(yizhong.POWER_ID).amount;
        }
        super.applyPowers();
    }
}
