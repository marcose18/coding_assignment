
import java.util.*;
import java.io.*;

class Item {
    String id;
    int price;
    String category;

    Item(String id, int price, String category) {
        this.id = id;
        this.price = price;
        this.category = category;
    }
}

class GroceryStore {
    static HashMap<String, Integer> inventory = new HashMap<>();
    static HashMap<String, Item> items = new HashMap<>();
    static HashMap<String, Integer> discounted_classes = new HashMap<>();

    static void printInventory() {
        inventory.forEach((key, value) -> System.out.println("Item id :- " + key + ", Left in store = " + value));
    }

    static void addItems(String id, Item item, int count) {
        if (!inventory.containsKey(id)) {
            inventory.put(id, count);
        } else {
            int totItems = inventory.get(id);
            totItems += count;
            inventory.put(id, totItems);
        }
        items.put(id, item);
    }

    static void removeItems(String id, int count) {
        inventory.put(id, inventory.get(id) - count);
    }

    static void add_discounted_classes(String category, int discount) {
        if (discounted_classes.containsKey(category)) {
            System.out.println("You have already inserted the discount for this category. Not inserting previous value");
            return;
        }
        discounted_classes.put(category, discount);
    }

    static int getPrice(String id) {
        int total = items.get(id).price;
        if (discounted_classes.containsKey(items.get(id).category)) {
            int discount = discounted_classes.get(items.get(id).category);
            total = (int) Math.round(total * (100 - discount) / 100.0);
        }
        return total;
    }

    static int get_amount_after_discount(int total, int discount) {
        return (int) Math.round(total * (100 - discount) / 100.0);
    }
}

public class register {
    static long total_sales = 0;

    static void generate_bill(ArrayList<Pair> list) {
        generate_bill(list, 0);
    }

    static void generate_bill(ArrayList<Pair> list, int discount) {
        print_bill(list, discount);
    }

    static void print_bill(ArrayList<Pair> list, int discount) {
        int total_bill = 0;
        for (Pair item : list) {
            int total = GroceryStore.getPrice(item.id) * item.count;
            total_bill += total;
            GroceryStore.removeItems(item.id, item.count);
            System.out.println("Item id :- " + item.id + ", count :- " + item.count + " total = " + total);
        }
        System.out.println("Your Total Bill = " + total_bill);
        total_sales += total_bill;
        if (discount > 0) {
            int discounted_bill = GroceryStore.get_amount_after_discount(total_bill, discount);
            System.out.println("Your discounted bill = " + discounted_bill);
            total_sales += discounted_bill - total_bill;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int total_items_to_add_in_inventory = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 1; i <= total_items_to_add_in_inventory; i++) {
            st = new StringTokenizer(br.readLine());
            String id = st.nextToken();
            int price = Integer.parseInt(st.nextToken());
            String category = st.nextToken();
            int count = Integer.parseInt(st.nextToken());
            GroceryStore.addItems(id, new Item(id, price, category), count);
        }
        GroceryStore.printInventory();
        ArrayList<Pair> list;
        int iterations = 0;
        /*
            As mentioned in the assignment to consider few iterations and then print total sales
            and inventory. So 2 iterations considered.
        */
        while (iterations < 2) {
            int items_to_purchase = Integer.parseInt(br.readLine());
            list = new ArrayList<>();
            for (int i = 1; i <= items_to_purchase; i++) {
                st = new StringTokenizer(br.readLine());
                String id = st.nextToken();
                int count = Integer.parseInt(st.nextToken());
                list.add(new Pair(id, count));
            }
            System.out.println("% discount to be given = ");
            st = new StringTokenizer(br.readLine());
            int discount = Integer.parseInt(st.nextToken());
            if (discount == 0)
                generate_bill(list);
            else {
                generate_bill(list, discount);
            }
            ++iterations;
        }
        System.out.println("Total Sales today: " + total_sales);
        GroceryStore.printInventory();

    }
}

class Pair {
    String id;
    int count;

    Pair(String id, int count) {
        this.id = id;
        this.count = count;
    }
}