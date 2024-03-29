package theImmortal.mechanics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theImmortal.actions.utility.ClearAllDebuffsAction;
import theImmortal.patches.combat.ExhaustionMechanics;
import theImmortal.patches.general.Immortality;
import theImmortal.ui.ExhaustionPanel;
import theImmortal.util.UC;
import theImmortal.vfx.combat.FireIgniteEffect;
import theImmortal.vfx.combat.unique.ReviveEffect;

import static theImmortal.characters.ImmortalCharacter.Enums.THE_IMMORTAL;

public class ImmortalityManager {
    public static final int MAX_DEATH_COUNT = 4;

    public static boolean isImmortal() {
        return AbstractDungeon.player.chosenClass == THE_IMMORTAL;
    }

    public static void exhaustionLogic(AbstractCard c, AbstractCreature target) {
        int ex = getExhaustion();
        if(ex > 0) {
            UC.doDmg(UC.p(), ex, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE, true, true);
            UC.doVfx(new FireIgniteEffect(ImmortalityManager.getExhaustionPanel().current_x, ImmortalityManager.getExhaustionPanel().current_y, ex*5));
        }
    }

    public static boolean deathLogic() {
        if(isImmortal()) {
            if (getExhaustion() < MAX_DEATH_COUNT) {
                UC.p().isDead = false;
                addExhaustion(1);
                UC.atb(new ClearAllDebuffsAction(UC.p()));
                AbstractDungeon.player.currentHealth = MathUtils.floor(AbstractDungeon.player.maxHealth/2.0f);
                AbstractDungeon.player.healthBarUpdatedEvent();

                UC.doVfx(new ReviveEffect(UC.p()));

                return true;
            }
        }
        return false;
    }

    public static ExhaustionPanel getExhaustionPanel() {
        return ExhaustionMechanics.ExhaustionFields.panel.get(AbstractDungeon.player);
    }

    public static int getExhaustion() {
        if(UC.p() != null) {
            return Immortality.ImmortalityFields.exhaustion.get(AbstractDungeon.player);
        }
        return 0;
    }

    public static void setExhaustion(int newVal) {
        if(Immortality.ImmortalityFields.exhaustion.get(AbstractDungeon.player) != null) {
            Immortality.ImmortalityFields.exhaustion.set(AbstractDungeon.player, newVal);
        }
    }

    public static void addExhaustion(int addVal) {
        if(UC.p() != null) {
            Immortality.ImmortalityFields.exhaustion.set(AbstractDungeon.player, Immortality.ImmortalityFields.exhaustion.get(UC.p()) + addVal);
        }
    }

    public static boolean shouldRenderExhaustion() {
        return CardCrawlGame.isInARun() && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && isImmortal();
    }
}
