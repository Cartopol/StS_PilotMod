package pilot.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.characters.Pilot;

public class ApexFormPower extends CustomPilotModPower {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(ApexFormPower.class);
    public static final String POWER_ID = STATIC.ID;

    public ApexFormPower(AbstractCreature owner, int amount) {
        super(STATIC);

        this.type = PowerType.BUFF;

        this.owner = owner;
        this.amount = amount;

        updateDescription();
    }


    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
       if (((Pilot)AbstractDungeon.player).hasAdvantage()) {
           super.onPlayCard(card, m);
           flash();
           AbstractPlayer p = AbstractDungeon.player;
           AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MomentumPower(p, amount)));
       }
    }

    @Override
    public void updateDescription() {
            description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public AbstractPower makeCopy() {
        return new ApexFormPower(owner, amount);
    }
}
