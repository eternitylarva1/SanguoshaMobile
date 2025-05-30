//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.screens;

import androidTestMod.patch.SkinSelectPatch;
import androidTestMod.patch.SkinSelectScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.TrialHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import com.megacrit.cardcrawl.metrics.BotDataUploader.GameDataType;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Iterator;

public class CharacterSelectScreen {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final UIStrings uiStrings2;
    public static final String[] A_TEXT;
    private static float ASC_LEFT_W;
    private static float ASC_RIGHT_W;
    private SeedPanel seedPanel = new SeedPanel();
    private static final float SEED_X;
    private static final float SEED_Y;
    private static final String CHOOSE_CHAR_MSG;
    public ConfirmButton confirmButton;
    public MenuCancelButton cancelButton;
    public ArrayList<CharacterOption> options;
    private boolean anySelected;
    public Texture bgCharImg;
    private Color bgCharColor;
    private static final float BG_Y_OFFSET_TARGET = 0.0F;
    private float bg_y_offset;
    public boolean isAscensionMode;
    private boolean isAscensionModeUnlocked;
    private Hitbox ascensionModeHb;
    private Hitbox ascLeftHb;
    private Hitbox ascRightHb;
    private Hitbox seedHb;
    public int ascensionLevel;
    public String ascLevelInfoString;

    public CharacterSelectScreen() {
        this.confirmButton = new ConfirmButton(TEXT[1]);
        this.cancelButton = new MenuCancelButton();
        this.options = new ArrayList();
        this.anySelected = false;
        this.bgCharImg = null;
        this.bgCharColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.bg_y_offset = 0.0F;
        this.isAscensionMode = false;
        this.isAscensionModeUnlocked = false;
        this.ascensionLevel = 0;
        this.ascLevelInfoString = "";
    }

