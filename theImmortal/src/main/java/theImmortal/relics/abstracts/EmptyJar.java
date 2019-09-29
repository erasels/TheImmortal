package theImmortal.relics.abstracts;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theImmortal.TheImmortal;
import theImmortal.util.UC;

public class EmptyJar extends ImmortalRelic {
    public static final String ID = TheImmortal.makeID("EmptyJar");

    private static final int TMPSTR = 1;

    public EmptyJar() {
        super(ID, "EmptyJar.png", RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (damageAmount > 0) {
                UC.doPow(UC.p(), new StrengthPower(UC.p(), TMPSTR));
                UC.doPow(UC.p(), new LoseStrengthPower(UC.p(), TMPSTR));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + TMPSTR + DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new EmptyJar();
    }
}