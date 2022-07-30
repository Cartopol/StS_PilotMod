package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.powers.LoseProtectPower;
import pilot.powers.ProtectPower;

public class Embark extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Embark.class);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int PROTECT = 1;
    private static final int UPGRADE_COST = 0;

    public Embark() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = PROTECT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ProtectPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseProtectPower(p, magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            upgradeDescription();
        }
    }
}
