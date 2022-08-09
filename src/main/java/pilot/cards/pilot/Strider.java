package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.powers.StriderPower;

public class Strider extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Strider.class);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;
    private static final int SHIELD = 8;
    private static final int UPGRADE_PLUS_SHIELD = 4;
    private static final int DASH_AMOUNT = 2;
    private static final int UPGRADE_PLUS_DASH = 1;


    public Strider() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = SHIELD;
        metaMagicNumber = baseMetaMagicNumber = DASH_AMOUNT;

        cardsToPreview = new Pilot_Dash();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        StriderPower pow = new StriderPower(p, magicNumber, metaMagicNumber, new Pilot_Dash());
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
            upgradeMetaMagicNumber(UPGRADE_PLUS_DASH);
            upgradeDescription();
        }
    }

}