    public void initialize() {
        this.options.add(new CharacterOption(TEXT[2], CardCrawlGame.characterManager.recreateCharacter(PlayerClass.IRONCLAD), ImageMaster.CHAR_SELECT_IRONCLAD, ImageMaster.CHAR_SELECT_BG_IRONCLAD));
        if (!UnlockTracker.isCharacterLocked("The Silent")) {
            this.options.add(new CharacterOption(TEXT[3], CardCrawlGame.characterManager.recreateCharacter(PlayerClass.THE_SILENT), ImageMaster.CHAR_SELECT_SILENT, ImageMaster.CHAR_SELECT_BG_SILENT));
        } else {
            this.options.add(new CharacterOption(CardCrawlGame.characterManager.recreateCharacter(PlayerClass.THE_SILENT)));
        }

        if (!UnlockTracker.isCharacterLocked("Defect")) {
            this.options.add(new CharacterOption(TEXT[4], CardCrawlGame.characterManager.recreateCharacter(PlayerClass.DEFECT), ImageMaster.CHAR_SELECT_DEFECT, ImageMaster.CHAR_SELECT_BG_DEFECT));
        } else {
            this.options.add(new CharacterOption(CardCrawlGame.characterManager.recreateCharacter(PlayerClass.DEFECT)));
        }

        if (!UnlockTracker.isCharacterLocked("Watcher")) {
            this.addCharacterOption(PlayerClass.WATCHER);
        } else {
            this.options.add(new CharacterOption(CardCrawlGame.characterManager.recreateCharacter(PlayerClass.WATCHER)));
        }

        this.positionButtons();
        this.isAscensionMode = Settings.gamePref.getBoolean("Ascension Mode Default", false);
        FontHelper.cardTitleFont.getData().setScale(1.0F);
        ASC_LEFT_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[6], 9999.0F, 0.0F);
        ASC_RIGHT_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[7] + "22", 9999.0F, 0.0F);
        this.ascensionModeHb = new Hitbox(ASC_LEFT_W + 100.0F * Settings.scale, 70.0F * Settings.scale);
        if (!Settings.isMobile) {
            this.ascensionModeHb.move((float)Settings.WIDTH / 2.0F - ASC_LEFT_W / 2.0F - 50.0F * Settings.scale, 70.0F * Settings.scale);
        } else {
            this.ascensionModeHb.move((float)Settings.WIDTH / 2.0F - ASC_LEFT_W / 2.0F - 50.0F * Settings.scale, 100.0F * Settings.scale);
        }

        this.ascLeftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.ascRightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.seedHb = new Hitbox(700.0F * Settings.scale, 60.0F * Settings.scale);
        this.seedHb.move(90.0F * Settings.scale, 70.0F * Settings.scale);
    }

    private void addCharacterOption(AbstractPlayer.PlayerClass c) {
        AbstractPlayer p = CardCrawlGame.characterManager.recreateCharacter(c);
        this.options.add(p.getCharacterSelectOption());
    }

    private void positionButtons() {
        int count = this.options.size();
        float offsetX = (float)Settings.WIDTH / 2.0F - 330.0F * Settings.scale;

        for(int i = 0; i < count; ++i) {
            if (Settings.isMobile) {
                ((CharacterOption)this.options.get(i)).hb.move(offsetX + (float)i * 220.0F * Settings.scale, 230.0F * Settings.yScale);
            } else {
                ((CharacterOption)this.options.get(i)).hb.move(offsetX + (float)i * 220.0F * Settings.scale, 190.0F * Settings.yScale);
            }
        }

    }

    public void open(boolean isEndless) {
        Settings.isEndless = isEndless;
        Settings.seedSet = false;
        Settings.seed = null;
        Settings.specialSeed = null;
        Settings.isTrial = false;
        CardCrawlGame.trial = null;
        this.cancelButton.show(TEXT[5]);
        CardCrawlGame.mainMenuScreen.screen = CurScreen.CHAR_SELECT;
    }

    private void setRandomSeed() {
        long sourceTime = System.nanoTime();
        Random rng = new Random(sourceTime);
        Settings.seedSourceTimestamp = sourceTime;
        Settings.seed = SeedHelper.generateUnoffensiveSeed(rng);
        Settings.seedSet = false;
    }

    public void update() {
        if (SkinSelectPatch.isSelected())
            SkinSelectScreen.Inst.update();
        if (this.ascLeftHb != null) {
            if (!Settings.isMobile) {
                this.ascLeftHb.move((float)Settings.WIDTH / 2.0F + 200.0F * Settings.scale - ASC_RIGHT_W * 0.25F, 70.0F * Settings.scale);
                this.ascRightHb.move((float)Settings.WIDTH / 2.0F + 200.0F * Settings.scale + ASC_RIGHT_W * 1.25F, 70.0F * Settings.scale);
            } else {
                this.ascLeftHb.move((float)Settings.WIDTH / 2.0F + 200.0F * Settings.scale - ASC_RIGHT_W * 0.25F, 100.0F * Settings.scale);
                this.ascRightHb.move((float)Settings.WIDTH / 2.0F + 200.0F * Settings.scale + ASC_RIGHT_W * 1.25F, 100.0F * Settings.scale);
            }
        }

        this.anySelected = false;
        if (!this.seedPanel.shown) {
            Iterator var1 = this.options.iterator();

            while(var1.hasNext()) {
                CharacterOption o = (CharacterOption)var1.next();
                o.update();
                if (o.selected) {
                    this.anySelected = true;
                    this.isAscensionModeUnlocked = UnlockTracker.isAscensionUnlocked(o.c);
                    if (!this.isAscensionModeUnlocked) {
                        this.isAscensionMode = false;
                    }
                }
            }

            this.updateButtons();
            if (InputHelper.justReleasedClickLeft && !this.anySelected) {
                this.confirmButton.isDisabled = true;
                this.confirmButton.hide();
            }

            if (this.anySelected) {
                this.bgCharColor.a = MathHelper.fadeLerpSnap(this.bgCharColor.a, 1.0F);
                this.bg_y_offset = MathHelper.fadeLerpSnap(this.bg_y_offset, -0.0F);
            } else {
                this.bgCharColor.a = MathHelper.fadeLerpSnap(this.bgCharColor.a, 0.0F);
            }

            this.updateAscensionToggle();
            if (this.anySelected) {
                this.seedHb.update();
            }
        }

        this.seedPanel.update();
        if (this.seedHb.hovered && InputHelper.justClickedLeft || CInputActionSet.drawPile.isJustPressed()) {
            InputHelper.justClickedLeft = false;
            this.seedHb.hovered = false;
            this.seedPanel.show();
        }

        CardCrawlGame.mainMenuScreen.superDarken = this.anySelected;
    }

    private void updateAscensionToggle() {
        if (this.isAscensionModeUnlocked) {
            if (this.anySelected) {
                this.ascensionModeHb.update();
                this.ascRightHb.update();
                this.ascLeftHb.update();
            }

            if (InputHelper.justClickedLeft) {
                if (this.ascensionModeHb.hovered) {
                    this.ascensionModeHb.clickStarted = true;
                } else if (this.ascRightHb.hovered) {
                    this.ascRightHb.clickStarted = true;
                } else if (this.ascLeftHb.hovered) {
                    this.ascLeftHb.clickStarted = true;
                }
            }

            if (this.ascensionModeHb.clicked || CInputActionSet.proceed.isJustPressed()) {
                this.ascensionModeHb.clicked = false;
                this.isAscensionMode = !this.isAscensionMode;
                Settings.gamePref.putBoolean("Ascension Mode Default", this.isAscensionMode);
                Settings.gamePref.flush();
            }

            Iterator var1;
            CharacterOption o;
            if (this.ascLeftHb.clicked || CInputActionSet.pageLeftViewDeck.isJustPressed()) {
                this.ascLeftHb.clicked = false;
                var1 = this.options.iterator();

                while(var1.hasNext()) {
                    o = (CharacterOption)var1.next();
                    if (o.selected) {
                        o.decrementAscensionLevel(this.ascensionLevel - 1);
                        break;
                    }
                }
            }

            if (this.ascRightHb.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                this.ascRightHb.clicked = false;
                var1 = this.options.iterator();

                while(var1.hasNext()) {
                    o = (CharacterOption)var1.next();
                    if (o.selected) {
                        o.incrementAscensionLevel(this.ascensionLevel + 1);
                        break;
                    }
                }
            }
        }

    }

    public void justSelected() {
        this.bg_y_offset = 0.0F;
    }

    public void updateButtons() {
        this.cancelButton.update();
        this.confirmButton.update();
        CharacterOption selected;
        if (this.cancelButton.hb.clicked || InputHelper.pressedEscape) {
            CardCrawlGame.mainMenuScreen.superDarken = false;
            InputHelper.pressedEscape = false;
            this.cancelButton.hb.clicked = false;
            this.cancelButton.hide();
            CardCrawlGame.mainMenuScreen.panelScreen.refresh();

            for(Iterator var1 = this.options.iterator(); var1.hasNext(); selected.selected = false) {
                selected = (CharacterOption)var1.next();
            }

            this.bgCharColor.a = 0.0F;
            this.anySelected = false;
        }

        if (this.confirmButton.hb.clicked) {
            this.confirmButton.hb.clicked = false;
            this.confirmButton.isDisabled = true;
            this.confirmButton.hide();
            if (Settings.seed == null) {
                this.setRandomSeed();
            } else {
                Settings.seedSet = true;
            }

            CardCrawlGame.mainMenuScreen.isFadingOut = true;
            CardCrawlGame.mainMenuScreen.fadeOutMusic();
            Settings.isDailyRun = false;
            boolean isTrialSeed = TrialHelper.isTrialSeed(SeedHelper.getString(Settings.seed));
            if (isTrialSeed) {
                Settings.specialSeed = Settings.seed;
                long sourceTime = System.nanoTime();
                Random rng = new Random(sourceTime);
                Settings.seed = SeedHelper.generateUnoffensiveSeed(rng);
                Settings.isTrial = true;
            }

            ModHelper.setModsFalse();
            AbstractDungeon.generateSeeds();
            AbstractDungeon.isAscensionMode = this.isAscensionMode;
            if (this.isAscensionMode) {
                AbstractDungeon.ascensionLevel = this.ascensionLevel;
            } else {
                AbstractDungeon.ascensionLevel = 0;
            }

            this.confirmButton.hb.clicked = false;
            this.confirmButton.hide();
            selected = null;
            Iterator var3 = this.options.iterator();

            while(var3.hasNext()) {
                CharacterOption o = (CharacterOption)var3.next();
                if (o.selected) {
                    selected = o;
                }
            }

            if (selected != null && CardCrawlGame.steelSeries.isEnabled) {
                CardCrawlGame.steelSeries.event_character_chosen(selected.c.chosenClass);
            }

            if (Settings.isDemo || Settings.isPublisherBuild) {
                BotDataUploader poster = new BotDataUploader();
                poster.setValues(GameDataType.DEMO_EMBARK, (String)null, (ArrayList)null);
                Thread t = new Thread(poster);
                t.setName("LeaderboardPoster");
                t.run();
            }
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.bgCharColor);
        if (this.bgCharImg != null) {
            if (Settings.isSixteenByTen) {
                sb.draw(this.bgCharImg, (float)Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
            } else if (Settings.isFourByThree) {
                sb.draw(this.bgCharImg, (float)Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.yScale, Settings.yScale, 0.0F, 0, 0, 1920, 1200, false, false);
            } else if (Settings.isLetterbox) {
                sb.draw(this.bgCharImg, (float)Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.xScale, Settings.xScale, 0.0F, 0, 0, 1920, 1200, false, false);
            } else {
                sb.draw(this.bgCharImg, (float)Settings.WIDTH / 2.0F - 960.0F, (float)Settings.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
            }
        }

        this.cancelButton.render(sb);
        this.confirmButton.render(sb);
        this.renderAscensionMode(sb);
        this.renderSeedSettings(sb);
        this.seedPanel.render(sb);
        boolean anythingSelected = false;
        CharacterOption o;
        if (!this.seedPanel.shown) {
            for(Iterator var3 = this.options.iterator(); var3.hasNext(); o.render(sb)) {
                o = (CharacterOption)var3.next();
                if (o.selected) {
                    anythingSelected = true;
                }
            }
        }

        if (!this.seedPanel.shown && !anythingSelected) {
            if (!Settings.isMobile) {
                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CHOOSE_CHAR_MSG, (float)Settings.WIDTH / 2.0F, 340.0F * Settings.yScale, Settings.CREAM_COLOR, 1.2F);
            } else {
                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CHOOSE_CHAR_MSG, (float)Settings.WIDTH / 2.0F, 380.0F * Settings.yScale, Settings.CREAM_COLOR, 1.2F);
            }
        }
        if (SkinSelectPatch.isSelected())
            SkinSelectScreen.Inst.render(sb);
    }

    private void renderSeedSettings(SpriteBatch sb) {
        if (this.anySelected) {
            Color textColor = Settings.GOLD_COLOR;
            if (this.seedHb.hovered) {
                textColor = Settings.GREEN_TEXT_COLOR;
                TipHelper.renderGenericTip((float)InputHelper.mX + 50.0F * Settings.scale, (float)InputHelper.mY + 100.0F * Settings.scale, TEXT[11], TEXT[12]);
            }

            if (!Settings.isControllerMode) {
                if (Settings.seedSet) {
                    FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[10], SEED_X, SEED_Y, 9999.0F, 0.0F, textColor);
                    FontHelper.renderFontLeftTopAligned(sb, FontHelper.cardTitleFont, SeedHelper.getUserFacingSeedString(), SEED_X - 30.0F * Settings.scale + FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[13], 9999.0F, 0.0F), 90.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
                } else {
                    FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[13], SEED_X, SEED_Y, 9999.0F, 0.0F, textColor);
                }
            } else {
                if (Settings.seedSet) {
                    FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[10], SEED_X + 100.0F * Settings.scale, SEED_Y, 9999.0F, 0.0F, textColor);
                    FontHelper.renderFontLeftTopAligned(sb, FontHelper.cardTitleFont, SeedHelper.getUserFacingSeedString(), SEED_X - 30.0F * Settings.scale + FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[13], 9999.0F, 0.0F) + 100.0F * Settings.scale, 90.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
                } else {
                    FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[13], SEED_X + 100.0F * Settings.scale, SEED_Y, 9999.0F, 0.0F, textColor);
                }

                sb.draw(ImageMaster.CONTROLLER_LT, 80.0F * Settings.scale - 32.0F, 80.0F * Settings.scale - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            }

            this.seedHb.render(sb);
        }
    }

    private void renderAscensionMode(SpriteBatch sb) {
        if (this.anySelected) {
            if (this.isAscensionModeUnlocked) {
                if (!Settings.isMobile) {
                    sb.draw(ImageMaster.OPTION_TOGGLE, (float)Settings.WIDTH / 2.0F - ASC_LEFT_W - 16.0F - 30.0F * Settings.scale, this.ascensionModeHb.cY - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);
                } else {
                    sb.draw(ImageMaster.CHECKBOX, (float)Settings.WIDTH / 2.0F - ASC_LEFT_W - 36.0F * Settings.scale - 32.0F, this.ascensionModeHb.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);
                }

                if (this.ascensionModeHb.hovered) {
                    FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[6], (float)Settings.WIDTH / 2.0F - ASC_LEFT_W / 2.0F, this.ascensionModeHb.cY, Settings.GREEN_TEXT_COLOR);
                    TipHelper.renderGenericTip((float)InputHelper.mX - 140.0F * Settings.scale, (float)InputHelper.mY + 340.0F * Settings.scale, TEXT[8], TEXT[9]);
                } else {
                    FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[6], (float)Settings.WIDTH / 2.0F - ASC_LEFT_W / 2.0F, this.ascensionModeHb.cY, Settings.GOLD_COLOR);
                }

                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[7] + this.ascensionLevel, (float)Settings.WIDTH / 2.0F + ASC_RIGHT_W / 2.0F + 200.0F * Settings.scale, this.ascensionModeHb.cY, Settings.BLUE_TEXT_COLOR);
                if (this.isAscensionMode) {
                    sb.setColor(Color.WHITE);
                    if (!Settings.isMobile) {
                        sb.draw(ImageMaster.OPTION_TOGGLE_ON, (float)Settings.WIDTH / 2.0F - ASC_LEFT_W - 16.0F - 30.0F * Settings.scale, this.ascensionModeHb.cY - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);
                    } else {
                        sb.draw(ImageMaster.TICK, (float)Settings.WIDTH / 2.0F - ASC_LEFT_W - 36.0F * Settings.scale - 32.0F, this.ascensionModeHb.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);
                    }

                    if (Settings.isMobile) {
                        FontHelper.renderFontCentered(sb, FontHelper.smallDialogOptionFont, this.ascLevelInfoString, (float)Settings.WIDTH / 2.0F, 60.0F * Settings.scale, Settings.CREAM_COLOR);
                    } else {
                        FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, this.ascLevelInfoString, (float)Settings.WIDTH / 2.0F, 35.0F * Settings.scale, Settings.CREAM_COLOR);
                    }
                }

                if (!this.ascLeftHb.hovered && !Settings.isControllerMode) {
                    sb.setColor(Color.LIGHT_GRAY);
                } else {
                    sb.setColor(Color.WHITE);
                }

                sb.draw(ImageMaster.CF_LEFT_ARROW, this.ascLeftHb.cX - 24.0F, this.ascLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
                if (!this.ascRightHb.hovered && !Settings.isControllerMode) {
                    sb.setColor(Color.LIGHT_GRAY);
                } else {
                    sb.setColor(Color.WHITE);
                }

                sb.draw(ImageMaster.CF_RIGHT_ARROW, this.ascRightHb.cX - 24.0F, this.ascRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
                if (Settings.isControllerMode) {
                    sb.draw(CInputActionSet.proceed.getKeyImg(), this.ascensionModeHb.cX - 100.0F * Settings.scale - 32.0F, this.ascensionModeHb.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                    sb.draw(CInputActionSet.pageLeftViewDeck.getKeyImg(), this.ascLeftHb.cX - 60.0F * Settings.scale - 32.0F, this.ascLeftHb.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                    sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), this.ascRightHb.cX + 60.0F * Settings.scale - 32.0F, this.ascRightHb.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                }

                this.ascensionModeHb.render(sb);
                this.ascLeftHb.render(sb);
                this.ascRightHb.render(sb);
            }

        }
    }

    public void deselectOtherOptions(CharacterOption characterOption) {
        Iterator var2 = this.options.iterator();

        while(var2.hasNext()) {
            CharacterOption o = (CharacterOption)var2.next();
            if (o != characterOption) {
                o.selected = false;
            }
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("CharacterSelectScreen");
        TEXT = uiStrings.TEXT;
        uiStrings2 = CardCrawlGame.languagePack.getUIString("AscensionModeDescriptions");
        A_TEXT = uiStrings2.TEXT;
        SEED_X = 60.0F * Settings.scale;
        SEED_Y = 90.0F * Settings.scale;
        CHOOSE_CHAR_MSG = TEXT[0];
    }
}
