package sgsMod.patch;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;

import com.megacrit.cardcrawl.android.mods.BaseMod;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomSavable;
import com.megacrit.cardcrawl.android.mods.interfaces.ISubscriber;
import com.megacrit.cardcrawl.android.mods.utils.SpireConfig;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class SkinSelectScreen implements ISubscriber, CustomSavable<Integer> {

    private static final ArrayList<Skin> SKINS = new ArrayList<>();

    public static SkinSelectScreen Inst;

    public Hitbox leftHb;

    public Hitbox rightHb;
    private Hitbox DeckControlHb;
    public static boolean deckLock = false;

    public TextureAtlas atlas;

    public Skeleton skeleton;

    public AnimationStateData stateData;
    public AnimationState state;

    public static String curName = "";

    public static String nextName = "";

    public static int index;

    public static SpireConfig config;

    static {
        try {
            config = new SpireConfig(SgsMod.MOD_ID, SgsMod.MOD_ID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties theDefaultSettings = new Properties();

    static {
        SKINS.add(new Skin("img/char/ShiBin.png", "士兵（男）","",true));
        SKINS.add(new Skin("img/char/ShiBin_f.png", "士兵（女）","",false));
        SKINS.add(new Skin("img/char/JieXuSheng.png", "界徐盛","初始卡组携带“破军”",true));
        SKINS.add(new Skin("img/char/HuangYueYing.png", "黄月英","初始卡组携带“集智”", false));
        SKINS.add(new Skin("img/char/ZhangJiao.png", "张角","初始卡组携带“雷击”和“闪电”", true));
        SKINS.add(new Skin("img/char/YuanShao.png", "袁绍","初始卡组携带“乱击”", true));
        SKINS.add(new Skin("img/char/SunShangXiang.png","孙尚香","初始卡组携带“良助”",false));
        SKINS.add(new Skin("img/char/ZhangRang.png", "张让","初始卡组携带“滔乱”", true));
        SKINS.add(new Skin("img/char/LiuYan.png", "刘焉","初始卡组携带“图射”", true));
        SKINS.add(new Skin("img/char/ShenLuXun.png", "神陆逊","初始卡组携带“军略”", true));
        //SKINS.add(new Skin("img/char/XiaoQiao.png","小乔","",""));
        //SKINS.add(new Skin("img/char/BaoSanNiang.png", "鲍三娘","","ShuYong"));
        Inst = new SkinSelectScreen();
    }

    public static Skin getSkin() {
        return SKINS.get(index);
    }

    public SkinSelectScreen() {
        index = 0;
        refresh();
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.DeckControlHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);

        BaseMod.subscribe(this);
    }

    public static void refresh() {
        Skin skin = SKINS.get(index);
        curName = skin.name;
        nextName = ( SKINS.get(nextIndex())).name;

        try {
            config.setInt("Skin", index);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int prevIndex() {
        return (index - 1 < 0) ? (SKINS.size() - 1) : (index - 1);
    }

    public static int nextIndex() {
        return (index + 1 > SKINS.size() - 1) ? 0 : (index + 1);
    }

    public void update() {
        float centerX = Settings.WIDTH * 0.8F;
        float centerY = Settings.HEIGHT * 0.5F;
        this.leftHb.move(centerX - 200.0F * Settings.scale, centerY);
        this.rightHb.move(centerX + 200.0F * Settings.scale, centerY);
        this.DeckControlHb.move(centerX - 300.0F * Settings.scale, centerY - 110.0F * Settings.scale);
        updateInput();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == sgsMod.enums.CardColorEnum.SGS) {
            this.leftHb.update();
            this.rightHb.update();
            this.DeckControlHb.update();

            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
               

                index = prevIndex();
                //CardCrawlGame.sound.play(getSkin().word);
                refresh();
            }
            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
               
                index = nextIndex();
                //CardCrawlGame.sound.play(getSkin().word);
                refresh();
            }
            if (this.DeckControlHb.clicked || CInputActionSet.proceed.isJustPressed()) {
                this.DeckControlHb.clicked = false;
                deckLock = !deckLock;
            }

            if (InputHelper.justClickedLeft) {
                if (this.DeckControlHb.hovered) {
                    this.DeckControlHb.clickStarted = true;
                }
                if (this.leftHb.hovered)
                    this.leftHb.clickStarted = true;
                if (this.rightHb.hovered)
                    this.rightHb.clickStarted = true;
            }
        }
    }

    public void render(SpriteBatch sb) {
        Color RC = Color.valueOf("bacdbaff");
        float centerX = Settings.WIDTH * 0.8F;
        float centerY = Settings.HEIGHT * 0.5F;
        renderSkin(sb, centerX, centerY);

        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, curName, centerX, centerY, RC);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, getSkin().effect, centerX, centerY - Settings.HEIGHT * 0.05F, RC);
        String s = "皮肤仅影响外观，不改变初始卡组";
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, s, centerX, centerY - Settings.HEIGHT * 0.1F, RC);
        sb.draw(getSkin().t, centerX - Settings.WIDTH * 0.085F, centerY + Settings.HEIGHT * 0.035F);

        if (this.leftHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        if (this.rightHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb.cX - 24.0F, this.rightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);


        if (this.DeckControlHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CHECKBOX, this.DeckControlHb.cX - 24.0F, this.DeckControlHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);

        if (deckLock){
            //只有在影响初始卡组的情况下才会生效
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.TICK, this.DeckControlHb.cX - 24.0F, this.DeckControlHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);
        }

        this.rightHb.render(sb);
        this.leftHb.render(sb);
        this.DeckControlHb.render(sb);

    }

    public void renderSkin(SpriteBatch sb, float x, float y) {

        if (this.atlas == null)
            return;
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        this.skeleton.updateWorldTransform();
        this.skeleton.setPosition(x, y);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }

    public static class Skin {
        public String img;
        public Texture t;
        public String name;
        public String effect;
        public boolean isMale;
        public String word;

        public Skin(String img, String name, String effect, String word, boolean isMale) {
            this.img = img;
            t = AssetLoader.getTexture(SgsMod.MOD_ID,img);
            this.name = name;
            this.effect = effect;
            this.isMale = isMale;
            this.word = word;
        }

        public Skin(String charPath, String name, String effect, String word) {
            this(charPath, name, effect, word,  false);
        }

        public Skin(String charPath, String name, String effect, boolean isMale) {
            this(charPath, name, effect, "",  isMale);
        }
    }
    public void onLoad(Integer arg0) {
        index = arg0;
        refresh();
    }

    public Integer onSave() {
        return index;
    }

    public static boolean isMaleChar(){
        return getSkin().isMale;
    }

    public static void initModSettings() {
        //默认值
        theDefaultSettings.setProperty("Skin", "0");
        try {
            config = new SpireConfig("SgsMod", "SgsConfig", theDefaultSettings);
            config.load();
            index = config.getInt("Skin");
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
