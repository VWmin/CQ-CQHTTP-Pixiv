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
 * @date 2020/4/24 18:14
 */
public class CharacterSupply extends BaseSupply{
    private static final int baodi = 10;

    private static final String ARGS = "args";
    private static final String CHARACTER = "character";
    private static final String DEBRIS = "debris";
    private static final String MATERIAL = "material";

    private final List<String> debrisItems;

    private final Map<String, List<String>> realItemMapA;

    public CharacterSupply(ResourceManager resourceManager) throws IOException, BackingStoreException {
        super(resourceManager);
        Ini ini = new Ini(resourceManager.getExpansionSupplyASetting().getInputStream());
        Preferences prefs = new IniPreferences(ini);
        Preferences args = prefs.node(ARGS);
        Preferences character = prefs.node(CHARACTER);
        Preferences debris = prefs.node(DEBRIS);
        Preferences material = prefs.node(MATERIAL);

        double valuableProbability = args.getDouble("valuable", 15);
        double totalProbability = args.getDouble("total", 100);

        this.debrisItems = Arrays.asList(debris.keys());


        init(Arrays.asList(args.get("valuableItems", "").split(" ")),
                valuableProbability, totalProbability, character, debris, material);
        realItemMapA = readToMap(resourceManager.getExpansionSupplyAContent());
    }

    @Override
    boolean isDebris(String item) {
        return debrisItems.contains(item);
    }


    @Override
    public Item next(Person person){
        String item = person.needSomethingBetter(baodi)
                ? nextValuable()
                : next();

        person.increase(isValuable(item));

        return getItem(item, realItemMapA);
    }

}
