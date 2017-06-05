package afdw.customport;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Mod(modid = "customport", name = "CustomPort", version = "1.0")
public class CustomPort {
    private static File configFile;
    private static String port = "";

    @Mod.EventHandler
    public void onPreInitialization(FMLPreInitializationEvent event) {
        configFile = new File(
            event.getSuggestedConfigurationFile()
                .getAbsolutePath()
                .replaceAll(".cfg$", ".txt")
        );
        if (configFile.exists()) {
            try {
                port = FileUtils.readFileToString(configFile, StandardCharsets.UTF_8);
            } catch (IOException ignored) {
            }
        }
    }

    @SuppressWarnings("unused")
    public static String getPort() {
        return port;
    }

    @SuppressWarnings("unused")
    public static void setPort(String newValue) {
        port = newValue;
        try {
            FileUtils.writeStringToFile(configFile, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
