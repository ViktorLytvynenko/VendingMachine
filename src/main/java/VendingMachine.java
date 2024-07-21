import java.util.Scanner;

public class VendingMachine {
    public static double[] banknotes = {100.0, 50.0, 20.0, 10.0, 5.0, 2.0, 1.0, 0.5, 0.25, 0.1, 0.05, 0.02, 0.01};
    public static int[] banknotesAvailable = {5, 10, 0, 2, 4, 5, 5, 5, 5, 5, 5, 5, 5};

    public static void game() {
        assortment();
    }

    public static void assortment() {
        Scanner scanner = new Scanner(System.in);

        String[] assortment = {"cola 12 1.15", "bubbleGum 100 0.3", "fanta 10 1.15", "sprite 11 1.15", "chocolate 11 1.75"};
        for (int i = 0; i < assortment.length; i++) {
            String[] product = assortment[i].split(" ");
            String productProposition = product[0] + " - price: $" + product[2];
            System.out.println(i + " " + productProposition);
        }

        int choiceId;

        do {
            System.out.println("Choose number of product");
            choiceId = scanner.nextInt();
            if (choiceId < 0 || choiceId >= assortment.length) {
                System.out.println("Wrong number");
            }

        } while (choiceId < 0 || choiceId >= assortment.length);


        int choiceQuantity;
        int productQuantity;
        double productPrice;

        String[] product = assortment[choiceId].split(" ");

        do {
            System.out.println("What quantity do you want?");
            choiceQuantity = scanner.nextInt();
            productQuantity = Integer.parseInt(product[1]);
            productPrice = Double.parseDouble(product[2]);
            if (choiceQuantity < 0 || choiceQuantity >= productQuantity) {
                System.out.println("Wrong quantity or insufficient quantity of product");
            }

        } while (choiceQuantity < 0 || choiceQuantity >= productQuantity);

        productQuantity -= choiceQuantity;
        assortment[choiceId] = product[0] + " " + productQuantity + " " + productPrice;

        putCash(productPrice, choiceQuantity);
    }

    public static void putCash(double productPrice, int choiceQuantity) {
        double requiredSum = productPrice * choiceQuantity;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Put please $" + requiredSum + " into the vending machine");

        double cash = 0.0;
        do {
            double banknote = scanner.nextDouble();
            System.out.println("You put $" + banknote);
            cash += banknote;
            cash = Math.round(cash * 100) / 100.0;
            System.out.println("Left $" + Math.round((requiredSum - cash) * 100) / 100.0);
        }
        while (cash < requiredSum);

        for (int i = 0; i < banknotes.length; i++) {
            if (cash == banknotes[i]) {
                banknotesAvailable[i] += 1;
            }
        }

        if (cash == requiredSum) {
            System.out.println("Here is your product(s)");
            return;
        }
        double changeSum = Math.round((cash - requiredSum) * 100) / 100.0;
        change(changeSum);
    }

    public static void change(double changeSum) {
        int[] banknotesChange = new int[banknotes.length];

        for (int i = 0; i < banknotes.length; i++) {
            if (changeSum == 0.0) {
                break;
            }

            if (changeSum / banknotes[i] > 1 && banknotesAvailable[i] > 0) {
                int banknoteQuantity = (int) (changeSum / banknotes[i]);

                if (banknotesAvailable[i] < banknoteQuantity) {
                    banknoteQuantity = banknotesAvailable[i];
                }
                changeSum = changeSum - (banknotes[i] * banknoteQuantity);
                banknotesChange[i] = banknoteQuantity;
                banknotesAvailable[i] -= banknoteQuantity;
            }
        }
        System.out.println("Your change:");
        for (int i = 0; i < banknotesChange.length; i++) {
            if (banknotesChange[i] > 0) {
                System.out.println(banknotesChange[i] + " : $" + banknotes[i]);
            }
        }
        System.out.println("Here is your product(s)");
    }
}
