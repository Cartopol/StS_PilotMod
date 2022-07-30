package pilot.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LoseProtectPower extends CustomPilotModPower {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(LoseProtectPower.class);
    public static final String POWER_ID = STATIC.ID;

    public LoseProtectPower(AbstractCreature owner, int amount) {
        super(STATIC);

        this.type = PowerType.DEBUFF;

        this.owner = owner;
        this.amount = amount;

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new ProtectPower(this.owner, -this.amount), -this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
            description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public AbstractPower makeCopy() {
        return new LoseProtectPower(owner, amount);
    }
}
