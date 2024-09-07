import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class car {

    private String carId;
    private String brand;
    private String Model;
    private double basePricePerDay;
    private boolean isAvailable;

    public car(String carId, String brand,String Model,double basePricePerDay){
        this.carId=carId;
        this.brand=brand;
        this.Model=Model;
        this.basePricePerDay=basePricePerDay;
        this.isAvailable=isAvailable;

    }
    public String getCarId(){
        return carId;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return Model;
    }

    public double calculatePrice(int rentalDays){
        return basePricePerDay*rentalDays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable=false;
    }

    public void returnCar(){
        isAvailable=true;
    }
}

class Customer{
    private String customerId;
    private String Name;

    public Customer(String customerId,String name){
        this.customerId=customerId;
        this.Name=name;
    }

    public String getCustomerId(){
        return customerId;
    }

    public String getname() {
        return Name;
    }
}

class Rental {
    private car car;
    private Customer customer;
    private int days;


    public Rental(car car, Customer customer, int days) {
        this.car=car;
        this.customer=customer;
        this.days=days;

    }

    public car getCar(){
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class carRentalSystem {
    private List<car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public carRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();

    }

    public void addCar(car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;

            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("car returned successfully");
        } else {
            System.out.println("car was not returned");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== car Rental System =====");
            System.out.println("1. Rent in Car");
            System.out.println("2. return a Car");
            System.out.println("3. exit");
            System.out.println("4. Enter your Choice");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume new line.

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.println("Enter your Name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for (car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + "-" + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.println("\nEnter he car Id you want to rent ");
                String carId = scanner.nextLine();

                System.out.println("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); //consume nw line.

                Customer newCustomer = new Customer("cus" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                car selectdCar = null;
                for (car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectdCar = car;
                        break;
                    }
                }
                if (selectdCar != null) {
                    double totalPrice = selectdCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println(" Customer Id: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getname());
                    System.out.println("car: " + selectdCar.getBrand() + " " + selectdCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nconfirm rental(y/N: ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("y")) {
                        rentCar(selectdCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully, ");

                    } else {
                        System.out.println("\nRental canceled");
                    }

                } else {
                    System.out.println("\nInvalid car selection or car not available for rent. ");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a car ==\n");
                System.out.println("Enter the car Id you want to return: ");
                String carId = scanner.nextLine();

                car carToReturn = null;
                for (car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {

                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getname());
                    } else {
                        System.out.println("car was not rented or rental information is missing. ");
                    }
                } else {
                    System.out.println("Invalid car id or car is not rented ");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice, PLease Enter a valid option. ");
            }
        }
        System.out.println("\nThank You for using the car Rental System!");
    }

    public class main {
        public static void main(String[] args) {
            carRentalSystem rentalSystem = new carRentalSystem();
            car car1 = new car("C001", "Toyota", "Camry", 60.0);
            car car2 = new car("C002", "Honda", "Accord", 70.0);
            car car3 = new car("C003", "Mahindra", "Thar", 150.0);
            rentalSystem.addCar(car1);
            rentalSystem.addCar(car2);
            rentalSystem.addCar(car3);


            rentalSystem.menu();
        }
    }
}
