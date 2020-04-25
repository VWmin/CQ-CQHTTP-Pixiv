package com.vwmin.coolq.honkar3rd.gacha;

import com.vwmin.coolq.honkar3rd.ResourceManager;
import com.vwmin.coolq.honkar3rd.entities.Item;
import com.vwmin.coolq.honkar3rd.entities.Person;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/25 13:33
 */
public class EquipmentSupply extends BaseSupply{
    private static final int baodi = 10;
    private static final String ARGS = "args";
    private static final String EQUIPMENT = "equipment";
    private static final String STIGMA = "stigma";
    private static final String MATERIAL = "material";

    private final Map<String, List<String>> realItemMap;

    public static final int FOCUSED_A = 0;
    public static final int FOCUSED_B = 1;
    public static final int EXPANSION_B = 2;

    public EquipmentSupply(ResourceManager resourceManager, int mode) throws IOException, BackingStoreException {
        super(resourceManager);
        Ini ini = new Ini((mode == EXPANSION_B)
                ? resourceManager.getExpansionSupplyBSetting().getInputStream()
                : resourceManager.getFocusedSupplySetting().getInputStream());
        Preferences prefs = new IniPreferences(ini);
        Preferences args = prefs.node(ARGS);
        Preferences equipment = prefs.node(EQUIPMENT);
        Preferences stigma = prefs.node(STIGMA);
        Preferences material = prefs.node(MATERIAL);

        final double totalProbability = args.getDouble("total", 100);
        final double valuableProbability = args.getDouble("valuable", 12.398);

        init(Arrays.asList(args.get("valuableItems", "").split(" ")),
                valuableProbability, totalProbability, equipment, stigma, material);

        realItemMap = (mode == FOCUSED_A)
                ? readToMap(resourceManager.getFocusedSupplyAContent())
                : (mode == FOCUSED_B)
                    ? readToMap(resourceManager.getFocusedSupplyBContent())
                    : readToMap(resourceManager.getExpansionSupplyBContent());
    }

    @Override
    boolean isDebris(String item) {
        return false;
    }

    @Override
    public Item next(Person person) {
        String item = person.needSomethingBetter(baodi)
                ? nextValuable()
                : next();

        person.increase(isValuable(item));

        return getItem(item, realItemMap);
    }

}
