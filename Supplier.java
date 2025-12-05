package client;

public class Supplier extends BaseClient {

    public static void main(String[] args) throws Exception {
        Supplier s = new Supplier();
        s.connect();
        s.sendIdentity("SUPPLIER");
        System.out.println("Supplier ready. Commands: CHECK, ADD_APPLES n, ADD_ORANGES n, EXIT");

        s.interactionLoop();
    }
}