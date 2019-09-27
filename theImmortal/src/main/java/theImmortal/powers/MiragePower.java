package theImmortal.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theImmortal.TheImmortal;
import theImmortal.powers.abstracts.AbstractImmortalPower;
import theImmortal.universal.CardHPLossHook;
import theImmortal.util.UC;

public class MiragePower extends AbstractImmortalPower implements CloneablePowerInterface, CardHPLossHook, NonStackablePower {
    public static final String POWER_ID = TheImmortal.makeID("Mirage");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private AbstractMonster flame;

    public MiragePower(int amount, AbstractMonster flame) {
        name = NAME;
        ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        type = AbstractPower.PowerType.BUFF;
        this.flame = flame;
        updateDescription();
        isTurnBased = true;
        loadRegion("afterImage");
        priority = 99;
    }

    @Override
    public void updateDescription() {
        String tmp = DESCRIPTIONS[0]; //Until the end of your #b
        if(amount>1) {
            tmp += amount + DESCRIPTIONS[1]; // next turns,
        } else {
            tmp += DESCRIPTIONS[2]; // [REMPVE_SPACE] turn,
        }
        tmp+=DESCRIPTIONS[3]; // if you would lose HP from a card,
        if(flame != null) {
            tmp += FontHelper.colorString(flame.name, "y");
        } else {
            tmp += DESCRIPTIONS[4]; //a random #yFlame
        }
        tmp += DESCRIPTIONS[5]; // loses it instead.
        description = tmp;
    }

    @Override
    public int preHPLoss(AbstractCard c, int loss) {
        AbstractMonster m = UC.getRandomFlame(flame!=null?(f -> f == flame):null);
        if(m != null) {
            UC.doDmg(m, loss, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.FIRE, true, true);
            return 0;
        }
        return loss;
    }

    @Override
    public boolean isStackable(AbstractPower p) {
        return ((MiragePower)p).flame == this.flame;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer) {
            if (this.amount <= 0) {
                UC.atb(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            } else {
                UC.atb(new ReducePowerAction(this.owner, this.owner, this, 1));
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
        return new RecklessAttackPower(amount);
    }

}

