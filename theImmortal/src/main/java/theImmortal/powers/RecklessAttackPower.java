package theImmortal.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theImmortal.TheImmortal;
import theImmortal.powers.abstracts.AbstractImmortalPower;
import theImmortal.universal.CardHPLossHook;
import theImmortal.util.UC;

import static theImmortal.util.UC.atb;
import static theImmortal.util.UC.doVfx;

public class RecklessAttackPower extends AbstractImmortalPower implements CloneablePowerInterface, CardHPLossHook {
    public static final String POWER_ID = TheImmortal.makeID("RecklessAttack");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RecklessAttackPower(int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        type = AbstractPower.PowerType.BUFF;
        updateDescription();
        isTurnBased = false;
        loadRegion("brutality");
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse) {
            atb(new SFXAction("ATTACK_PIERCING_WAIL", -0.5f));
            doVfx(new ShockWaveEffect(owner.hb.cX, owner.hb.cY, Color.FIREBRICK, ShockWaveEffect.ShockWaveType.ADDITIVE));
            flash();
            UC.att(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public int modifyHPCost(AbstractCard c, int hpcost) {
        return c.costForTurn < 1?hpcost:c.costForTurn * amount;
    }

    @Override
    public void stackPower(int i) {
        super.stackPower(-amount);
        updateDescription();
    }


    @Override
    public AbstractPower makeCopy() {
        return new RecklessAttackPower(amount);
    }

}

