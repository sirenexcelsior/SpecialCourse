public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        String expression = "13 + (3 - 4) * 5";
        System.out.println("Result: " + calculator.evaluate(expression));
    }
}
