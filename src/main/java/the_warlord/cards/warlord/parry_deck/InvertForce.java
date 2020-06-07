package the_warlord.cards.warlord.parry_deck;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import the_warlord.WarlordMod;
import the_warlord.cards.warlord.KineticRelease;
import the_warlord.characters.Warlord;
import the_warlord.powers.ParryPower;

public class InvertForce extends CustomParryCard {
    public static final String ID = WarlordMod.makeID(InvertForce.class);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Warlord.Enums.WARLORD_CARD_COLOR;

    private static final int COST = COST_UNPLAYABLE;

    public InvertForce() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 0;
        cardsToPreview = new KineticRelease();
    }

    @Override
    public void useParry() {
        magicNumber = calculateBonusMagicNumber();
        KineticRelease c = new KineticRelease();
        c.setDamage(this.magicNumber);
        addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
        if (!upgraded) {
            removeFromMasterParryDeck(this);
        }
    }

    @Override
    public int calculateBonusMagicNumber() {
        int damageParried = 0;
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(ParryPower.POWER_ID)) {
            damageParried = ParryPower.damageParriedThisTurn;
        }
        return damageParried;
    }


    @Override
    public String getRawDynamicDescriptionSuffix() {
        return EXTENDED_DESCRIPTION[0];
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDescription();
        }
    }
}
