package sgsMod.cards.General;

import sgsMod.cards.Others.SgsCard;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.Duality;

public class ChengLue extends SgsCard {
    public static final String ID = ChengLue.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/XuYou.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private boolean Changed = false;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public ChengLue() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(AbstractCard.CardTags.General);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
       
        if(!Changed){
            this.addToBot(new DiscardAction(p, p, 2, false));
            this.addToBot(new GainEnergyAction(2));
        }
        else{
            this.addToBot(new DrawCardAction(2));
        }


        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, new Duality()));
        Changed = (!Changed);

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ChengLue();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (Changed){
            this.glowColor = Color.GREEN;
        }

    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeBaseCost(0);
        }
    }


}
