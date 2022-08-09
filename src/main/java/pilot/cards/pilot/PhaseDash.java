package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.patches.ReflexFieldPatch;

public class PhaseDash extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(PhaseDash.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;
    private static final int INTANGIBLE = 1;
    private static final int DISCARD = 2;
    private static final int UPGRADE_PLUS_DISCARD = -1;

    public PhaseDash() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = INTANGIBLE;
        metaMagicNumber = baseMetaMagicNumber = DISCARD;
        ReflexFieldPatch.hasReflex.set(this, true);

        //design doc doesn't specify this as Armament
//        ArmamentFieldPatch.isArmament.set(this, true);
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
