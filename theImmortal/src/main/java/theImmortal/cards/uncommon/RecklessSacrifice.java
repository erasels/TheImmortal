package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theImmortal.actions.unique.RecklessSacrificeAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.patches.cards.CardENUMs;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.atb;
import static theImmortal.util.UC.p;

public class RecklessSacrifice extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RecklessSacrifice",
            -1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = 2;

    public RecklessSacrifice() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        baseMagicNumber2 = magicNumber2 = 0;
        baseShowNumber = showNumber = 0;
        tags.add(CardENUMs.HPLOSS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RecklessSacrificeAction(m, damage, magicNumber, freeToPlayOnce, energyOnUse));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int i = p().hasRelic(ChemicalX.ID)?2:0;
        showNumber = damage*((int)Math.pow(2, EnergyPanel.getCurrentEnergy() +i) - 1);
        magicNumber2 = magicNumber*((int)Math.pow(2, EnergyPanel.getCurrentEnergy() +i) - 1);
        isShowNumberModified = true;
        isMagicNumber2Modified = true;
    }
}