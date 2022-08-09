package pilot.cards.pilot.titan_deck;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static pilot.PilotMod.makeCharPath;

public abstract class CustomTitanCard extends CustomPilotModCard {

    public CustomTitanCard(String id, int cost, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, cost, type, color, rarity, target);

        if (type.equals(AbstractCard.CardType.ATTACK)) {
            setBackgroundTexture(makeCharPath("pilot/card_bgs/card_bg_titan_attack_512.png"), makeCharPath("pilot/card_bgs/card_bg_titan_attack_1024.png"));
        }
        if (type.equals(SKILL)) {
            setBackgroundTexture(makeCharPath("pilot/card_bgs/card_bg_titan_skill_512.png"), makeCharPath("pilot/card_bgs/card_bg_titan_skill_1024.png"));
        }
        if (type.equals(AbstractCard.CardType.POWER)) {
            setBackgroundTexture(makeCharPath("pilot/card_bgs/card_bg_titan_power_512.png"), makeCharPath("pilot/card_bgs/card_bg_titan_power_1024.png"));
        }

        // this tag is added so that these cards will not be generated in combat.
        tags.add(AbstractCard.CardTags.HEALING);

    }

//    @Override
//    public final void use(AbstractPlayer p, AbstractMonster m) {
//            }
//
//    @Override
//    public final void onChoseThisOption() {
//        applyPowers();
//        TitanDeck.playedThisCombatCount++;
//        useTitanCard();
//    }

    public static AbstractCard getMasterTitanDeckEquivalent(AbstractCard playingCard) {
        for (AbstractCard c : TitanDeck.masterTitanDeck.group) {
            if (c.uuid.equals(playingCard.uuid)) {
                return c;
            }
        }
        return null;
    }

    public static void removeFromMasterTitanDeck(AbstractCard playingCard) {
        AbstractCard c = getMasterTitanDeckEquivalent(playingCard);
        if (c != null) {
            TitanDeck.masterTitanDeck.removeCard(c);
            PilotMod.logger.info(c + " removed from Master Titan Deck");
            PilotMod.logger.info("Master Titan Deck contains: " + TitanDeck.masterTitanDeck);

        }
    }

        @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (ArmamentFieldPatch.isArmament.get(this)) {
            return ((Pilot) AbstractDungeon.player).hasTitan();
        }
        return super.canUse(p, m);
    }



    @Override
    public void setBackgroundTexture(String backgroundSmallImg, String backgroundLargeImg) {
        super.setBackgroundTexture(backgroundSmallImg, backgroundLargeImg);
    }



}
