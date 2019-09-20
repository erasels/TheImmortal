package theImmortal.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.actions.unique.ExpiationAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;
import theImmortal.vfx.combat.BubbleEffect;

import static theImmortal.TheImmortal.makeID;

public class Expiation extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Expiation",
            1,
            CardType.SKILL,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 2;
    private static final int HEALING = 1;

    public Expiation() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doVfx(new BubbleEffect(Color.GOLDENROD, "TINGSHA", (((AbstractDungeon.player.hb.cY) + (64f* Settings.scale)))));
        UC.atb(new ExpiationAction(block, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Expiation();
    }
}