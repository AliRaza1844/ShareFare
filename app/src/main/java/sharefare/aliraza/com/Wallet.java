package sharefare.aliraza.com;

/**
 * Created by Hina Ghafoor on 12/17/2017.
 */

public class Wallet {
    private static final Wallet wallet = new Wallet();
    private float balance = 0;
    private String paymentMethod = null;

    public Wallet(){}

    public float getBalance() {
        return balance;
    }

    public static Wallet getWallet() {
        return wallet;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
