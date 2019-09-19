package theImmortal.cards.basic;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;

import static basemod.helpers.BaseModCardTags.BASIC_STRIKE;
import static theImmortal.TheImmortal.makeID;

public class Strike extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Strike",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public Strike() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);

        tags.add(BASIC_STRIKE);
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doDmg(m,this.damage, MathUtils.randomBoolean() ? AbstractGameAction.AttackEffect.SLASH_VERTICAL : AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Strike();
    }
}