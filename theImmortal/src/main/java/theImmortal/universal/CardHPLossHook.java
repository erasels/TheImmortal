package theImmortal.universal;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface CardHPLossHook {
    default int modifyHPCost(AbstractCard c, int hpcost) {return hpcost;}
    default int preHPLoss(AbstractCard c, int initialCost) {return initialCost;}
    default void postHPLoss(AbstractCard c, int loss) {}
}
