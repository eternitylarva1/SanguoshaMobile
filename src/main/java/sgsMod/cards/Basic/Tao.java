package sgsMod.cards.Basic;

import sgsMod.cards.Others.SgsCard;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Tao extends SgsCard {
        public static final String ID = Tao.class.getSimpleName();
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        public static final String NAME = cardStrings.NAME;
        public static final String DESCRIPTION = cardStrings.DESCRIPTION;
        private static final int COST = 1;

        public static final String IMG_PATH = "img/cards/Tao.png";

        //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
        public Tao() {
            super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
            this.exhaust = true;
            this.baseMagicNumber = 7;
            this.isEthereal = true;
            this.tags.add(CardTags.HEALING);
            this.tags.add(AbstractCard.CardTags.Basic);
        }

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            //使用卡牌时触发的动作
           
            this.addToBot(new HealAction(p, p, this.baseMagicNumber));
        }

        @Override
        public AbstractCard makeCopy() {
            //复制卡牌时触发
            return (AbstractCard)new Tao();
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
}
