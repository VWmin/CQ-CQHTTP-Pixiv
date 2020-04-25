package com.vwmin.coolq.honkar3rd;

import com.google.gson.*;
import com.vwmin.coolq.honkar3rd.entities.*;
import com.vwmin.coolq.honkar3rd.entities.Character;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/25 14:02
 */
public class ItemDeserializer implements JsonDeserializer<Map<String, Item>> {

    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(Map.class, new ItemDeserializer())
                .create();
    }

    private ItemDeserializer(){};

    @Override
    public Map<String, Item> deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<String, Item> map = new HashMap<>();
        JsonArray characters = element.getAsJsonObject().get("character").getAsJsonArray();
        JsonArray stigmas = element.getAsJsonObject().get("stigma").getAsJsonArray();
        JsonArray equipments = element.getAsJsonObject().get("equipment").getAsJsonArray();
        JsonArray materials = element.getAsJsonObject().get("material").getAsJsonArray();

        characters.forEach((character) ->{
            Character characterObj = gson.fromJson(character.toString(), Character.class);
            map.put(characterObj.getRealName(), characterObj);
        });

        stigmas.forEach((stigma) ->{
            Stigma stigmaObj = gson.fromJson(stigma.toString(), Stigma.class);
            map.put(stigmaObj.getRealName(), stigmaObj);
        });

        equipments.forEach((equipment) ->{
            Equipment obg = gson.fromJson(equipment.toString(), Equipment.class);
            map.put(obg.getRealName(), obg);
        });

        materials.forEach((material)->{
            Material obj = gson.fromJson(material.toString(), Material.class);
            map.put(obj.getRealName(), obj);
        });

        return map;
    }

    public static Map<String, Item> deserialize(String json){
        return gson.fromJson(json, Map.class);
    }
}
