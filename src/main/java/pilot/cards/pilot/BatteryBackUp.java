package pilot.cards.pilot;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;

public class BatteryBackUp extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(BatteryBackUp.class);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int SHIELDS = 13;

    public BatteryBackUp() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = SHIELDS;
        this.exhaust = true;
    }

    @Override
    public boolean shouldGlowGold() {
        return ((Pilot)AbstractDungeon.player).hasTitan();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (((Pilot)p).hasTitan()) {
            ((Pilot) p).getTitan().increaseShields(magicNumber);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDescription();
            this.selfRetain = true;
        }
    }
}
