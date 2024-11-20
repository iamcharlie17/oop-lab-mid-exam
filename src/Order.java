import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static final double TAX_RATE = 0.08;
    private static final double WAFFLE_CONE_PRICE = 5.00;
    private static final List<IceCream> AVAILABLE_ICE_CREAMS = new ArrayList<>();
    private static final List<Topping> AVAILABLE_TOPPINGS = new ArrayList<>();

    static {
        AVAILABLE_ICE_CREAMS.add(new IceCream("Mint Chocolate Chip", 2.80));
        AVAILABLE_ICE_CREAMS.add(new IceCream("Chocolate Fudge", 3.00));
        AVAILABLE_ICE_CREAMS.add(new IceCream("Strawberry Swirl", 2.75));
        AVAILABLE_ICE_CREAMS.add(new IceCream("Pistachio Delight", 3.25));

        AVAILABLE_TOPPINGS.add(new Topping("Sprinkles", 0.30));
        AVAILABLE_TOPPINGS.add(new Topping("Marshmallow", 0.70));
        AVAILABLE_TOPPINGS.add(new Topping("Crushed Oreo", 0.85));
        AVAILABLE_TOPPINGS.add(new Topping("Fresh Strawberries", 1.00));
        AVAILABLE_TOPPINGS.add(new Topping("Chocolate Chips", 0.50));
    }

    private final String servingType;
    private final List<String> orderDetails;
    private double subtotal;

    public Order(String servingType){
        if (!servingType.equalsIgnoreCase("Waffle Cone")
                && !servingType.equalsIgnoreCase("Paper Cup")) {
            throw new IllegalArgumentException("Invalid serving type! Choose 'Waffle Cone' or 'Paper Cup'.");
        }
        this.servingType = servingType;
        this.orderDetails = new ArrayList<>();
        this.subtotal = servingType.equalsIgnoreCase("Waffle Cone") ?
                WAFFLE_CONE_PRICE : 0;
    }

    public void addIceCream(String flavor, int scoops) {
        IceCream iceCream = AVAILABLE_ICE_CREAMS.stream()
                .filter(i -> i.getName().equalsIgnoreCase(flavor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ice cream flavor!"));

        double cost = iceCream.getPrice() * scoops;
        orderDetails.add(String.format("%s - %d scoop(s): $%.2f", iceCream.getName(), scoops, cost));
        subtotal += cost;
    }

    public void addToppings(String toppingName, int quantity){
        Topping topping = AVAILABLE_TOPPINGS.stream()
                .filter(t -> t.getName().equalsIgnoreCase(toppingName))
                .findFirst().orElseThrow(()-> new IllegalArgumentException("Invalid topping name!"));
        double cost = topping.getPrice() * quantity;
        orderDetails.add(String.format("%s - %d time(s): $%.2f", topping.getName(), quantity , cost));
        subtotal += cost;
    }

    public void generateInvoice(){
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        StringBuilder invoice = new StringBuilder("Ice Cream Shop Invoice\n");
        for (String detail : orderDetails) {
            invoice.append(detail).append("\n");
        }

        invoice.append(String.format("Subtotal: $%.2f\n", subtotal))
                .append(String.format("Tax: $%.2f\n", tax))
                .append(String.format("Total Amount Due: $%.2f", total));


        System.out.println("===========================");
        System.out.println(invoice);
        System.out.println("===========================");

        try(FileWriter fileWriter = new FileWriter("invoice.txt")){
            fileWriter.write(invoice.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
