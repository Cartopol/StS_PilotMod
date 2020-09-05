package shapes.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shapes.characters.Shapes;

public class RemoveUnwantedBaseRelicsPatch {
    @SpirePatch(clz = AbstractDungeon.class, method = "initializeRelicList")
    public static class Implementation
    {
        @SpirePrefixPatch
        public static void patch(AbstractDungeon __instance) {
            boolean warlord = __instance.player instanceof Shapes;

            if (warlord) {
                //use this to remove specific relics from pool if player character is Shapes
//                AbstractDungeon.relicsToRemoveOnStart.add(PrismaticShard.ID);
            }

        }
    }
}