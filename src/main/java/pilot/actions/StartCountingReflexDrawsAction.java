package pilot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import pilot.PilotMod;
import pilot.characters.Pilot;

public class StartCountingReflexDrawsAction extends AbstractGameAction {
    private Pilot player;

    public StartCountingReflexDrawsAction(Pilot player) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.player = player;

        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        player.cardsDrawnAfterStartOfTurn = 0;
        player.startCounting = true;
        PilotMod.logger.info("Start counting card draws for Reflex");
        PilotMod.logger.info("Cards drawn since turn start reset to 0");

        this.isDone = true;
    }
}
