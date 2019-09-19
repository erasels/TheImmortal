package theImmortal.cards.abstracts;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import theImmortal.TheImmortal;
import theImmortal.characters.ImmortalCharacter;
import theImmortal.util.CardInfo;
import theImmortal.util.TextureLoader;

import java.lang.reflect.Field;

import static theImmortal.TheImmortal.makeID;

public abstract class ImmortalCard extends CustomCard {
    protected CardStrings cardStrings;
    protected String img;

    protected boolean upgradesDescription;

    protected int baseCost;

    protected boolean upgradeCost;
    protected boolean upgradeDamage;
    protected boolean upgradeBlock;
    protected boolean upgradeMagic;

    protected int costUpgrade;
    protected int damageUpgrade;
    protected int blockUpgrade;
    protected int magicUpgrade;

    protected boolean baseExhaust;
    protected boolean upgExhaust;
    protected boolean baseInnate;
    protected boolean upgInnate;

    public int baseHPCost;
    public int hpCost;
    public boolean isHPCostModified;
    protected boolean upgradedHPCost;
    protected boolean upgradeHPCost;
    protected int hpCostUpgrade;

    public ImmortalCard(CardInfo cardInfo, boolean upgradesDescription) {
        this(ImmortalCharacter.Enums.COLOR_IMMORTAL, cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }

    public ImmortalCard(CardColor color, String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription) {
        super(makeID(cardName), "", (String) null, cost, "", cardType, color, rarity, target);

        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);

        img = TextureLoader.getAndLoadCardTextureString(cardName, cardType);
        this.textureImg = img;
        loadCardImage(textureImg);

        this.rawDescription = cardStrings.DESCRIPTION;
        this.originalName = cardStrings.NAME;
        this.name = originalName;

        this.baseCost = cost;

        this.baseHPCost = -1;
        this.hpCost = -1;
        this.isHPCostModified = false;

        this.upgradesDescription = upgradesDescription;

        this.upgradedHPCost = false;

        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;
        this.upgradeHPCost = false;

        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;
        this.hpCostUpgrade = 0;


        InitializeCard();
    }

    //Methods meant for constructor use
    public void setDamage(int damage) {
        this.setDamage(damage, 0);
    }

    public void setBlock(int block) {
        this.setBlock(block, 0);
    }

    public void setMagic(int magic) {
        this.setMagic(magic, 0);
    }

    public void setHPCost(int hpCost) {
        this.setHPCost(hpCost, 0);
    }

    public void setCostUpgrade(int costUpgrade) {
        this.costUpgrade = costUpgrade;
        this.upgradeCost = true;
    }

    public void setExhaust(boolean exhaust) {
        this.setExhaust(exhaust, exhaust);
    }

    public void setDamage(int damage, int damageUpgrade) {
        this.baseDamage = this.damage = damage;
        if (damageUpgrade != 0) {
            this.upgradeDamage = true;
            this.damageUpgrade = damageUpgrade;
        }
    }

    public void setBlock(int block, int blockUpgrade) {
        this.baseBlock = this.block = block;
        if (blockUpgrade != 0) {
            this.upgradeBlock = true;
            this.blockUpgrade = blockUpgrade;
        }
    }

    public void setMagic(int magic, int magicUpgrade) {
        this.baseMagicNumber = this.magicNumber = magic;
        if (magicUpgrade != 0) {
            this.upgradeMagic = true;
            this.magicUpgrade = magicUpgrade;
        }
    }

    public void setHPCost(int hpCost, int hpCostUpgrade) {
        this.baseHPCost = this.hpCost = hpCost;
        if (hpCostUpgrade != 0) {
            this.upgradeHPCost = true;
            this.hpCostUpgrade = hpCostUpgrade;
        }
    }

    public void setExhaust(boolean baseExhaust, boolean upgExhaust) {
        this.baseExhaust = baseExhaust;
        this.upgExhaust = upgExhaust;
        this.exhaust = baseExhaust;
    }

    public void setInnate(boolean baseInnate, boolean upgInnate) {
        this.baseInnate = baseInnate;
        this.isInnate = baseInnate;
        this.upgInnate = upgInnate;
    }

