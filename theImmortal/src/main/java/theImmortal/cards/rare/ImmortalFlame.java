package theImmortal.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class ImmortalFlame extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ImmortalFlame",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int VAL = 7;
    private static final int UPG_VAL = 3;

    public ImmortalFlame() {
        super(cardInfo, false);
        setDamage(VAL, UPG_VAL);
    }

    public void trigger() {
        doVfx(new InflameEffect(p()));
        doAllDmg(damage, AbstractGameAction.AttackEffect.FIRE, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }
}