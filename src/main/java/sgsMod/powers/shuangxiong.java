package sgsMod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class shuangxiong extends AbstractPower {
    public static final String POWER_ID = shuangxiong.class.getSimpleName();
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public shuangxiong(AbstractCreature owner) {//参数：owner-能力施加对象、amount-施加能力层数。在cards的use里面用ApplyPowerAction调用进行传递。
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.img =  AssetLoader.getTexture(SgsMod.MOD_ID,"img/power_img/shuangxiong.png");
        //以上五句不可缺少，照抄即可。记得修改this.img的图片路径。
        updateDescription();//调用该方法（第36行）的文本更新函数,更新一次文本描叙，不可缺少。
        this.type = PowerType.BUFF;//能力种类，可以不填写，会默认为PowerType.BUFF。PowerType.BUFF不会被人工制品抵消，PowerType.DEBUFF会被人工制品抵消。
    }

    public void updateDescription() {
        if(DESCRIPTIONS.length == 1){
            this.description = (DESCRIPTIONS[0]);
        }
        else if(DESCRIPTIONS.length == 2){
            this.description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]);
        }
        else{
            this.description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]);
        }
    }

    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }//可通过添加if判定this.amount来限制层数上限。

}

