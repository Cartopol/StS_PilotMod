package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;

public class Pull_the_Pin extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Pull_the_Pin.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public Pull_the_Pin() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        this.cardsToPreview = new Frag();
    }

    @Override
    public void use (AbstractPlayer p, AbstractMonster m){
        addToBot(new MakeTempCardInDrawPileAction(new Frag(), 1, false, true));
    }

    @Override
    public void upgrade(){
        if (!upgraded) {
            upgradeName();
            upgradeDescription();
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}
