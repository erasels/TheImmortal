package theImmortal.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theImmortal.TheImmortal;
import theImmortal.powers.abstracts.AbstractImmortalPower;
import theImmortal.util.UC;

public class BurningMadnessPower extends AbstractImmortalPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheImmortal.makeID("BurningMadness");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BurningMadnessPower(AbstractCreature owner, int hploss, int stramt) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = hploss;
        this.amount2 = stramt;
        type = AbstractPower.PowerType.BUFF;
        updateDescription();
        isTurnBased = false;
        loadRegion("anger");
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {
        UC.atb(new LoseHPAction(owner, owner, amount));
        UC.doPow(owner, new StrengthPower(owner, amount2));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2 += 1; //Can't get second amount atm, patch not really needed as long as I don't change it
    }

    @Override
    public AbstractPower makeCopy() {
        return new BurningMadnessPower(owner, amount, amount2);
    }
}

