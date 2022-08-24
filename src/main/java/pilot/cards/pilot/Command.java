package pilot.cards.pilot;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.actions.DrawArmamentAction;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;

public class Command extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Command.class);

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;
    private static final int DRAW = 1;
    private static final int UPGRADED_PLUS_DRAW = 1;

    public Command() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DRAW;
        this.isEthereal = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawArmamentAction(false));
        if (upgraded = true) { //this only seems to work sometimes and I'm not sure why
            addToBot(new DrawArmamentAction(false));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_DRAW);
            upgradeDescription();
        }
    }
}
