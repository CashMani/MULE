package sample;

/**
 * Created by mckenzieelliott on 9/18/15.
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * An interface for a Player's inventory.
 */
public interface InventoryInterface {

    /**
     * Increases Player's stocked money amount.
     * @param valueToAdd amount of money to add to a Player's inventory
     */
    void addMoney(int valueToAdd);

    /**
     * Decreases Player's stocked money amount.
     * @param valueToSubtract amount of money to subtract
     *                        from a Player's Inventory
     */
    void subtractMoney(int valueToSubtract);




}
