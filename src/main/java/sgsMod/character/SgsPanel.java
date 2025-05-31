//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sgsMod.character;

import sgsMod.SgsMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;

public class SgsPanel extends CutscenePanel {
    private Texture img;
    private Color color;
    public boolean activated;
    public boolean finished;
    public boolean fadeOut;
    private String sfx;

    public SgsPanel(String imgUrl, String sfx) {
        super(imgUrl);
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.activated = false;
        this.finished = false;
        this.fadeOut = false;
        this.sfx = null;
        this.img = AssetLoader.getTexture(SgsMod.MOD_ID,imgUrl);
        this.sfx = sfx;
    }

    public SgsPanel(String imgUrl) {
        this(imgUrl, (String)null);
    }

    public void update() {
        Color var10000;
        if (this.fadeOut) {
            var10000 = this.color;
            var10000.a -= Gdx.graphics.getDeltaTime();
            if (this.color.a < 0.0F) {
                this.color.a = 0.0F;
                this.finished = true;
            }

        } else {
            if (this.activated && !this.finished) {
                if (this.sfx != null) {
                    var10000 = this.color;
                    var10000.a += Gdx.graphics.getDeltaTime() * 10.0F;
                } else {
                    var10000 = this.color;
                    var10000.a += Gdx.graphics.getDeltaTime();
                }

                if (this.color.a > 1.0F) {
                    this.color.a = 1.0F;
                    this.finished = true;
                }
            }

        }
    }

    public void activate() {
        if (this.sfx != null) {
            CardCrawlGame.screenShake.shake(ShakeIntensity.HIGH, ShakeDur.SHORT, false);
            CardCrawlGame.sound.play(this.sfx);
            CardCrawlGame.sound.playA(this.sfx, -0.2F);
        }

        this.activated = true;
    }

    public void render(SpriteBatch sb) {
        if (this.img != null) {
            sb.setColor(this.color);
            if (Settings.isSixteenByTen) {
                sb.draw(this.img, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
            } else {
                sb.draw(this.img, 0.0F, -50.0F * Settings.scale, (float)Settings.WIDTH, (float)Settings.HEIGHT + 110.0F * Settings.scale);
            }
        }

    }

    public void fadeOut() {
        this.fadeOut = true;
        this.finished = false;
    }

    public void dispose() {
        if (this.img != null) {
            this.img.dispose();
            this.img = null;
        }

    }
}
