package pilot.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.cards.OnDeploySubscriber;
import pilot.characters.Pilot;

public class StriderPower extends CustomPilotModPower implements OnDeploySubscriber {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(StriderPower.class);
    public static final String POWER_ID = STATIC.ID;
    public AbstractCard cardToAdd;

    public StriderPower(AbstractCreature owner, int shields, int nbToAdd, AbstractCard cardToAdd) {
        super(STATIC);

        this.type = PowerType.BUFF;

        this.owner = owner;
        this.amount = shields;
        this.amount2 = nbToAdd;
        this.cardToAdd = cardToAdd;

        updateDescription();
    }

    @Override
    public void updateDescription() {
            description = String.format(DESCRIPTIONS[0], amount, amount2);
    }

    @Override
    public AbstractPower makeCopy() {
        return new StriderPower(owner, amount, amount2, cardToAdd);
    }



    @Override
    public void onDeploy() {
        Pilot p = (Pilot)AbstractDungeon.player;
        p.getTitan().increaseShields(amount);
        addToBot(new MakeTempCardInDrawPileAction(cardToAdd, amount2, true, true));
        if (AbstractDungeon.player.hasPower(this.ID)) {
            addToBot(new RemoveSpecificPowerAction(p, p, this));
        }
    }
}
