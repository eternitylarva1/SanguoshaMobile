package androidTestMod.cards.JinNang;

import androidTestMod.actions.unique.LineAction;
import androidTestMod.characters.SanGuoShaCharacter;
import androidTestMod.helper.SoundController;
import androidTestMod.powers.shandian;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class ShanDian extends CustomCard{
    public static final String ID = ShanDian.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ShanDian.png";
    private static final int COST = 0;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public ShanDian() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, androidTestMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.NONE);
        this.tags.add(AbstractCard.CardTags.JinNang);
        this.baseMagicNumber = 30;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new LineAction(ID, SoundController.SoundType.Turn));
        this.addToBot(new ApplyPowerAction(p, p, new shandian(p, this.baseMagicNumber)));
    }

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ShanDian();
    }

    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeMagicNumber(10);
        }
    }
}
