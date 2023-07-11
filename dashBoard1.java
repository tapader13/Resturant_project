import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class dashBoard1 {
    private static List<MenuItem> menu = new ArrayList<>();
    private static TableNode firstTable = null;

    public static void main(String[] args) {
        System.out.println(
                ".......................This is our initial project for online restaurant.....................");
        int n;
        System.out.println("                If you are a user, type 1\n                If you are an admin, type 2");
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice one ");
        n = sc.nextInt();
        sc.nextLine();
        int choice;
        if (n == 1) {
            System.out.println("1. Show menu item list");
            System.out.println("2. Order placement");
            System.out.println("3. Table Management ");
            System.out.print("Choice one ");
            choice = sc.nextInt();
            do {
                switch (choice) {
                    case 1:
                        sowMenu();
                        break;
                    case 2:
                        orderPlace();
                        break;
                    case 3:
                        manageTables();
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
                System.out.print("Choice one ");
                choice = sc.nextInt();
            } while (choice != 6);

        } else if (n == 2) {
            String filePa = "adminRegi.txt";
            try (FileReader fileReader = new FileReader(filePa);
                    BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                String line, fileEmail = null, failPass = null;
                String inputEmail;
                String inputPass;
                if ((line = bufferedReader.readLine()) != null) {
                    fileEmail = line.trim();
                }
                if ((line = bufferedReader.readLine()) != null) {
                    failPass = line.trim();
                }
                System.out.println("Enter your email ");
                inputEmail = sc.nextLine();
                System.out.println("Enter your password ");
                inputPass = sc.nextLine();
                if (inputEmail.equals(fileEmail) && inputPass.equals(failPass)) {
                    System.out.println("1. Add menu item");
                    System.out.println("2. Serve orders");
                    choice = sc.nextInt();
                    sc.nextLine();
                    do {
                        switch (choice) {
                            case 1:
                                addMenu();
                                break;

                            case 2:
                                serveOrder();
                                break;

                            default:
                                System.out.println("Invalid choice");
                        }
                        Scanner inp = new Scanner(System.in);
                        int ice = inp.nextInt();
                        sc.nextLine();
                        if (ice == 1) {
                            addMenu();
                        } else if (ice == 2) {
                            serveOrder();
                        }
                    } while (choice != 3);
                } else {
                    System.out.println("Invalid login");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Invalid input");
        }
    }

    private static void serveOrder() {
        Scanner sc = new Scanner(System.in);
        String filePa = "order.txt";
        try (FileReader fileReader = new FileReader(filePa);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            int lineCount = lines.size();
            int currentIndex = 0;
            while (currentIndex < lineCount) {
                String fileName = "order_" + (currentIndex / 5 + 1) + ".txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    for (int i = 0; i < 5 && currentIndex < lineCount; i++) {
                        writer.write(lines.get(currentIndex));
                        writer.newLine();
                        currentIndex++;
                    }
                } catch (IOException e) {
                    System.out.println("Failed to write the order to the file.");
                    e.printStackTrace();
                }
            }
            lines.subList(0, currentIndex).clear();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePa))) {
                for (String remainingLine : lines) {
                    writer.write(remainingLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Failed to update the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Failed to read the file.");
            e.printStackTrace();
        }
    }

    private static void addMenu() {
        Scanner sc = new Scanner(System.in);
        String fName, descp, price, availib;
        String serNum;
        System.out.print("Enter the serial number of the food ");
        serNum = sc.nextLine();
        System.out.print("Enter the name of the food ");
        fName = sc.nextLine();
        System.out.print("Enter the description of the food ");
        descp = sc.nextLine();
        System.out.print("Enter the price of the food ");
        price = sc.next();
        System.out.print("Enter the availability of the food ");
        availib = sc.next();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt", true))) {
            writer.newLine();
            writer.write(serNum);
            writer.write(". ");
            writer.write("\"");
            writer.write(fName);
            writer.write("\", \"");
            writer.write(descp);
            writer.write("\",");
            writer.write(price);
            writer.write(",");
            writer.write(availib);
            writer.write(",");
        } catch (IOException e) {
            System.out.println("Failed to write the order to the file.");
            e.printStackTrace();
        }
    }

    private static void sowMenu() {
        List<String> lines = new ArrayList<>();
        String filePa = "file.txt";
        try (FileReader fileReader = new FileReader(filePa);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("If you want to sort the menu by price, press 1. Otherwise, press 2.");
        Scanner sc = new Scanner(System.in);
        int press = sc.nextInt();
        if (press == 1) {
            Collections.sort(lines, new Comparator<String>() {
                @Override
                public int compare(String line1, String line2) {
                    double price1 = extractPrice(line1);
                    double price2 = extractPrice(line2);
                    return Double.compare(price1, price2);
                }
            });
            for (String line : lines) {
                System.out.println(line);
            }
        } else if (press == 2) {
            return;
        }
    }

    private static double extractPrice(String line) {
        String[] tokens = line.split(",");
        return Double.parseDouble(tokens[2].trim());
    }

    private static void orderPlace() {
        List<String> lines = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String filePa = "file.txt";
        try (FileReader fileReader = new FileReader(filePa);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            System.out.println(line);
        }

        double totalAmount = 0;
        String order;
        while (true) {
            System.out.print("Enter the orderNumber (0 to exit): ");
            int totalOrder = sc.nextInt();
            if (totalOrder == 0) {
                break;
            }
            sc.nextLine();
            while (totalOrder >= 1) {
                System.out.print("Enter the order: ");
                order = sc.nextLine();
                System.out.print("Enter the price: ");
                double price = sc.nextDouble();
                totalAmount += price;
                totalOrder--;
                sc.nextLine();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("order.txt", true))) {
                    writer.write("Order: " + order);
                } catch (IOException e) {
                    System.out.println("Failed to write the order to the file.");
                    e.printStackTrace();
                }
            }
            System.out.print("Enter the table number: ");
            int tableNumber = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter the customer name: ");
            String customerName = sc.nextLine();
            System.out.print("Enter the customer phone number: ");
            String customerPhoneNumber = sc.nextLine();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("order.txt", true))) {
                writer.newLine();
                writer.write("Table Number: " + tableNumber);
                writer.newLine();
                writer.write("Customer Name: " + customerName);
                writer.newLine();
                writer.write("Customer Phone Number: " + customerPhoneNumber);
                writer.newLine();
                writer.write("Total Amount: " + totalAmount);
                writer.newLine();
                System.out.println("Order placed successfully!");
            } catch (IOException e) {
                System.out.println("Failed to write the order to the file.");
                e.printStackTrace();
            }
        }
    }

    private static void manageTables() {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("Table Management");
        System.out.println("1. Add a table");
        System.out.println("2. Show all tables");
        System.out.println("3. Update table status");
        System.out.print("Choice one: ");
        choice = sc.nextInt();

        do {
            switch (choice) {
                case 1:
                    addTable();
                    break;
                case 2:
                    showAllTables();
                    break;
                case 3:
                    updateTableStatus();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            System.out.println("If you want to manage table More, press 1. Otherwise, press 2.");
            Scanner plac = new Scanner(System.in);
            int press = plac.nextInt();
            if (press == 1) {
                manageTables();
            } else if (press == 2) {
                return;
            }
        } while (choice != 4);
    }

    private static void addTable() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter table number: ");
        int tableNumber = sc.nextInt();
        System.out.println("Enter seating capacity: ");
        int seatingCapacity = sc.nextInt();
        TableNode newTable = new TableNode(tableNumber, seatingCapacity, true);
        if (firstTable == null) {
            firstTable = newTable;
        } else {
            TableNode currentTable = firstTable;
            while (currentTable.getNext() != null) {
                currentTable = currentTable.getNext();
            }
            currentTable.setNext(newTable);
        }
        System.out.println("Table added successfully!");

    }

    private static void showAllTables() {
        System.out.println("Table Number | Seating Capacity | Status");
        TableNode currentTable = firstTable;
        while (currentTable != null) {
            System.out.println(currentTable.getTableNumber() + " | " +
                    currentTable.getSeatingCapacity() + " | " +
                    (currentTable.isAvailable() ? "Available" : "Occupied"));
            currentTable = currentTable.getNext();
        }

    }

    private static void updateTableStatus() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter table number to update status: ");
        int tableNumber = sc.nextInt();
        TableNode currentTable = firstTable;
        while (currentTable != null) {
            if (currentTable.getTableNumber() == tableNumber) {
                System.out.println("Enter new status (1 for Available, 0 for Occupied): ");
                int status = sc.nextInt();
                currentTable.setAvailable(status == 1);
                System.out.println("Table status updated successfully!");
                return;
            }
            currentTable = currentTable.getNext();
        }
        System.out.println("Table not found!");
    }
}

class MenuItem {
    private String name;
    private String description;
    private double price;
    private boolean available;

    public MenuItem(String name, String description, double price, boolean available) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}

class TableNode {
    private int tableNumber;
    private int seatingCapacity;
    private boolean available;
    private TableNode next;

    public TableNode(int tableNumber, int seatingCapacity, boolean available) {
        this.tableNumber = tableNumber;
        this.seatingCapacity = seatingCapacity;
        this.available = available;
        this.next = null;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public TableNode getNext() {
        return next;
    }

    public void setNext(TableNode next) {
        this.next = next;
    }
}
