package sgsMod.cards.Others;

import sgsMod.actions.unique.FireDamageAction;
import sgsMod.actions.unique.LineAction;

import sgsMod.helper.SoundController;
import sgsMod.powers.junluebiaoji;
import sgsMod.powers.tiesuolianhuan;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class ZhanHuo extends SgsCard {
        public static final String ID = ZhanHuo.class.getSimpleName();
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        public static final String NAME = cardStrings.NAME;
        public static final String DESCRIPTION = cardStrings.DESCRIPTION;
        private static final int COST = 2;

        public static final String IMG_PATH = "img/cards/CuiKe.png";

        //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
        public ZhanHuo() {
            super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.SPECIAL, CardTarget.NONE);
            this.exhaust = true;
            this.baseDamage = 20;
            this.baseMagicNumber = 3;
        }

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            //使用卡牌时触发的动作
            if(p.hasPower(junluebiaoji.POWER_ID)){
                this.addToBot(new LineAction(ID, SoundController.SoundType.Battle));

                this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.GOLD.cpy())));

                int count = 0;
                if(p.hasPower(junluebiaoji.POWER_ID)){
                    count = p.getPower(junluebiaoji.POWER_ID).amount;
                }

                if(p.hasPower(junluebiaoji.POWER_ID)){
                    this.addToBot(new RemoveSpecificPowerAction(p, p, junluebiaoji.POWER_ID));
                }

                for(AbstractMonster m1: AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!m1.isDeadOrEscaped() && m1.hasPower(tiesuolianhuan.POWER_ID)){
                        this.addToBot(new FireDamageAction(m1, new DamageInfo(p, this.damage * count, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                        break;
                    }
                }

            /*for(AbstractMonster m1: AbstractDungeon.getCurrRoom().monsters.monsters){
                if(!m1.isDeadOrEscaped() && m1.hasPower(tiesuolianhuan.POWER_ID)){
                    this.addToBot(new ApplyPowerAction(m1, p, new StrengthPower(m1, -this.baseMagicNumber)));
                }
            }*/


            }

        }

        @Override
        public AbstractCard makeCopy() {
            //复制卡牌时触发
            return new ZhanHuo();
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
}
