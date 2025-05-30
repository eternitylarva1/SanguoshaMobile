package androidTestMod.cards.JinNang;

import androidTestMod.actions.unique.LimitedLineAction;
import androidTestMod.actions.unique.LineAction;
import androidTestMod.actions.unique.WordFloatEffect;
import androidTestMod.characters.SanGuoShaCharacter;
import androidTestMod.powers.guicai;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class LeBuSiShu extends CustomCard{
    public static final String ID = LeBuSiShu.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/LeBuSiShu.png";
    private static final int COST = 3;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public LeBuSiShu() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.BASIC, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.JinNang);
        this.exhaust = true;
        this.baseMagicNumber = 99;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        this.addToBot(new LimitedLineAction(ID, true, true));
        boolean useful = false;
        if(p.hasPower(guicai.POWER_ID)){
            useful = true;
            this.addToBot(new LineAction("GuiCai"));
        }
        else{
            int res = AbstractDungeon.cardRandomRng.random(0, 3);
            if(res > 0){
                useful = true;
            }
        }

        if(useful){
            //this.addToBot(new StunMonsterAction(m, p));
            this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.baseMagicNumber), -this.baseMagicNumber));
            if (m != null && !m.hasPower("Artifact")) {
                this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.baseMagicNumber), this.baseMagicNumber));
            }
        }
        else {
            AbstractDungeon.effectList.add(new WordFloatEffect(m.hb.cX, m.hb.cY, "未命中"));
        }


    }

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new LeBuSiShu();
    }

    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}
