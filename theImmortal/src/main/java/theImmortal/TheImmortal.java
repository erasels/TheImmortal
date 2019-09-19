package theImmortal;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theImmortal.characters.ImmortalCharacter;
import theImmortal.mechanics.ImmortalityManager;
import theImmortal.util.IDCheckDontTouchPls;
import theImmortal.util.TextureLoader;
import theImmortal.variables.DefaultCustomVariable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class TheImmortal implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PreStartGameSubscriber{
    public static final Logger logger = LogManager.getLogger(TheImmortal.class.getName());
    private static String modID;

    public static Properties theImmortalSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    private static final String MODNAME = "The Immortal";
    private static final String AUTHOR = "erasels";
    private static final String DESCRIPTION = "An undying character visits the spire and may get more than they bargained for.";

    public static final Color IMMORTAL_RED = CardHelper.getColor(60, 36, 36);

    private static final String ATTACK_IMMORTAL_RED = "theImmortalResources/images/512/bg_attack_immortal_red.png";
    private static final String SKILL_IMMORTAL_RED = "theImmortalResources/images/512/bg_skill_immortal_red.png";
    private static final String POWER_IMMORTAL_RED = "theImmortalResources/images/512/bg_power_immortal_red.png";

    private static final String ENERGY_ORB_IMMORTAL_RED = "theImmortalResources/images/512/card_immortal_red_orb.png";
    private static final String CARD_ENERGY_ORB = "theImmortalResources/images/512/card_small_orb.png";

    private static final String ATTACK_IMMORTAL_RED_PORTRAIT = "theImmortalResources/images/1024/bg_attack_immortal_red.png";
    private static final String SKILL_IMMORTAL_RED_PORTRAIT = "theImmortalResources/images/1024/bg_skill_immortal_red.png";
    private static final String POWER_IMMORTAL_RED_PORTRAIT = "theImmortalResources/images/1024/bg_power_immortal_red.png";
    private static final String ENERGY_ORB_IMMORTAL_RED_PORTRAIT = "theImmortalResources/images/1024/card_immortal_red_orb.png";

    private static final String THE_IMMORTAL_BUTTON = "theImmortalResources/images/charSelect/CharacterButton.png";
    private static final String THE_IMMORTAL_PORTRAIT = "theImmortalResources/images/charSelect/CharacterPortraitBG.png";
    public static final String THE_IMMORTAL_SHOULDER_1 = "theImmortalResources/images/char/shoulder.png";
    public static final String THE_IMMORTAL_SHOULDER_2 = "theImmortalResources/images/char/shoulder2.png";
    public static final String THE_IMMORTAL_CORPSE = "theImmortalResources/images/char/corpse.png";

    public static final String BADGE_IMAGE = "theImmortalResources/images/Badge.png";

    public static final String THE_IMMORTAL_SKELETON_ATLAS = "theImmortalResources/images/char/skeleton.atlas";
    public static final String THE_IMMORTAL_SKELETON_JSON = "theImmortalResources/images/char/skeleton.json";

    public TheImmortal() {
        BaseMod.subscribe(this);

        setModID("theImmortal");

        logger.info("Creating the color " + ImmortalCharacter.Enums.COLOR_IMMORTAL.toString());

        BaseMod.addColor(ImmortalCharacter.Enums.COLOR_IMMORTAL, IMMORTAL_RED, IMMORTAL_RED, IMMORTAL_RED,
                IMMORTAL_RED, IMMORTAL_RED, IMMORTAL_RED, IMMORTAL_RED,
                ATTACK_IMMORTAL_RED, SKILL_IMMORTAL_RED, POWER_IMMORTAL_RED, ENERGY_ORB_IMMORTAL_RED,
                ATTACK_IMMORTAL_RED_PORTRAIT, SKILL_IMMORTAL_RED_PORTRAIT, POWER_IMMORTAL_RED_PORTRAIT,
                ENERGY_ORB_IMMORTAL_RED_PORTRAIT, CARD_ENERGY_ORB);

        theImmortalSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("theImmortal", "theImmortalConfig", theImmortalSettings);
            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        TheImmortal immortalMod = new TheImmortal();
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + ImmortalCharacter.Enums.THE_IMMORTAL.toString());
        BaseMod.addCharacter(new ImmortalCharacter("the Immortal", ImmortalCharacter.Enums.THE_IMMORTAL), THE_IMMORTAL_BUTTON, THE_IMMORTAL_PORTRAIT, ImmortalCharacter.Enums.THE_IMMORTAL);
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, enablePlaceholder, settingsPanel, (label) -> {}, (button) -> {
            enablePlaceholder = button.enabled;
            try {
                SpireConfig config = new SpireConfig("theImmortal", "theImmortalConfig", theImmortalSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsPanel.addUIElement(enableNormalsButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        BaseMod.addSaveField("TheImmortalExhuastionCount", new CustomSavable<Integer>() {
            @Override
            public Integer onSave() {
                return ImmortalityManager.getExhaustion();
            }

            @Override
            public void onLoad(Integer i) {
                ImmortalityManager.setExhaustion(i);
            }
        });
    }

    @Override
    public void receivePreStartGame() {
    }

    @Override
    public void receiveEditRelics() {
    }

    @Override
    public void receiveEditCards() {
        pathCheck();
        BaseMod.addDynamicVariable(new DefaultCustomVariable());

        try {
            AutoLoader.addCards();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/cardStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/powerStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/relicStrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, getModID() + "Resources/localization/eng/eventStrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, getModID() + "Resources/localization/eng/potionStrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/characterStrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, getModID() + "Resources/localization/eng/orbStrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "Resources/localization/eng/uiStrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/keywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    public static String makePath(String resourcePath) {
        return getModID() + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return getModID() + "Resources/images/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }

    public static String makeCharPath(String resourcePath) {
        return getModID() + "Resources/images/char/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }


    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = TheImmortal.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = TheImmortal.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = TheImmortal.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
}
