package theImmortal.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theImmortal.patches.combat.BurstMechanics;

public class UC {
    //Common references
    public static AbstractPlayer p = AbstractDungeon.player;

    //Checks
    public static boolean checkBurst() {
        return BurstMechanics.PlayerBurstField.isBurst.get(p);
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
        if(top) {
            att(new DamageAction(target, new DamageInfo(p, amount, dt), ae, fast));
        } else {
            atb(new DamageAction(target, new DamageInfo(p, amount, dt), ae, fast));
        }
    }

    public static void doDef(int amount) {
        atb(new GainBlockAction(p, p, amount));
    }

    public static void doPow(AbstractCreature target, AbstractPower p) {
        doPow(target, p, false);
    }

    public static void doPow(AbstractCreature target, AbstractPower p, boolean top) {
        doPow(UC.p, target, p, top);
    }

    public static void doPow(AbstractCreature source, AbstractCreature target, AbstractPower p, boolean top) {
        if(top) {
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
}
