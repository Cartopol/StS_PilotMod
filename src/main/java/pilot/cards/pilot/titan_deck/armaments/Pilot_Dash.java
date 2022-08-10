package pilot.cards.pilot.titan_deck.armaments;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;
import pilot.patches.ReflexFieldPatch;

public class Pilot_Dash extends CustomTitanCard {
    public static final String ID = PilotMod.makeID(Pilot_Dash.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = -2;
    private static final int BLOCK = 4;
    private static final int DEX = 1;
    private static final int UPGRADE_PLUS_DEX = 1;

    public Pilot_Dash() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = DEX;
        ArmamentFieldPatch.isArmament.set(this, true);
        ReflexFieldPatch.hasReflex.set(this, true);
        exhaust = true;
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (((Pilot)p).hasTitan()) {
            addToBot(new GainBlockAction(p, block));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, DEX)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DEX);
            upgradeDescription();
        }
    }
}
