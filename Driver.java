import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class Driver {
    private String name;
    private String carModel;
    private String licensePlate;
    private boolean available;

    public Driver(String name, String carModel, String licensePlate) {
        this.name = name;
        this.carModel = carModel;
        this.licensePlate = licensePlate;
        this.available = true;  // Initially, the driver is available
    }

    public String getName() {
        return name;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayInfo() {
        System.out.println("Driver Name: " + name);
        System.out.println("Car Model: " + carModel);
        System.out.println("License Plate: " + licensePlate);
    }
}

class Rider {
    private String name;
    private String pickupLocation;
    private String dropLocation;

    public Rider(String name, String pickupLocation, String dropLocation) {
        this.name = name;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
    }

    public String getName() {
        return name;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void displayInfo() {
        System.out.println("Rider Name: " + name);
        System.out.println("Pickup Location: " + pickupLocation);
        System.out.println("Drop Location: " + dropLocation);
    }
}

class Ride {
    private Driver driver;
    private Rider rider;
    private boolean rideStarted;

    public Ride(Driver driver, Rider rider) {
        this.driver = driver;
        this.rider = rider;
        this.rideStarted = false;  // Ride starts as not started
    }

    public void startRide() {
        if (!rideStarted) {
            System.out.println("Ride started with the following details:");
            rider.displayInfo();
            driver.displayInfo();  // Display driver info here
            rideStarted = true;
        } else {
            System.out.println("Ride is already started.");
        }
    }

    public void endRide() {
        if (rideStarted) {
            System.out.println("Ride ended. Thank you for riding with us!");
            driver.setAvailable(true);  // Mark driver as available after the ride ends
            rideStarted = false;
        } else {
            System.out.println("Ride has not started yet.");
        }
    }

    public boolean isRideStarted() {
        return rideStarted;
    }
}

class RideShareApp {
    private List<Driver> drivers;
    private List<Rider> riders;

    public RideShareApp() {
        drivers = new ArrayList<>();
        riders = new ArrayList<>();
    }

    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public void addRider(Rider rider) {
        riders.add(rider);
    }

    public Optional<Driver> findAvailableDriver() {
        return drivers.stream().filter(Driver::isAvailable).findFirst();
    }

    public Ride requestRide(Rider rider) {
        Optional<Driver> availableDriver = findAvailableDriver();
        if (availableDriver.isPresent()) {
            Driver driver = availableDriver.get();
            driver.setAvailable(false);  // Mark the driver as unavailable
            Ride ride = new Ride(driver, rider);
            return ride;
        } else {
            System.out.println("No drivers available at the moment. Please try again later.");
            return null;
        }
    }

    public static void main(String[] args) {
        RideShareApp app = new RideShareApp();
        Scanner scanner = new Scanner(System.in);

        // Adding drivers
        app.addDriver(new Driver("Alice", "Toyota Prius", "XYZ 1234"));
        app.addDriver(new Driver("Bob", "Honda Civic", "ABC 5678"));

        System.out.println("Welcome to the RideShare App!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Request a Ride");
            System.out.println("2. Cancel");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            if (choice == 1) {
                System.out.print("Enter your name: ");
                String riderName = scanner.nextLine();
                System.out.print("Enter pickup location: ");
                String pickupLocation = scanner.nextLine();
                System.out.print("Enter drop location: ");
                String dropLocation = scanner.nextLine();

                Rider rider = new Rider(riderName, pickupLocation, dropLocation);
                Ride ride = app.requestRide(rider);

                if (ride != null) {
                    while (true) {
                        System.out.println("\nRide Options:");
                        System.out.println("1. Start Ride");
                        System.out.println("2. End Ride");
                        System.out.println("3. Check Ride Status");
                        System.out.println("4. Cancel");

                        int rideChoice = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline

                        if (rideChoice == 1) {
                            ride.startRide();
                        } else if (rideChoice == 2) {
                            ride.endRide();
                            break;  // Exit the ride options menu after ending the ride
                        } else if (rideChoice == 3) {
                            if (ride.isRideStarted()) {
                                System.out.println("The ride has started.");
                            } else {
                                System.out.println("The ride has not started yet.");
                            }
                        } else if (rideChoice == 4) {
                            System.out.println("Cancelled.");
                            break;  // Exit the ride options menu
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    }
                }
            } else if (choice == 2) {
                System.out.println("Thank you for using our RideSharing App!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
