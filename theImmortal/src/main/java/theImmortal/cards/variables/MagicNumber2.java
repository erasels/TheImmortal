package theImmortal.cards.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theImmortal.cards.abstracts.ImmortalCard;

public class MagicNumber2 extends DynamicVariable {
    @Override
    public String key() {
        return "theImmortal:M2";
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof ImmortalCard) {
            return ((ImmortalCard) card).baseMagicNumber2;
        }
        return -1;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof ImmortalCard) {
            return ((ImmortalCard) card).magicNumber2;
        }
        return -1;
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof ImmortalCard) {
            return ((ImmortalCard) card).isMagicNumber2Modified;
        }
        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof ImmortalCard) {
            ((ImmortalCard) card).isMagicNumber2Modified = true;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof ImmortalCard) {
            return ((ImmortalCard) card).upgradedMagicNumber2;
        }
        return false;
    }
}
