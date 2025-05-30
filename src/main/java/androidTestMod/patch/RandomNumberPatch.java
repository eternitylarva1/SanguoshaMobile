package androidTestMod.patch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

//@SpirePatch(clz = DrawPilePanel.class, method = "render")
public class RandomNumberPatch {
    private static final float HB_W = 300.0F * Settings.scale;
    private static final float HB_H = 420.0F * Settings.scale;


    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(DrawPilePanel __instance, SpriteBatch sb) {
        if (AbstractDungeon.isScreenUp) {
            return;
        }

        AbstractCard hovered = null;
        int hoveredIndex = -1;

        List<AbstractCard> NextRandomCards = generateCardChoices(AbstractCard.CardType.POWER);

        int i = 0;
        for (AbstractCard c : NextRandomCards) {
                i++;

        }

        for (AbstractCard c : NextRandomCards) {
                i--;
                AbstractCard ret = renderCard(__instance, sb, c, i, 0.45F, true);
                if (ret != null) {
                    hovered = ret;
                    hoveredIndex = i;
                }
        }
        if (hovered != null)
            renderCard(__instance, sb, hovered, hoveredIndex, 0.8F, false);
    }

    private static Field frameShadowColorField = null;

    private static boolean isMintySpireExists() {
        try {
            Class.forName("mintySpire.utility.StsLibChecker");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static AbstractCard renderCard(DrawPilePanel __instance, SpriteBatch sb, AbstractCard card, int i, float scale, boolean hitbox) {
        AbstractCard hovered = null;
        float prev_current_x = card.current_x;
        float prev_current_y = card.current_y;
        float prev_drawScale = card.drawScale;
        float prev_angle = card.angle;
        card.current_x = __instance.current_x + (hitbox ? 75 : 'õ') * Settings.scale;
        card.current_y = __instance.current_y + (220 + i * 27) * Settings.scale;
        if (isMintySpireExists() && (AbstractDungeon.player.hasRelic("Frozen Eye") || AbstractDungeon.isScreenUp))
            card.current_y += 310.0F;
        card.drawScale = scale;
        card.angle = 0.0F;
        card.lighten(true);
        if (hitbox) {
            card.hb.move(card.current_x, card.current_y);
            card.hb.resize(HB_W * card.drawScale, HB_H * card.drawScale);
            card.hb.update();
            if (card.hb.hovered)
                hovered = card;
        }
        Color frameShadowColor = null;
        float prev_frameShadow_a = 0.0F;
        if (hitbox)
            try {
                if (frameShadowColorField == null) {
                    frameShadowColorField = AbstractCard.class.getDeclaredField("frameShadowColor");
                    frameShadowColorField.setAccessible(true);
                }
                frameShadowColor = (Color) frameShadowColorField.get(card);
                prev_frameShadow_a = frameShadowColor.a;
                frameShadowColor.a = 0.0F;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        card.render(sb);
        if (hitbox)
            frameShadowColor.a = prev_frameShadow_a;
        card.current_x = prev_current_x;
        card.current_y = prev_current_y;
        card.drawScale = prev_drawScale;
        card.angle = prev_angle;
        return hovered;
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(Hitbox.class, "render");
            return LineFinder.findInOrder(ctBehavior, (Matcher) methodCallMatcher);
        }
    }

    private static ArrayList<AbstractCard> generateCardChoices(AbstractCard.CardType type) {
        ArrayList<AbstractCard> derp = new ArrayList<>();
        Random newRandom = cardRandomRng.copy();

        while(derp.size() != 3) {
            boolean dupe = false;
            AbstractCard tmp = null;
            tmp = returnTrulyRandomCardInCombat(type, newRandom);

            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                derp.add(tmp.makeCopy());
            }
        }

        return derp;
    }

    public static AbstractCard returnTrulyRandomCardInCombat(AbstractCard.CardType type, Random random) {


        ArrayList<AbstractCard> list = new ArrayList<>();
        Iterator<AbstractCard> var2 = srcCommonCardPool.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.type == type && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcUncommonCardPool.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.type == type && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        var2 = srcRareCardPool.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.type == type && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        return list.get(random.random(list.size() - 1));
    }
}