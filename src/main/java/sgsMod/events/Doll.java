package sgsMod.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.List;

public class Doll extends AbstractImageEvent {
    public static final String ID = "Doll";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String ENTER_WORDS; //进入房间时的文字
    private static final String ACCEPT_BODY;
    private static final String EXIT_BODY;
    private static final float HP_DRAIN = 0.5F;
    private int screenNum = 0;
    private int hpLoss = 0;

    public Doll() {
        super(NAME, "test", "images/events/ghost.jpg");
        this.body = ENTER_WORDS;
        this.hpLoss = MathUtils.ceil((float)AbstractDungeon.player.maxHealth * 0.35F);
        if (this.hpLoss >= AbstractDungeon.player.maxHealth) {
            this.hpLoss = AbstractDungeon.player.maxHealth - 1;
        }

        if (AbstractDungeon.ascensionLevel >= 15) { //进阶十五后所有事件会变差

            this.imageEventText.setDialogOption(OPTIONS[3] + this.hpLoss + OPTIONS[1], new Apparition());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.hpLoss + OPTIONS[1], new Apparition());
        }

        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
           
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(ACCEPT_BODY);
                        AbstractDungeon.player.decreaseMaxHealth(this.hpLoss);
                        this.becomeGhost();
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    default:
                        logMetricIgnored("Ghosts");
                        this.imageEventText.updateBodyText(EXIT_BODY);
                        this.screenNum = 2;
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            case 1:
                this.openMap();
                break;
            default:
                this.openMap();
        }

    }

    private void becomeGhost() {
        List<String> cards = new ArrayList();
        int amount = 5;
        if (AbstractDungeon.ascensionLevel >= 15) {
            amount -= 2;
        }

        for(int i = 0; i < amount; ++i) {
            AbstractCard c = new Apparition();
            cards.add(c.cardID);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }

        logMetricObtainCardsLoseMapHP("Ghosts", "Became a Ghost", cards, this.hpLoss);
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Ghosts");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        ENTER_WORDS = DESCRIPTIONS[0];
        ACCEPT_BODY = DESCRIPTIONS[2];
        EXIT_BODY = DESCRIPTIONS[3];
    }






}
