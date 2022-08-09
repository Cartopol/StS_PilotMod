package pilot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.cards.OnDeploySubscriber;

public class DeployTitanAction extends AbstractGameAction {

    public DeployTitanAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnDeploySubscriber) {
                p.flash();
                ((OnDeploySubscriber) p).onDeploy();
            }
        }

        this.isDone = true;
    }
}
