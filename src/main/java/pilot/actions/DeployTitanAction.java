package pilot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.cards.OnDeploySubscriber;
import pilot.titan.TitanOrb;

public class DeployTitanAction extends AbstractGameAction {

    public DeployTitanAction(int shields) {
        this.amount = shields;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        addToBot(new ChannelAction(new TitanOrb(amount)));
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnDeploySubscriber) {
                p.flash();
                ((OnDeploySubscriber) p).onDeploy();
            }
        }

        this.isDone = true;
    }
}
