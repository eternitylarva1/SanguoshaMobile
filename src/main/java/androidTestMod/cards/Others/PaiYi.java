package androidTestMod.cards.Others;

import androidTestMod.characters.SanGuoShaCharacter;
import androidTestMod.powers.quan;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PaiYi extends SgsCard {
        public static final String ID = PaiYi.class.getSimpleName();
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        public static final String NAME = cardStrings.NAME;
        public static final String DESCRIPTION = cardStrings.DESCRIPTION;
        private static final int COST = 0;

        public static final String IMG_PATH = "img/cards/ZhongHui.png";

        //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
        public PaiYi() {
            super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.SPECIAL, CardTarget.SELF);
            this.baseMagicNumber = 1;
        }

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            //使用卡牌时触发的动作
            if(p.hasPower(quan.POWER_ID)){
                this.addToBot(new SFXAction(ID));
                this.addToBot(new ReducePowerAction(p, p, quan.POWER_ID, 1));
                this.addToBot(new GainEnergyAction(this.baseMagicNumber));
                this.addToBot(new DrawCardAction(2));
            }
        }

        @Override
        public AbstractCard makeCopy() {
            //复制卡牌时触发
            return new PaiYi();
        }

        @Override
        public void upgrade() {
            //卡牌升级后的效果
            if (!this.upgraded) {
                //更改名字和提高3点格挡
                upgradeName();
                upgradeMagicNumber(1);
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                this.initializeDescription();
            }
        }
}
