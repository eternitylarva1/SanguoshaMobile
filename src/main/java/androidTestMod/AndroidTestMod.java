package androidTestMod;

import androidTestMod.cards.Basic.*;
import androidTestMod.cards.General.*;
import androidTestMod.cards.JinNang.*;
import androidTestMod.cards.Others.*;
import androidTestMod.cards.yellow.TestStrike;
import androidTestMod.character.Mycharacter;
import androidTestMod.characters.SanGuoShaCharacter;
import androidTestMod.enums.CardColorEnum;
import androidTestMod.enums.LibraryTypeEnum;
import androidTestMod.helpers.Keyword;
import androidTestMod.relics.*;
import com.badlogic.gdx.graphics.Color;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.BaseMod;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.android.mods.helpers.CardColorBundle;
import com.megacrit.cardcrawl.android.mods.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import java.util.ArrayList;

public class AndroidTestMod implements EditCardsSubscriber,
                                       PostInitializeSubscriber,
                                       EditStringsSubscriber,
                                       EditRelicsSubscriber,
                                       EditKeywordsSubscriber,EditCharactersSubscriber ,PostEnergyRechargeSubscriber{
    public static final String MOD_ID = "AndroidTestMod";
    public static final Color YELLOW_COLOR = new Color(0.98F, 0.95F, 0.05F, 1.0F);
    public static final Color SILVER = CardHelper.getColor(255, 215, 0);
    public static final ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = getResourcePath("char/Character_Portrait.png");
    private static final String MOD_BADGE = "img/UI_past/badge.png";
    //攻击、技能、能力牌的背景图片(512)
    private static final String ATTACK_R = "img/512/attack.png";
    private static final String SKILL_CC = "img/512/skill.png";
    private static final String POWER_CC = "img/512/power.png";

    private static final String ENERGY_ORB_CC = "img/512/Beatrice_orb.png";
    //攻击、技能、能力牌的背景图片(1024)
    private static final String ATTACK_CC_PORTRAIT = "img/1024/attack.png";
    private static final String SKILL_CC_PORTRAIT = "img/1024/skill.png";
    private static final String POWER_CC_PORTRAIT = "img/1024/power.png";
    private static final String ENERGY_ORB_CC_PORTRAIT = "img/1024/Beatrice_orb.png";
    public static final String CARD_ENERGY_ORB = "img/512/Beatrice_small_orb.png";
    //选英雄界面的角色图标、选英雄时的背景图片
    private static final String MY_CHARACTER_BUTTON = "img/charSelect/SgsButton.png";
    private static final String CG = "img/charSelect/SgsCG.png";
    public static void initialize() {
        new AndroidTestMod();
    }


    public AndroidTestMod() {
        BaseMod.subscribe(this);
        CardColorBundle bundle = new CardColorBundle();
        bundle.cardColor = CardColorEnum.SGS_COLOR;
        bundle.modId = MOD_ID;
        bundle.bgColor =
            bundle.cardBackColor =
            bundle.frameColor =
            bundle.frameOutlineColor =
            bundle.descBoxColor =
            bundle.trailVfxColor =
            bundle.glowColor = YELLOW_COLOR;
        bundle.libraryType = LibraryTypeEnum.SGS_COLOR;
        bundle.attackBg = ATTACK_R;
        bundle.skillBg = SKILL_CC;
        bundle.powerBg = POWER_CC;
        bundle.cardEnergyOrb = CARD_ENERGY_ORB;
        bundle.energyOrb = ENERGY_ORB_CC;
        bundle.attackBgPortrait = ATTACK_CC_PORTRAIT;
        bundle.skillBgPortrait = SKILL_CC_PORTRAIT;
        bundle.powerBgPortrait = POWER_CC_PORTRAIT;
        bundle.energyOrbPortrait = ENERGY_ORB_CC_PORTRAIT;
        bundle.setEnergyPortraitWidth(164);
        bundle.setEnergyPortraitHeight(164);
        BaseMod.addColor(bundle);
    }

    public static String makeId(String name) {
        return MOD_ID + ":" + name;
    }

    public static String getResourcePath(String path) {
        return "TestImages/" + path;
    }

    @Override
    public void receiveEditCards() {
        loadCardsToAdd();
        for (AbstractCard card : cardsToAdd) {
            BaseMod.addCard((CustomCard) card);
        }
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.getColorBundleMap().get(CardColorEnum.SGS_COLOR).loadRegion();
        UnlockTracker.unlockCard(TestStrike.ID);
    }

    @Override
    public void receiveEditStrings() {
        String language;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
            default:
                language = "eng";
        }
        BaseMod.loadCustomStringsFile(MOD_ID, CardStrings.class, "localization/" + language + "/SGS_cards-zh.json");
        BaseMod.loadCustomStringsFile(MOD_ID, PowerStrings.class, "localization/" + language + "/SGS_powers_zh.json");
        BaseMod.loadCustomStringsFile(MOD_ID, RelicStrings.class, "localization/" + language + "/SGS_relics-zh.json");
        BaseMod.loadCustomStringsFile(MOD_ID, CharacterStrings.class, "localization/" + language + "/characters.json");


    }
    private void loadCardsToAdd() {
        //将自定义的卡牌添加到这里
        cardsToAdd.clear();
        cardsToAdd.add(new BaoLi().makeCopy());
        cardsToAdd.add(new BaZhen().makeCopy());
        cardsToAdd.add(new BingLiangCunDuan().makeCopy());
        cardsToAdd.add(new BuQu().makeCopy());
        cardsToAdd.add(new ChengLue().makeCopy());
        cardsToAdd.add(new ChuiFeng().makeCopy());
        cardsToAdd.add(new CuiKe().makeCopy());
        cardsToAdd.add(new DaWu().makeCopy());
        //l.add(new DiLu());
        cardsToAdd.add(new DuWu().makeCopy());
        cardsToAdd.add(new FenYin().makeCopy());
        cardsToAdd.add(new GongAo().makeCopy());
        cardsToAdd.add(new GuanXing().makeCopy());
        cardsToAdd.add(new GuiCai().makeCopy());
        cardsToAdd.add(new GuoHeChaiQiao().makeCopy());
        cardsToAdd.add(new GuoSe().makeCopy());
        cardsToAdd.add(new GuZheng().makeCopy());
        cardsToAdd.add(new HanYong().makeCopy());
        cardsToAdd.add(new HuaiJu().makeCopy());
        cardsToAdd.add(new HuiWan().makeCopy());
        cardsToAdd.add(new HuoGong().makeCopy());
        cardsToAdd.add(new HuoJi().makeCopy());
        cardsToAdd.add(new HuoSha().makeCopy());
        cardsToAdd.add(new KangKai().makeCopy());
        cardsToAdd.add(new JueDou().makeCopy());
        cardsToAdd.add(new JunLue().makeCopy());
        cardsToAdd.add(new JuXiang().makeCopy());
        cardsToAdd.add(new JiangChi().makeCopy());
        cardsToAdd.add(new JianXiong().makeCopy());
        cardsToAdd.add(new JianYing().makeCopy());
        cardsToAdd.add(new JiaoZi().makeCopy());
        cardsToAdd.add(new JieMing().makeCopy());
        cardsToAdd.add(new JieYing().makeCopy());
        cardsToAdd.add(new JieYingZi().makeCopy());
        cardsToAdd.add(new JieZhiHeng().makeCopy());
        cardsToAdd.add(new JiLi().makeCopy());
        cardsToAdd.add(new Jiu().makeCopy());
        cardsToAdd.add(new JiZhi().makeCopy());
        cardsToAdd.add(new JueYing().makeCopy());
        cardsToAdd.add(new JuGu().makeCopy());
        cardsToAdd.add(new JuShou().makeCopy());
        cardsToAdd.add(new KeJi().makeCopy());
        cardsToAdd.add(new KongCheng().makeCopy());
        cardsToAdd.add(new KuangCai().makeCopy());
        cardsToAdd.add(new KuangFeng().makeCopy());
        cardsToAdd.add(new KuangGu().makeCopy());
        cardsToAdd.add(new KunFen().makeCopy());
        cardsToAdd.add(new KuRou().makeCopy());
        cardsToAdd.add(new LangXi().makeCopy());
        cardsToAdd.add(new LeBuSiShu().makeCopy());
        cardsToAdd.add(new LeiJi().makeCopy());
        cardsToAdd.add(new LeiSha().makeCopy());
        cardsToAdd.add(new LiangZhu().makeCopy());
        cardsToAdd.add(new LianPo().makeCopy());
        cardsToAdd.add(new LianYing().makeCopy());
        cardsToAdd.add(new LieGong().makeCopy());
        cardsToAdd.add(new LiMu().makeCopy());
        cardsToAdd.add(new LongDan().makeCopy());
        cardsToAdd.add(new LuanJi().makeCopy());
        cardsToAdd.add(new LuoYi().makeCopy());
        cardsToAdd.add(new MieJi().makeCopy());
        cardsToAdd.add(new MiJi().makeCopy());
        cardsToAdd.add(new MingJian().makeCopy());
        cardsToAdd.add(new MoShi().makeCopy());
        cardsToAdd.add(new MuMu().makeCopy());
        cardsToAdd.add(new NanManRuQin().makeCopy());
        cardsToAdd.add(new PaiYi().makeCopy());
        cardsToAdd.add(new PaoXiao().makeCopy());
        cardsToAdd.add(new PoJun().makeCopy());
        cardsToAdd.add(new QiangXi().makeCopy());
        cardsToAdd.add(new QiangZhi().makeCopy());
        //l.add(new QiaoMeng().makeCopy());
        cardsToAdd.add(new QiCai().makeCopy());
        cardsToAdd.add(new QiMou().makeCopy());
        cardsToAdd.add(new QingGuo().makeCopy());
        cardsToAdd.add(new QinYin().makeCopy());
        cardsToAdd.add(new QuanJi().makeCopy());
        cardsToAdd.add(new QueDi1().makeCopy());
        cardsToAdd.add(new QueDi2().makeCopy());
        cardsToAdd.add(new QueDi3().makeCopy());
        cardsToAdd.add(new RenDe().makeCopy());
        cardsToAdd.add(new Sha().makeCopy());
        cardsToAdd.add(new Shan().makeCopy());
        cardsToAdd.add(new ShanDian().makeCopy());
        cardsToAdd.add(new ShiBei().makeCopy());
        cardsToAdd.add(new ShuangXiong().makeCopy());
        cardsToAdd.add(new ShunShouQianYang().makeCopy());
        cardsToAdd.add(new Tao().makeCopy());
        cardsToAdd.add(new TaoLuan().makeCopy());
        cardsToAdd.add(new TaoYuanJieYi().makeCopy());
        cardsToAdd.add(new TiaoXin().makeCopy());
        cardsToAdd.add(new TieSuoLianHuan().makeCopy());
        cardsToAdd.add(new TunJiang().makeCopy());
        cardsToAdd.add(new TuShe().makeCopy());
        cardsToAdd.add(new WangXi().makeCopy());
        cardsToAdd.add(new WanJianQiFa().makeCopy());
        cardsToAdd.add(new WuGuFengDeng().makeCopy());
        cardsToAdd.add(new WuSheng().makeCopy());
        cardsToAdd.add(new WuShuang().makeCopy());
        cardsToAdd.add(new WuXieKeJi().makeCopy());
        cardsToAdd.add(new WuZhongShengYou().makeCopy());
        cardsToAdd.add(new XianTu().makeCopy());
        cardsToAdd.add(new XiongHuo().makeCopy());
        cardsToAdd.add(new XiongLuan().makeCopy());
        cardsToAdd.add(new XunXun().makeCopy());
        cardsToAdd.add(new YanYu().makeCopy());
        cardsToAdd.add(new YaoWu().makeCopy());
        cardsToAdd.add(new YeYan().makeCopy());
        cardsToAdd.add(new YingHun().makeCopy());
        cardsToAdd.add(new YingJian().makeCopy());
        cardsToAdd.add(new YingYuan().makeCopy());
        cardsToAdd.add(new YiJi().makeCopy());
        cardsToAdd.add(new YiZhong().makeCopy());
        cardsToAdd.add(new YouDi().makeCopy());
        cardsToAdd.add(new ZhanHuo().makeCopy());
        cardsToAdd.add(new ZhanJue().makeCopy());
        cardsToAdd.add(new ZhaXiang().makeCopy());
        cardsToAdd.add(new ZhiHeng().makeCopy());
        for(AbstractCard c :cardsToAdd){
            UnlockTracker.markCardAsSeen(c.cardID);
        }
    }


    @Override
    public void receiveEditRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(BaiYinShiZi.ID);
        retVal.add(BaGuaZhen.ID);
        retVal.add(GuDingDao.ID);
        retVal.add(LuxuryGeneralCardPack.ID);
        retVal.add(QingGangJian.ID);
        retVal.add(QingLongYanYueDao.ID);
        retVal.add(RandomGeneralCardPack.ID);
        retVal.add(RenWangDun.ID);
        retVal.add(SgsModCore.ID);
        retVal.add(ShengGuangBaiYi.ID);
        retVal.add(ZhuGeLianNu.ID);
        retVal.add(ZhuQueYuShan.ID);

        BaseMod.addRelicToCustomPool(new BaiYinShiZi(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new BaGuaZhen(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new GuDingDao(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new LuxuryGeneralCardPack(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new QingGangJian(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new QingLongYanYueDao(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new RandomGeneralCardPack(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new RenWangDun(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new SgsModCore(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new ShengGuangBaiYi(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new ZhuGeLianNu(), androidTestMod.enums.CardColorEnum.SGS_COLOR);
        BaseMod.addRelicToCustomPool(new ZhuQueYuShan(), androidTestMod.enums.CardColorEnum.SGS_COLOR);

        //解锁所有的自定遗物；
        for (String s : retVal) {
            UnlockTracker.markRelicAsSeen(s);
        }
    }

    @Override
    public void receiveEditKeywords() {
        String language;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
            default:
                language = "eng";
        }
        final Gson gson = new Gson();
        final String json = AssetLoader.getString(MOD_ID, "localization/" + language + "/AndroidTest-KeywordStrings.json");
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        BaseMod.addKeyword(null, new String[] { "基本" }, "包括 #y杀 、 #y闪 、 #y桃 、 #y酒 、 #y火杀 和 #y雷杀 。");
        BaseMod.addKeyword(null, new String[] { "暴戾" }, "拥有 #y暴戾 标记的单位受到的攻击伤害增加50%。回合开始时，随机执行以下一项： NL -受到10点 #r火焰 伤害和1层 #y虚弱 ； NL -失去10点生命； NL -失去2点力量，令玩家获得2点力量。 NL 有 #y暴戾标记 的目标死亡时，在玩家手牌中加入一张“徐荣·暴戾”。");
        BaseMod.addKeyword(null, new String[] { "次数" }, "每次使用 #y“杀” 时消耗1次使用次数。通常，在每个回合开始获得1次“杀”的使用次数。");
        BaseMod.addKeyword(null, new String[] { "转化" }, "#y转化 的牌耗能变为0，且生效一次后就会 #b消耗。");
        BaseMod.addKeyword(null, new String[] { "转换技" }, "使用带有 #y转换技 的牌时，将循环触发 #b阳 和 #b阴 的效果。");
        BaseMod.addKeyword(null, new String[] { "锦囊" }, "包含三国杀原版的所有锦囊牌。");
        BaseMod.addKeyword(null, new String[] { "火焰" }, "处于 #y连环 状态下的单位受到 #r火焰 伤害时，所有处于 #y连环 状态的敌人受到等量伤害，然后移除连环状态。");
        BaseMod.addKeyword(null, new String[] { "雷电" }, "处于 #y连环 状态下的单位受到 #b雷电 伤害时，所有处于 #y连环 状态的敌人受到等量伤害，然后移除连环状态。");
        BaseMod.addKeyword(null, new String[] { "连环" }, "处于 #y连环 状态下的敌人受到属性伤害（包括 #y火焰 和 #y雷电 ）时，所有处于 #y连环 状态的敌人受到等量伤害，然后移除连环状态。");
        BaseMod.addKeyword(null, new String[] { "通用" }, "包括 #y力量 、 #y多层护甲 、 #y金属化 、 #y壁垒 。");
        BaseMod.addKeyword(null, new String[] { "可多次使用" }, "#y可多次使用 的牌打出后将返回你的手牌，并在 #b回合结束 时自动丢弃。");
        BaseMod.addKeyword(null, new String[] { "军略" }, "用于触发 #b摧克 和 #b绽火 的效果。");
        BaseMod.addKeyword(null, new String[] { "权" }, "每有一张 #b权 ，你的手牌上限+1。");
        BaseMod.addKeyword(null, new String[] { "却敌" }, "选择： NL 1.抽 #y3 张牌。 NL 2.丢弃一张 基本 牌，本回合内使用的下一张杀或决斗伤害提高 #y20 点。 NL 3.依次执行前两项，失去 #y1 点最大生命。");
        BaseMod.addKeyword(null, new String[] { "不屈" }, "当你失去生命时，改为失去等量的 #y不屈 层数。当失去全部的 #y不屈 时，受到以此法抵挡的伤害。");
        BaseMod.addKeyword(null, new String[] { "向死存魏" }, "将生命值调整为1，并获得等同于失去生命的 #b格挡 和 #b壁垒 效果。");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Mycharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, CG, CardColorEnum.SGS);

    }

    @Override
    public void receivePostEnergyRecharge() {
        for (AbstractCard c : recyclecards) {
            AbstractCard card = c.makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, false, true, true));
        }
        recyclecards.clear();
    }
}
