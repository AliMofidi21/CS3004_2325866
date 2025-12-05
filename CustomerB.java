package client;

public class CustomerB extends BaseClient {

    public static void main(String[] args) throws Exception {
        CustomerB c = new CustomerB();
        c.connect();
        c.sendIdentity("B");
        System.out.println("Customer B ready. Commands: CHECK, BUY_APPLES n, BUY_ORANGES n, EXIT");

        c.interactionLoop();
    }
}