import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Brief explanation of algorithm.
 *
 * I put each char and count of occurrences to map
 * Then I check each char of input string against created map, if exist in map then i subtract the occurrences
 * At the end, if every char occurrence state in map is 0 - we know we have found an anagram
 *
 * Optimization check - if string lengths are not equal, then we can't have an anagram.
 *
 * Using HasMap as a data structure because Get and ContainsKey time complexity is O(1) e.g perfect for this problem.
 */
public class AnagramFinder {

    public static void main(String[] argv) throws IOException {
        long startTime = System.currentTimeMillis();
        String result = getAnagrams(argv[0],argv[1]);
        long stop = System.currentTimeMillis() - startTime;
        System.out.println((stop/1000.0) + "," + result);
    }

    private static String getAnagrams(String filePath, String word) throws IOException {

        List<String> anagrams = new ArrayList<>();
        AtomicBoolean isNotAnagram = new AtomicBoolean(false);

        String current;

        InputStreamReader inputReader = new InputStreamReader(new FileInputStream(filePath), "utf-8");
        BufferedReader bufferReader = new BufferedReader(inputReader);

        while ((current = bufferReader.readLine()) != null) {
            HashMap<Character, Integer> charMap = new HashMap<>();
            isNotAnagram.set(false);

            if (isWordLengthsEqual(current, word)) {
                current.chars().forEach(c -> charMap.merge((char) c, 1, Integer::sum));
                word.chars().forEach(c -> charMap.merge((char) c, -1, Integer::sum));

                charMap.values().forEach(i -> {
                    if (i != 0) isNotAnagram.set(true);
                });

                if (!isNotAnagram.get()) {
                    anagrams.add(current);
                }
            }
        }
        return anagrams.stream().collect(Collectors.joining(","));
    }

    private static boolean isWordLengthsEqual(String s1, String s2) {
        return s1.length() == s2.length();
    }

}
