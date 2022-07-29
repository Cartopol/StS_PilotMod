package pilot.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class TitanFieldPatch {
    public static SpireField<Boolean> requiresTitan = new SpireField<>(() -> false);

}
