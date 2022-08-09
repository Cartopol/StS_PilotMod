package pilot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import pilot.cards.pilot.titan_deck.TitanDeck;

public class DrawArmamentAction extends AbstractGameAction {
    boolean leaveCopyInTitanDeck;

    public DrawArmamentAction(boolean leaveCopyInTitanDeck) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.leaveCopyInTitanDeck = leaveCopyInTitanDeck;
    }

    public void update() {
        TitanDeck.drawArmament(leaveCopyInTitanDeck);

        this.isDone = true;
    }
}
