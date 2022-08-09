package pilot.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.cards.OnDeploySubscriber;
import pilot.characters.Pilot;

public class RoninPower extends CustomPilotModPower implements OnDeploySubscriber {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(RoninPower.class);
    public static final String POWER_ID = STATIC.ID;
    public AbstractCard cardToAdd1;
    public AbstractCard cardToAdd2;

    public RoninPower(AbstractCreature owner, int shields, int nbToAdd, AbstractCard cardToAdd1, AbstractCard cardToAdd2) {
        super(STATIC);

        this.type = PowerType.BUFF;

        this.owner = owner;
        this.amount = shields;
        this.amount2 = nbToAdd;
        this.cardToAdd1 = cardToAdd1;
        this.cardToAdd2 = cardToAdd2;

        updateDescription();
    }

    @Override
    public void updateDescription() {
            description = String.format(DESCRIPTIONS[0], amount, amount2);
    }

    @Override
    public AbstractPower makeCopy() {
        return new RoninPower(owner, amount, amount2, cardToAdd1, cardToAdd2);
    }



    @Override
    public void onDeploy() {
        Pilot p = (Pilot)AbstractDungeon.player;
        p.getTitan().increaseShields(amount);
        addToBot(new MakeTempCardInDrawPileAction(cardToAdd1, amount2, true, true));
        addToBot(new MakeTempCardInDrawPileAction(cardToAdd2, amount2, true, true));
        if (AbstractDungeon.player.hasPower(this.ID)) {
            addToBot(new RemoveSpecificPowerAction(p, p, this));
        }
    }
}
