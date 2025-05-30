package androidTestMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowRoom;

@SpirePatch(clz = NeowRoom.class, method = "update")
public class UnlockModAscensionLevelPatch {
    @SpireInsertPatch(rloc = 1)
    public static void Insert(NeowRoom n){
        AbstractPlayer p = AbstractDungeon.player;
        p.getCharStat().incrementVictory();
        int t = AbstractDungeon.ascensionLevel;
        for(int i = 0; i < 20; i++){
            AbstractDungeon.ascensionLevel = i;
            p.getCharStat().incrementAscension();
        }
        AbstractDungeon.ascensionLevel = t;
    }

}
