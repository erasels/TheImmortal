package theImmortal.patches.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
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
        public static void Prefix(CardGroup __instance, AbstractCard card) {
            if (card.cardID.equals(ImmortalFlame.ID)) {
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().exhaustPile, c -> (c == card), c-> {
                    if(c.get(0) != null) ((ImmortalFlame) c.get(0)).trigger();
                    UC.p().hand.glowCheck();
                }));
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().drawPile, c -> (c == card), c-> {
                    if(c.get(0) != null) ((ImmortalFlame) c.get(0)).trigger();
                    UC.p().hand.glowCheck();
                }));
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().discardPile, c -> (c == card), c-> {
                    if(c.get(0) != null) ((ImmortalFlame) c.get(0)).trigger();
                    UC.p().hand.glowCheck();
                }));
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToDeck")
    public static class StaysInHand2 {
        public static void Prefix(CardGroup __instance, AbstractCard card, boolean randomSpot) {
            if (card.cardID.equals(ImmortalFlame.ID) && __instance == AbstractDungeon.player.hand && AbstractDungeon.player.hand.contains(card)) {
                AbstractDungeon.actionManager.addToTop(new FetchAction(UC.p().exhaustPile, c -> (c == card), c-> {
                    if(c.get(0) != null) ((ImmortalFlame) c.get(0)).trigger();
                    UC.p().hand.glowCheck();
                }));
            }
        }
    }
}
