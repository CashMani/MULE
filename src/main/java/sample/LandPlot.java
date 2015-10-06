package sample;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

/**
 * Created by AlexandraLink on 10/1/15.
 */
public class LandPlot {

    private final String type;
    private Player owner;
    private Color color;
    private final Button location;
    private Mule muleOnProp;

    public LandPlot(String type, Player owner, Color color, Button location) {
        this.type = type;
        this.owner = owner;
        this.color = color;
        this.location = location;
        muleOnProp = null;
        System.out.println("Landplot successfully assigned to " + owner.getName());
    }

    public void changeOwner(Player newOwner) {
        owner = newOwner;
        color = newOwner.getColor();
    }

    public void placeMule(Mule mule) {
        if (muleOnProp == null) {
            muleOnProp = mule;
        } else {
            System.out.println("Mule already on property, so your new mule ran away!!");
        }
    }
}
