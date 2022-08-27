package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;

public class SmokeBomb extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(SmokeBomb.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;


    public SmokeBomb() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        cardsToPreview = new Dazed();
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // count down from the hand limit to the player's hand size + the number of cards drawn by this card
        for (int i = 10; i > p.hand.size() + magicNumber; --i) {
            addToBot(new MakeTempCardInHandAction(new Dazed()));
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
