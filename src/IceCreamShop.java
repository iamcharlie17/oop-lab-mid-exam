public class IceCreamShop {
    public static void main(String[] args) {
        Order order = new Order("Waffle Cone");
        order.addIceCream("Chocolate Fudge", 1);
        order.addIceCream("Mint Chocolate Chip", 2);

        order.addToppings("Sprinkles", 2);
        order.addToppings("Marshmallow", 1);


    }
}
