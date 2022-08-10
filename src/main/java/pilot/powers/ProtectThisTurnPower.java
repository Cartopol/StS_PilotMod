package pilot.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.characters.Pilot;

public class ProtectThisTurnPower extends CustomPilotModPower {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(ProtectThisTurnPower.class);
    public static final String POWER_ID = STATIC.ID;

    public ProtectThisTurnPower(AbstractCreature owner, int amount) {
        super(STATIC);

        this.type = PowerType.BUFF;

        this.owner = owner;
        this.amount = amount;

        updateDescription();
    }


    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (amount > 0 && ((Pilot)AbstractDungeon.player).hasTitan() && damageAmount > 0) {
            this.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            ((Pilot)AbstractDungeon.player).getTitan().damageTitan(damageAmount);
            return 0;
        }

        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = String.format(DESCRIPTIONS[0]);
        }
        else {
            description = String.format(DESCRIPTIONS[1], amount);
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ProtectThisTurnPower(owner, amount);
    }
}
