package theImmortal.patches.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theImmortal.cards.rare.ImmortalFlame;
import theImmortal.util.UC;

public class ImmortalFlamePatches {
    @SpirePatch(clz = CardGroup.class, method = "moveToDiscardPile")
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    @SpirePatch(clz = CardGroup.class, method = "moveToBottomOfDeck")
    @SpirePatch(clz = CardGroup.class, method = "resetCardBeforeMoving")
    public static class StaysInHand {
        @SpirePrefixPatch
        public static void patch(CardGroup __instance, AbstractCard card) {
            if (card.cardID.equals(ImmortalFlame.ID)) {
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().exhaustPile, c -> (c.uuid == card.uuid), c-> triggerFlame(c.get(0))));
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().drawPile, c -> (c.uuid == card.uuid), c-> triggerFlame(c.get(0))));
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().discardPile, c -> (c.uuid == card.uuid), c-> triggerFlame(c.get(0))));
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToDeck")
    public static class StaysInHand2 {
        @SpirePrefixPatch
        public static void patch(CardGroup __instance, AbstractCard card, boolean randomSpot) {
            if (card.cardID.equals(ImmortalFlame.ID) && __instance == AbstractDungeon.player.hand && AbstractDungeon.player.hand.contains(card)) {
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().exhaustPile, c -> (c.uuid == card.uuid), c-> triggerFlame(c.get(0))));
            }
        }
    }

    private static void triggerFlame(AbstractCard c) {
        if(c == null) return;
        ((ImmortalFlame)c).trigger();
        UC.p().hand.glowCheck();
    }
}
