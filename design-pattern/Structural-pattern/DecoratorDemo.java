interface Coffee { String getDescription(); int getCost(); }

class BasicCoffee implements Coffee {
    public String getDescription() { return "Coffee"; }
    public int getCost() { return 50; }
}

class MilkDecorator implements Coffee {
    private Coffee coffee;
    public MilkDecorator(Coffee c) { this.coffee = c; }
    public String getDescription() { return coffee.getDescription() + ", Milk"; }
    public int getCost() { return coffee.getCost() + 10; }
}

public class DecoratorDemo {
    public static void main(String[] args) {
        Coffee c = new BasicCoffee();
        System.out.println(c.getDescription() + " = " + c.getCost());

        c = new MilkDecorator(c);
        System.out.println(c.getDescription() + " = " + c.getCost());
    }
}
