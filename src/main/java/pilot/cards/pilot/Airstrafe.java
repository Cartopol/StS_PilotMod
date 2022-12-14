package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.powers.MomentumPower;

public class Airstrafe extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Airstrafe.class);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;

    public Airstrafe() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(MomentumPower.POWER_ID)) {
            AbstractPower momentumPower = p.getPower(MomentumPower.POWER_ID);
                addToBot(new ApplyPowerAction(p, p, new MomentumPower(p, momentumPower.amount)));

        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDescription();
            this.exhaust = false;
        }
    }
}
