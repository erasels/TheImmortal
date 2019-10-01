package theImmortal.cards.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theImmortal.cards.abstracts.ImmortalCard;

public class ShowNumber extends DynamicVariable {
    @Override
    public String key() {
        return "theImmortal:SN";
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof ImmortalCard) {
            return ((ImmortalCard) card).baseShowNumber;
        }
        return -1;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof ImmortalCard) {
            return ((ImmortalCard) card).showNumber;
        }
        return -1;
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof ImmortalCard) {
            return ((ImmortalCard) card).isShowNumberModified;
        }
        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof ImmortalCard) {
            ((ImmortalCard) card).isShowNumberModified = true;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}
