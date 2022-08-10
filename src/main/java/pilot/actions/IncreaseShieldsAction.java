package pilot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import pilot.characters.Pilot;

public class IncreaseShieldsAction extends AbstractGameAction {
    private int amount;

    public IncreaseShieldsAction(int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (((Pilot)p).hasTitan()) {
            ((Pilot)p).getTitan().increaseShields(amount);
        }

        this.isDone = true;
    }
}
