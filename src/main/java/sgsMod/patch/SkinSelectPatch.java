package sgsMod.patch;



import com.megacrit.cardcrawl.android.mods.utils.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class SkinSelectPatch {
 
    public static boolean isSelected() {
        return (CardCrawlGame.chosenCharacter == sgsMod.enums.CardColorEnum.SGS &&(CardCrawlGame.mainMenuScreen != null
                && CardCrawlGame.mainMenuScreen.charSelectScreen != null
                && CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CHAR_SELECT
                && !AbstractDungeon.isPlayerInDungeon()
                && (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected")) );
    }
}
