package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;

public class Pilot_Sprint extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Pilot_Sprint.class);

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int DRAW = 2;
    private static final int DEX = 1;
    private static final int UPGRADE_PLUS_DEX = 1;

    public Pilot_Sprint() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DRAW;
        urMagicNumber = baseUrMagicNumber = DEX;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, urMagicNumber)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeUrMagicNumber(UPGRADE_PLUS_DEX);
            upgradeDescription();
        }
    }
}
