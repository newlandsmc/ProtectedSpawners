package com.semivanilla.protectedspawners.config;

import com.google.common.base.Throwables;
import com.semivanilla.protectedspawners.ProtectedSpawners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Config {

    private static final String HEADER = "";

    private static File CONFIG_FILE;
    public static File CONFIG_PATH;
    public static YamlConfiguration config;

    static int version;
    static boolean verbose;

    public static void init() {
        CONFIG_PATH = ProtectedSpawners.getInstance().getDataFolder();
        CONFIG_FILE = new File(CONFIG_PATH, "config.yml");
        config = new YamlConfiguration();
        try {
            config.load(CONFIG_FILE);
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load config.yml, please correct your syntax errors", ex);
            Throwables.throwIfUnchecked(ex);
        }
        config.options().header(HEADER);
        config.options().copyDefaults(true);
        verbose = getBoolean("verbose", false);

        version = getInt("config-version", 1);

        readConfig(Config.class, null);
    }

    protected static void log(String s) {
        if (verbose) {
            log(Level.INFO, s);
        }
    }

    static void readConfig(Class<?> clazz, Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                if (method.getParameterTypes().length == 0 && method.getReturnType() == Void.TYPE) {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                    } catch (InvocationTargetException ex) {
                        Throwables.throwIfUnchecked(ex);
                    } catch (Exception ex) {
                        Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method, ex);
                    }
                }
            }
        }
        saveConfig();
    }

    static void saveConfig() {
        try {
            config.save(CONFIG_FILE);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + CONFIG_FILE, ex);
        }
    }

    private static void set(String path, Object val) {
        config.addDefault(path, val);
        config.set(path, val);
    }

    private static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }

    private static <T> List getList(String path, T def) {
        config.addDefault(path, def);
        return config.getList(path, config.getList(path));
    }

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    protected static void log(Level level, String s) {
        Bukkit.getLogger().log(level, s);
    }

    /** ONLY EDIT BELOW THIS LINE **/
    public static boolean preventMinecartChest = false;
    public static String blockBreakMessage = "<red>You can't break this block, to break it do /<command> and break this block";
    public static String commandTimeOutMessage = "<red>Timed out, rerun /<command> to break protected blocks";
    public static String commandBreakMessage = "<red>You can now break protected blocks.";
    public static List<String> lootableMaterials = new ArrayList<>(){{add("CHEST");add("SPAWNER");}};
    public static int commandTimeOut = 300;
    private static void blockSettings() {
        preventMinecartChest = getBoolean("enabled.minecartchest", preventMinecartChest);
        blockBreakMessage = getString("messages.break-block-failed", blockBreakMessage);
        commandBreakMessage = getString("messages.player-command-can-break", commandBreakMessage);
        commandTimeOutMessage = getString("messages.player-command-time-out", commandTimeOutMessage);
        lootableMaterials = getList("materials-enabled", lootableMaterials);
        commandTimeOut = getInt("command-time-out", commandTimeOut);
    }

}
