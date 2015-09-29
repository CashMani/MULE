package sample;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 * Created by mani on 9/24/15.
 */
public class Verifier {
    public static String verifyName(String input) throws IllegalArgumentException {
        String name = "";
        if (input == null || input == "" || input.isEmpty()) {
            throw new IllegalArgumentException("Did not enter a proper name for a Player.");
        } else
            return input;
    }

    public static Main.Race verifyRace(ToggleGroup g) {
        Toggle toggled = g.getSelectedToggle();
        ObservableList<Toggle> toggles = g.getToggles();
        for (Toggle t : toggles) {
            if (t.isSelected()) {
                if (t.toString().contains("Bonzoid")) return Main.Race.BONZOID;
                else if (t.toString().contains("Flapper")) return Main.Race.FLAPPER;
                else if (t.toString().contains("Gollumer")) return Main.Race.GOLLUMER;
                else if (t.toString().contains("Humanoid")) return Main.Race.HUMANOID;
                else if (t.toString().contains("Leggite")) return Main.Race.LEGGITE;
                else if (t.toString().contains("Mechtron")) return Main.Race.MECHTRON;
                else if (t.toString().contains("Packer")) return Main.Race.PACKER;
                else if (t.toString().contains("Spheroid")) return Main.Race.SPHEROID;
            }
        }
        return Main.Race.BONZOID;
    }
}