    public void setMultiDamage(boolean isMultiDamage) {
        this.isMultiDamage = isMultiDamage;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();

        if (card instanceof ImmortalCard) {
            card.rawDescription = this.rawDescription;
            ((ImmortalCard) card).upgradesDescription = this.upgradesDescription;

            ((ImmortalCard) card).baseCost = this.baseCost;

            ((ImmortalCard) card).baseHPCost = this.baseHPCost;
            ((ImmortalCard) card).hpCost = this.hpCost;

            ((ImmortalCard) card).upgradeCost = this.upgradeCost;
            ((ImmortalCard) card).upgradeDamage = this.upgradeDamage;
            ((ImmortalCard) card).upgradeBlock = this.upgradeBlock;
            ((ImmortalCard) card).upgradeMagic = this.upgradeMagic;
            ((ImmortalCard) card).upgradeHPCost = this.upgradeHPCost;

            ((ImmortalCard) card).costUpgrade = this.costUpgrade;
            ((ImmortalCard) card).damageUpgrade = this.damageUpgrade;
            ((ImmortalCard) card).blockUpgrade = this.blockUpgrade;
            ((ImmortalCard) card).magicUpgrade = this.magicUpgrade;
            ((ImmortalCard) card).hpCostUpgrade = this.hpCostUpgrade;

            ((ImmortalCard) card).baseExhaust = this.baseExhaust;
            ((ImmortalCard) card).upgExhaust = this.upgExhaust;
            ((ImmortalCard) card).baseInnate = this.baseInnate;
            ((ImmortalCard) card).upgInnate = this.upgInnate;
        }

        return card;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();

            if (this.upgradesDescription)
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;

            if (upgradeCost) {
                int diff = this.baseCost - this.cost; //positive if cost is reduced

                this.upgradeBaseCost(costUpgrade);
                this.cost -= diff;
                this.costForTurn -= diff;
                if (cost < 0)
                    cost = 0;

                if (costForTurn < 0)
                    costForTurn = 0;
            }

            if (upgradeDamage)
                this.upgradeDamage(damageUpgrade);

            if (upgradeBlock)
                this.upgradeBlock(blockUpgrade);

            if (upgradeMagic)
                this.upgradeMagicNumber(magicUpgrade);

            if (upgradeHPCost)
                this.upgradeHPCost(hpCostUpgrade);

            if (baseExhaust ^ upgExhaust) //different
                this.exhaust = upgExhaust;

            if (baseInnate ^ upgInnate) //different
                this.isInnate = upgInnate;


            this.initializeDescription();
        }
    }

    public void InitializeCard() {
        FontHelper.cardDescFont_N.getData().setScale(1.0f);
        this.initializeTitle();
        this.initializeDescription();
    }


    private static Texture HP_COST_ORB;
    private static Color renderColor = Color.WHITE.cpy();

    //TODO: Make this render in the big card view
    public static void renderHPCost(ImmortalCard card, SpriteBatch sb) {
        float drawX = card.current_x - 256.0F;
        float drawY = card.current_y - 256.0F;

        if (HP_COST_ORB == null) {
            HP_COST_ORB = TextureLoader.getTexture(TheImmortal.makeImagePath("512/CardHPCostOrb.png"));
        }
        if(ENERGY_COST_MODIFIED_COLOR == null){
            getColorConstants();
        }

        if (!card.isLocked && card.isSeen) {
            //logger.info("attempting render");
            if (card.hpCost > -1) {
                card.renderHelper(sb, renderColor, HP_COST_ORB, drawX, drawY);

                String msg = Integer.toString(card.hpCost);
                Color costColor = Color.WHITE.cpy();
                if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(card)) {
                    if (card.isHPCostModified) {
                        if (card.hpCost > card.baseHPCost) {
                            costColor = ENERGY_COST_RESTRICTED_COLOR;
                        } else if (card.hpCost < card.baseHPCost) {
                            costColor = ENERGY_COST_MODIFIED_COLOR;
                        }
                    }
                }
                costColor.a = card.transparency;

                FontHelper.renderRotatedText(sb, getHPCostFont(card), msg, card.current_x,
                        card.current_y, -132.0F * card.drawScale * Settings.scale,
                        129.0F * card.drawScale * Settings.scale, card.angle,
                        true, costColor);
            }
        }
    }

    private void renderHelper(SpriteBatch sb, Color color, Texture img, float drawX, float drawY) {
        float scale = 0.25f;
        sb.setColor(color);
        sb.draw(img, drawX , drawY, 256.0F, 256.0F, 512.0F, 512.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
    }

    private static BitmapFont getHPCostFont(AbstractCard card) {
        FontHelper.cardEnergyFont_L.getData().setScale(card.drawScale * 0.75f);
        return FontHelper.cardEnergyFont_L;
    }

    private static Color ENERGY_COST_RESTRICTED_COLOR, ENERGY_COST_MODIFIED_COLOR;

    private static void getColorConstants() {
        Field f;
        try {
            f = AbstractCard.class.getDeclaredField("ENERGY_COST_RESTRICTED_COLOR");
            f.setAccessible(true);
            ENERGY_COST_RESTRICTED_COLOR = (Color) f.get(null);

            f = AbstractCard.class.getDeclaredField("ENERGY_COST_MODIFIED_COLOR");
            f.setAccessible(true);
            ENERGY_COST_MODIFIED_COLOR = (Color) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (this.upgradedHPCost) {
            this.hpCost = this.baseHPCost;
            this.isHPCostModified = true;
        }
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.hpCost = this.baseHPCost;
        this.isHPCostModified = false;
    }

    @Override
    public void applyPowers() {
        this.applyPowersToHPCost();
        super.applyPowers();
    }

    private void applyPowersToHPCost() {
        this.isHPCostModified = false;
        this.hpCost = this.baseHPCost;
        /*if (UC.p.hasPower(SealPower.ID))
            this.hpCost = 0;*/
        if (this.hpCost != this.baseHPCost) this.isHPCostModified = true;
    }

    protected void upgradeHPCost(int amount) {
        this.baseHPCost += amount;
        this.hpCost = this.baseHPCost;
        this.upgradedHPCost = true;
    }
}