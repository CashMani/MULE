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
    private int energyProd;
    private int foodProd;
    private int oreProd;

    public LandPlot(String type, Player owner, Color color, Button location) {
        this.type = type;
        this.owner = owner;
        this.color = color;
        this.location = location;
        setProductionRates();
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

    public boolean hasMule() {
        return muleOnProp != null;
    }

    public void setMule(Mule m) {
        this.muleOnProp = m;
    }

    public Button getButton() {
        return this.location;
    }

    public void setProductionRates() {
        if (type.equalsIgnoreCase("River")) {
            energyProd = 2;
            foodProd = 4;
            oreProd = 0;
        } else if (type.equalsIgnoreCase("Plain")) {
            energyProd = 3;
            foodProd = 2;
            oreProd = 1;
        } else if (type.equalsIgnoreCase("1 Mountain")) {
            energyProd = 1;
            foodProd = 1;
            oreProd = 2;
        } else if (type.equalsIgnoreCase("2 Mountain")) {
            energyProd = 1;
            foodProd = 1;
            oreProd = 3;
        } else if (type.equalsIgnoreCase("3 Mountain")) {
            energyProd = 1;
            foodProd = 1;
            oreProd = 4;
        } else {
            energyProd = 0;
            foodProd = 0;
            oreProd = 0;
            System.out.println("Error in decided land type for production rates.");
        }
    }


}
