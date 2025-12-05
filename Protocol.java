package common;

public class Protocol {

    // Commands customers/supplier send to the server
    public static final String CHECK_STOCK = "CHECK";
    public static final String BUY_APPLES = "BUY_APPLES";
    public static final String BUY_ORANGES = "BUY_ORANGES";
    public static final String ADD_APPLES = "ADD_APPLES";
    public static final String ADD_ORANGES = "ADD_ORANGES";

    // Response formatting
    public static String stockMessage(int apples, int oranges) {
    	return "Current stock: Apples = " + apples + ", Oranges = " + oranges;
    }
}