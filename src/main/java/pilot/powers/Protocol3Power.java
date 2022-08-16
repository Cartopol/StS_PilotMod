package pilot.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Protocol3Power extends CustomPilotModPower {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(Protocol3Power.class);
    public static final String POWER_ID = STATIC.ID;

    public Protocol3Power(AbstractCreature owner, int amount) {
        super(STATIC);

        this.type = PowerType.BUFF;

        this.owner = owner;
        this.amount = amount;

        updateDescription();
    }


    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new ProtectPower(owner, amount)));
    }

    @Override
    public void updateDescription() {
            description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public AbstractPower makeCopy() {
        return new Protocol3Power(owner, amount);
    }
}
