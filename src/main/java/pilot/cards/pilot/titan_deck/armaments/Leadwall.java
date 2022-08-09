package pilot.cards.pilot.titan_deck.armaments;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;

public class Leadwall extends CustomTitanCard {
    public static final String ID = PilotMod.makeID(Leadwall.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int BLOCK = 1;
    private static final int DEX = 1;
    private static final int UPGRADE_PLUS_DEX = 1;

    public Leadwall() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = DEX;
        ArmamentFieldPatch.isArmament.set(this, true);
    }

    @Override
    public boolean shouldGlowGold() {
        return isEngaging();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_LIGHT));

        if (isEngaging()) {
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
