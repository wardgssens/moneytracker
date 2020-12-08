package factory;

public class FactoryProvider {
    public static AbstractFactory getFactory(String factoryType) {
        if (factoryType.equalsIgnoreCase("ticket")) {
            return new TicketFactory();
        } else if (factoryType.equalsIgnoreCase("person")) {
            return new PersonFactory();
        } else {
            return null;
        }
    }
}
