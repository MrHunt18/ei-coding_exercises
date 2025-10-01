interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " using Credit Card"); }
}

class UPIPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " using UPI"); }
}

class PaymentContext {
    private PaymentStrategy strategy;
    public PaymentContext(PaymentStrategy s) { this.strategy = s; }
    public void execute(int amount) { strategy.pay(amount); }
}

public class PaymentDemo {
    public static void main(String[] args) {
        PaymentContext ctx = new PaymentContext(new CreditCardPayment());
        ctx.execute(500);
        ctx = new PaymentContext(new UPIPayment());
        ctx.execute(200);
    }
}
