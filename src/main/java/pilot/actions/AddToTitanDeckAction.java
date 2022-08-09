package pilot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.TitanDeck;

public class AddToTitanDeckAction extends AbstractGameAction {
    private final AbstractCard card;
    private boolean removeFromDrawPile;

    public AddToTitanDeckAction(AbstractCard card, boolean fromDrawPile) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
        this.removeFromDrawPile = fromDrawPile;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (!removeFromDrawPile) {
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, true, false));
        }
        TitanDeck.masterTitanDeck.addToRandomSpot(card.makeStatEquivalentCopy());
            AbstractDungeon.player.drawPile.removeCard(this.card);
        PilotMod.logger.info("Added " + card + " to Titan Deck from drawPile: " + removeFromDrawPile);

        PilotMod.logger.info("Titan Deck contains" + TitanDeck.masterTitanDeck);

        this.isDone = true;
    }
}
