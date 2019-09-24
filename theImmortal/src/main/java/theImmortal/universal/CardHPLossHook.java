package theImmortal.universal;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface CardHPLossHook {
    int modifyHPCost(AbstractCard c, int hpcost);
}
