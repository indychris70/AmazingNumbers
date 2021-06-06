package numbers;

import java.util.Scanner;

public class Main {
    enum Messages {
        WELCOME("Welcome to Amazing Numbers!\n"),
        INSTRUCTIONS("Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit."),
        PROMPT("Enter a request:"),
        EXIT_MESSAGE("Goodbye!");

        private String text;

        Messages(String text) {
            this.text = text;
        }

        public void print() {
            System.out.println(text);
        }
    }

    public static void main(String[] args) {
        run();
    }

    // Private methods

    private static void run() {
        Messages.WELCOME.print();
        Messages.INSTRUCTIONS.print();
        Input input = new Input(getInput());
        Controller controller = Controller.newController();

        while (!input.exitCommandReceived()) {
            if (input.isValid()) {
                controller.setInput(input);
                controller.print();
            } else {
                input.printErrors();
            }
            input = new Input(getInput());
        }
        Messages.EXIT_MESSAGE.print();
    }

    private static String getInput() {
        Scanner s = new Scanner(System.in);
        Messages.PROMPT.print();
        String input = s.nextLine();
        while (input.isEmpty()) {
            Messages.INSTRUCTIONS.print();
            Messages.PROMPT.print();
            input = s.nextLine();
        }
        return input;
    }
}
