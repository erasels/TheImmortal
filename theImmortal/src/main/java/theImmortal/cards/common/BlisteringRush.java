package theImmortal.cards.common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.powers.IgnitePower;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class BlisteringRush extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BlisteringRush",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DMG = 8;
    private static final int UPG_DMG = 2;
    private static final int MAG = 4;
    private static final int UPG_MAG = 2;
    private static final int HPLOSS = 2;

    public BlisteringRush() {
        super(cardInfo, false);
        p(); //Stupid intellij stuff
        setDamage(DMG, UPG_DMG);
        setMagic(MAG, UPG_MAG);
        setHPCost(HPLOSS);
        setBurst(false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doAllDmg(damage, MathUtils.randomBoolean()? AbstractGameAction.AttackEffect.FIRE: AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, false);
        if(checkBurst()) {
            getAliveMonsters().forEach(mon -> doPow(mon, new IgnitePower(mon, magicNumber)));
        }
    }
}