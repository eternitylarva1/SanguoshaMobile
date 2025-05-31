//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.neow;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class NeowRoom extends AbstractRoom {
    public NeowRoom(boolean isDone) {
        this.phase = RoomPhase.EVENT;
        this.event = new NeowEvent(isDone);
        this.event.onEnterRoom();
    }

    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        super.update();
        AbstractPlayer p = AbstractDungeon.player;
        p.getCharStat().incrementVictory();
        int t = AbstractDungeon.ascensionLevel;
        for(int i = 0; i < 20; i++){
            AbstractDungeon.ascensionLevel = i;
            p.getCharStat().incrementAscension();
        }
        AbstractDungeon.ascensionLevel = t;
        if (!AbstractDungeon.isScreenUp) {
            this.event.update();
        }

        if (this.event.waitTimer == 0.0F && !this.event.hasFocus && this.phase != RoomPhase.COMBAT) {
            this.phase = RoomPhase.COMPLETE;
            this.event.reopen();
        }

    }

    public void render(SpriteBatch sb) {
        super.render(sb);
        this.event.render(sb);
    }

    public void renderAboveTopPanel(SpriteBatch sb) {
        super.renderAboveTopPanel(sb);
        if (this.event != null) {
            this.event.renderAboveTopPanel(sb);
        }

    }
}
