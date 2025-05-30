package androidTestMod.character;

import androidTestMod.AndroidTestMod;
import androidTestMod.cards.Basic.Jiu;
import androidTestMod.cards.Basic.Sha;
import androidTestMod.cards.Basic.Shan;
import androidTestMod.cards.General.*;
import androidTestMod.cards.JinNang.JueDou;
import androidTestMod.cards.JinNang.LeBuSiShu;
import androidTestMod.cards.JinNang.ShanDian;
import androidTestMod.cards.yellow.TestStrike;
import androidTestMod.enums.CardColorEnum;
import androidTestMod.patch.SkinSelectScreen;
import androidTestMod.relics.RandomGeneralCardPack;
import androidTestMod.relics.SgsModCore;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.megacrit.cardcrawl.cards.AbstractCard;import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;
import java.util.Iterator;


import static androidTestMod.AndroidTestMod.getResourcePath;
import static androidTestMod.AndroidTestMod.makeId;

// 继承CustomPlayer类
public class Mycharacter extends CustomPlayer {
    // 火堆的人物立绘（行动前）
    private static final String MY_CHARACTER_SHOULDER_1 = getResourcePath("char/shoulder1.png");
    // 火堆的人物立绘（行动后）
    private static final String MY_CHARACTER_SHOULDER_2 = getResourcePath("char/shoulder2.png");
    private static final String SHOULDER_2 = "img/char/shoulder1.png";
    private static final String SHOULDER_1 = "img/char/shoulder1.png";
    private static final int STARTING_HP = 80;
    private static final int MAX_HP = 80;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int HAND = 5;
    // 人物死亡图像
    private static final String CORPSE_IMAGE = getResourcePath("char/corpse.png");
    // 战斗界面左下角能量图标的每个图层
    private static final String[] ORB_TEXTURES = new String[]{
            getResourcePath("orb/layer5.png"),
            getResourcePath("orb/layer4.png"),
            getResourcePath("orb/layer3.png"),
            getResourcePath("orb/layer2.png"),
            getResourcePath("orb/layer1.png"),
            getResourcePath("orb/layer6.png"),
            getResourcePath("orb/layer5d.png"),
            getResourcePath("orb/layer4d.png"),
            getResourcePath("orb/layer3d.png"),
            getResourcePath("orb/layer2d.png"),
            getResourcePath("orb/layer1d.png")
    };
    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};
    // 人物的本地化文本，如卡牌的本地化文本一样，如何书写见下

    protected void initializeClass(String imgUrl, String shoulder2ImgUrl, String shouldImgUrl, String corpseImgUrl, CharSelectInfo info, float hb_x, float hb_y, float hb_w, float hb_h, EnergyManager energy) {
        if (imgUrl != null) {
            this.img = AssetLoader.getTexture(AndroidTestMod.MOD_ID,imgUrl);
        }

        if (this.img != null) {
            this.atlas = null;
        }

        this.shoulderImg = AssetLoader.getTexture(AndroidTestMod.MOD_ID,shouldImgUrl);
        this.shoulder2Img = AssetLoader.getTexture(AndroidTestMod.MOD_ID,shoulder2ImgUrl);
        this.corpseImg = AssetLoader.getTexture(AndroidTestMod.MOD_ID,corpseImgUrl);


        if (Settings.isMobile) {
            hb_w *= 1.17F;
        }

        this.maxHealth = info.maxHp;
        this.startingMaxHP = this.maxHealth;
        this.currentHealth = info.currentHp;
        this.masterMaxOrbs = info.maxOrbs;
        this.energy = energy;
        this.masterHandSize = HAND_SIZE;
        this.gameHandSize = this.masterHandSize;
        this.gold = info.gold;
        this.displayGold = this.gold;
        this.hb_h = hb_h * Settings.xScale;
        this.hb_w = hb_w * Settings.scale;
        this.hb_x = hb_x * Settings.scale;
        this.hb_y = hb_y * Settings.scale;
        this.hb = new Hitbox(this.hb_w, this.hb_h);
        this.healthHb = new Hitbox(this.hb.width, 72.0F * Settings.scale);
        this.refreshHitboxLocation();
    }
