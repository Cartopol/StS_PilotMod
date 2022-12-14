package pilot.characters;

import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import basemod.interfaces.OnStartBattleSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import pilot.PilotMod;
import pilot.actions.AddToTitanDeckAction;
import pilot.actions.DrawArmamentAction;
import pilot.actions.StartCountingReflexDrawsAction;
import pilot.cards.OnDrawCardSubscriber;
import pilot.cards.pilot.Command;
import pilot.cards.pilot.Defend;
import pilot.cards.pilot.Strike;
import pilot.cards.pilot.titan_deck.TitanDeck;
import pilot.cards.pilot.titan_deck.armaments.CoverFire;
import pilot.cards.pilot.titan_deck.armaments.Shield;
import pilot.patches.ArmamentFieldPatch;
import pilot.powers.MomentumPower;
import pilot.relics.DomeShieldRelic;
import pilot.titan.TitanOrb;

import java.util.ArrayList;
import java.util.List;

import static pilot.PilotMod.makeCharPath;
import static pilot.PilotMod.makeID;
import static pilot.characters.Pilot.Enums.PILOT_CARD_COLOR;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in PilotMod-Character-Strings.json in the resources

public class Pilot extends CustomPlayer implements OnStartBattleSubscriber, OnDrawCardSubscriber {
    private static final int TITAN_BASE_SHIELDS = 8;
    private static final int ADVANTAGE_THRESHOLD = 5;
    public boolean startCounting;


    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass PILOT;
        @SpireEnum(name = "PILOT_GRAY_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor PILOT_CARD_COLOR;
        @SpireEnum(name = "PILOT_GRAY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType PILOT_LIBRARY_COLOR;
    }

    // Note: These have to live in a separate static subclass to ensure the BaseMode.addColor call can happen before the
    // static initializers for the class run, due to temporal coupling between the abstract base class initializers.
    public static class ColorInfo {
        // Character Color
        public static final Color CHARACTER_COLOR = new Color(0.7f, 0.8f, 1.0f, 1.0f);

        // Card backgrounds - The actual rectangular card.
        public static final String CARD_BG_ATTACK_TEXTURE = makeCharPath("pilot/card_bgs/card_bg_attack_512.png");
        public static final String CARD_BG_ATTACK_PORTRAIT_TEXTURE = makeCharPath("pilot/card_bgs/card_bg_attack_1024.png");
        public static final String CARD_BG_SKILL_TEXTURE = makeCharPath("pilot/card_bgs/card_bg_skill_512.png");
        public static final String CARD_BG_SKILL_PORTRAIT_TEXTURE = makeCharPath("pilot/card_bgs/card_bg_skill_1024.png");
        public static final String CARD_BG_POWER_TEXTURE = makeCharPath("pilot/card_bgs/card_bg_power_512.png");
        public static final String CARD_BG_POWER_PORTRAIT_TEXTURE = makeCharPath("pilot/card_bgs/card_bg_power_1024.png");
        public static final String CARD_OVERLAY_ENERGY_ORB_TEXTURE = makeCharPath("pilot/card_bgs/card_overlay_energy_orb_512.png");
        public static final String CARD_SMALL_ENERGY_ORB_TEXTURE = makeCharPath("pilot/card_bgs/card_small_energy_orb.png");
        public static final String CARD_ENERGY_ORB_PORTRAIT_TEXTURE = makeCharPath("pilot/card_bgs/card_energy_orb.png");

        public static void registerColorWithBaseMod() {
            BaseMod.addColor(
                    Enums.PILOT_CARD_COLOR,
                    CHARACTER_COLOR,
                    CHARACTER_COLOR,
                    CHARACTER_COLOR,
                    CHARACTER_COLOR,
                    CHARACTER_COLOR,
                    CHARACTER_COLOR,
                    CHARACTER_COLOR,
                    CARD_BG_ATTACK_TEXTURE,
                    CARD_BG_SKILL_TEXTURE,
                    CARD_BG_POWER_TEXTURE,
                    CARD_OVERLAY_ENERGY_ORB_TEXTURE,
                    CARD_BG_ATTACK_PORTRAIT_TEXTURE,
                    CARD_BG_SKILL_PORTRAIT_TEXTURE,
                    CARD_BG_POWER_PORTRAIT_TEXTURE,
                    CARD_ENERGY_ORB_PORTRAIT_TEXTURE,
                    CARD_SMALL_ENERGY_ORB_TEXTURE);
        }
    }

    // =============== CHARACTER ENUMERATORS  =================

    public int cardsDrawnAfterStartOfTurn = 0;

    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 60;
    public static final int MAX_HP = 60;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 4;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("PilotCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES ===============

    // Character assets
    public static final String CHARACTER_SELECT_BUTTON_TEXTURE = makeCharPath("pilot/char_select_button.png");
    public static final String CHARACTER_SELECT_BG_TEXTURE = makeCharPath("pilot/char_select_bg.png");
    public static final String SHOULDER_DARK_TEXTURE = makeCharPath("pilot/shoulder_dark.png");
    public static final String SHOULDER_LIT_TEXTURE = makeCharPath("pilot/shoulder_lit.png");
    public static final String CORPSE_TEXTURE = makeCharPath("pilot/corpse.png");

    public static final String[] ENERGY_ORB_LAYER_TEXTURES = {
            makeCharPath("pilot/energy_orb/layer1.png"),
            makeCharPath("pilot/energy_orb/layer2.png"),
            makeCharPath("pilot/energy_orb/layer3.png"),
            makeCharPath("pilot/energy_orb/layer4.png"),
            makeCharPath("pilot/energy_orb/layer5.png"),
            makeCharPath("pilot/energy_orb/layer6.png"),
            makeCharPath("pilot/energy_orb/layer1d.png"),
            makeCharPath("pilot/energy_orb/layer2d.png"),
            makeCharPath("pilot/energy_orb/layer3d.png"),
            makeCharPath("pilot/energy_orb/layer4d.png"),
            makeCharPath("pilot/energy_orb/layer5d.png"),};

    private static final float DIALOG_OFFSET_X = 0.0F * Settings.scale;
    private static final float DIALOG_OFFSET_Y = 200.0F * Settings.scale;

    // =============== /TEXTURES/ ===============

    public Pilot(String name, PlayerClass setClass) {
        super(
                name,
                setClass,
                ENERGY_ORB_LAYER_TEXTURES,
                makeCharPath("pilot/energy_orb/vfx.png"),
                null,
                (String) null);

        this.loadAnimation(PilotMod.makeCharPath("pilot/skeleton.atlas"), PilotMod.makeCharPath("pilot/skeleton.json"), 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        initializeClass(
                null,
                SHOULDER_DARK_TEXTURE,
                SHOULDER_LIT_TEXTURE,
                CORPSE_TEXTURE,
                getLoadout(), 0F, -10.0F, 160.0F, 280.0F, new EnergyManager(ENERGY_PER_TURN));

        this.dialogX = drawX + DIALOG_OFFSET_X;
        this.dialogY = drawY + DIALOG_OFFSET_Y;
    }

    @Override
    public void movePosition(float x, float y) {
        super.movePosition(x, y);
        this.dialogX = x + DIALOG_OFFSET_X;
        this.dialogY = y + DIALOG_OFFSET_Y;
    }

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                NAMES[0],
                TEXT[0],
                STARTING_HP,
                MAX_HP,
                ORB_SLOTS,
                STARTING_GOLD,
                CARD_DRAW,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        PilotMod.logger.info("Constructing Pilot starting deck");

        ArrayList<String> startingDeck = new ArrayList<>();

        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);

        startingDeck.add(Defend.ID);
        startingDeck.add(Defend.ID);
        startingDeck.add(Defend.ID);

//        startingDeck.add(Titanfall.ID);
        startingDeck.add(Command.ID);

        startingDeck.add(CoverFire.ID);
        startingDeck.add(Shield.ID);
        startingDeck.add(Shield.ID);

        return startingDeck;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        // Note: only the first relic gets replaced when selecting the "replace starter relic" Neow boon
        retVal.add(DomeShieldRelic.ID);

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_FIRE", 0.8f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_FIRE";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return PILOT_CARD_COLOR;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return ColorInfo.CHARACTER_COLOR;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    //TODO define card to use in Match and Keep event
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new Pilot(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return ColorInfo.CHARACTER_COLOR;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return ColorInfo.CHARACTER_COLOR;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.FIRE};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    // When you defeat the heart, this happens
    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(makeCharPath("pilot/victory_scene/panel_1.png"), "ORB_LIGHTNING_EVOKE"));
        panels.add(new CutscenePanel(makeCharPath("pilot/victory_scene/panel_2.png")));
        panels.add(new CutscenePanel(makeCharPath("pilot/victory_scene/panel_3.png")));
        return panels;
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage(makeCharPath("pilot/victory_scene/background.jpg"));
    }

