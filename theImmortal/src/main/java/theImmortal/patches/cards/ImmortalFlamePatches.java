package theImmortal.patches.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theImmortal.cards.rare.ImmortalFlame;
import theImmortal.util.UC;

import java.lang.reflect.Field;

import static theImmortal.TheImmortal.logger;

public class ImmortalFlamePatches {
    @SpirePatch(clz = CardGroup.class, method = "moveToDiscardPile")
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    @SpirePatch(clz = CardGroup.class, method = "moveToBottomOfDeck")
    @SpirePatch(clz = CardGroup.class, method = "resetCardBeforeMoving")
    public static class StaysInHand {
        @SpirePrefixPatch
        public static void patch(CardGroup __instance, AbstractCard card) {
            if (card.cardID.equals(ImmortalFlame.ID)) {
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().exhaustPile, c -> (c.uuid == card.uuid), c -> triggerFlame(c.get(0))));
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().drawPile, c -> (c.uuid == card.uuid), c -> triggerFlame(c.get(0))));
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().discardPile, c -> (c.uuid == card.uuid), c -> triggerFlame(c.get(0))));
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToDeck")
    public static class StaysInHand2 {
        @SpirePrefixPatch
        public static void patch(CardGroup __instance, AbstractCard card, boolean randomSpot) {
            if (card.cardID.equals(ImmortalFlame.ID) && __instance == AbstractDungeon.player.hand && AbstractDungeon.player.hand.contains(card)) {
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().exhaustPile, c -> (c.uuid == card.uuid), c -> triggerFlame(c.get(0))));
            }
        }
    }

    @SpirePatch(clz = DiscardAtEndOfTurnAction.class, method = "update")
    public static class MakeDiscardAtEndOfTurnActionNotDumb {
        private static Field isEndTurn;

        static {
            try {
                isEndTurn = DiscardAction.class.getDeclaredField("endTurn");
                isEndTurn.setAccessible(true);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        @SpirePostfixPatch
        public static void NODUMBDISCARDSTUPIDBIGDUMBIDIOT(DiscardAtEndOfTurnAction __instance) throws IllegalAccessException {
            if (__instance.isDone) {
                if (AbstractDungeon.actionManager.actions.removeIf(a -> {
                    try {
                        return (a instanceof DiscardAction && (boolean) isEndTurn.get(a));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage());
                        return false;
                    }
                })) {
                    int index = 0;
                    for (; index < AbstractDungeon.actionManager.actions.size(); ++index) {
                        if (AbstractDungeon.actionManager.actions.get(index) instanceof RestoreRetainedCardsAction) {
                            break;
                        }
                    }

                    AbstractDungeon.actionManager.actions.add(index, new DiscardAction(AbstractDungeon.player, null, AbstractDungeon.player.hand.size(), true, true));
                }
            }
        }
    }


    private static void triggerFlame(AbstractCard c) {
        if (c == null) return;
        ((ImmortalFlame) c).trigger();
        UC.p().hand.glowCheck();
    }
}
