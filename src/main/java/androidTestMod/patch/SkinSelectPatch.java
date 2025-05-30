package androidTestMod.patch;



import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.megacrit.cardcrawl.android.mods.utils.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

public class SkinSelectPatch {
 
    public static boolean isSelected() {
        return (CardCrawlGame.chosenCharacter == androidTestMod.enums.CardColorEnum.SGS && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected"));
    }
}
