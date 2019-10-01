package theImmortal.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.function.Consumer;

public class DrawnCardHook {
    public static Consumer<AbstractCard> callback;

    @SpirePatch(clz = DrawCardAction.class, method = "update")
    public static class AddCallback {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(ScryAction __instance) {
            if (callback != null) {
                callback.accept(AbstractDungeon.player.drawPile.getTopCard());
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "draw");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }
}
