package theImmortal.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theImmortal.universal.CardHPLossHook;
import theImmortal.util.UC;

public class HPLossCardsPatches {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class HPLossFields {
        public static SpireField<Integer> baseHPCost = new SpireField<>(() -> -1);
        public static SpireField<Integer> hpCost = new SpireField<>(() -> -1);
        public static SpireField<Boolean> isHPCostModified = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class CopyShenans {
        @SpirePostfixPatch
        public static AbstractCard patch(AbstractCard __result, AbstractCard __instance) {
            UC.setBaseHPCost(__result, UC.getBaseHPCost(__instance));
            UC.setHPCost(__result, UC.getHPCost(__instance));
            return __result;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "resetAttributes")
    public static class ResetShenans {
        @SpirePostfixPatch
        public static void patch(AbstractCard __instance) {
            UC.setHPCost(__instance, UC.getBaseHPCost(__instance));
            UC.setHPCostModified(__instance, false);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "applyPowers")
    public static class ApplyPowersShenans {
        @SpirePostfixPatch
        public static void patch(AbstractCard __instance) {
            UC.setHPCostModified(__instance, false);
            UC.setHPCost(__instance, UC.getBaseHPCost(__instance));
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof CardHPLossHook) {
                    UC.setHPCost(__instance, ((CardHPLossHook) p).modifyHPCost(__instance, UC.getHPCost(__instance)));
                }
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof CardHPLossHook) {
                    UC.setHPCost(__instance, ((CardHPLossHook) r).modifyHPCost(__instance, UC.getHPCost(__instance)));
                }
            }
            if (UC.getHPCost(__instance) < 0) {
                UC.setHPCost(__instance, 0);
            }
            UC.setHPCostModified(__instance, UC.getHPCost(__instance) != UC.getBaseHPCost(__instance));
        }
    }
}
