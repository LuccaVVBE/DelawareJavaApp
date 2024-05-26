package repository;

import domain.*;
import enums.BoxType;
import enums.EmployeeRole;
import util.PasswordUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DatabaseSeeder {

    public static void seedDatabase() {

        GenericDao<Address> addressDao = new GenericDaoJpa<>(Address.class);
        GenericDao<Carrier> carrierDao = new GenericDaoJpa<>(Carrier.class);
        GenericDao<Employee> employeeDao = new GenericDaoJpa<>(Employee.class);
        GenericDao<Company> companyDao = new GenericDaoJpa<>(Company.class);
        GenericDao<Notification> notificationDao = new GenericDaoJpa<>(Notification.class);
        GenericDao<Order> orderDao = new GenericDaoJpa<>(Order.class);
        GenericDao<OrderItem> orderItemDao = new GenericDaoJpa<>(OrderItem.class);
        GenericDao<Product> productDao = new GenericDaoJpa<>(Product.class);
        GenericDao<ProductCategory> productCategoryDao = new GenericDaoJpa<>(ProductCategory.class);
        GenericDao<ProductDescription> productDescriptionDao = new GenericDaoJpa<>(ProductDescription.class);
        GenericDao<ProductPrice> productPriceDao = new GenericDaoJpa<>(ProductPrice.class);
        GenericDao<Box> boxDao = new GenericDaoJpa<>(Box.class);
//        GenericDao<TrackAndTrace> trackAndTraceDao = new GenericDaoJpa<>(TrackAndTrace.class);

        Address address1 = new Address("Hier", "Valentijn Vaerwyckweg", "1", "Ghent", "9000", "Belgium");

        if (addressDao.findAll().stream().anyMatch(address -> address.equals(address1))) {
            System.out.println("Database already seeded");
            return;
        }

        Address address2 = new Address("Overginder", "Valentijn Vaerwyckweg", "1", "Ghent", "9000", "Belgium");
        Address address3 = new Address("Schoonmeersen", "Valentijn Vaerwyckweg", "1", "Ghent", "9000", "Belgium");
        Address address4 = new Address("Tech Park", "Innovation Street", "42", "New York", "10001", "USA");
        Address address5 = new Address("Eco Square", "Green Avenue", "7", "London", "SW1A 1AA", "UK");
        Address address6 = new Address("Fitness Plaza", "Healthy Way", "16", "Sydney", "2000", "Australia");
        Address address7 = new Address("Gourmet Corner", "Delicious Lane", "55", "Paris", "75001", "France");
        Address address8 = new Address("Fashion Hub", "Style Street", "88", "Milan", "20121", "Italy");
        Address address9 = new Address("Travel Center", "Adventure Road", "101", "Barcelona", "08001", "Spain");

        Company techGurus = new Company("Tech Gurus", "/images/tech-gurus.png", "02 201 23 45", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Company ecoWarriors = new Company("Eco Warriors", "/images/eco-warriors.png", "02 341 21 65", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Company fitnessRevolution = new Company("Fitness Revolution", "/images/fitness-revolution.png", "02 521 83 85", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Company gourmetDelights = new Company("Gourmet Delights", "/images/gourmet-delights.png", "02 441 53 41", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Company fashionForward = new Company("Fashion Forward", "/images/fashion-forward.png", "02 299 20 49", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Company travelExplorers = new Company("Travel Explorers", "/images/travel-explorers.png", "02 215 45 11", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());

        Carrier carrier1 = new Carrier("info@bpost.be", "Bpost", "02 201 23 45", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Bpost_logo.svg/2560px-Bpost_logo.svg.png", true, 15, true, "0105", null, techGurus);
        Carrier carrier2 = new Carrier("info@ups.com", "UPS", "1-800-742-5877", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/United_Parcel_Service_logo_2014.svg/1024px-United_Parcel_Service_logo_2014.svg.png", true, 20, true, "0106", null, techGurus);
        Carrier carrier3 = new Carrier("info@fedex.com", "FedEx", "1-800-463-3339", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/FedEx_Corporation_-_2016_Logo.svg/2560px-FedEx_Corporation_-_2016_Logo.svg.png", true, 25, true, "0107", null, techGurus);
        Carrier carrier4 = new Carrier("info@dhl.com", "DHL", "1-800-225-5345", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/DHL_Logo.svg/2560px-DHL_Logo.svg.png", true, 30, true, "0108", null, techGurus);
        Carrier carrier5 = new Carrier("info@usps.com", "USPS", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0109", null, techGurus);
        Carrier carrier6 = new Carrier("info@postnl.com", "PostNL", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0109", null, techGurus);

        Carrier carrier7 = new Carrier("info@bpost.be", "Bpost", "02 201 23 45", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Bpost_logo.svg/2560px-Bpost_logo.svg.png", true, 15, true, "0205", null, ecoWarriors);
        Carrier carrier8 = new Carrier("info@ups.com", "UPS", "1-800-742-5877", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/United_Parcel_Service_logo_2014.svg/1024px-United_Parcel_Service_logo_2014.svg.png", true, 20, true, "0206", null, ecoWarriors);
        Carrier carrier9 = new Carrier("info@fedex.com", "FedEx", "1-800-463-3339", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/FedEx_Corporation_-_2016_Logo.svg/2560px-FedEx_Corporation_-_2016_Logo.svg.png", true, 25, true, "0207", null, ecoWarriors);
        Carrier carrier10 = new Carrier("info@dhl.com", "DHL", "1-800-225-5345", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/DHL_Logo.svg/2560px-DHL_Logo.svg.png", true, 30, true, "0208", null, ecoWarriors);
        Carrier carrier11 = new Carrier("info@usps.com", "USPS", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0209", null, ecoWarriors);
        Carrier carrier12 = new Carrier("info@postnl.com", "PostNL", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0210", null, ecoWarriors);

        Carrier carrier13 = new Carrier("info@bpost.be", "Bpost", "02 201 23 45", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Bpost_logo.svg/2560px-Bpost_logo.svg.png", true, 15, true, "0305", null, fitnessRevolution);
        Carrier carrier14 = new Carrier("info@ups.com", "UPS", "1-800-742-5877", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/United_Parcel_Service_logo_2014.svg/1024px-United_Parcel_Service_logo_2014.svg.png", true, 20, true, "0306", null, fitnessRevolution);
        Carrier carrier15 = new Carrier("info@fedex.com", "FedEx", "1-800-463-3339", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/FedEx_Corporation_-_2016_Logo.svg/2560px-FedEx_Corporation_-_2016_Logo.svg.png", true, 25, true, "0307", null, fitnessRevolution);
        Carrier carrier16 = new Carrier("info@dhl.com", "DHL", "1-800-225-5345", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/DHL_Logo.svg/2560px-DHL_Logo.svg.png", true, 30, true, "0308", null, fitnessRevolution);
        Carrier carrier17 = new Carrier("info@usps.com", "USPS", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0309", null, fitnessRevolution);
        Carrier carrier18 = new Carrier("info@postnl.com", "PostNL", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0310", null, fitnessRevolution);

        Carrier carrier19 = new Carrier("info@bpost.be", "Bpost", "02 201 23 45", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Bpost_logo.svg/2560px-Bpost_logo.svg.png", true, 15, true, "0405", null, gourmetDelights);
        Carrier carrier20 = new Carrier("info@ups.com", "UPS", "1-800-742-5877", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/United_Parcel_Service_logo_2014.svg/1024px-United_Parcel_Service_logo_2014.svg.png", true, 20, true, "0406", null, gourmetDelights);
        Carrier carrier21 = new Carrier("info@fedex.com", "FedEx", "1-800-463-3339", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/FedEx_Corporation_-_2016_Logo.svg/2560px-FedEx_Corporation_-_2016_Logo.svg.png", true, 25, true, "0407", null, gourmetDelights);
        Carrier carrier22 = new Carrier("info@dhl.com", "DHL", "1-800-225-5345", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/DHL_Logo.svg/2560px-DHL_Logo.svg.png", true, 30, true, "0408", null, gourmetDelights);
        Carrier carrier23 = new Carrier("info@usps.com", "USPS", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0409", null, gourmetDelights);
        Carrier carrier24 = new Carrier("info@postnl.com", "PostNL", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0410", null, gourmetDelights);

        Carrier carrier25 = new Carrier("info@bpost.be", "Bpost", "02 201 23 45", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Bpost_logo.svg/2560px-Bpost_logo.svg.png", true, 15, true, "0505", null, fashionForward);
        Carrier carrier26 = new Carrier("info@ups.com", "UPS", "1-800-742-5877", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/United_Parcel_Service_logo_2014.svg/1024px-United_Parcel_Service_logo_2014.svg.png", true, 20, true, "0506", null, fashionForward);
        Carrier carrier27 = new Carrier("info@fedex.com", "FedEx", "1-800-463-3339", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/FedEx_Corporation_-_2016_Logo.svg/2560px-FedEx_Corporation_-_2016_Logo.svg.png", true, 25, true, "0507", null, fashionForward);
        Carrier carrier28 = new Carrier("info@dhl.com", "DHL", "1-800-225-5345", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/DHL_Logo.svg/2560px-DHL_Logo.svg.png", true, 30, true, "0508", null, fashionForward);
        Carrier carrier29 = new Carrier("info@usps.com", "USPS", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0509", null, fashionForward);
        Carrier carrier30 = new Carrier("info@postnl.com", "PostNL", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0510", null, fashionForward);

        Carrier carrier31 = new Carrier("info@bpost.be", "Bpost", "02 201 23 45", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Bpost_logo.svg/2560px-Bpost_logo.svg.png", true, 15, true, "0605", null, travelExplorers);
        Carrier carrier32 = new Carrier("info@ups.com", "UPS", "1-800-742-5877", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/United_Parcel_Service_logo_2014.svg/1024px-United_Parcel_Service_logo_2014.svg.png", true, 20, true, "0606", null, travelExplorers);
        Carrier carrier33 = new Carrier("info@fedex.com", "FedEx", "1-800-463-3339", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/FedEx_Corporation_-_2016_Logo.svg/2560px-FedEx_Corporation_-_2016_Logo.svg.png", true, 25, true, "0607", null, travelExplorers);
        Carrier carrier34 = new Carrier("info@dhl.com", "DHL", "1-800-225-5345", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/DHL_Logo.svg/2560px-DHL_Logo.svg.png", true, 30, true, "0608", null, travelExplorers);
        Carrier carrier35 = new Carrier("info@usps.com", "USPS", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0609", null, travelExplorers);
        Carrier carrier36 = new Carrier("info@postnl.com", "PostNL", "1-800-275-8777", "https://upload.wikimedia.org/wikipedia/en/thumb/3/37/United_States_Postal_Service_Logo.svg/2560px-United_States_Postal_Service_Logo.svg.png", true, 18, true, "0610", null, travelExplorers);


        techGurus.setCarriers(Set.of(carrier1, carrier2, carrier3, carrier4, carrier5, carrier6));
        ecoWarriors.setCarriers(Set.of(carrier7, carrier8, carrier9, carrier10, carrier11, carrier12));
        fitnessRevolution.setCarriers(Set.of(carrier13, carrier14, carrier15, carrier16, carrier17, carrier18));
        gourmetDelights.setCarriers(Set.of(carrier19, carrier20, carrier21, carrier22, carrier23, carrier24));
        fashionForward.setCarriers(Set.of(carrier25, carrier26, carrier27, carrier28, carrier29, carrier30));
        travelExplorers.setCarriers(Set.of(carrier31, carrier32, carrier33, carrier34, carrier35, carrier36));

        // Employees
        Employee employee1 = new Employee("Jochen", "Van Severen", EmployeeRole.ADMIN, PasswordUtil.hash("jochen_pass"), "jochenvanseveren@outlook.com", techGurus);
        Employee employee2 = new Employee("Nataliia", "Chapaieva", EmployeeRole.ADMIN, PasswordUtil.hash("nataliia_pass"), "nataliia.chapaieva@student.hogent.be", ecoWarriors);
        Employee employee3 = new Employee("Lucca", "Van Veerdeghem", EmployeeRole.ADMIN, PasswordUtil.hash("lucca_pass"), "lucca.van.veerdeghem@telenet.be", fitnessRevolution);
        Employee employee4 = new Employee("Fatih", "Salman", EmployeeRole.ADMIN, PasswordUtil.hash("123456"), "fatih.salman@student.hogent.be", gourmetDelights);
        Employee employee5 = new Employee("Fedor", "Danilov", EmployeeRole.ADMIN, PasswordUtil.hash("fedor_pass"), "random@gmail.com", fashionForward);
        Employee employee6 = new Employee("Test", "User", EmployeeRole.WAREHOUSE_EMPLOYEE, PasswordUtil.hash("test_pass"), "testuser@test.com", travelExplorers);
        Employee employee7 = new Employee("Jan", "Janssens", EmployeeRole.ADMIN, PasswordUtil.hash("jan_pass"), "jan.janssens@gmail.com", techGurus);
        Employee employee8 = new Employee("Piet", "Pieters", EmployeeRole.WAREHOUSE_EMPLOYEE, PasswordUtil.hash("piet_pass"), "piet.pieters@outlook.com", ecoWarriors);
        Employee employee9 = new Employee("Tom", "Thompson", EmployeeRole.ADMIN, PasswordUtil.hash("tom_pass"), "tom.thompson@gmail.com", fitnessRevolution);
        Employee employee10 = new Employee("Emma", "Emmerson", EmployeeRole.ADMIN, PasswordUtil.hash("emma_pass"), "emma.emmerson@student.hogent.be", gourmetDelights);
        Employee employee11 = new Employee("Sophie", "Smith", EmployeeRole.ADMIN, PasswordUtil.hash("sophie_pass"), "sophie.smith@test.com", fashionForward);
        Employee employee12 = new Employee("John", "Doe", EmployeeRole.WAREHOUSE_EMPLOYEE, PasswordUtil.hash("john_pass"), "johndoe@outlook.com", travelExplorers);

        // Notifications
        Notification notification1 = new Notification("Status Update", "Your order with order id 1 has been processed", LocalDateTime.parse("2023-05-02T19:58:43.940"), null, techGurus);
        Notification notification2 = new Notification("Status Update", "Your order with order id 1 has been shipped", LocalDateTime.parse("2023-05-02T19:58:43.946"), null, techGurus);
        Notification notification3 = new Notification("Status Update", "Your order with order id 2 has been processed", LocalDateTime.parse("2023-05-02T19:58:44.042"), null, ecoWarriors);
        Notification notification4 = new Notification("Status Update", "Your order with order id 2 has been shipped", LocalDateTime.parse("2023-05-02T19:58:44.046"), null, ecoWarriors);
        Notification notification5 = new Notification("Status Update", "Your order with order id 3 has been processed", LocalDateTime.parse("2023-05-02T19:58:44.098"), null, fitnessRevolution);
        Notification notification6 = new Notification("Status Update", "Your order with order id 3 has been shipped", LocalDateTime.parse("2023-05-02T19:58:44.103"), null, fitnessRevolution);
        Notification notification7 = new Notification("Status Update", "Your order with order id 4 has been processed", LocalDateTime.parse("2023-05-02T19:58:44.148"), null, gourmetDelights);
        Notification notification8 = new Notification("Status Update", "Your order with order id 4 has been shipped", LocalDateTime.parse("2023-05-02T19:58:44.153"), null, gourmetDelights);
        Notification notification9 = new Notification("Status Update", "Your order with order id 5 has been processed", LocalDateTime.parse("2023-05-02T19:58:44.175"), null, fashionForward);
        Notification notification10 = new Notification("Status Update", "Your order with order id 5 has been shipped", LocalDateTime.parse("2023-05-02T19:58:44.180"), null, fashionForward);

        // Boxes for techGurus
        Box box1 = new Box("TechGurus Regular Cardboard", 50, 30, 20, BoxType.STANDARD, true, 4.99, techGurus);
        Box box2 = new Box("TechGurus Pallet", 75, 175, 75, BoxType.CUSTOM, true, 19.99, techGurus);
        Box box3 = new Box("TechGurus Phone Box", 9, 8, 14.5, BoxType.STANDARD, true, 5.99, techGurus);
        Box box4 = new Box("TechGurus Laptop Box", 44.2, 6.6, 34.2, BoxType.STANDARD, true, 5.99, techGurus);
        Box box5 = new Box("TechGurus Large Box", 75, 150, 75, BoxType.CUSTOM, false, 6.99, techGurus);

// Boxes for ecoWarriors
        Box box6 = new Box("EcoWarriors Small Box", 25, 25, 25, BoxType.CUSTOM, false, 3.99, ecoWarriors);
        Box box7 = new Box("EcoWarriors Medium Box", 50, 50, 50, BoxType.CUSTOM, false, 5.99, ecoWarriors);
        Box box8 = new Box("EcoWarriors Large Box", 75, 75, 75, BoxType.CUSTOM, false, 7.99, ecoWarriors);
        Box box9 = new Box("EcoWarriors XL Box", 100, 100, 100, BoxType.CUSTOM, false, 9.99, ecoWarriors);
        Box box10 = new Box("EcoWarriors XXL Box", 125, 125, 125, BoxType.CUSTOM, false, 11.99, ecoWarriors);

// Boxes for fitnessRevolution
        Box box11 = new Box("FitnessRevolution Small Box", 25, 25, 25, BoxType.CUSTOM, false, 3.99, fitnessRevolution);
        Box box12 = new Box("FitnessRevolution Medium Box", 50, 50, 50, BoxType.CUSTOM, false, 5.99, fitnessRevolution);
        Box box13 = new Box("FitnessRevolution Large Box", 75, 75, 75, BoxType.CUSTOM, false, 7.99, fitnessRevolution);
        Box box14 = new Box("FitnessRevolution XL Box", 100, 100, 100, BoxType.CUSTOM, false, 9.99, fitnessRevolution);
        Box box15 = new Box("FitnessRevolution XXL Box", 125, 125, 125, BoxType.CUSTOM, false, 11.99, fitnessRevolution);

// Boxes for gourmetDelights
        Box box16 = new Box("GourmetDelights Small Box", 25, 25, 25, BoxType.CUSTOM, false, 3.99, gourmetDelights);
        Box box17 = new Box("GourmetDelights Medium Box", 50, 50, 50, BoxType.CUSTOM, false, 5.99, gourmetDelights);
        Box box18 = new Box("GourmetDelights Large Box", 75, 75, 75, BoxType.CUSTOM, false, 7.99, gourmetDelights);
        Box box19 = new Box("GourmetDelights XL Box", 100, 100, 100, BoxType.CUSTOM, false, 9.99, gourmetDelights);
        Box box20 = new Box("GourmetDelights XXL Box", 125, 125, 125, BoxType.CUSTOM, false, 11.99, gourmetDelights);

// Boxes for fashionForward
        Box box21 = new Box("FashionForward Small Box", 25, 25, 25, BoxType.CUSTOM, false, 3.99, fashionForward);
        Box box22 = new Box("FashionForward Medium Box", 50, 50, 50, BoxType.CUSTOM, false, 5.99, fashionForward);
        Box box23 = new Box("FashionForward Large Box", 75, 75, 75, BoxType.CUSTOM, false, 7.99, fashionForward);
        Box box24 = new Box("FashionForward XL Box", 100, 100, 100, BoxType.CUSTOM, false, 9.99, fashionForward);
        Box box25 = new Box("FashionForward XXL Box", 125, 125, 125, BoxType.CUSTOM, false, 11.99, fashionForward);

// Boxes for travelExplorers
        Box box26 = new Box("TravelExplorers Small Box", 25, 25, 25, BoxType.CUSTOM, false, 3.99, travelExplorers);
        Box box27 = new Box("TravelExplorers Medium Box", 50, 50, 50, BoxType.CUSTOM, false, 5.99, travelExplorers);
        Box box28 = new Box("TravelExplorers Large Box", 75, 75, 75, BoxType.CUSTOM, false, 7.99, travelExplorers);
        Box box29 = new Box("TravelExplorers XL Box", 100, 100, 100, BoxType.CUSTOM, false, 9.99, travelExplorers);
        Box box30 = new Box("TravelExplorers XXL Box", 125, 125, 125, BoxType.CUSTOM, false, 11.99, travelExplorers);

        techGurus.setBoxes(Set.of(box1, box2, box3, box4, box5));
        ecoWarriors.setBoxes(Set.of(box6, box7, box8, box9, box10));
        fitnessRevolution.setBoxes(Set.of(box11, box12, box13, box14, box15));
        gourmetDelights.setBoxes(Set.of(box16, box17, box18, box19, box20));
        fashionForward.setBoxes(Set.of(box21, box22, box23, box24, box25));
        travelExplorers.setBoxes(Set.of(box26, box27, box28, box29, box30));

        // Create orders
        Order order1 = new Order(box1, LocalDateTime.parse("2023-05-01T19:58:43.929"), techGurus, fitnessRevolution, address2);
        Order order2 = new Order(box2, LocalDateTime.parse("2023-05-02T19:58:44.035"), ecoWarriors, fitnessRevolution, address3);
        Order order3 = new Order(box1, LocalDateTime.parse("2023-05-03T19:58:44.091"), fitnessRevolution, gourmetDelights, address7);
        Order order4 = new Order(box3, LocalDateTime.parse("2023-05-04T19:58:44.141"), gourmetDelights, fashionForward, address8);
        Order order5 = new Order(box4, LocalDateTime.parse("2023-05-05T19:58:44.169"), fashionForward, travelExplorers, address9);
        Order order6 = new Order(box2, LocalDateTime.parse("2023-06-02T19:58:45.200"), travelExplorers, techGurus, address1);
        Order order7 = new Order(box1, LocalDateTime.parse("2023-07-02T19:58:45.250"), techGurus, ecoWarriors, address2);
        Order order8 = new Order(box1, LocalDateTime.parse("2023-08-02T19:58:45.300"), ecoWarriors, fitnessRevolution, address3);
        Order order9 = new Order(box3, LocalDateTime.parse("2023-09-02T19:58:45.350"), fitnessRevolution, gourmetDelights, address7);
        Order order10 = new Order(box1, LocalDateTime.parse("2023-01-02T19:58:45.400"), gourmetDelights, fashionForward, address8);
        Order order11 = new Order(box2, LocalDateTime.parse("2023-02-02T19:59:00.000"), techGurus, ecoWarriors, address2);
        Order order12 = new Order(box3, LocalDateTime.parse("2023-05-03T19:59:01.000"), ecoWarriors, fitnessRevolution, address3);
        Order order13 = new Order(box1, LocalDateTime.parse("2023-04-02T19:59:02.000"), fitnessRevolution, gourmetDelights, address7);
        Order order14 = new Order(box4, LocalDateTime.parse("2023-05-05T19:59:03.000"), gourmetDelights, fashionForward, address8);
        Order order15 = new Order(box2, LocalDateTime.parse("2023-06-02T19:59:04.000"), fashionForward, travelExplorers, address9);
        Order order16 = new Order(box1, LocalDateTime.parse("2023-05-07T19:59:05.000"), travelExplorers, techGurus, address1);
        Order order17 = new Order(box3, LocalDateTime.parse("2023-05-08T19:59:06.000"), techGurus, ecoWarriors, address2);
        Order order18 = new Order(box2, LocalDateTime.parse("2023-05-09T19:59:07.000"), ecoWarriors, fitnessRevolution, address3);
        Order order19 = new Order(box3, LocalDateTime.parse("2023-05-12T19:59:08.000"), fitnessRevolution, gourmetDelights, address7);
        Order order20 = new Order(box3, LocalDateTime.parse("2023-05-22T19:59:09.000"), gourmetDelights, fashionForward, address8);
        Order order21 = new Order(box1, LocalDateTime.parse("2023-05-22T19:59:10.000"), fashionForward, travelExplorers, address9);
        Order order22 = new Order(box4, LocalDateTime.parse("2023-05-12T19:59:11.000"), travelExplorers, techGurus, address1);
        Order order23 = new Order(box2, LocalDateTime.parse("2023-05-15T19:59:12.000"), techGurus, ecoWarriors, address2);
        Order order24 = new Order(box2, LocalDateTime.parse("2023-05-11T19:59:13.000"), ecoWarriors, fitnessRevolution, address3);
        Order order25 = new Order(box2, LocalDateTime.parse("2023-05-11T19:59:14.000"), fitnessRevolution, gourmetDelights, address7);
        Order order26 = new Order(box3, LocalDateTime.parse("2023-05-15T19:59:15.000"), gourmetDelights, fashionForward, address8);
        Order order27 = new Order(box2, LocalDateTime.parse("2023-12-02T19:59:16.000"), fashionForward, travelExplorers, address9);
        Order order28 = new Order(box1, LocalDateTime.parse("2023-12-02T19:59:17.000"), travelExplorers, techGurus, address1);
        Order order29 = new Order(box3, LocalDateTime.parse("2023-12-02T19:59:18.000"), techGurus, ecoWarriors, address2);
        Order order30 = new Order(box4, LocalDateTime.parse("2023-12-22T19:59:19.000"), ecoWarriors, fitnessRevolution, address3);
        Order order31 = new Order(box3, LocalDateTime.parse("2023-05-12T19:59:20.000"), fitnessRevolution, gourmetDelights, address7);
        Order order32 = new Order(box4, LocalDateTime.parse("2023-05-24T19:59:21.000"), gourmetDelights, fashionForward, address8);
        Order order33 = new Order(box2, LocalDateTime.parse("2023-05-23T19:59:22.000"), fashionForward, travelExplorers, address9);
        Order order34 = new Order(box1, LocalDateTime.parse("2023-05-12T19:59:23.000"), travelExplorers, techGurus, address1);
        Order order35 = new Order(box1, LocalDateTime.parse("2023-03-02T19:59:24.000"), techGurus, ecoWarriors, address2);
        Order order36 = new Order(box1, LocalDateTime.parse("2023-06-02T19:59:25.000"), ecoWarriors, fitnessRevolution, address3);
        Order order37 = new Order(box1, LocalDateTime.parse("2023-07-02T19:59:26.000"), gourmetDelights, fashionForward, address8);
        Order order38 = new Order(box1, LocalDateTime.parse("2023-01-02T19:59:27.000"), fashionForward, travelExplorers, address9);
        Order order39 = new Order(box3, LocalDateTime.parse("2023-02-02T19:59:28.000"), travelExplorers, techGurus, address1);
        Order order40 = new Order(box3, LocalDateTime.parse("2023-03-02T19:59:29.000"), techGurus, ecoWarriors, address2);
        Order order41 = new Order(box2, LocalDateTime.parse("2023-01-02T19:59:30.000"), ecoWarriors, fitnessRevolution, address3);
        Order order42 = new Order(box1, LocalDateTime.parse("2023-02-02T19:59:31.000"), gourmetDelights, fashionForward, address8);
        Order order43 = new Order(box4, LocalDateTime.parse("2023-03-02T19:59:32.000"), fashionForward, travelExplorers, address9);
        Order order44 = new Order(box2, LocalDateTime.parse("2023-05-02T19:59:33.000"), travelExplorers, techGurus, address1);
        Order order45 = new Order(box3, LocalDateTime.parse("2023-05-02T19:59:34.000"), techGurus, ecoWarriors, address2);
        Order order46 = new Order(box2, LocalDateTime.parse("2023-05-02T19:59:35.000"), ecoWarriors, fitnessRevolution, address3);
        Order order47 = new Order(box3, LocalDateTime.parse("2023-05-02T19:59:36.000"), gourmetDelights, fitnessRevolution, address8);
        Order order48 = new Order(box1, LocalDateTime.parse("2023-05-02T19:59:37.000"), fashionForward, travelExplorers, address9);
        Order order49 = new Order(box1, LocalDateTime.parse("2023-05-02T19:59:38.000"), travelExplorers, techGurus, address1);
        Order order50 = new Order(box3, LocalDateTime.parse("2023-05-02T19:59:39.000"), techGurus, ecoWarriors, address2);

//        OrderItems
        OrderItem orderItem1 = new OrderItem(1, order1, null);
        OrderItem orderItem2 = new OrderItem(2, order2, null);
        OrderItem orderItem3 = new OrderItem(3, order3, null);
        OrderItem orderItem4 = new OrderItem(4, order4, null);
        OrderItem orderItem5 = new OrderItem(5, order5, null);
        OrderItem orderItem6 = new OrderItem(6, order6, null);
        OrderItem orderItem7 = new OrderItem(7, order7, null);
        OrderItem orderItem8 = new OrderItem(8, order8, null);
        OrderItem orderItem9 = new OrderItem(9, order9, null);
        OrderItem orderItem10 = new OrderItem(10, order10, null);
        OrderItem orderItem11 = new OrderItem(11, order11, null);
        OrderItem orderItem12 = new OrderItem(12, order12, null);
        OrderItem orderItem13 = new OrderItem(13, order13, null);
        OrderItem orderItem14 = new OrderItem(14, order14, null);
        OrderItem orderItem15 = new OrderItem(15, order15, null);
        OrderItem orderItem16 = new OrderItem(16, order16, null);
        OrderItem orderItem17 = new OrderItem(17, order17, null);
        OrderItem orderItem18 = new OrderItem(18, order18, null);
        OrderItem orderItem19 = new OrderItem(19, order19, null);
        OrderItem orderItem20 = new OrderItem(20, order20, null);
        OrderItem orderItem21 = new OrderItem(21, order21, null);
        OrderItem orderItem22 = new OrderItem(22, order22, null);
        OrderItem orderItem23 = new OrderItem(23, order23, null);
        OrderItem orderItem24 = new OrderItem(24, order24, null);
        OrderItem orderItem25 = new OrderItem(25, order25, null);
        OrderItem orderItem26 = new OrderItem(26, order26, null);
        OrderItem orderItem27 = new OrderItem(27, order27, null);
        OrderItem orderItem28 = new OrderItem(28, order28, null);
        OrderItem orderItem29 = new OrderItem(29, order29, null);
        OrderItem orderItem30 = new OrderItem(30, order30, null);
        OrderItem orderItem31 = new OrderItem(31, order31, null);
        OrderItem orderItem32 = new OrderItem(32, order32, null);
        OrderItem orderItem33 = new OrderItem(33, order33, null);
        OrderItem orderItem34 = new OrderItem(34, order34, null);
        OrderItem orderItem35 = new OrderItem(35, order35, null);
        OrderItem orderItem36 = new OrderItem(36, order36, null);
        OrderItem orderItem37 = new OrderItem(37, order37, null);
        OrderItem orderItem38 = new OrderItem(38, order38, null);
        OrderItem orderItem39 = new OrderItem(39, order39, null);
        OrderItem orderItem40 = new OrderItem(40, order40, null);
        OrderItem orderItem41 = new OrderItem(41, order41, null);
        OrderItem orderItem42 = new OrderItem(42, order42, null);
        OrderItem orderItem43 = new OrderItem(43, order43, null);
        OrderItem orderItem44 = new OrderItem(44, order44, null);
        OrderItem orderItem45 = new OrderItem(45, order45, null);
        OrderItem orderItem46 = new OrderItem(46, order46, null);
        OrderItem orderItem47 = new OrderItem(47, order47, null);
        OrderItem orderItem48 = new OrderItem(48, order48, null);
        OrderItem orderItem49 = new OrderItem(49, order49, null);
        OrderItem orderItem50 = new OrderItem(50, order50, null);
        OrderItem orderItem51 = new OrderItem(51, order1, null);
        OrderItem orderItem52 = new OrderItem(52, order2, null);
        OrderItem orderItem53 = new OrderItem(53, order3, null);
        OrderItem orderItem54 = new OrderItem(54, order4, null);
        OrderItem orderItem55 = new OrderItem(55, order5, null);
        OrderItem orderItem56 = new OrderItem(56, order6, null);
        OrderItem orderItem57 = new OrderItem(57, order7, null);
        OrderItem orderItem58 = new OrderItem(58, order8, null);
        OrderItem orderItem59 = new OrderItem(59, order9, null);
        OrderItem orderItem60 = new OrderItem(60, order10, null);
        OrderItem orderItem61 = new OrderItem(61, order11, null);
        OrderItem orderItem62 = new OrderItem(62, order12, null);
        OrderItem orderItem63 = new OrderItem(63, order13, null);
        OrderItem orderItem64 = new OrderItem(64, order14, null);
        OrderItem orderItem65 = new OrderItem(65, order15, null);
        OrderItem orderItem66 = new OrderItem(66, order16, null);
        OrderItem orderItem67 = new OrderItem(67, order17, null);
        OrderItem orderItem68 = new OrderItem(68, order18, null);
        OrderItem orderItem69 = new OrderItem(69, order19, null);
        OrderItem orderItem70 = new OrderItem(70, order20, null);
        OrderItem orderItem71 = new OrderItem(71, order21, null);
        OrderItem orderItem72 = new OrderItem(72, order22, null);
        OrderItem orderItem73 = new OrderItem(73, order23, null);
        OrderItem orderItem74 = new OrderItem(74, order24, null);
        OrderItem orderItem75 = new OrderItem(75, order25, null);
        OrderItem orderItem76 = new OrderItem(76, order26, null);
        OrderItem orderItem77 = new OrderItem(77, order27, null);
        OrderItem orderItem78 = new OrderItem(78, order28, null);
        OrderItem orderItem79 = new OrderItem(79, order29, null);


//        Products
        Product product1 = new Product(20, 5, null, null, null);
        Product product2 = new Product(25, 3, null, null, null);
        Product product3 = new Product(10, 14, null, null, null);
        Product product4 = new Product(15, 7, null, null, null);
        Product product5 = new Product(20, 5, null, null, null);
        Product product6 = new Product(25, 3, null, null, null);
        Product product7 = new Product(10, 14, null, null, null);
        Product product8 = new Product(15, 7, null, null, null);
        Product product9 = new Product(20, 5, null, null, null);
        Product product10 = new Product(25, 3, null, null, null);
        Product product11 = new Product(10, 14, null, null, null);
        Product product12 = new Product(15, 7, null, null, null);
        Product product13 = new Product(20, 5, null, null, null);
        Product product14 = new Product(25, 3, null, null, null);
        Product product15 = new Product(10, 14, null, null, null);
        Product product16 = new Product(15, 7, null, null, null);
        Product product17 = new Product(20, 5, null, null, null);
        Product product18 = new Product(25, 3, null, null, null);
        Product product19 = new Product(10, 14, null, null, null);
        Product product20 = new Product(15, 7, null, null, null);


//        Productdescriptions
        ProductDescription productDescription1 = new ProductDescription("en", "Samsung oled 55 inch - Model A", "55 oled tv from samsung - Model A", "Black big tv for living rooms - Model A", product1);
        ProductDescription productDescription2 = new ProductDescription("en", "Samsung oled 65 inch - Model B", "65 oled tv from samsung - Model B", "Black big tv for living rooms - Model B", product2);
        ProductDescription productDescription3 = new ProductDescription("en", "Samsung oled 55 inch - Model C", "55 oled tv from samsung - Model C", "Black big tv for living rooms - Model C", product3);
        ProductDescription productDescription4 = new ProductDescription("en", "Samsung a75 - Model D", "samsung galaxy smartphone - Model D", "Newest product samsung perfect for video - Model D", product4);
        ProductDescription productDescription5 = new ProductDescription("en", "Samsung a300 - Model E", "samsung galaxy smartphone - Model E", "Newest product samsung perfect for video - Model E", product5);
        ProductDescription productDescription6 = new ProductDescription("en", "Samsung a90 - Model F", "samsung galaxy smartphone - Model F", "Newest product samsung perfect for video - Model F", product6);
        ProductDescription productDescription7 = new ProductDescription("en", "Samsung a75 - Model G", "samsung galaxy smartphone - Model G", "Newest product samsung perfect for video - Model G", product7);
        ProductDescription productDescription8 = new ProductDescription("en", "Samsung a300 - Model H", "samsung galaxy smartphone - Model H", "Newest product samsung perfect for video - Model H", product8);
        ProductDescription productDescription9 = new ProductDescription("en", "Samsung a90 - Model I", "samsung galaxy smartphone - Model I", "Newest product samsung perfect for video - Model I", product9);
        ProductDescription productDescription10 = new ProductDescription("en", "Samsung a75 - Model J", "samsung galaxy smartphone - Model J", "Newest product samsung perfect for video - Model J", product10);
        ProductDescription productDescription11 = new ProductDescription("en", "Samsung a300 - Model K", "samsung galaxy smartphone - Model K", "Newest product samsung perfect for video - Model K", product11);
        ProductDescription productDescription12 = new ProductDescription("en", "Samsung a90 - Model L", "samsung galaxy smartphone - Model L", "Newest product samsung perfect for video - Model L", product12);
        ProductDescription productDescription13 = new ProductDescription("en", "Samsung a75 - Model M", "samsung galaxy smartphone - Model M", "Newest product samsung perfect for video - Model M", product13);
        ProductDescription productDescription14 = new ProductDescription("en", "Samsung a300 - Model N", "samsung galaxy smartphone - Model N", "Newest product samsung perfect for video - Model N", product14);
        ProductDescription productDescription15 = new ProductDescription("en", "Samsung a90 - Model O", "samsung galaxy smartphone - Model O", "Newest product samsung perfect for video - Model O", product15);
        ProductDescription productDescription16 = new ProductDescription("en", "Samsung a75 - Model P", "samsung galaxy smartphone - Model P", "Newest product samsung perfect for video - Model P", product16);
        ProductDescription productDescription17 = new ProductDescription("en", "Samsung a300 - Model Q", "samsung galaxy smartphone - Model Q", "Newest product samsung perfect for video - Model Q", product17);
        ProductDescription productDescription18 = new ProductDescription("en", "Samsung a90 - Model R", "samsung galaxy smartphone - Model R", "Newest product samsung perfect for video - Model R", product18);
        ProductDescription productDescription19 = new ProductDescription("en", "Samsung a75 - Model S", "samsung galaxy smartphone - Model S", "Newest product samsung perfect for video - Model S", product19);
        ProductDescription productDescription20 = new ProductDescription("en", "Samsung a300 - Model T", "samsung galaxy smartphone - Model T", "Newest product samsung perfect for video - Model T", product20);

        ProductPrice productPrice1 = new ProductPrice("EUR", "EAX", 199.99F, product1);
        ProductPrice productPrice2 = new ProductPrice("EUR", "EAX", 299.99F, product2);
        ProductPrice productPrice3 = new ProductPrice("EUR", "EAX", 199.99F, product3);
        ProductPrice productPrice4 = new ProductPrice("EUR", "EAX", 199.99F, product4);
        ProductPrice productPrice5 = new ProductPrice("EUR", "EAX", 199.99F, product5);
        ProductPrice productPrice6 = new ProductPrice("EUR", "EAX", 199.99F, product6);
        ProductPrice productPrice7 = new ProductPrice("EUR", "EAX", 199.99F, product7);
        ProductPrice productPrice8 = new ProductPrice("EUR", "EAX", 199.99F, product8);
        ProductPrice productPrice9 = new ProductPrice("EUR", "EAX", 199.99F, product9);
        ProductPrice productPrice10 = new ProductPrice("EUR", "EAX", 199.99F, product10);
        ProductPrice productPrice11 = new ProductPrice("EUR", "EAX", 199.99F, product11);
        ProductPrice productPrice12 = new ProductPrice("EUR", "EAX", 199.99F, product12);
        ProductPrice productPrice13 = new ProductPrice("EUR", "EAX", 199.99F, product13);
        ProductPrice productPrice14 = new ProductPrice("EUR", "EAX", 199.99F, product14);
        ProductPrice productPrice15 = new ProductPrice("EUR", "EAX", 199.99F, product15);
        ProductPrice productPrice16 = new ProductPrice("EUR", "EAX", 199.99F, product16);
        ProductPrice productPrice17 = new ProductPrice("EUR", "EAX", 199.99F, product17);
        ProductPrice productPrice18 = new ProductPrice("EUR", "EAX", 199.99F, product18);
        ProductPrice productPrice19 = new ProductPrice("EUR", "EAX", 199.99F, product19);
        ProductPrice productPrice20 = new ProductPrice("EUR", "EAX", 199.99F, product20);


////        track and traces
//        TrackAndTrace trackAndTrace1 = new TrackAndTrace("010511234567890","2565", order1 );
//        TrackAndTrace trackAndTrace2 = new TrackAndTrace("010511234567891","2566", order2 );
//        TrackAndTrace trackAndTrace3 = new TrackAndTrace("010511234567892","2567", order3 );
//        TrackAndTrace trackAndTrace4 = new TrackAndTrace("010511234567893","2568", order4 );
//        TrackAndTrace trackAndTrace5 = new TrackAndTrace("010511234567894","2569", order5 );
//        TrackAndTrace trackAndTrace6 = new TrackAndTrace("010511234567895","2570", order6 );
//        TrackAndTrace trackAndTrace7 = new TrackAndTrace("010511234567896","2571", order7 );
//        TrackAndTrace trackAndTrace8 = new TrackAndTrace("010511234567897","2572", order8 );
//        TrackAndTrace trackAndTrace9 = new TrackAndTrace("010511234567898","2573", order9 );
//        TrackAndTrace trackAndTrace10 = new TrackAndTrace("010511234567899","2574", order10 );

//        Adding product prices to the corresponding products
        product1.setProductPrices(Set.of(productPrice1));
        product2.setProductPrices(Set.of(productPrice2));
        product3.setProductPrices(Set.of(productPrice3));
        product4.setProductPrices(Set.of(productPrice4));
        product5.setProductPrices(Set.of(productPrice5));
        product6.setProductPrices(Set.of(productPrice6));
        product7.setProductPrices(Set.of(productPrice7));
        product8.setProductPrices(Set.of(productPrice8));
        product9.setProductPrices(Set.of(productPrice9));
        product10.setProductPrices(Set.of(productPrice10));
        product11.setProductPrices(Set.of(productPrice11));
        product12.setProductPrices(Set.of(productPrice12));
        product13.setProductPrices(Set.of(productPrice13));
        product14.setProductPrices(Set.of(productPrice14));
        product15.setProductPrices(Set.of(productPrice15));
        product16.setProductPrices(Set.of(productPrice16));
        product17.setProductPrices(Set.of(productPrice17));
        product18.setProductPrices(Set.of(productPrice18));
        product19.setProductPrices(Set.of(productPrice19));
        product20.setProductPrices(Set.of(productPrice20));


//        Adding product descriptions to the corresponding products
        product1.setProductDescriptions(Set.of(productDescription1));
        product2.setProductDescriptions(Set.of(productDescription2));
        product3.setProductDescriptions(Set.of(productDescription3));
        product4.setProductDescriptions(Set.of(productDescription4));
        product5.setProductDescriptions(Set.of(productDescription5));
        product6.setProductDescriptions(Set.of(productDescription6));
        product7.setProductDescriptions(Set.of(productDescription7));
        product8.setProductDescriptions(Set.of(productDescription8));
        product9.setProductDescriptions(Set.of(productDescription9));
        product10.setProductDescriptions(Set.of(productDescription10));
        product11.setProductDescriptions(Set.of(productDescription11));
        product12.setProductDescriptions(Set.of(productDescription12));
        product13.setProductDescriptions(Set.of(productDescription13));
        product14.setProductDescriptions(Set.of(productDescription14));
        product15.setProductDescriptions(Set.of(productDescription15));
        product16.setProductDescriptions(Set.of(productDescription16));
        product17.setProductDescriptions(Set.of(productDescription17));
        product18.setProductDescriptions(Set.of(productDescription18));
        product19.setProductDescriptions(Set.of(productDescription19));
        product20.setProductDescriptions(Set.of(productDescription20));

        //        Product Categories
        ProductCategory productCategory1 = new ProductCategory("Screens", Set.of(product1, product2));
        ProductCategory productCategory2 = new ProductCategory("Screens", Set.of(product3, product4));
        ProductCategory productCategory3 = new ProductCategory("Screens", Set.of(product5, product6));
        ProductCategory productCategory4 = new ProductCategory("Smartphones", Set.of(product7, product8));
        ProductCategory productCategory5 = new ProductCategory("Smartphones", Set.of(product9, product10));
        ProductCategory productCategory6 = new ProductCategory("Smartphones", Set.of(product11, product12));
        ProductCategory productCategory7 = new ProductCategory("Smartphones", Set.of(product13, product14));
        ProductCategory productCategory8 = new ProductCategory("Speakers", Set.of(product15, product16));
        ProductCategory productCategory9 = new ProductCategory("Speakers", Set.of(product17, product18));
        ProductCategory productCategory10 = new ProductCategory("Speakers", Set.of(product19, product20));


//        Adding products to the corresponding product categories
        product1.setProductCategories(Set.of(productCategory1));
        product2.setProductCategories(Set.of(productCategory1));
        product3.setProductCategories(Set.of(productCategory2));
        product4.setProductCategories(Set.of(productCategory2));
        product5.setProductCategories(Set.of(productCategory3));
        product6.setProductCategories(Set.of(productCategory3));
        product7.setProductCategories(Set.of(productCategory4));
        product8.setProductCategories(Set.of(productCategory4));
        product9.setProductCategories(Set.of(productCategory5));
        product10.setProductCategories(Set.of(productCategory5));
        product11.setProductCategories(Set.of(productCategory6));
        product12.setProductCategories(Set.of(productCategory6));
        product13.setProductCategories(Set.of(productCategory7));
        product14.setProductCategories(Set.of(productCategory7));
        product15.setProductCategories(Set.of(productCategory8));
        product16.setProductCategories(Set.of(productCategory8));
        product17.setProductCategories(Set.of(productCategory9));
        product18.setProductCategories(Set.of(productCategory9));
        product19.setProductCategories(Set.of(productCategory10));
        product20.setProductCategories(Set.of(productCategory10));


//        Adding pruducts to the corresponding order items
        orderItem1.setProduct(product1);
        orderItem2.setProduct(product2);
        orderItem3.setProduct(product3);
        orderItem4.setProduct(product4);
        orderItem5.setProduct(product5);
        orderItem6.setProduct(product6);
        orderItem7.setProduct(product7);
        orderItem8.setProduct(product8);
        orderItem9.setProduct(product9);
        orderItem10.setProduct(product10);
        orderItem11.setProduct(product11);
        orderItem12.setProduct(product12);
        orderItem13.setProduct(product13);
        orderItem14.setProduct(product14);
        orderItem15.setProduct(product15);
        orderItem16.setProduct(product16);
        orderItem17.setProduct(product17);
        orderItem18.setProduct(product18);
        orderItem19.setProduct(product19);
        orderItem20.setProduct(product20);
        orderItem21.setProduct(product1);
        orderItem22.setProduct(product2);
        orderItem23.setProduct(product3);
        orderItem24.setProduct(product4);
        orderItem25.setProduct(product5);
        orderItem26.setProduct(product6);
        orderItem27.setProduct(product7);
        orderItem28.setProduct(product8);
        orderItem29.setProduct(product9);
        orderItem30.setProduct(product10);
        orderItem31.setProduct(product11);
        orderItem32.setProduct(product12);
        orderItem33.setProduct(product13);
        orderItem34.setProduct(product14);
        orderItem35.setProduct(product15);
        orderItem36.setProduct(product16);
        orderItem37.setProduct(product17);
        orderItem38.setProduct(product18);
        orderItem39.setProduct(product19);
        orderItem40.setProduct(product20);
        orderItem41.setProduct(product1);
        orderItem42.setProduct(product2);
        orderItem43.setProduct(product3);
        orderItem44.setProduct(product4);
        orderItem45.setProduct(product5);
        orderItem46.setProduct(product6);
        orderItem47.setProduct(product7);
        orderItem48.setProduct(product8);
        orderItem49.setProduct(product9);
        orderItem50.setProduct(product10);
        orderItem51.setProduct(product11);
        orderItem52.setProduct(product12);
        orderItem53.setProduct(product13);
        orderItem54.setProduct(product14);
        orderItem55.setProduct(product15);
        orderItem56.setProduct(product16);
        orderItem57.setProduct(product17);
        orderItem58.setProduct(product18);
        orderItem59.setProduct(product19);
        orderItem60.setProduct(product20);
        orderItem61.setProduct(product1);
        orderItem62.setProduct(product2);
        orderItem63.setProduct(product3);
        orderItem64.setProduct(product4);
        orderItem65.setProduct(product5);
        orderItem66.setProduct(product6);
        orderItem67.setProduct(product7);
        orderItem68.setProduct(product8);
        orderItem69.setProduct(product9);
        orderItem70.setProduct(product10);
        orderItem71.setProduct(product11);
        orderItem72.setProduct(product12);
        orderItem73.setProduct(product13);
        orderItem74.setProduct(product14);
        orderItem75.setProduct(product15);
        orderItem76.setProduct(product16);
        orderItem77.setProduct(product17);
        orderItem78.setProduct(product18);
        orderItem79.setProduct(product19);


        // Adding addresses to the corresponding companies
        address1.setCompany(techGurus);
        address2.setCompany(ecoWarriors);
        address3.setCompany(fitnessRevolution);
        address4.setCompany(techGurus);
        address5.setCompany(ecoWarriors);
        address6.setCompany(fitnessRevolution);
        address7.setCompany(gourmetDelights);
        address8.setCompany(fashionForward);
        address9.setCompany(travelExplorers);


        // Adding employees to the corresponding companies
        techGurus.setEmployees(Set.of(employee1, employee7));
        ecoWarriors.setEmployees(Set.of(employee2, employee8));
        fitnessRevolution.setEmployees(Set.of(employee3, employee9));
        gourmetDelights.setEmployees(Set.of(employee4, employee10));
        fashionForward.setEmployees(Set.of(employee5, employee11));
        travelExplorers.setEmployees(Set.of(employee6, employee12));


        techGurus.setAddresses(Set.of(address1, address4));
        ecoWarriors.setAddresses(Set.of(address2, address5));
        fitnessRevolution.setAddresses(Set.of(address3, address6));
        gourmetDelights.setAddresses(Set.of(address7));
        fashionForward.setAddresses(Set.of(address8));
        travelExplorers.setAddresses(Set.of(address9));


        // Adding orders to the corresponding companies
        techGurus.setOrders(Set.of(order1, order7, order11, order16, order17, order22, order23, order28, order29, order34, order35, order40, order41, order44, order45, order50));
        ecoWarriors.setOrders(Set.of(order2, order8, order12, order18, order19, order24, order25, order30, order31, order36, order37, order42, order43, order46, order47));
        fitnessRevolution.setOrders(Set.of(order3, order9, order13, order20, order21, order26, order27, order32, order33, order38, order39, order48, order49));
        gourmetDelights.setOrders(Set.of(order4, order10, order14, order15, order37, order42, order47));
        fashionForward.setOrders(Set.of(order5, order14, order15, order20, order21, order26, order27, order32, order33, order38, order39, order48, order49));
        travelExplorers.setOrders(Set.of(order6, order11, order16, order17, order22, order23, order28, order29, order34, order35, order40, order41, order44, order45, order50));

//        Adding orders to the corresponding customer companies
        techGurus.setPlacedOrders(Set.of(order1, order7, order11, order16, order17, order22, order23, order28, order29, order34, order35, order40, order41, order44, order45, order50));
        ecoWarriors.setPlacedOrders(Set.of(order2, order8, order12, order18, order19, order24, order25, order30, order31, order36, order37, order42, order43, order46, order47));
        fitnessRevolution.setPlacedOrders(Set.of(order3, order9, order13, order20, order21, order26, order27, order32, order33, order38, order39, order48, order49));
        gourmetDelights.setPlacedOrders(Set.of(order4, order10, order14, order15, order37, order42, order47));
        fashionForward.setPlacedOrders(Set.of(order5, order14, order15, order20, order21, order26, order27, order32, order33, order38, order39, order48, order49));
        travelExplorers.setPlacedOrders(Set.of(order6, order11, order16, order17, order22, order23, order28, order29, order34, order35, order40, order41, order44, order45, order50));


        // Setting notifications for companies
        techGurus.setNotifications(Set.of(notification1, notification2));
        ecoWarriors.setNotifications(Set.of(notification3, notification4));
        fitnessRevolution.setNotifications(Set.of(notification5, notification6));
        gourmetDelights.setNotifications(Set.of(notification7, notification8));
        fashionForward.setNotifications(Set.of(notification9, notification10));

        GenericDaoJpa.startTransaction();

        for (Address address : Arrays.asList(address1, address2, address3, address4, address5, address6, address7, address8, address9)) {
            addressDao.insert(address);
        }

        // Adding order items to the corresponding orders
        order1.setOrderItems(Set.of(orderItem1, orderItem51));
        order2.setOrderItems(Set.of(orderItem2, orderItem52));
        order3.setOrderItems(Set.of(orderItem3, orderItem53));
        order4.setOrderItems(Set.of(orderItem4, orderItem54));
        order5.setOrderItems(Set.of(orderItem5, orderItem55));
        order6.setOrderItems(Set.of(orderItem6, orderItem56));
        order7.setOrderItems(Set.of(orderItem7, orderItem57));
        order8.setOrderItems(Set.of(orderItem8, orderItem58));
        order9.setOrderItems(Set.of(orderItem9, orderItem59));
        order10.setOrderItems(Set.of(orderItem10, orderItem60));
        order11.setOrderItems(Set.of(orderItem11, orderItem61));
        order12.setOrderItems(Set.of(orderItem12, orderItem62));
        order13.setOrderItems(Set.of(orderItem13, orderItem63));
        order14.setOrderItems(Set.of(orderItem14, orderItem64));
        order15.setOrderItems(Set.of(orderItem15, orderItem65));
        order16.setOrderItems(Set.of(orderItem16, orderItem66));
        order17.setOrderItems(Set.of(orderItem17, orderItem67));
        order18.setOrderItems(Set.of(orderItem18, orderItem68));
        order19.setOrderItems(Set.of(orderItem19, orderItem69));
        order20.setOrderItems(Set.of(orderItem20, orderItem70));
        order21.setOrderItems(Set.of(orderItem21, orderItem71));
        order22.setOrderItems(Set.of(orderItem22, orderItem72));
        order23.setOrderItems(Set.of(orderItem23, orderItem73));
        order24.setOrderItems(Set.of(orderItem24, orderItem74));
        order25.setOrderItems(Set.of(orderItem25, orderItem75));
        order26.setOrderItems(Set.of(orderItem26, orderItem76));
        order27.setOrderItems(Set.of(orderItem27, orderItem77));
        order28.setOrderItems(Set.of(orderItem28, orderItem78));
        order29.setOrderItems(Set.of(orderItem29, orderItem79));
        order30.setOrderItems(Set.of(orderItem30));
        order31.setOrderItems(Set.of(orderItem31));
        order32.setOrderItems(Set.of(orderItem32));
        order33.setOrderItems(Set.of(orderItem33));
        order34.setOrderItems(Set.of(orderItem34));
        order35.setOrderItems(Set.of(orderItem35));
        order36.setOrderItems(Set.of(orderItem36));
        order37.setOrderItems(Set.of(orderItem37));
        order38.setOrderItems(Set.of(orderItem38));
        order39.setOrderItems(Set.of(orderItem39));
        order40.setOrderItems(Set.of(orderItem40));
        order41.setOrderItems(Set.of(orderItem41));
        order42.setOrderItems(Set.of(orderItem42));
        order43.setOrderItems(Set.of(orderItem43));
        order44.setOrderItems(Set.of(orderItem44));
        order45.setOrderItems(Set.of(orderItem45));
        order46.setOrderItems(Set.of(orderItem46));
        order47.setOrderItems(Set.of(orderItem47));
        order48.setOrderItems(Set.of(orderItem48));
        order49.setOrderItems(Set.of(orderItem49));
        order50.setOrderItems(Set.of(orderItem50));


        for (Company company : Arrays.asList(techGurus, ecoWarriors, fitnessRevolution, gourmetDelights, fashionForward, travelExplorers)) {
            companyDao.insert(company);
        }

        for (Carrier carrier : Arrays.asList(carrier1, carrier2, carrier3, carrier4, carrier5, carrier6, carrier7, carrier8, carrier9, carrier10, carrier11, carrier12, carrier13, carrier14, carrier15, carrier16, carrier17, carrier18, carrier19, carrier20, carrier21, carrier22, carrier23, carrier24, carrier25, carrier26, carrier27, carrier28, carrier29, carrier30, carrier31, carrier32, carrier33, carrier34, carrier35, carrier36)) {
            carrierDao.insert(carrier);
        }

        for (Employee employee : Arrays.asList(employee1, employee2, employee3, employee4, employee5, employee6)) {
            employeeDao.insert(employee);
        }

        // Persisting notifications
        for (Notification notification : Arrays.asList(notification1, notification2, notification3, notification4, notification5, notification6, notification7, notification8, notification9, notification10)) {
            notificationDao.insert(notification);
        }

        // Persist boxes
        for (Box box : Arrays.asList(box1, box2, box3, box4, box5)) {
            boxDao.insert(box);
        }

        // Persist orders
        for (Order order : Arrays.asList(order1, order2, order3, order4, order5, order6, order7, order8, order9, order10, order11, order12, order13, order14, order15, order16, order17, order18, order19, order20, order21, order22, order23, order24, order25, order26, order27, order28, order29, order30, order31, order32, order33, order34, order35, order36, order37, order38, order39, order40, order41, order42, order43, order44, order45, order46, order47, order48, order49, order50)) {
            orderDao.insert(order);
        }


        for (ProductDescription productDescription : Arrays.asList(productDescription1, productDescription2, productDescription3, productDescription4, productDescription5, productDescription6, productDescription7, productDescription8, productDescription9, productDescription10, productDescription11, productDescription12, productDescription13, productDescription14, productDescription15, productDescription16, productDescription17, productDescription18, productDescription19, productDescription20)) {
            productDescriptionDao.insert(productDescription);
        }

//        Persist products
        for (Product product : Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10, product11, product12, product13, product14, product15, product16, product17, product18, product19, product20)) {
            productDao.insert(product);
        }

        for (ProductCategory productCategory : Arrays.asList(productCategory1, productCategory2, productCategory3, productCategory4, productCategory5, productCategory6, productCategory7, productCategory8, productCategory9, productCategory10)) {
            productCategoryDao.insert(productCategory);
        }

        // Persist orderItems
        for (OrderItem orderItem : Arrays.asList(orderItem1, orderItem2, orderItem3, orderItem4, orderItem5, orderItem6, orderItem7, orderItem8, orderItem9, orderItem10, orderItem11, orderItem12, orderItem13, orderItem14, orderItem15, orderItem16, orderItem17, orderItem18, orderItem19, orderItem20, orderItem21, orderItem22, orderItem23, orderItem24, orderItem25, orderItem26, orderItem27, orderItem28, orderItem29, orderItem30, orderItem31, orderItem32, orderItem33, orderItem34, orderItem35, orderItem36, orderItem37, orderItem38, orderItem39, orderItem40, orderItem41, orderItem42, orderItem43, orderItem44, orderItem45, orderItem46, orderItem47, orderItem48, orderItem49, orderItem50, orderItem51, orderItem52, orderItem53, orderItem54, orderItem55, orderItem56, orderItem57, orderItem58, orderItem59, orderItem60, orderItem61, orderItem62, orderItem63, orderItem64, orderItem65, orderItem66, orderItem67, orderItem68, orderItem69, orderItem70, orderItem71, orderItem72, orderItem73, orderItem74, orderItem75, orderItem76, orderItem77, orderItem78, orderItem79)) {
            orderItemDao.insert(orderItem);
        }


        for (ProductPrice productPrice : Arrays.asList(productPrice1, productPrice2, productPrice3, productPrice4, productPrice5, productPrice6, productPrice7, productPrice8, productPrice9, productPrice10, productPrice11, productPrice12, productPrice13, productPrice14, productPrice15, productPrice16, productPrice17, productPrice18, productPrice19, productPrice20)) {
            productPriceDao.insert(productPrice);
        }


        GenericDaoJpa.commitTransaction();

//        GenericDaoJpa.startTransaction();
//
//        // Setting carriers for some orders
//        order1.getTrackAndTrace().setCarrier(carrier1);
//        order2.getTrackAndTrace().setCarrier(carrier2);
//        order3.getTrackAndTrace().setCarrier(carrier3);
//        order4.getTrackAndTrace().setCarrier(carrier4);
//        order5.getTrackAndTrace().setCarrier(carrier5);
//        order6.getTrackAndTrace().setCarrier(carrier1);
//        order7.getTrackAndTrace().setCarrier(carrier2);
//        order8.getTrackAndTrace().setCarrier(carrier3);
//        order9.getTrackAndTrace().setCarrier(carrier4);
//        order10.getTrackAndTrace().setCarrier(carrier5);
//        order11.getTrackAndTrace().setCarrier(carrier1);
//        order12.getTrackAndTrace().setCarrier(carrier2);
//        order13.getTrackAndTrace().setCarrier(carrier3);
//        order14.getTrackAndTrace().setCarrier(carrier4);
//        order15.getTrackAndTrace().setCarrier(carrier5);
//
////        Setting some other statuses
//        order1.getTrackAndTrace().setStatus(OrderStatus.DELIVERED);
//        order2.getTrackAndTrace().setStatus(OrderStatus.DELIVERED);
//        order3.getTrackAndTrace().setStatus(OrderStatus.SHIPPED);
//        order4.getTrackAndTrace().setStatus(OrderStatus.SHIPPED);
//        order5.getTrackAndTrace().setStatus(OrderStatus.SHIPPED);
//        order6.getTrackAndTrace().setStatus(OrderStatus.DELIVERED);
//
//        GenericDaoJpa.commitTransaction();
        GenericDaoJpa.closePersistency();

    }
}

