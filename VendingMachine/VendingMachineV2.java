// A Different Kind of Implementation for Vending Machine..
// You need to get you head Around OOD Design Stuff.


// You will have a State Interface and Rest all States will implement 
// Try to always code to Interfaces or Abstraction..

// Some of the DisAdvantages of the State Design Pattern is Each State with a function needs
// To know which Next state it needs to go..

public interface VendingMachineState {
    public void collectCash(int cash);
    public void dispenseChange(String productCode);
    public void dispenseItem(String productCode);
    public void cancelTransaction();
}

// Even the States Which Implemented the State Interface can Do the State Transition Too..
// Four States Defined.. Ready ,DispenseChange, DispenseItem, TransactionCancelled.
public class DispenseChange implements VendingMachineState {
    private VendingMachine vendingMachine;

    public DispenseChange(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }
    
    @Override 
    public void collectCash(int cash) {
        throw new RunTimeException("State :: DispenseChange, Unable to Collect Cash");
    }

    @Override
    public void dispenseChange(String productCode) {
        int change = vendingMachine.calculateChange(productCode);

        System.out.println("Change :: " + change + " -- Returned");
        this.vendingMachine.setState(new DispenseItem(vendingMachine));
        this.vendingMachine.dispenseItem(productCode);
    }

    @Override
    public void dispenseItem(String productCode) {
        throw new RunTimeException("State :: DispenseChange, Unable to Dispense Item");
    }

    @Override
    public void cancelTransaction() {
        throw new RunTimeException("State :: DispenseChange, Unable to cancelTransaction");
    }
}

public class DispenseItem implements VendingMachineState {
    private VendingMachine vendingMachine;

    public DispenseItem(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }
    
    @Override 
    public void collectCash(int cash) {
        throw new RunTimeException("State :: DispenseItem, Unable to Collect Cash");
    }

    @Override
    public void dispenseChange(String productCode) {
        throw new RunTimeException("State :: DispenseItem, Unable to Dispense Change");
    }

    @Override
    public void dispenseItem(String productCode) {
        this.vendingMachine.removeProduct(productCode);
        System.out.println("Dispensing Item :: " + productCode);
        this.vendingMachine.setState(new Ready(this.vendingMachine));
    }

    @Override
    public void cancelTransaction() {
        throw new RunTimeException("State :: DispenseItem, Unable to cancelTransaction");
    }
}

public class Ready implements VendingMachineState {
    private VendingMachine vendingMachine;

    public Ready(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }
    
    @Override 
    public void collectCash(int cash) {
        this.vendingMachine.addCollectedCash(cash);
    }

    @Override
    public void dispenseChange(String productCode) {
        this.vendingMachine.setState(new DispenseChange(this.vendingMachine));
        this.vendingMachine.dispenseChange(productCode);
    }

    @Override
    public void dispenseItem(String productCode) {
        throw new RunTimeException("State :: Ready, Unable to Dispense Item");
    }

    @Override
    public void cancelTransaction() {
        this.vendingMachine.setState(new TransactionCancelled(this.vendingMachine));
        this.vendingMachine.cancelTransaction();
    }
}

public class TransactionCancelled implements VendingMachineState {
    private VendingMachine vendingMachine;

    public Ready(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }
    
    @Override 
    public void collectCash(int cash) {
        throw new RunTimeException("State :: TransactionCancelled, Unable to collect cash");
    }

    @Override
    public void dispenseChange(String productCode) {
        throw new RunTimeException("State :: TransactionCancelled, Unable to dispense Change");
    }

    @Override
    public void dispenseItem(String productCode) {
        throw new RunTimeException("State :: TransactionCancelled, Unable to dispense Item");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Cash Returned.. :: " + this.vendingMachine.getCollectedCash());
        this.vendingMachine.setCollectedCash(0);
        this.vendingMachine.setState(new Ready(this.vendingMachine);
    }
}

// You can Implement it or can Leave it Just a normal class
public class VendingMachine implements VendingMachineState { 
    private int collectedCash;
    private State state;
    private Map<String, Set<String>> productCodeItemMap;
    private Map<String, Integer> productCodePriceMap;

    @Override
    public void collectCash(int collectedCash) {
        this.collectedCash += collectedCash;
    }

    public VendingMachine setCollectedCash(int collectedCash) {
        this.collectedCash = collectedCash;
        return this;
    }

    public State getState() {
        return state;
    }

    public VendingMachine setState(State state) {
        this.state = state;
        return this;
    }

    public void removeProduct(String productCode) {

    }

    @Override
    public void dispenseChange(String productCode) {
        this.state.dispenseChange(productCode);
    }

    @Override
    public void cancelTransaction() {
        this.state.cancelTransaction();
    }

    public int calculateChange(String productCode) {
        return collectedCash - productCodePriceMap.get(productCode);
    }

    @Override
    public void dispenseItem(String productCode) {
        this.state.dispenseItem(productCode);
    }

    public int getCollectedCash() {
        return collectedCash;
    }
}
