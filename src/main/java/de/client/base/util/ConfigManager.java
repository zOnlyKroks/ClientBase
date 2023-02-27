package de.client.base.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.client.base.ClientBase;
import de.client.base.keybinding.KeybindingManager;
import de.client.base.module.Module;
import de.client.base.config.SettingBase;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class ConfigManager {

    static final  List<Module> toBeEnabled = new ArrayList<>();
    static final  File         CONFIG_FILE;
    public static boolean      loaded      = false;
    public static boolean      enabled     = false;

    static {
        CONFIG_FILE = new File(ClientBase.BASE, "config." + ClientBase.CLIENT_ID);
    }

    /**
     * Encrypts a byte array with a key
     *
     * @param in  The byte array to encrypt
     * @param key The key to use
     * @return The encrypted byte array
     * @throws Exception If something goes wrong
     */
    static byte[] encrypt(byte[] in, String key) throws Exception {
        byte[] k = key.getBytes(StandardCharsets.UTF_8);
        MessageDigest msgd = MessageDigest.getInstance("SHA-1");
        k = msgd.digest(k);
        k = Arrays.copyOf(k, 16);
        SecretKeySpec sks = new SecretKeySpec(k, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        return cipher.doFinal(in);
    }

    /**
     * Compresses a byte array using GZIP Deflate
     *
     * @param in The input
     * @return The compressed output
     * @throws Exception If something goes wrong
     */
    public static byte[] compress(byte[] in) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (DeflaterOutputStream dos = new DeflaterOutputStream(os)) {
            dos.write(in);
        }
        return os.toByteArray();
    }

    /**
     * Decompressed a byte array using GZIP Inflate
     *
     * @param in The compressed data
     * @return The decompressed date
     * @throws Exception If something goes wrong
     */
    public static byte[] decompress(byte[] in) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (InflaterOutputStream ios = new InflaterOutputStream(os)) {
            ios.write(in);
        }

        return os.toByteArray();
    }

    /**
     * Decrypts a byte array with a key
     *
     * @param in  The byte array to decrypt
     * @param key The key used to encrypt the byte array
     * @return The decrypted byte array
     * @throws Exception If something goes wrong
     */
    static byte[] decrypt(byte[] in, String key) throws Exception {
        byte[] k = key.getBytes(StandardCharsets.UTF_8);
        MessageDigest msgd = MessageDigest.getInstance("SHA-1");
        k = msgd.digest(k);
        k = Arrays.copyOf(k, 16);
        SecretKeySpec sks = new SecretKeySpec(k, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        return cipher.doFinal(in);
    }

    /**
     * Saves the current state of the client to the file
     */
    public static void saveState() {
        if (!loaded || !enabled) {
            System.out.println("Not saving config because we didnt load it yet");
            return;
        }
        System.out.println("Saving state");
        JsonObject base = new JsonObject();
        JsonArray enabled = new JsonArray();
        JsonArray config = new JsonArray();
        for (Module module : ClientBase.getModuleManager().getModules()) {
            if (module.isEnabled()) {
                enabled.add(module.getName());
            }
            JsonObject currentConfig = new JsonObject();
            currentConfig.addProperty("name", module.getName());
            JsonArray pairs = new JsonArray();
            for (SettingBase<?> dynamicValue : module.config.getSettings()) {
                JsonObject jesus = new JsonObject();
                jesus.addProperty("key", dynamicValue.getName());
                jesus.addProperty("value", dynamicValue.getValue() + "");
                pairs.add(jesus);
            }
            currentConfig.add("pairs", pairs);
            config.add(currentConfig);
        }
        base.add("enabled", enabled);
        base.add("config", config);
        try {
            FileUtils.writeByteArrayToFile(CONFIG_FILE, encrypt(compress(base.toString().getBytes(StandardCharsets.UTF_8)), "amogus"));
            //            FileUtils.write(CONFIG_FILE, encrypt(base.toString(), "amogus"), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save config!");
        }
    }

    /**
     * Loads the state we saved earlier from the file
     */
    public static void loadState() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            if (!CONFIG_FILE.isFile()) {
                //noinspection ResultOfMethodCallIgnored
                CONFIG_FILE.delete();
            }
            if (!CONFIG_FILE.exists()) {
                return;
            }
            byte[] retrv = FileUtils.readFileToByteArray(CONFIG_FILE);
            //            String retrv = FileUtils.readFileToString(CONFIG_FILE, StandardCharsets.UTF_8);
            String decr = new String(decompress(decrypt(retrv, "amogus")));
            JsonObject config = new JsonParser().parse(decr).getAsJsonObject();
            if (config.has("config") && config.get("config").isJsonArray()) {
                JsonArray configArray = config.get("config").getAsJsonArray();
                for (JsonElement jsonElement : configArray) {
                    if (jsonElement.isJsonObject()) {
                        JsonObject jobj = jsonElement.getAsJsonObject();
                        String name = jobj.get("name").getAsString();
                        Module j = ClientBase.getModuleManager().getModuleByName(name);
                        if (j == null) {
                            continue;
                        }
                        if (jobj.has("pairs") && jobj.get("pairs").isJsonArray()) {
                            JsonArray pairs = jobj.get("pairs").getAsJsonArray();
                            for (JsonElement pair : pairs) {
                                JsonObject jo = pair.getAsJsonObject();
                                String key = jo.get("key").getAsString();
                                String value = jo.get("value").getAsString();
                                SettingBase<?> val = j.config.get(key);
                                if (val != null) {
                                    val.accept(value);
                                }
                            }
                        }
                    }
                }
            }

            if (config.has("enabled") && config.get("enabled").isJsonArray()) {
                for (JsonElement enabled : config.get("enabled").getAsJsonArray()) {
                    String name = enabled.getAsString();
                    Module m = ClientBase.getModuleManager().getModuleByName(name);
                    if (m != null) {
                        toBeEnabled.add(m);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            KeybindingManager.reload();
        }
    }

    /**
     * Enables all modules to be enabled, when we are in game
     */
    public static void enableModules() {
        if (enabled) {
            return;
        }
        enabled = true;
        for (Module module : toBeEnabled) {
            module.setEnabled(true);
        }
    }

}
