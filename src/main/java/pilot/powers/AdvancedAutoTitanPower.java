package pilot.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.actions.DrawArmamentAction;

public class AdvancedAutoTitanPower extends CustomPilotModPower {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(AdvancedAutoTitanPower.class);
    public static final String POWER_ID = STATIC.ID;

    public AdvancedAutoTitanPower(AbstractCreature owner, int amount) {
        super(STATIC);

        this.type = PowerType.BUFF;

        this.owner = owner;
        this.amount = amount;

        updateDescription();
    }


    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        AbstractDungeon.actionManager.addToBottom(new DrawArmamentAction(false));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = String.format(DESCRIPTIONS[0], amount);
        }
        else {
            description = String.format(DESCRIPTIONS[1], amount);
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new AdvancedAutoTitanPower(owner, amount);
    }
}
