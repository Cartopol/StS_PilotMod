package pilot.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.PilotMod;
import pilot.actions.DrawArmamentAction;
import pilot.characters.Pilot;

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
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurn();
        PilotMod.logger.info("Drawing for Advanced Auto Pilot power");
        if (((Pilot)owner).hasTitan()) {
            AbstractDungeon.actionManager.addToBottom(new DrawArmamentAction(1));
        }
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
