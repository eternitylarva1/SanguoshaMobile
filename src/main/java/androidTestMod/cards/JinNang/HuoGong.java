package androidTestMod.cards.JinNang;

import androidTestMod.actions.unique.FireDamageAction;
import androidTestMod.actions.unique.LimitedLineAction;
import androidTestMod.characters.SanGuoShaCharacter;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class HuoGong extends CustomCard{
    public static final String ID = HuoGong.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/HuoGong.png";
    private static final int COST = 1;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public HuoGong() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.JinNang);
        this.baseDamage = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new LimitedLineAction(ID, true, true));
        this.addToBot(new FireDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        this.addToBot(new ExhaustAction(1, false, false));
    }

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new HuoGong();
    }

    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeDamage(4);
        }
    }



}
