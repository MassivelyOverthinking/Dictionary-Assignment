import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Dictionary<String, Integer> dictionary = new Dictionary<>(10, 0.5);

        dictionary.insert("Hello", 1);
        dictionary.insert("World", 2);
        dictionary.insert("Random", 3);
        dictionary.insert("Words", 4);
        dictionary.insert("Here", 5);
        dictionary.insert("!", 6);

        Integer result1 = dictionary.get("Hello");
        Integer result2 = dictionary.get("World");
        Integer result3 = dictionary.get("Random");
        Integer result4 = dictionary.get("Words");
        Integer result5 = dictionary.get("Here");
        Integer result6 = dictionary.get("!");
        System.out.println("Result: " + result1);
        System.out.println("Result: " + result2);
        System.out.println("Result: " + result3);
        System.out.println("Result: " + result4);
        System.out.println("Result: " + result5);
        System.out.println("Result: " + result6);   // To determine Rehash-functionality.
        System.out.println("--------------- After Rehash ---------------");
        Integer result7 = dictionary.get("Hello");
        Integer result8 = dictionary.get("World");
        Integer result9 = dictionary.get("Random");
        Integer result10 = dictionary.get("Words");
        Integer result11 = dictionary.get("Here");
        System.out.println("Result: " + result7);
        System.out.println("Result: " + result8);
        System.out.println("Result: " + result9);
        System.out.println("Result: " + result10);
        System.out.println("Result: " + result11);
        System.out.println("--------------- Keys & Values ---------------");

        ArrayList<String> keysList = dictionary.keys();
        ArrayList<Integer> valuesList = dictionary.values();
        System.out.println("All Keys: " + keysList);
        System.out.println("All Values: " + valuesList);
    }
}
