package theImmortal.patches.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.UC;

public class HPCostCardMechanics {
    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class hpCostCapture {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(AbstractPlayer __instance, AbstractCard c, AbstractMonster m, int energyOnUse) {
            int hpcost = UC.getHPCost(c);
            if (hpcost > 0) {
                UC.doDmg(UC.p, hpcost, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE, true);
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
