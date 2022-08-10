package pilot.cards.pilot.titan_deck.armaments;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;
import pilot.patches.ReflexFieldPatch;

public class PhaseDash extends CustomTitanCard {
    public static final String ID = PilotMod.makeID(PhaseDash.class);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = -2;
    private static final int INTANGIBLE = 1;
    private static final int DISCARD = 2;
    private static final int UPGRADE_PLUS_DISCARD = -1;

    public PhaseDash() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = INTANGIBLE;
        metaMagicNumber = baseMetaMagicNumber = DISCARD;
        ReflexFieldPatch.hasReflex.set(this, true);
        this.exhaust = true;
        this.isEthereal = true;
        ArmamentFieldPatch.isArmament.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber)));
        addToBot(new DiscardAction(p, p, metaMagicNumber, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMetaMagicNumber(UPGRADE_PLUS_DISCARD);
            upgradeDescription();
        }
    }
}
