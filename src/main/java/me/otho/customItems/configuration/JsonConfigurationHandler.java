package me.otho.customItems.configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.apache.commons.lang3.ArrayUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import me.otho.customItems.registry.Registry;
import me.otho.customItems.utility.LogHelper;

public class JsonConfigurationHandler {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping()
        .create();
    public static JsonSchema data;
    public static JsonSchema allData;

    public static void init(String folderPath) {
        File folder = new File(folderPath);
        allData = new JsonSchema();

        if (folder.exists()) {
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                if (listOfFiles.length > 0) {
                    LogHelper.info("Reading config files: ", 0);
                    JsonReader reader;

                    int i;

                    for (i = 0; i < listOfFiles.length; i++) {
                        File file = listOfFiles[i];

                        if (file.isFile() && file.getName()
                            .endsWith(".json")) {
                            try {
                                reader = new JsonReader(
                                    new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
                                LogHelper.info("Reading json file: " + file.getName(), 1);
                                JsonSchema data = GSON.fromJson(reader, JsonSchema.class);
                                mergeGson(data, allData);
                            } catch (Exception e) {
                                LogHelper.warn("Errored from json: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                    LogHelper.finishSection();
                    Registry.register(allData);
                }
            }
        } else {
            folder.mkdir();
        }
    }

    public static void post_init() {
        Registry.change(allData);
    }

    private static void mergeGson(JsonSchema data, JsonSchema mergeTo) {
        mergeTo.blocks = ArrayUtils.addAll(data.blocks, mergeTo.blocks);
        mergeTo.chests = ArrayUtils.addAll(data.chests, mergeTo.chests);
        mergeTo.items = ArrayUtils.addAll(data.items, mergeTo.items);
        mergeTo.foods = ArrayUtils.addAll(data.foods, mergeTo.foods);
        mergeTo.pickaxes = ArrayUtils.addAll(data.pickaxes, mergeTo.pickaxes);
        mergeTo.axes = ArrayUtils.addAll(data.axes, mergeTo.axes);
        mergeTo.shovels = ArrayUtils.addAll(data.shovels, mergeTo.shovels);
        mergeTo.hoes = ArrayUtils.addAll(data.hoes, mergeTo.hoes);
        mergeTo.swords = ArrayUtils.addAll(data.swords, mergeTo.swords);
        mergeTo.helmets = ArrayUtils.addAll(data.helmets, mergeTo.helmets);
        mergeTo.chestplates = ArrayUtils.addAll(data.chestplates, mergeTo.chestplates);
        mergeTo.leggings = ArrayUtils.addAll(data.leggings, mergeTo.leggings);
        mergeTo.boots = ArrayUtils.addAll(data.boots, mergeTo.boots);
        mergeTo.fluids = ArrayUtils.addAll(data.fluids, mergeTo.fluids);
        mergeTo.creativeTabs = ArrayUtils.addAll(data.creativeTabs, mergeTo.creativeTabs);
        mergeTo.crops = ArrayUtils.addAll(data.crops, mergeTo.crops);

        mergeTo.changeBlocks = ArrayUtils.addAll(data.changeBlocks, mergeTo.changeBlocks);
        mergeTo.changeItems = ArrayUtils.addAll(data.changeItems, mergeTo.changeItems);
        mergeTo.changeFoods = ArrayUtils.addAll(data.changeFoods, mergeTo.changeFoods);

        mergeTo.oreGen = ArrayUtils.addAll(data.oreGen, mergeTo.oreGen);

        mergeTo.entitiesDrop = ArrayUtils.addAll(data.entitiesDrop, mergeTo.entitiesDrop);
        mergeTo.blocksDrop = ArrayUtils.addAll(data.blocksDrop, mergeTo.blocksDrop);
    }
}
