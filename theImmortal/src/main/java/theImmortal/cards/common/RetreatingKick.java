package theImmortal.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.patches.cards.CardENUMs;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class RetreatingKick extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RetreatingKick",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DMG = 6;
    private static final int UPG_DMG = 2;

    public RetreatingKick() {
        super(cardInfo, false);

        setDamage(DMG, UPG_DMG);
        tags.add(CardENUMs.BURST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDmg(m, damage, AbstractGameAction.AttackEffect.BLUNT_LIGHT);

        if (checkBurst()) {
            doDef(damage);
        }
    }
}