    @Override
    public String getPortraitImageName() {
        return CHARACTER_SELECT_BG_TEXTURE;
    }

    @Override
    public void onVictory() {
        super.onVictory();
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        TitanDeck.reset();
        for (AbstractCard c : drawPile.group) {
            if (ArmamentFieldPatch.isArmament.get(c)) {
                AbstractDungeon.actionManager.addToBottom(new AddToTitanDeckAction(c, true));
            }
        }
    }

    @Override
    public void applyStartOfTurnRelics() {
        super.applyStartOfTurnRelics();
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        super.renderPlayerImage(sb);
    }

    @Override
    public void renderPowerTips(SpriteBatch sb) { super.renderPowerTips(sb);
    }

    public boolean hasTitan() {
        for (AbstractOrb o : this.orbs) {
            if (o instanceof TitanOrb) {
                return true;
            }
        } return false;
    }

    public TitanOrb getTitan() {
        if (hasTitan()) {
            for (AbstractOrb o : this.orbs) {
                if (o instanceof TitanOrb) {
                    return (TitanOrb) o;
                }
            }
        } return null;
    }

    public boolean hasAdvantage() {
        boolean hasAdvantage = this.hand.size() >= ADVANTAGE_THRESHOLD;
        return hasAdvantage;
    }

    public boolean hasMomentum() {
        boolean hasMomentum = this.hasPower(MomentumPower.POWER_ID);
        return hasMomentum;
    }

