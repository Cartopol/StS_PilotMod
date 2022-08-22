package pilot.cards.pilot.titan_deck.armaments;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.actions.ExhumeArmamentAction;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;

public class Pilot_Dash extends CustomTitanCard {
    public static final String ID = PilotMod.makeID(Pilot_Dash.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;
    private static final int CARDS_RETURNED = 1;
    private static final int UPGRADE_PLUS_CARDS_RETURNED = 1;


    public Pilot_Dash() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        ArmamentFieldPatch.isArmament.set(this, true);
        magicNumber = baseMagicNumber = CARDS_RETURNED;
        exhaust = true;
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (((Pilot)p).hasTitan()) {
            for (int i = 0; i < magicNumber; ++i) {
                addToBot(new ExhumeArmamentAction(false));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_CARDS_RETURNED);
            upgradeDescription();
        }
    }
}
