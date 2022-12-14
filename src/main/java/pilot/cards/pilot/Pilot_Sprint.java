package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.powers.MomentumPower;

public class Pilot_Sprint extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Pilot_Sprint.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int MOMENTUM = 5;
    private static final int UPGRADED_PLUS_MOMENTUM = 2;

    public Pilot_Sprint() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        urMagicNumber = baseUrMagicNumber = MOMENTUM;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MomentumPower(p, urMagicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDescription();
            upgradeUrMagicNumber(UPGRADED_PLUS_MOMENTUM);
        }
    }
}
