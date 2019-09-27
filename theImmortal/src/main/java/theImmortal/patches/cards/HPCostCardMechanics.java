package theImmortal.patches.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.universal.CardHPLossHook;
import theImmortal.util.UC;

public class HPCostCardMechanics {
    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class hpCostCapture {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(AbstractPlayer __instance, AbstractCard c, AbstractMonster m, int energyOnUse) {
            int hpcost = UC.getHPCost(c);
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof CardHPLossHook) {
                    hpcost = ((CardHPLossHook)p).preHPLoss(c, hpcost);
                }
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof CardHPLossHook) {
                    hpcost = ((CardHPLossHook)r).preHPLoss(c, hpcost);
                }
            }
            if (hpcost > 0) {
                UC.doDmg(UC.p(), hpcost, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE, true);
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof CardHPLossHook) {
                        ((CardHPLossHook)p).postHPLoss(c, hpcost);
                    }
                }
                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    if (r instanceof CardHPLossHook) {
                        ((CardHPLossHook)r).postHPLoss(c, hpcost);
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }


    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy", paramtypez = {SpriteBatch.class})
    public static class RunicCardElementRenderInLibraryPatch {
        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            ImmortalCard.renderHPCost( __instance, sb);
        }
    }
}
