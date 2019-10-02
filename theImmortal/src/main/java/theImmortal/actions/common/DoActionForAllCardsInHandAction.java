package theImmortal.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theImmortal.util.UC;

import java.util.ArrayList;
import java.util.function.Consumer;

public class DoActionForAllCardsInHandAction extends AbstractGameAction {
    private Consumer<ArrayList<AbstractCard>> callback;

    public DoActionForAllCardsInHandAction(Consumer<ArrayList<AbstractCard>> callback) {
        this.callback = callback;
    }

    @Override
    public void update() {
        callback.accept(UC.p().hand.group);
        isDone = true;
    }
}
