package pilot.cards.pilot.titan_deck.armaments;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.actions.ExhumeArmamentAction;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;


public class Leadwall extends CustomTitanCard {
    public static final String ID = PilotMod.makeID(Leadwall.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int CARDS_RETURNED = 1;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Leadwall() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = CARDS_RETURNED;
        ArmamentFieldPatch.isArmament.set(this, true);
        exhaust = true;
        isEthereal = true;
    }

    @Override
    public boolean shouldGlowGold() { return ((Pilot) AbstractDungeon.player).hasAdvantage();}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_LIGHT));

        if (((Pilot)p).hasAdvantage()) {
            addToBot(new ExhumeArmamentAction(false));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeDescription();
        }
    }
}
