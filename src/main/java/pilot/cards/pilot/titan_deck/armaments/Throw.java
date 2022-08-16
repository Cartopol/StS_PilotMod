package pilot.cards.pilot.titan_deck.armaments;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;
import pilot.powers.MomentumPower;

public class Throw extends CustomTitanCard {
    public static final String ID = PilotMod.makeID(Throw.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;
    private static final int MOMENTUM = 1;
    private static final int DRAW = 1;
    private static final int UPGRADE_PLUS_DRAW = 1;

    public Throw() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MOMENTUM;
        metaMagicNumber = baseMetaMagicNumber = DRAW;
        this.exhaust = true;
        this.isEthereal = true;
        ArmamentFieldPatch.isArmament.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MomentumPower(p, magicNumber)));
        addToBot(new DrawCardAction(metaMagicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMetaMagicNumber(UPGRADE_PLUS_DRAW);
            upgradeDescription();
        }
    }
}
