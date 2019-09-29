package theImmortal.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theImmortal.TheImmortal;
import theImmortal.patches.cards.CardENUMs;
import theImmortal.powers.abstracts.AbstractImmortalPower;
import theImmortal.util.UC;

public class UpdraftPower extends AbstractImmortalPower implements CloneablePowerInterface, OnPlayerDeathPower {
    public static final String POWER_ID = TheImmortal.makeID("Updraft");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UpdraftPower(int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        type = AbstractPower.PowerType.BUFF;
        updateDescription();
        isTurnBased = false;
        loadRegion("firebreathing");
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer p, DamageInfo damageInfo) {
        UC.att(new RemoveSpecificPowerAction(p, p, this));
        UC.att(new HealAction(p, p, countHarmCards()*amount));
        return false;
    }

    private int countHarmCards() {
        return (int)UC.p().masterDeck.group.stream().filter(c -> c.tags.contains(CardENUMs.HPLOSS)).count();
    }


    @Override
    public AbstractPower makeCopy() {
        return new UpdraftPower(amount);
    }
}

