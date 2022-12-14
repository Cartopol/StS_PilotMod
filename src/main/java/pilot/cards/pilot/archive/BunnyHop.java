package pilot.cards.pilot.archive;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.patches.ReflexFieldPatch;
import pilot.powers.MomentumPower;


public class BunnyHop extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(BunnyHop.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int MOMENTUM = 2;
    private static final int ENERGY = 1;
    private static final int DRAW = 1;

    public BunnyHop() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        ReflexFieldPatch.hasReflex.set(this, true);
        magicNumber = baseMagicNumber = MOMENTUM;
        exhaust = true;
    }

//    @Override
//    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
//        this.cantUseMessage = "I can't play this!";
//        return false;
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MomentumPower(p, magicNumber)));
        if (upgraded) {
            addToBot(new DrawCardAction(DRAW));
        }
        if (((Pilot)p).hasAdvantage()) {
            addToBot(new GainEnergyAction(ENERGY));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDescription();
        }
    }
}
