package sgsMod.cards.General;

import sgsMod.actions.unique.CardBackToHandAction;

import sgsMod.helper.SgsHelper;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DuWu extends CustomCard {
    public static final String ID = DuWu.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/ZhuGeKe.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public DuWu() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseMagicNumber = 0;
        this.baseDamage = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        if (this.baseMagicNumber > 0) {
            this.addToBot(new DiscardAction(p, p, this.baseMagicNumber, false));
        }

        if (this.baseMagicNumber < 1) {
           
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        this.baseMagicNumber = this.baseMagicNumber + 1;
        this.addToBot(new CardBackToHandAction(p, this));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new DuWu();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeDamage(3);
        }
    }

    public void atTurnStart() {
        this.baseMagicNumber = 0;
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        //多次使用的牌在回合结束时自动丢弃
        this.addToBot(new DiscardSpecificCardAction(this));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (p.hand.size() < (this.baseMagicNumber + 1)) {
            //没有杀的使用次数时不能出杀
            this.cantUseMessage = SgsHelper.noEnoughCards();
            return false;
        } else {
            return canUse;
        }
    }
}
