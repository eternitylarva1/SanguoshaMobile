package androidTestMod;

import androidTestMod.cards.yellow.TestStrike;
import androidTestMod.character.Mycharacter;
import androidTestMod.enums.CardColorEnum;
import androidTestMod.enums.LibraryTypeEnum;
import androidTestMod.helpers.Keyword;
import androidTestMod.relics.RuneOctahedron;
import com.badlogic.gdx.graphics.Color;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.BaseMod;
import com.megacrit.cardcrawl.android.mods.helpers.CardColorBundle;
import com.megacrit.cardcrawl.android.mods.interfaces.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class AndroidTestMod implements EditCardsSubscriber,
                                       PostInitializeSubscriber,
                                       EditStringsSubscriber,
                                       EditRelicsSubscriber,
                                       EditKeywordsSubscriber,EditCharactersSubscriber {
    public static final String MOD_ID = "AndroidTestMod";
    public static final Color YELLOW_COLOR = new Color(0.98F, 0.95F, 0.05F, 1.0F);
    private static final String MY_CHARACTER_BUTTON = getResourcePath("char/Character_Button.png");
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = getResourcePath("char/Character_Portrait.png");
    public static void initialize() {
        new AndroidTestMod();
    }

    public AndroidTestMod() {
        BaseMod.subscribe(this);
        CardColorBundle bundle = new CardColorBundle();
        bundle.cardColor = CardColorEnum.YELLOW;
        bundle.modId = MOD_ID;
        bundle.bgColor =
            bundle.cardBackColor =
            bundle.frameColor =
            bundle.frameOutlineColor =
            bundle.descBoxColor =
            bundle.trailVfxColor =
            bundle.glowColor = YELLOW_COLOR;
        bundle.libraryType = LibraryTypeEnum.YELLOW;
        bundle.attackBg = getResourcePath("512/bg_attack_huntress.png");
        bundle.skillBg = getResourcePath("512/bg_skill_huntress.png");
        bundle.powerBg = getResourcePath("512/bg_power_huntress.png");
        bundle.cardEnergyOrb = getResourcePath("512/card_huntress_orb.png");
        bundle.energyOrb = getResourcePath("512/card_small_orb.png");
        bundle.attackBgPortrait = getResourcePath("1024/bg_attack_huntress.png");
        bundle.skillBgPortrait = getResourcePath("1024/bg_skill_huntress.png");
        bundle.powerBgPortrait = getResourcePath("1024/bg_power_huntress.png");
        bundle.energyOrbPortrait = getResourcePath("1024/card_huntress_orb.png");
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
        BaseMod.addCard(new TestStrike());
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.getColorBundleMap().get(CardColorEnum.YELLOW).loadRegion();
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
        BaseMod.loadCustomStringsFile(MOD_ID, CardStrings.class, "localization/" + language + "/AndroidTest-CardStrings.json");
        BaseMod.loadCustomStringsFile(MOD_ID, RelicStrings.class, "localization/" + language + "/AndroidTest-RelicStrings.json");
        BaseMod.loadCustomStringsFile(MOD_ID, CharacterStrings.class, "localization/" + language + "/characters.json");

    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new RuneOctahedron());
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
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Mycharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, CardColorEnum.Cangjie);

    }
}
