package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;

public class Flatline extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Flatline.class);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int WEAK = 1;

    public Flatline() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = WEAK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster target1 = AbstractDungeon.getRandomMonster();
        AbstractMonster target2 = AbstractDungeon.getRandomMonster();
        addToBot(new DamageAction(target1, new DamageInfo(p, damage), AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(target1, p, new WeakPower(target1, magicNumber, false)));

        addToBot(new DamageAction(target2, new DamageInfo(p, damage), AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(target2, p, new WeakPower(target2, magicNumber, false)));
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
