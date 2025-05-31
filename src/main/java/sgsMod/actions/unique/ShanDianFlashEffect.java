package sgsMod.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import sgsMod.SgsMod;import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ShanDianFlashEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private final Texture img;
    private static final int W = 32;
    private float scale;
    private AbstractCreature c;

    public ShanDianFlashEffect(AbstractCreature c) {
        this.scale = Settings.scale;
        if (!c.isDeadOrEscaped()) {
            this.x = c.hb.cX;
            this.y = c.hb.cY;
        }

        this.img =  AssetLoader.getTexture(SgsMod.MOD_ID,"img/power_img/shandian.png");

        this.duration = 0.7F;
        this.startingDuration = 0.7F;
        this.color = Color.WHITE.cpy();
        this.renderBehind = false;
    }

    public void update() {
        super.update();
        this.scale = Interpolation.exp5In.apply(Settings.scale, Settings.scale * 0.3F, this.duration / this.startingDuration);
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x - 16.0F, this.y - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, this.scale * 12.0F, this.scale * 12.0F, 0.0F, 0, 0, 32, 32, false, false);
        sb.draw(this.img, this.x - 16.0F, this.y - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, this.scale * 10.0F, this.scale * 10.0F, 0.0F, 0, 0, 32, 32, false, false);
        sb.draw(this.img, this.x - 16.0F, this.y - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, this.scale * 8.0F, this.scale * 8.0F, 0.0F, 0, 0, 32, 32, false, false);
        sb.draw(this.img, this.x - 16.0F, this.y - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, this.scale * 7.0F, this.scale * 7.0F, 0.0F, 0, 0, 32, 32, false, false);

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}