@Override
    protected void initializeStarterRelics(PlayerClass chosenClass) {
        super.initializeStarterRelics(chosenClass);
        ArrayList<String> relics = this.getStartingRelics();
        relics.add(SgsModCore.ID);
        relics.add(RunicPyramid.ID);


        int index = 0;

        for(Iterator<String> var4 = relics.iterator(); var4.hasNext(); ++index) {
            String s = (String)var4.next();
            RelicLibrary.getRelic(s).makeCopy().instantObtain(this, index, false);
        }


        AbstractDungeon.relicsToRemoveOnStart.addAll(relics);
    }

    public Mycharacter(String name) {
        super(AndroidTestMod.MOD_ID,name, CardColorEnum.SGS,null,getResourcePath("orb/vfx.png"), null, null, null);


        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);


        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        this.initializeClass(
                getResourcePath("char/character.png"), // 人物图片
                SHOULDER_2, SHOULDER_1,
                CORPSE_IMAGE, // 人物死亡图像
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量
        );


        // 如果你的人物没有动画，那么这些不需要写
        // this.loadAnimation("char/character.atlas", "char/character.json", 1.8F);
        // AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        // e.setTime(e.getEndTime() * MathUtils.random());
        // e.setTimeScale(1.2F);


    }

    // 初始卡组的ID，可直接写或引用变量
    public ArrayList<String> getStartingDeck() {
        //添加初始卡组
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Sha.ID);
        retVal.add(Sha.ID);
        retVal.add(Sha.ID);
        retVal.add(Sha.ID);
        //retVal.add(Sha.ID);

        retVal.add(Shan.ID);
        retVal.add(Shan.ID);
        retVal.add(Shan.ID);
        retVal.add(Shan.ID);
        //retVal.add(Shan.ID);

        retVal.add(Jiu.ID);

        retVal.add(JueDou.ID);
        retVal.add(LeBuSiShu.ID);
        //retVal.add(ShanDian.ID);
        //retVal.add(WanJianQiFa.ID);

        if(!SkinSelectScreen.deckLock){
            switch (SkinSelectScreen.getSkin().name){
                case "界徐盛": retVal.add(PoJun.ID); break;
                case "黄月英": retVal.add(JiZhi.ID); break;
                case "张角": retVal.add(LeiJi.ID); retVal.add(ShanDian.ID); break;
                case "袁绍": retVal.add(LuanJi.ID); break;
                case "孙尚香": retVal.add(LiangZhu.ID); break;
                case "张让": retVal.add(TaoLuan.ID); break;
                case "刘焉": retVal.add(TuShe.ID); break;
                case "神陆逊": retVal.add(JunLue.ID); break;
            }
        }


        return retVal;
    }

    // 初始遗物的ID，可以先写个原版遗物凑数
    @Override
    public ArrayList<String> getStartingRelics() {
        //添加初始遗物
        ArrayList<String> starting_retVal = new ArrayList<>();
        starting_retVal.add(RandomGeneralCardPack.ID);
        return starting_retVal;
    }

    public CharSelectInfo getLoadout() {

            //选英雄界面的文字描述
            String title="";
            String flavor="";
            if (Settings.language == Settings.GameLanguage.ZHS) {
                title = "三国杀";
                flavor = "“我们的游戏，正在蒸蒸日上哦！”";
            } else if (Settings.language == Settings.GameLanguage.ZHT) {
                title = "三国杀";
                flavor = "“我们的游戏，正在蒸蒸日上哦！”";
                //当设定为中国台湾省，title和flavor为繁体描述
            } else {
                //其他用英文替代
                title = "SanGuoSha";
                flavor = "This mod supports Chinese only. Please change language setting into Chinese.";
            }


            return new CharSelectInfo(title, flavor, STARTING_HP, MAX_HP,HAND_SIZE , STARTING_GOLD, HAND, this, getStartingRelics(), getStartingDeck(), false);


    }

    // 人物名字（出现在游戏左上角）
    public String getTitle(PlayerClass playerClass) {
        //应该是进游戏后左上角的角色名
        String title="";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "三国杀";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "三国杀";
        } else {
            title = "Sgs";
        }

        return title;
    }

    // 你的卡牌颜色（这个枚举在最下方创建）
    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.SGS_COLOR;
    }

    // 翻牌事件出现的你的职业牌（一般设为打击）
    @Override
    public AbstractCard getStartCardForEvent() {
        return new TestStrike();
    }

    // 卡牌轨迹颜色
    @Override
    public Color getCardTrailColor() {
        return AndroidTestMod.YELLOW_COLOR;
    }

    // 高进阶带来的生命值损失
    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    // 卡牌的能量字体，没必要修改
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    // 人物选择界面点击你的人物按钮时触发的方法，这里为屏幕轻微震动
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    // 碎心图片
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("img/char/scene1.png", "ATTACK_POISON2"));
        panels.add(new CutscenePanel("img/char/scene2.png","ATTACK_FIRE"));
        panels.add(new CutscenePanel("img/char/scene3.png"));
        panels.add(new CutscenePanel("img/char/scene4.png"));
        return panels;
    }

    // 自定义模式选择你的人物时播放的音效
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    // 游戏中左上角显示在你的名字之后的人物名称
    @Override
    public String getLocalizedCharacterName() {
        String char_name;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            char_name = "三国杀";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            char_name = "三国杀";
        } else {
            char_name = "SanGuoSha";
        }
        return char_name;
    }

    // 创建人物实例，照抄
    @Override
    public AbstractPlayer newInstance() {
        return new Mycharacter(this.name);
    }

    // 第三章面对心脏说的话（例如战士是“你握紧了你的长刀……”之类的）
    public String getSpireHeartText() {
        return Settings.language == Settings.GameLanguage.ZHS ? "NL 你举起青釭剑……" : "NL You hold Onigari No Ryuuou...";
    }

    // 打心脏的颜色，不是很明显
    @Override
    public Color getSlashAttackColor() {
        return AndroidTestMod.YELLOW_COLOR;
    }

    // 吸血鬼事件文本，主要是他（索引为0）和她（索引为1）的区别（机器人另外）
    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    // 卡牌选择界面选择该牌的颜色
    @Override
    public Color getCardRenderColor() {
        return AndroidTestMod.YELLOW_COLOR;
    }

    // 第三章面对心脏造成伤害时的特效
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }



    // 以下为原版人物枚举、卡牌颜色枚举扩展的枚举，需要写，接下来要用

    // 注意此处是在 MyCharacter 类内部的静态嵌套类中定义的新枚举值
    // 不可将该定义放在外部的 MyCharacter 类中，具体原因见《高级技巧 / 01 - Patch / SpireEnum》

}
