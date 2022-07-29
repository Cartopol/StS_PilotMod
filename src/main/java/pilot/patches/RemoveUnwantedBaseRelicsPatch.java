package pilot.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import pilot.characters.Pilot;

public class RemoveUnwantedBaseRelicsPatch {
    @SpirePatch(clz = AbstractDungeon.class, method = "initializeRelicList")
    public static class Implementation
    {
        @SpirePrefixPatch
        public static void patch(AbstractDungeon __instance) {
            boolean pilot = __instance.player instanceof Pilot;

            if (pilot) {
                //use this to remove specific relics from pool if player character is Pilot
//                AbstractDungeon.relicsToRemoveOnStart.add(PrismaticShard.ID);
            }

        }
    }
}