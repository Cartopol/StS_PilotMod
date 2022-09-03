package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.cards.pilot.tempCards.Mastiff;
import pilot.characters.Pilot;
import pilot.powers.MomentumPower;

public class Rush extends CustomPilotModCard{
    public static final String ID = PilotMod.makeID(Rush.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;
    private static final int DISCARD = 1;
    private static final int MOMENTUM = 3;
    private static final int UPGRADE_PLUS_MOMENTUM = 3;

    public Rush() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DISCARD;
        metaMagicNumber = baseMetaMagicNumber = MOMENTUM;
        this.cardsToPreview = new Mastiff();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MomentumPower(p, metaMagicNumber)));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
        addToBot(new DiscardAction(p, p, magicNumber, false));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMetaMagicNumber(UPGRADE_PLUS_MOMENTUM);
        }
    }
}
