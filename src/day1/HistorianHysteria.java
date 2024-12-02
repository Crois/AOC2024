package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HistorianHysteria {

    public static final boolean TEST = true;
    public static final String DATA_DAY_1_EXAMPLE_PUZZLE_1 = "data/day1/example/puzzle1";
    public static final String DATA_DAY_1_TEST_PUZZLE_1 = "data/day1/test/puzzle1";

    public static void main(String[] args) {
        AbstractMap.SimpleEntry<List<Integer>, List<Integer>> sortedLists = readLists(TEST ?
                        DATA_DAY_1_TEST_PUZZLE_1 : DATA_DAY_1_EXAMPLE_PUZZLE_1);
        int dist = calculateDists(sortedLists.getKey(), sortedLists.getValue());
        int similarity = calculateSimilarity(sortedLists.getKey(), sortedLists.getValue());
        System.out.printf("Distance = %d%n", dist);
        System.out.printf("Similarity = %d%n", similarity);
    }

    private static int calculateSimilarity(List<Integer> left, List<Integer> right) {
        int similaritySum = 0;
        for (int i = 0; i < left.size(); i++) {
            similaritySum += calulateSimilarityPoint(left.get(i), right);
        }
        return similaritySum;
    }

    private static int calulateSimilarityPoint(Integer integer, List<Integer> right) {
        int similarityPoint = 0;
        for (Integer rightPoint : right
             ) {
            if (Objects.equals(integer, rightPoint)) {
                similarityPoint += integer;
            }
        }
        return similarityPoint;
    }

    private static int calculateDists(List<Integer> left, List<Integer> right) {
        int distSum = 0;
        for (int i = 0; i < left.size(); i++) {
            distSum += calulateDistPoint(left.get(i), right.get(i));
        }
        return distSum;
    }

    private static int calulateDistPoint(Integer left, Integer right) {
        return (int) euklideanDistance(left, right, 0, 0);
    }

    private static double euklideanDistance(int x1, int x2, int y1, int y2){
        // Distanz berechnen
        return  Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private static AbstractMap.SimpleEntry<List<Integer>, List<Integer>> readLists(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int sum = 0;
            List<Integer> leftList = new ArrayList<>();
            List<Integer> rightList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                addNumbersToList(line, leftList, rightList);
            }
            Stream<Integer> sortedLeft = leftList.stream().sorted();
            Stream<Integer> sortedRight = rightList.stream().sorted();

            return new AbstractMap.SimpleEntry<>(sortedLeft.toList(), sortedRight.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void addNumbersToList(String line, List<Integer> leftList,
                    List<Integer> rightList) {
        String regex = "\\d+";

        // Pattern und Matcher initialisieren
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()){
            leftList.add(Integer.valueOf(matcher.group()));
        }
        if(matcher.find()){
            rightList.add(Integer.valueOf(matcher.group()));
        }
    }
}
