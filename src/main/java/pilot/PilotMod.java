package pilot;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.clapper.util.classutil.RegexClassFilter;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.relics.CustomPilotModRelic;
import pilot.util.ReflectionUtils;
import pilot.util.TextureLoader;
import pilot.variables.BaseBlockNumber;
import pilot.variables.BaseDamageNumber;
import pilot.variables.MetaMagicNumber;
import pilot.variables.UrMagicNumber;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SpireInitializer
public class PilotMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        OnPowersModifiedSubscriber,
        StartActSubscriber,
        StartGameSubscriber {
    public static final String MOD_ID = "pilot";

    public static final Logger logger = LogManager.getLogger(PilotMod.class.getName());

    public static final String MODNAME = "The Pilot";
    private static final String AUTHOR = "";
    private static final String DESCRIPTION = "The Pilot";

    // =============== INPUT TEXTURE LOCATION =================

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "pilotResources/images/Badge.png";


    // =============== MAKE RESOURCE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return MOD_ID + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return MOD_ID + "Resources/images/relics/" + resourcePath;
    }

    public static String makeCharPath(String resourcePath) {
        return MOD_ID + "Resources/images/characters/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return MOD_ID + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return MOD_ID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return MOD_ID + "Resources/images/" + resourcePath;
    }

    public static String makeLocalizedStringsPath(Settings.GameLanguage language, String resourcePath) {
        String languageFolder =
                // Disable this until we can get it back up to date
                // Settings.language == Settings.GameLanguage.FRA ? "fra" :
                /* default: */ "eng";

        return MOD_ID + "Resources/localization/" + languageFolder + "/" + resourcePath;
    }

    // =============== /MAKE RESOURCE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public PilotMod() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        logger.info("Done subscribing");

        logger.info("Adding mod settings");
        PilotModSettings.initialize();
        logger.info("Done adding mod settings");

        logger.info("Creating new card colors...");
        Pilot.ColorInfo.registerColorWithBaseMod();
        logger.info("Done creating colors");

        logger.info("Adding save fields");
        logger.info("Done adding save fields");
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing PilotMod. =========================");
        PilotMod defaultmod = new PilotMod();
        logger.info("========================= /PilotMod Initialized./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters.");

        // order matters; this will be the order they appear in the char select screen

        logger.info("Adding Pilot...");
        BaseMod.addCharacter(
                new Pilot("The Pilot", Pilot.Enums.PILOT),
                Pilot.CHARACTER_SELECT_BUTTON_TEXTURE,
                Pilot.CHARACTER_SELECT_BG_TEXTURE,
                Pilot.Enums.PILOT);

        logger.info("Added characters");
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================

    private static void registerPowerInDevConsole(Class<? extends AbstractPower> pilotModPower) {
        try {
            String id = (String) pilotModPower.getField("POWER_ID").get(null);
            logger.info("Registering power: " + id);
            BaseMod.addPower(pilotModPower, id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerPowersInDevConsole() {
        logger.info("Registering powers in developer console");

        ArrayList<Class<AbstractPower>> powers = ReflectionUtils.findAllConcretePilotModClasses(new RegexClassFilter("^pilot\\.powers\\.(.+)Power$"));
        for (Class<AbstractPower> power : powers) {
            registerPowerInDevConsole(power);
        }

        logger.info("Done registering powers");
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Registering dev console commands");

        logger.info("Registering mod config page");
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, PilotModSettings.createSettingsPanel());

        logger.info("Registering potions");
        logger.info("Registering powers for dev console");
        registerPowersInDevConsole();

        // =============== EVENTS =================

        // =============== /EVENTS/ =================
    }

    // =============== / POST-INITIALIZE/ =================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        List<CustomPilotModRelic> relics = ReflectionUtils.instantiateAllConcretePilotModSubclasses(CustomPilotModRelic.class);
        for (CustomPilotModRelic relicInstance : relics) {
            logger.info("Adding relic: " + relicInstance.relicId);
            if (relicInstance.relicColor.equals(AbstractCard.CardColor.COLORLESS)) {
                BaseMod.addRelic(relicInstance, RelicType.SHARED);
            } else {
                BaseMod.addRelicToCustomPool(relicInstance, relicInstance.relicColor);
            }
            UnlockTracker.markRelicAsSeen(relicInstance.relicId);
        }

        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding cards");

        BaseMod.addDynamicVariable(new BaseBlockNumber());
        BaseMod.addDynamicVariable(new BaseDamageNumber());
        BaseMod.addDynamicVariable(new UrMagicNumber());
        BaseMod.addDynamicVariable(new MetaMagicNumber());


//        new AutoAdd(PilotMod.MOD_ID).packageFilter("cards").setDefaultSeen(true).cards();

        List<CustomPilotModCard> cards = ReflectionUtils.instantiateAllConcretePilotModSubclasses(CustomPilotModCard.class);
        for (CustomPilotModCard cardInstance : cards) {
            logger.info("Adding card: " + cardInstance.cardID);
            BaseMod.addCard(cardInstance);
            UnlockTracker.unlockCard(cardInstance.cardID);
        }

        logger.info("Done adding cards!");
    }

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    private void loadStrings(Class<?> stringType, String stringsFileName) {
        // We load english first as a fallback for yet-to-be-translated things, then load the "true" language
        BaseMod.loadCustomStringsFile(stringType, makeLocalizedStringsPath(Settings.GameLanguage.ENG, stringsFileName));
        BaseMod.loadCustomStringsFile(stringType, makeLocalizedStringsPath(Settings.language, stringsFileName));
    }
    
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + MOD_ID);

        loadStrings(CardStrings.class, "PilotMod-Card-Strings.json");
        loadStrings(CharacterStrings.class, "PilotMod-Character-Strings.json");
        loadStrings(EventStrings.class, "PilotMod-Event-Strings.json");
        loadStrings(PotionStrings.class, "PilotMod-Potion-Strings.json");
        loadStrings(PowerStrings.class, "PilotMod-Power-Strings.json");
        loadStrings(RelicStrings.class, "PilotMod-Relic-Strings.json");
        loadStrings(UIStrings.class, "PilotMod-UI-Strings.json");

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(makeLocalizedStringsPath(Settings.language, "PilotMod-Keyword-Strings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(MOD_ID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return MOD_ID + ":" + idText;
    }

    public static String makeID(Class idClass) {
        return makeID(idClass.getSimpleName());
    }

    @Override
    public void receivePowersModified() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof OnPowersModifiedSubscriber) {
                    ((OnPowersModifiedSubscriber) p).receivePowersModified();
                }
            }
        }
    }

    @Override
    public void receiveStartAct() {
    }

    @Override
    public void receiveStartGame() {
    }
}
