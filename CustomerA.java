package client;

public class CustomerA extends BaseClient {

    public static void main(String[] args) throws Exception {
        CustomerA c = new CustomerA();
        c.connect();
        c.sendIdentity("A");
        System.out.println("Customer A ready.");
        System.out.println("Allowed commands: CHECK, BUY_APPLES n, BUY_ORANGES n, EXIT");

        c.interactionLoop();
    }
}