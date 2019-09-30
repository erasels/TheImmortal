package theImmortal.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theImmortal.actions.utility.DamageAllAction;
import theImmortal.enemies.AbstractFlame;
import theImmortal.patches.cards.HPLossCardsPatches;
import theImmortal.patches.combat.BurstMechanics;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UC {
    //Common references
    public static AbstractPlayer p() {
        return AbstractDungeon.player;
    }

    //Checks
    public static boolean checkBurst() {
        return BurstMechanics.PlayerBurstField.isBurst.get(p());
    }

    //Actionmanager
    public static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    //Do common effect
    public static void doDmg(AbstractCreature target, int amount) {
        doDmg(target, amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt) {
        doDmg(target, amount, dt, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, int amount, AbstractGameAction.AttackEffect ae) {
        doDmg(target, amount, DamageInfo.DamageType.NORMAL, ae);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae) {
        doDmg(target, amount, dt, ae, false);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae, boolean fast) {
        doDmg(target, amount, dt, ae, fast, false);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae, boolean fast, boolean top) {
        if (top) {
            att(new DamageAction(target, new DamageInfo(p(), amount, dt), ae, fast));
        } else {
            atb(new DamageAction(target, new DamageInfo(p(), amount, dt), ae, fast));
        }
    }

    public static void doAllDmg(int amount, AbstractGameAction.AttackEffect ae, boolean top) {
        if (top) {
            att(new DamageAllAction(p(), amount, false, DamageInfo.DamageType.NORMAL, ae, false));
        } else {
            atb(new DamageAllAction(p(), amount, false, DamageInfo.DamageType.NORMAL, ae, false));
        }
    }

    public static void doDef(int amount) {
        atb(new GainBlockAction(p(), p(), amount));
    }

    public static void doPow(AbstractCreature target, AbstractPower p) {
        doPow(target, p, false);
    }

    public static void doPow(AbstractCreature target, AbstractPower p, boolean top) {
        doPow(UC.p(), target, p, top);
    }

    public static void doPow(AbstractCreature source, AbstractCreature target, AbstractPower p, boolean top) {
        if (top) {
            att(new ApplyPowerAction(target, source, p, p.amount));
        } else {
            atb(new ApplyPowerAction(target, source, p, p.amount));
        }
    }

    public static void doVfx(AbstractGameEffect gameEffect) {
        atb(new VFXAction(gameEffect));
    }

    public static void doVfx(AbstractGameEffect gameEffect, float duration) {
        atb(new VFXAction(gameEffect, duration));
    }

    //Getters
    public static Color getRandomFireColor() {
        int i = MathUtils.random(3);
        switch (i) {
            case 0:
                return Color.ORANGE;
            case 1:
                return Color.YELLOW;
            default:
                return Color.RED;
        }
    }

    public static AbstractMonster getRandomFlame(Predicate<AbstractMonster> exclusion) {
        ArrayList<AbstractMonster> mons = AbstractDungeon.getMonsters().monsters.stream().filter(m -> m instanceof AbstractFlame).collect(Collectors.toCollection(ArrayList::new));
        if (exclusion != null) {
            mons = mons.stream().filter(exclusion).collect(Collectors.toCollection(ArrayList::new));
        }
        return mons.get(AbstractDungeon.cardRandomRng.random(mons.size() - 1));
    }

    public static ArrayList<AbstractMonster> getAliveMonsters() {
        return AbstractDungeon.getMonsters().monsters.stream().filter(m -> !m.isDeadOrEscaped()).collect(Collectors.toCollection(ArrayList::new));
    }

    //HPLossCards methods
    public static int getHPCost(AbstractCard c) {
        return HPLossCardsPatches.HPLossFields.hpCost.get(c);
    }

    public static int getBaseHPCost(AbstractCard c) {
        return HPLossCardsPatches.HPLossFields.baseHPCost.get(c);
    }

    public static boolean getHPCostModified(AbstractCard c) {
        return HPLossCardsPatches.HPLossFields.isHPCostModified.get(c);
    }

    public static void setHPCost(AbstractCard c, int amount) {
        HPLossCardsPatches.HPLossFields.hpCost.set(c, amount);
    }

    public static void setBaseHPCost(AbstractCard c, int amount) {
        HPLossCardsPatches.HPLossFields.baseHPCost.set(c, amount);
    }

    public static void setHPCostModified(AbstractCard c, boolean state) {
        HPLossCardsPatches.HPLossFields.isHPCostModified.set(c, state);
    }
}
