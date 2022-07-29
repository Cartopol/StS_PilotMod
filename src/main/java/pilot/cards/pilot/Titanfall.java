package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.actions.DeployTitanAction;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.powers.ProtectPower;

public class Titanfall extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Titanfall.class);

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int SHIELD = 10;
    private static final int PROTECT = 1;
    private static final int CHAINGUN = 1;

    public Titanfall() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = SHIELD;
        urMagicNumber = baseUrMagicNumber = PROTECT;
        metaMagicNumber = baseMetaMagicNumber = CHAINGUN;

        cardsToPreview = new Chaingun();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DeployTitanAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new ProtectPower(p, urMagicNumber)));
        addToBot(new MakeTempCardInDrawPileAction(new Chaingun(), CHAINGUN, true, true));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDescription();
            this.isInnate = true;
        }
    }
}
