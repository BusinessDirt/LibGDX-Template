package businessdirt.libgdx.game.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static void logFileLoaded(String path) {
        Util.logEvent("- Loaded File - " + path);
    }

    public static void logEvent(String event) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        Date date = new Date(System.currentTimeMillis());

        String prefix = "> [" + formatter.format(date) + "] ";
        event = event.replaceAll("(\\n+)(\\s*)", "\n" + new String(new char[21]).replace("\0", " "));
        System.out.println(prefix + event);
    }

    public static File getConfigFolder() {
        File configFolder = new File(System.getenv("Appdata"), "businessdirt\\libgdxtemplate");
        if (!configFolder.exists()) {
            if (configFolder.mkdirs()) {
                Util.logEvent("Created config folder: " + configFolder.getAbsolutePath());
            } else {
                Util.logEvent("Couldn't create config folder!");
            }
        }
        return configFolder;
    }
}
