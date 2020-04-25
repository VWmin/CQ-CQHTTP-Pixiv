package com.vwmin.coolq.honkar3rd.gacha;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vwmin.coolq.honkar3rd.ItemUtil;
import com.vwmin.coolq.honkar3rd.ResourceManager;
import com.vwmin.coolq.honkar3rd.entities.Character;
import com.vwmin.coolq.honkar3rd.entities.Item;
import com.vwmin.coolq.honkar3rd.entities.Person;
import com.vwmin.coolq.honkar3rd.entities.Stigma;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/24 13:17
 */
public abstract class BaseSupply {
    private final List<String> rubbishItems;
    private final AliasMethod rubbishes;


    private final List<String> normalItems = new ArrayList<>();
    private final List<String> valuableItems = new ArrayList<>();

    private AliasMethod normal;
    private AliasMethod valuable;

    private final Map<String, List<String>> realItemMap;

    public BaseSupply(ResourceManager resourceManager) throws IOException, BackingStoreException {
        Ini ini = new Ini(resourceManager.getBaseSupplySetting().getInputStream());
        IniPreferences prefs = new IniPreferences(ini);
        Preferences rubbish = prefs.node("rubbish");

        rubbishItems = Arrays.asList(rubbish.keys());
        List<Double> properties = new ArrayList<>();
        rubbishItems.forEach((key)->properties.add(rubbish.getDouble(key, 0)));
        rubbishes = new AliasMethod(properties);

        realItemMap = readToMap(resourceManager.getBaseSupplyContent());
    }

    void init(List<String> valuableItems, double sumOfv, double sumOf, Preferences... pres) throws BackingStoreException {
        List<Double> probabilities = new ArrayList<>();
        List<Double> valuableProbabilities = new ArrayList<>();

        for (Preferences pre : pres){
            Arrays.stream(pre.keys()).forEach((key)->{
                normalItems.add(key);
                double tmp = pre.getDouble(key, 0);
                probabilities.add(tmp/sumOf);
                if (valuableItems.contains(key)){
                    valuableProbabilities.add(tmp/sumOfv);
                }
            });
        }

        normal = new AliasMethod(probabilities);
        valuable = new AliasMethod(valuableProbabilities);
        this.valuableItems.addAll(valuableItems);
    }

    Map<String, List<String>> readToMap(Resource resource) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, List<String>>> typeReference =
                new TypeReference<Map<String, List<String>>>(){};

        try(InputStream inputStream = resource.getInputStream()) {
            return mapper.readValue(inputStream, typeReference);
        }
    }


    String getRealItemName(String item, Map<String, List<String>> map) {
        List<String> names = map.get(item);
        return names.get(RandomUtil.nextInt(0, names.size()));
    }

    String next(){
        return normalItems.get(normal.next());
    }

    String nextValuable() {
        return valuableItems.get(valuable.next());
    }

    boolean isValuable(String item) {
        return valuableItems.contains(item);
    }

    public Item nextRubbish(){
        String item = rubbishItems.get(rubbishes.next());
        return getItem(item, realItemMap);
    }

    public Item getItem(String item, Map<String, List<String>> itemMap){
        Item realItem = ItemUtil.item(getRealItemName(item, itemMap));
        if (realItem instanceof Stigma){
            int randPos = RandomUtil.nextInt(0, 3);
            realItem.setRealName(realItem.getRealName()+Stigma.getStigmaPosition(randPos));
            ((Stigma) realItem).setPosition(randPos);
        }else if (realItem instanceof Character){
            if (isDebris(item)){
                ((Character) realItem).setDebris(true);
                realItem.setRealName(realItem.getRealName()+"碎片");
                realItem.setAmount(RandomUtil.nextInt(3, 8));
            }
        }

        return realItem;
    }

    public abstract Item next(Person person);

    abstract boolean isDebris(String item);



}
