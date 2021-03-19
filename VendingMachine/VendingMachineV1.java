// Trying to Implement VendingMachine Using State Design Pattern..
// But I Need to look at the Items and Slots too..

// You will have a State Interface and This interface will be implemented by 
// other or different States.

// State is like a behavior... So having defined it as an Interface will be helpful and Any other
// behaviors can also be implemented by main vending machine.
interface VendingMachineState {
    void selectProductAndInsertMoney(int money, String item);

    void dispenseProduct();
}

class NoMoneyState implements VendingMachineState {
    
    @Override
    void selectProductAndInsertMoney(int money, String item) {
        System.out.println(money + "Rs is inserted and product selected :: " + item);
    }

    @Override
    void dispenseProduct() {
        System.out.println("Cannot Dispense the Product in NoMoneyState 
        You Need to select Product and Insert Money");
    }
}

class HasMoneyState implements VendingMachineState {

    @Override
    void selectProductAndInsertMoney(int money, String item) {
        System.out.println("Already Product and Money Inserted wait for Dispense..");
    }

    @Override
    void dispenseProduct() {
        System.out.println("Product is Dispensed..");
    }
}

// So the Main Class will take care of changing the state and all 
// It will have a state value defined.
// The below vending Machine Doesn't really come under as a state but It is the Main Vending Machine..
// It can have slots and dispaching from Slots.. and There will be Inventory Handling Too..

// If you want to make it a little complex then there are good amount of stuff that you can do to make 
// it complex.
class VendingMachine implements VendingMachineState {

    private VendingMachineState state;

    public VendingMachine() {
        //Initial State in NoMoneyState
        state = new NoMoneyState();
    }

    public VendingMachineState getState() {
        return state;
    }

    public void setState(VendingMachineState vendingMachineState) {
        this.state = vendingMachineState;
    }

    // State Transitions are taken care here..
    @Override
    void selectProductAndInsertMoney(int money, String item) {
        state.selectProductAndInsertMoney(money, item);

        if (state instanceof NoMoneyState) {
            this.setState(new HasMoneyState());
            System.out.println("The Internal State of the VendingMachine is changed to :: " + 
                state.getClass().getName());
        }
    }

    // State Transitions are taken care here..
    @Override
    void dispenseProduct() {
        state.dispenseProduct();

        if (state instanceof HasMoneyState) {
            this.setState(new NoMoneyState());
            System.out.println("The Internal State of the VendingMachine is changed to :: " + 
                state.getClass().getName());
        }
    }
}

public class Client {

    public static void main(String[] args) {
        // This will be the client that will be using the Vending Machine..
    }
}
