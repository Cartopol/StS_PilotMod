package pilot.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pilot.PilotMod;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;
import pilot.patches.ReflexFieldPatch;

import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class CustomPilotModCard extends CustomCard {
    // These sentinel values are defined by the base game, we're just giving them more readable names.
    public static final int COST_X = -1;
    public static final int COST_UNPLAYABLE = -2;

    // Second magic number that isn't damage or block for card text display.
    public int urMagicNumber = 0;
    public int baseUrMagicNumber = 0;
    public boolean upgradedUrMagicNumber = false;
    public boolean isUrMagicNumberModified = false;
    
    // Third magic number that isn't damage or block for card text display.
    public int metaMagicNumber = 0;
    public int baseMetaMagicNumber = 0;
    public boolean upgradedMetaMagicNumber = false;
    public boolean isMetaMagicNumberModified = false;

    // This enables us to use separate rarities for card generation vs the title banner graphic
    // (primarily for SPECIAL cards that we want displayed at a non-common rarity)
    public CardRarity bannerImageRarity;

    protected CardStrings cardStrings;

    protected String[] EXTENDED_DESCRIPTION;

    private String rawDynamicDescriptionSuffix = "";

    public static final Logger logger = LogManager.getLogger(PilotMod.class.getName());
    private boolean isDrawnBeforePlayingOtherCards;



    private static String imgFromId(String id) {
        String unprefixedId = id.replace(PilotMod.MOD_ID + ":","");
        return PilotMod.makeCardPath(String.format("generated/%1$s.png", unprefixedId));
    }

    public CustomPilotModCard(final String id,
                              final int cost,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {
        this(id, languagePack.getCardStrings(id), imgFromId(id), cost, type, color, rarity, target);
    }

    private CustomPilotModCard(final String id,
                               final CardStrings cardStrings,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {
        super(id, cardStrings.NAME, img, cost, cardStrings.DESCRIPTION, type, color, rarity, target);

        this.cardStrings = cardStrings;
        bannerImageRarity = rarity;
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        energyOnUse = -1;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();

        if (upgradedUrMagicNumber) {
            urMagicNumber = baseUrMagicNumber;
            isUrMagicNumberModified = true;
        }

        if (upgradedMetaMagicNumber) {
            metaMagicNumber = baseMetaMagicNumber;
            isMetaMagicNumberModified = true;
        }
    }

    public void upgradeUrMagicNumber(int amount) {
        baseUrMagicNumber += amount;
        urMagicNumber = baseUrMagicNumber;
        upgradedUrMagicNumber = true;
    }

    public void upgradeMetaMagicNumber(int amount) {
        baseMetaMagicNumber += amount;
        metaMagicNumber = baseMetaMagicNumber;
        upgradedMetaMagicNumber = true;
    }

    public void upgradeDescription() {
        String upgradeDescription = languagePack.getCardStrings(cardID).UPGRADE_DESCRIPTION;
        if (upgradeDescription != null) {
            rawDescription = upgradeDescription;
            initializeDescription();
        }
    }

    @Override
    public void initializeDescription() {
        String originalRawDynamicDescriptionSuffix = this.rawDynamicDescriptionSuffix;
        String originalRawDescription = this.rawDescription;
        if (this.rawDynamicDescriptionSuffix != null) {
            this.rawDescription += this.rawDynamicDescriptionSuffix;
            this.rawDynamicDescriptionSuffix = null; // required to handle initializeDescription reentrancy via patches
        }

        super.initializeDescription();

        this.rawDescription = originalRawDescription;
        this.rawDynamicDescriptionSuffix = originalRawDynamicDescriptionSuffix;
    }

    protected void removeFromMasterDeck() {
        final AbstractCard masterDeckCard = StSLib.getMasterDeckEquivalent(this);
        if (masterDeckCard != null) {
            AbstractDungeon.player.masterDeck.removeCard(masterDeckCard);
        }
    }

    // Intended for cards with effects like "deals X damage + Y damage per Z (it should return "Y * Z")
    protected int calculateBonusBaseDamage() {
        return 0;
    }
    protected int calculateBonusBaseBlock() {
        return 0;
    }
    protected int calculateBonusMagicNumber() { return 0; }

    /**
     * Most cards will want to override the no-argument calculateBonusBaseDamage() instead of this version,
     * to ensure !D! placeholders are filled in reasonably when there is no monster hovered/selected yet.
     *
     * This version *only* applies when a monster is actively being hovered/selected/targeted; it should only
     * be used for cards which *cannot* calculate bonus damage except in the context of a specific monster
     * (eg, Godsbane)
     */
    protected int calculateBonusBaseDamage(AbstractMonster m) {
        return calculateBonusBaseDamage();
    }

    protected int calculateBonusBaseBlock(AbstractMonster m) {
        return calculateBonusBaseBlock();
    }

    protected int calculateBonusMagicNumber(AbstractMonster m) {
        return calculateBonusMagicNumber();
    }

    /**
     * This is intended for use with cards that work like Blizzard/Flechettes/etc, adding a parenthetical suffix
     * for dynamically calculated damage/block/etc values that summarizes the calculated total amount.
     *
     * This will be applied after applyPowers/calculateCardDamage and reset after onMoveToDiscard.
     */
    protected /* @NotNull */ String getRawDynamicDescriptionSuffix() { return ""; }

    // Note: this base game method is misleadingly named, it's also used when calculating card block
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseBlock = baseBlock;
        baseBlock += calculateBonusBaseBlock(mo);
        int realBaseDamage = baseDamage;
        baseDamage += calculateBonusBaseDamage(mo);

        magicNumber = baseMagicNumber + calculateBonusMagicNumber(mo);
        isMagicNumberModified = magicNumber != baseMagicNumber;

        super.calculateCardDamage(mo);

        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
        baseBlock = realBaseBlock;
        isBlockModified = block != baseBlock;

        setRawDynamicDescriptionSuffix(getRawDynamicDescriptionSuffix());
    }

    @Override
    public void applyPowers() {
        int realBaseBlock = baseBlock;
        baseBlock += calculateBonusBaseBlock();
        int realBaseDamage = baseDamage;
        baseDamage += calculateBonusBaseDamage();

        magicNumber = baseMagicNumber + calculateBonusMagicNumber();
        isMagicNumberModified = magicNumber != baseMagicNumber;

        super.applyPowers();

        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
        baseBlock = realBaseBlock;
        isBlockModified = block != baseBlock;


        // Turns Armaments Ethereal when Titan is destroyed
        if (ArmamentFieldPatch.isArmament.get(this) && !((Pilot)AbstractDungeon.player).hasTitan()) {
            this.isEthereal = true;
        }

        setRawDynamicDescriptionSuffix(getRawDynamicDescriptionSuffix());
    }

    private void setRawDynamicDescriptionSuffix(String newSuffix) {
        if (!this.rawDynamicDescriptionSuffix.equals(newSuffix)) {
            this.rawDynamicDescriptionSuffix = newSuffix;
            initializeDescription();
        }
    }

    @Override
    public final void onMoveToDiscard() {
        this.onMoveToDiscardImpl();
        energyOnUse = -1;
        setRawDynamicDescriptionSuffix("");
    }



    public void onMoveToDiscardImpl() { }
    public void atStartOfAct() { }
    public void atStartOfGame() { }

    public void applyLoadedMiscValue(int misc) { }

    public boolean shouldGlowGold() { return false; }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = shouldGlowGold() ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }





/*    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if (ReflexFieldPatch.hasReflex.get(this)) {
            if (((Pilot)AbstractDungeon.player).isReflexed()) {
                addToBot(new ReduceCostAction(this.uuid, 1));
                PilotMod.logger.info("Reflex card reduced by 1");
            }

//            AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(this, true, true, true));
//            AbstractDungeon.actionManager.addToBottom(new MillAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
    }*/

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        return null;
    }

    public boolean isEngaging(boolean isPlayingEngageCard) {
        int engageThreshold;
        if (isPlayingEngageCard) {
           engageThreshold = 1;
        } else {
            engageThreshold = 0;
        }
        boolean isEngaging = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() <= engageThreshold;
        logger.info("Pilot is engaging: {} ", isEngaging);
        return isEngaging;
    }



    @Override
    public void atTurnStart() {
        super.atTurnStart();

    }
}