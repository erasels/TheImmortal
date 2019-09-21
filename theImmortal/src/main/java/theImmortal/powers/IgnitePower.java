package theImmortal.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theImmortal.TheImmortal;
import theImmortal.powers.abstracts.AbstractImmortalPower;
import theImmortal.util.UC;
import theImmortal.vfx.general.StraightFireParticle;

import java.util.Random;

public class IgnitePower extends AbstractImmortalPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheImmortal.makeID("Ignite");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static Random rng = new Random();
    public static Color tintCol = Color.ORANGE.cpy().add(0.1f, 0.3f, 0.3f, 1.0f);

    private static final float INTERVAL = 0.05f;

    private float particleTimer;

    public IgnitePower(AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = AbstractPower.PowerType.DEBUFF;
        updateDescription();
        isTurnBased = true;
        loadRegion("explosive");
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfRound() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                int stack = MathUtils.ceil(amount / 2.0f);
                flashWithoutSound();
                UC.doDmg(owner, stack, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE);

                if (this.amount <= 0) {
                    UC.atb(new RemoveSpecificPowerAction(this.owner, this.owner, this));
                } else {
                    UC.atb(new ReducePowerAction(this.owner, this.owner, this, stack));
                }
            }
        }
    }

    @Override
    public void updateParticles() {
        this.particleTimer -= Gdx.graphics.getRawDeltaTime();
        if (this.particleTimer < 0.0F) {
            float xOff = ((owner.hb_w) * (float) rng.nextGaussian())*0.25f;
            if(MathUtils.randomBoolean()) {
                xOff = -xOff;
            }
            AbstractDungeon.effectList.add(new StraightFireParticle(owner.drawX + xOff, owner.drawY + MathUtils.random(owner.hb_h/2f), 75f));
            this.particleTimer = INTERVAL;
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "render")
    public static class ChangeColorOfAffectedMonsterPls {
        @SpirePrefixPatch
        public static void patch(AbstractMonster __instance, SpriteBatch sb) {
            if(__instance.hasPower(IgnitePower.POWER_ID)) {
                __instance.tint.color.mul(IgnitePower.tintCol);
            }
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        particleTimer = 0.4f;
    }

    @Override
    public void stackPower(int i) {
        super.stackPower(i);
        updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new IgnitePower(owner, amount);
    }
}
