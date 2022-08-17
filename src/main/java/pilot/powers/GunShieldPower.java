package pilot.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.actions.IncreaseShieldsAction;
import pilot.patches.ArmamentFieldPatch;

public class GunShieldPower extends CustomPilotModPower {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(GunShieldPower.class);
    public static final String POWER_ID = STATIC.ID;

    public GunShieldPower(AbstractCreature owner, int amount) {
        super(STATIC);

        this.type = PowerType.BUFF;

        this.owner = owner;
        this.amount = amount;

        updateDescription();
    }


    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (ArmamentFieldPatch.isArmament.get(card) && card.type.equals(AbstractCard.CardType.ATTACK)) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new IncreaseShieldsAction(amount));
        }
    }

    @Override
    public void updateDescription() {
            description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public AbstractPower makeCopy() {
        return new GunShieldPower(owner, amount);
    }
}
