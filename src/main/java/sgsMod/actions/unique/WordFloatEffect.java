package sgsMod.actions.unique;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WordFloatEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 2.3F;
    private float x;
    private float y;
    private static final float OFFSET_Y;
    private final String msg;
    private float scale = 1.0F;
    private float swayTimer = 0.0F;
    public AbstractCreature target;

    public WordFloatEffect(float x, float y, String msg) {
        this.duration = 2.3F;
        this.startingDuration = 2.3F;
        this.x = x;
        this.y = y + OFFSET_Y;
        this.msg = msg;
        this.color = Color.GREEN.cpy();
    }

    public void update() {
        float GRAVITY_Y = 75.0F * Settings.scale;
        this.swayTimer -= Gdx.graphics.getDeltaTime() * 4.0F;
        this.x += MathUtils.cos(this.swayTimer) * 2.0F;
        this.y += GRAVITY_Y * Gdx.graphics.getDeltaTime();
        super.update();
        this.scale = Settings.scale * this.duration / 2.3F + 1.0F;
    }

    public void render(SpriteBatch sb) {
        FontHelper.damageNumberFont.getData().setScale(this.scale);
        FontHelper.renderFontCentered(sb, FontHelper.damageNumberFont, this.msg, this.x, this.y, this.color);
    }

    public void dispose() {
    }

    static {
        OFFSET_Y = 150.0F * Settings.scale;
    }
}