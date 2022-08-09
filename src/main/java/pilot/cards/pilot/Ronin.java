package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.cards.pilot.titan_deck.armaments.Leadwall;
import pilot.characters.Pilot;
import pilot.powers.RoninPower;

public class Ronin extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Ronin.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int SHIELD = 5;
    private static final int UPGRADE_PLUS_SHIELD = 3;
    private static final int CARD_AMOUNT = 1;
    private static final int UPGRADE_PLUS_CARD = 1;


    public Ronin() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = SHIELD;
        metaMagicNumber = baseMetaMagicNumber = CARD_AMOUNT;

        cardsToPreview = new Leadwall();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        RoninPower pow = new RoninPower(p, magicNumber, metaMagicNumber, new Leadwall(), new PhaseDash());
        if (((Pilot)p).hasTitan()) {
            pow.onDeploy();
        }
        else {
            addToBot(new ApplyPowerAction(p, p, pow, magicNumber));
        }
    }



    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_SHIELD);
            upgradeMetaMagicNumber(UPGRADE_PLUS_CARD);
            upgradeDescription();
        }
    }

}
