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
import theImmortal.patches.cards.CardENUMs;
import theImmortal.util.CardInfo;
import theImmortal.util.TextureLoader;
import theImmortal.util.UC;

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

    protected boolean upgradedHPCost;
    protected boolean upgradeHPCost;
    protected int hpCostUpgrade;

    protected boolean upgradeBurst;
    public boolean hpCostCondition;

    protected boolean upgradeRetain;

    public int baseMagicNumber2;
    public int magicNumber2;
    public boolean isMagicNumber2Modified;

    public int baseShowNumber;
    public int showNumber;
    public boolean isShowNumberModified;


    public ImmortalCard(CardInfo cardInfo, boolean upgradesDescription) {
        this(ImmortalCharacter.Enums.COLOR_IMMORTAL, cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }

    public ImmortalCard(CardColor color, String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription) {
        super(makeID(cardName), "", (String) null, cost, "", cardType, color, rarity, target);

        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);

        img = TextureLoader.getAndLoadCardTextureString(cardName, cardType);
        this.textureImg = img;
        loadCardImage(textureImg);

        this.rarity = autoRarity();

        this.rawDescription = cardStrings.DESCRIPTION;
        this.originalName = cardStrings.NAME;
        this.name = originalName;

        this.baseCost = cost;

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

        hpCostCondition = true;
        upgradeBurst = false;
        upgradeRetain = false;

        if(cardName.toLowerCase().contains("strike")) {
            tags.add(CardTags.STRIKE);
        }

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
        UC.setBaseHPCost(this, hpCost);
        UC.setHPCost(this, hpCost);
        if (hpCostUpgrade != 0) {
            this.upgradeHPCost = true;
            this.hpCostUpgrade = hpCostUpgrade;
        }
        if (!this.tags.contains(CardENUMs.HPLOSS)) {
            this.tags.add(CardENUMs.HPLOSS);
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

    public void setBurst(boolean upgradeToBurst) {
        if(upgradeToBurst) {
            upgradeBurst = true;
        } else {
            tags.add(CardENUMs.BURST);
        }
    }

    public void setRetain(boolean upgradeToRetain) {
        if(upgradeToRetain) {
            upgradeRetain = true;
        } else {
            selfRetain = true;
        }
    }

    public void setMultiDamage(boolean isMultiDamage) {
        this.isMultiDamage = isMultiDamage;
    }

    private CardRarity autoRarity() {
        String packageName = this.getClass().getPackage().getName();

        String directParent;
        if (packageName.contains(".")) {
            directParent = packageName.substring(1 + packageName.lastIndexOf("."));
        } else {
            directParent = packageName;
        }
        switch (directParent) {
            case "common":
                return CardRarity.COMMON;
            case "uncommon":
                return CardRarity.UNCOMMON;
            case "rare":
                return CardRarity.RARE;
            case "basic":
                return CardRarity.BASIC;
            default:
                TheImmortal.logger.info("Automatic Card rarity resulted in SPECIAL, input: " + directParent);
                return CardRarity.SPECIAL;
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();

        if (card instanceof ImmortalCard) {
            card.rawDescription = this.rawDescription;
            ((ImmortalCard) card).upgradesDescription = this.upgradesDescription;

            ((ImmortalCard) card).baseCost = this.baseCost;

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

            ((ImmortalCard) card).baseMagicNumber2 = this.baseMagicNumber2;
            ((ImmortalCard) card).magicNumber2 = this.magicNumber2;
            ((ImmortalCard) card).baseShowNumber = this.baseShowNumber;
            ((ImmortalCard) card).showNumber = this.showNumber;
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

            if(upgradeBurst) {
                tags.add(CardENUMs.BURST);
            }

            if(upgradeRetain) {
                selfRetain = true;
            }

            this.initializeDescription();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if(this.hasTag(CardENUMs.BURST) && UC.anonymousCheckBurst()) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = BLUE_BORDER_GLOW_COLOR;
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
    public static void renderHPCost(AbstractCard card, SpriteBatch sb) {
        float drawX = card.current_x - 256.0F;
        float drawY = card.current_y - 256.0F;

        if (HP_COST_ORB == null) {
            HP_COST_ORB = TextureLoader.getTexture(TheImmortal.makeImagePath("512/CardHPCostOrb.png"));
        }
        if (ENERGY_COST_MODIFIED_COLOR == null) {
            getColorConstants();
        }

        if (!card.isLocked && card.isSeen) {
            if (UC.getHPCost(card) > -1) {
                if (!(card instanceof ImmortalCard) || ((ImmortalCard) card).hpCostCondition) {
                    ImmortalCard.renderHelper(card, sb, renderColor, HP_COST_ORB, drawX, drawY);

                    String msg = Integer.toString(UC.getHPCost(card));
                    Color costColor = Color.WHITE.cpy();
                    if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(card)) {
                        if (UC.getHPCostModified(card)) {
                            if (UC.getHPCost(card) > UC.getBaseHPCost(card) && UC.getHPCost(card) > 0) {
                                costColor = ENERGY_COST_RESTRICTED_COLOR;
                            } else if (UC.getHPCost(card) < UC.getBaseHPCost(card)) {
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
    }

    private static void renderHelper(AbstractCard __instance, SpriteBatch sb, Color color, Texture img, float drawX, float drawY) {
        sb.setColor(color);
        sb.draw(img, drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle, 0, 0, 512, 512, false, false);
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
            UC.setHPCost(this, UC.getBaseHPCost(this));
            UC.setHPCostModified(this, true);
        }
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.magicNumber2 = baseMagicNumber2;
        this.isMagicNumber2Modified = false;
        this.showNumber = baseShowNumber;
        this.isShowNumberModified = false;
    }

    @Override
    public void applyPowers() {
        this.applyPowersToMN2();
        this.applyPowersToSN();
        super.applyPowers();
    }

    private void applyPowersToMN2() {
        this.isMagicNumber2Modified = magicNumber2 != baseMagicNumber2;
    }
    private void applyPowersToSN() {
        this.isShowNumberModified = showNumber != baseShowNumber;
    }

    protected void upgradeHPCost(int amount) {
        UC.setBaseHPCost(this, UC.getBaseHPCost(this) + amount);
        UC.setHPCost(this, UC.getBaseHPCost(this));
        this.upgradedHPCost = true;
    }
}