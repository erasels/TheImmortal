package theImmortal.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theImmortal.TheImmortal;
import theImmortal.powers.abstracts.AbstractImmortalPower;
import theImmortal.util.UC;

public class IgnitePower extends AbstractImmortalPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheImmortal.makeID("Ignite");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

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
                int stack = MathUtils.ceil(amount/2.0f);
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
    public void stackPower(int i) {
        super.stackPower(i);
        updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new IgnitePower(owner, amount);
    }
}
