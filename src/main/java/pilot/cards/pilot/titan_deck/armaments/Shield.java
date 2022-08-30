package pilot.cards.pilot.titan_deck.armaments;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.actions.IncreaseShieldsAction;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;
import pilot.powers.ProtectPower;

public class Shield extends CustomTitanCard {
    public static final String ID = PilotMod.makeID(Shield.class);

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int SHIELDS = 3;
    private static final int PROTECT = 1;
    private static final int UPGRADE_PLUS_SHIELDS = 3;

    public Shield() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        metaMagicNumber = baseMetaMagicNumber = SHIELDS;
        magicNumber = baseMagicNumber = PROTECT;
        ArmamentFieldPatch.isArmament.set(this, true);
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseShieldsAction(metaMagicNumber));
        addToBot(new ApplyPowerAction(p, p, new ProtectPower(p, magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMetaMagicNumber(UPGRADE_PLUS_SHIELDS);
            upgradeDescription();
        }
    }
}
