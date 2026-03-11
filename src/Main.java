public class Main {
    public static void main(String[] args) {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.insert("Hello", 1);
        dictionary.insert("World", 2);
        dictionary.insert("Random", 3);
        dictionary.insert("Words", 4);
        dictionary.insert("Here", 5);

        Integer result1 = dictionary.get("Hello");
        Integer result2 = dictionary.get("World");
        Integer result3 = dictionary.get("Random");
        Integer result4 = dictionary.get("Words");
        Integer result5 = dictionary.get("Here");
        System.out.println("Result: " + result1);
        System.out.println("Result: " + result2);
        System.out.println("Result: " + result3);
        System.out.println("Result: " + result4);
        System.out.println("Result: " + result5);
    }
}
