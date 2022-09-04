package pilot.cards.pilot.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;
import pilot.powers.ProtectPower;


public class DomeShield extends CustomTitanCard{
    public static final String ID = PilotMod.makeID(DomeShield.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;
    private static final int PROTECT = 1;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_PROTECT = 1;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public DomeShield() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = PROTECT;
        baseMetaMagicNumber = metaMagicNumber = BLOCK;
        ArmamentFieldPatch.isArmament.set(this, true);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new ApplyPowerAction(p, p, new ProtectPower(p, magicNumber)));
        addToBot(new GainBlockAction(p, p, BLOCK));
    }

    @Override
    public void upgrade(){
        if(!upgraded){
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_PROTECT);
            upgradeMetaMagicNumber(UPGRADE_PLUS_BLOCK);
            upgradeDescription();
        }
    }
}
