package theImmortal.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theImmortal.TheImmortal;
import theImmortal.powers.abstracts.AbstractImmortalPower;
import theImmortal.util.UC;

public class LoseHPDrawCardPower extends AbstractImmortalPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheImmortal.makeID("LoseHPDrawCard");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LoseHPDrawCardPower(int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        type = AbstractPower.PowerType.BUFF;
        updateDescription();
        isTurnBased = false;
        loadRegion("skillBurn");
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + (amount>1?DESCRIPTIONS[2]:DESCRIPTIONS[1]);
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if(damageAmount>0) {
            UC.atb(new DrawCardAction(amount));
        }
        return damageAmount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new LoseHPDrawCardPower(amount);
    }
}

