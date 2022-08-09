package pilot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.TitanDeck;

public class ExhaustToTitanDeckAction extends AbstractGameAction {
    private final AbstractCard card;

    public ExhaustToTitanDeckAction(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {

        TitanDeck.masterTitanDeck.addToRandomSpot(card.makeStatEquivalentCopy());
            AbstractDungeon.player.exhaustPile.removeCard(this.card);
        PilotMod.logger.info("Added " + card + " to Titan Deck from exhaustPile");

        PilotMod.logger.info("Titan Deck contains" + TitanDeck.masterTitanDeck);

        this.isDone = true;
    }
}
