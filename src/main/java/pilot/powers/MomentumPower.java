package pilot.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.PilotMod;

public class MomentumPower extends CustomPilotModPower {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(MomentumPower.class);
    public static final String POWER_ID = STATIC.ID;
    public static final boolean LOSE_MOMENTUM = false;

    public MomentumPower(AbstractCreature owner, int amount) {
        super(STATIC);

        this.type = PowerType.BUFF;

        this.owner = owner;
        this.amount = amount;

        updateDescription();
    }


    @Override
    public float modifyBlock(float blockAmount) {
            return (blockAmount += (float)this.amount) < 0.0F ? 0.0F : blockAmount;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage + (float)this.amount : damage;
    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        if ((card.baseDamage > 0) || (card.baseBlock > 0)){
            AbstractPlayer p = AbstractDungeon.player;
            int reduceBy = 0;

            if (this.amount == 1) {
                reduceBy = 1;
            }

            if (this.amount > 1) {
                reduceBy = this.amount / 2;
            }

            addToBot(new ReducePowerAction(p, p, this, reduceBy));
            PilotMod.logger.info("Reduced Momentum by {}", reduceBy);
        }
    }

/*
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer p = AbstractDungeon.player;
            int reduceBy = 0;

            if (this.amount == 1) {
                reduceBy = 1;
            }

            if (this.amount > 1) {
                reduceBy = this.amount / 2;
            }

            addToBot(new ReducePowerAction(p, p, this, reduceBy));
            PilotMod.logger.info("Reduced Momentum by {}", reduceBy);

        }
        super.atEndOfTurn(isPlayer);
    }
*/
    @Override
    public void updateDescription() {
            description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public AbstractPower makeCopy() {
        return new MomentumPower(owner, amount);
    }
}
