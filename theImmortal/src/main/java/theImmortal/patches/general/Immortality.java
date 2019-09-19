package theImmortal.patches.general;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import theImmortal.mechanics.ImmortalityManager;

public class Immortality {
    @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class ImmortalityFields {
        public static SpireField<Integer> exhaustion = new SpireField<>(() -> 0);
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class ImmortalDeathPrevention {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn Insert(AbstractPlayer __instance, DamageInfo info) {
            //isDead is set to false in here
            if (ImmortalityManager.deathLogic()) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        //Trigger after death set for StSLib compatibility.
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "deathScreen");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }


}
