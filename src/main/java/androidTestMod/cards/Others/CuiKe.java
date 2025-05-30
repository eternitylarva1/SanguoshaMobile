package androidTestMod.cards.Others;

import androidTestMod.characters.SanGuoShaCharacter;
import androidTestMod.powers.junluebiaoji;
import androidTestMod.powers.tiesuolianhuan;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CuiKe extends SgsCard {
        public static final String ID = CuiKe.class.getSimpleName();
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        public static final String NAME = cardStrings.NAME;
        public static final String DESCRIPTION = cardStrings.DESCRIPTION;
        private static final int COST = 0;

        public static final String IMG_PATH = "img/cards/CuiKe.png";

        //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
        public CuiKe() {
            super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.SPECIAL, CardTarget.ENEMY);
            this.baseDamage = 20;
        }

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            //使用卡牌时触发的动作
            this.addToBot(new SFXAction(ID));
            boolean ji = false;
            if(p.hasPower(junluebiaoji.POWER_ID) && (p.getPower(junluebiaoji.POWER_ID).amount % 2 == 1)){
                ji = true;
            }

            if(ji){
                this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
            else{
                this.addToBot(new ApplyPowerAction(m, p, new tiesuolianhuan(m)));
                this.addToBot(new DrawCardAction(2));
                //this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 2, false)));
            }

            /*if(p.hasPower(junluebiaoji.POWER_ID) && (p.getPower(junluebiaoji.POWER_ID).amount > 7)){
                for(AbstractMonster m1 : AbstractDungeon.getCurrRoom().monsters.monsters){
                    this.addToBot(new DamageAction(m1, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                    this.addToBot(new RemoveSpecificPowerAction(p, p, junluebiaoji.POWER_ID));
                }
            }*/
        }

        @Override
        public AbstractCard makeCopy() {
            //复制卡牌时触发
            return new CuiKe();
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

        public void triggerOnGlowCheck() {
            AbstractPlayer p = AbstractDungeon.player;
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            if (p.hasPower(junluebiaoji.POWER_ID) && (p.getPower(junluebiaoji.POWER_ID).amount > 7)){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }

        }


}