    // Checks if the player has drawn since the start of turn
    public boolean isReflexed() {
        boolean isReflexed = cardsDrawnAfterStartOfTurn > 0;
        PilotMod.logger.info("Pilot is Reflexed: {}", isReflexed);
        return isReflexed;
    }

    @Override
    public void applyStartOfTurnPostDrawRelics() {
        super.applyStartOfTurnPostDrawRelics();

        if (this.hasTitan()) {
            AbstractDungeon.actionManager.addToBottom(new DrawArmamentAction(1));
        }
        else {
            PilotMod.logger.info("Pilot does not have a Titan, skipping Armament draw");
        }

        AbstractDungeon.actionManager.addToBottom(new StartCountingReflexDrawsAction(this));

//        cardsDrawnAfterStartOfTurn = 0;
//        startCounting = true;
//        PilotMod.logger.info("Start counting card draws for Reflex");
//        PilotMod.logger.info("Cards drawn since turn start reset to 0");


        // This gives the Titan 1 Protect stack at the start of turn if it doesn't have it yet.
//        if (!this.hasPower(ProtectPower.POWER_ID)) {
//            PilotMod.logger.info("Start of turn: Pilot does not have Protect, giving him 1 stack");
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ProtectPower(this, 1)));
//        }
    }


//    @Override
//    public void applyStartOfTurnPostDrawPowers() {
//        super.applyStartOfTurnPostDrawPowers();
//        cardsDrawnAfterStartOfTurn = 0;
//        startCounting = true;
//        PilotMod.logger.info("Start counting card draws for Reflex");
//        PilotMod.logger.info("Cards drawn since turn start reset to 0");
//
//    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        PilotMod.logger.info("Battle Start: Titan created");
        AbstractDungeon.actionManager.addToTop(new ChannelAction(new TitanOrb(TITAN_BASE_SHIELDS)));
        PilotMod.logger.info("Drawing Armament for 1st turn of combat");
        AbstractDungeon.actionManager.addToBottom(new DrawArmamentAction(1));
        cardsDrawnAfterStartOfTurn = 0;
        startCounting = false;

    }


    @Override
    public void applyStartOfTurnPreDrawCards() {
        startCounting = false;
        cardsDrawnAfterStartOfTurn = 0;
        PilotMod.logger.info("Stop counting card draws for Reflex");
        super.applyStartOfTurnPreDrawCards();
    }

    @Override
    public void onDraw() {
        if (startCounting) {
            cardsDrawnAfterStartOfTurn++;
            PilotMod.logger.info("Cards drawn since turn start: {} ", cardsDrawnAfterStartOfTurn);
        }
    }




}
