import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        generateRouteAnalyze();
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void generateRouteAnalyze() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                synchronized (sizeToFreq) {
                    String result = generateRoute("RLRFR", 100);
                    int sumR = 0;
                    for (char c : result.toCharArray()) {
                        if (c == 'R') {
                            sumR++;
                        }
                    }
                    if (sizeToFreq.containsKey(sumR)) {
                        sizeToFreq.put(sumR, sizeToFreq.get(sumR) + 1);
                    } else {
                        sizeToFreq.put(sumR, 1);
                    }
                }
            }
            ).start();
        }
        int maxEntry = Collections.max(sizeToFreq.entrySet(), Map.Entry.comparingByValue()).getValue();
        int key = 0;
        for (Map.Entry<Integer, Integer> item : sizeToFreq.entrySet()) {
            if (maxEntry == item.getValue()) {
                key = item.getKey();
                break;
            }
        }
        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\n", key, maxEntry);
        sizeToFreq.remove(key);
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> map : sizeToFreq.entrySet()) {
            System.out.printf("- %d (%d раз) \n", map.getKey(), map.getValue());
        }
    }
}