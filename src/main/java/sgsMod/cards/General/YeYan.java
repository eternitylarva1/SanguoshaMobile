package sgsMod.cards.General;

import sgsMod.actions.unique.FireDamageAction;
import sgsMod.cards.Others.SgsCard;

import sgsMod.helper.SgsHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;

public class YeYan extends SgsCard {
    public static final String ID = YeYan.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/ShenZhouYu.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public YeYan() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseMagicNumber = 4;
        this.baseDamage = 40;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new DiscardAction(p, p, 4, false));


        this.addToBot(new SFXAction("YeYan"));
        this.addToBot(new VFXAction(p, new ScreenOnFireEffect(), 0.8F));

        this.addToBot(new FireDamageAction((AbstractCreature) m, new DamageInfo((AbstractCreature) p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new YeYan();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeDamage(10);
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (p.hand.size() < 5) {
            this.cantUseMessage = SgsHelper.noEnoughCards();
            return false;
        } else {
            return canUse;
        }
    }
}
