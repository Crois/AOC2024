package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedNoseReports {

    public static final boolean TEST = true;
    public static final String DATA_DAY_1_EXAMPLE_PUZZLE_1 = "data/day2/example/puzzle1";
    public static final String DATA_DAY_1_TEST_PUZZLE_1 = "data/day2/test/puzzle1";

    public static void main(String[] args) {
        List<List<Integer>> reports = readReports(
                        TEST ? DATA_DAY_1_TEST_PUZZLE_1 : DATA_DAY_1_EXAMPLE_PUZZLE_1);
        //        for (List<Integer> report : reports) {
        //            System.out.println(Arrays.toString(report.toArray()));
        //        }
        boolean withBadLevel = true;
        AbstractMap.SimpleEntry<List<List<Integer>>, List<List<Integer>>> checkedReports = checkReports(
                        reports, withBadLevel);
        System.out.println("Safe Report (" + checkedReports.getKey().size() + "): "
                        + checkedReports.getKey() + "\n Unsafe Reports ("
                        + checkedReports.getValue().size() + "): " + checkedReports.getValue());
    }

    private static AbstractMap.SimpleEntry<List<List<Integer>>, List<List<Integer>>> checkReports(
                    List<List<Integer>> reports, boolean withBadLevel) {
        List<List<Integer>> safeReports = new ArrayList<>();
        List<List<Integer>> unsafeReports = new ArrayList<>();
        for (List<Integer> report : reports) {
            boolean safe;
            if (withBadLevel) {
                safe = checkReportWithBadLevel(report);
            } else {
                safe = checkReport(report);
            }
            if (safe) {
                safeReports.add(report);
            } else {
                unsafeReports.add(report);
            }
        }
        return new AbstractMap.SimpleEntry<>(safeReports, unsafeReports);
    }

    private static boolean checkReportWithBadLevel(List<Integer> report) {
        int lastNumber = 0;
        boolean decreasing = false;
        boolean increasing = false;
        for (int x = 0; x < report.size(); x++) {
            int i = report.get(x);
            int difference = i - lastNumber;

            if (lastNumber != 0) {
                if (difference > 0) {
                    increasing = true;
                } else if (difference < 0) {
                    decreasing = true;
                } else {
                    return checkBadLevelReport(report);
                }
                if (Math.abs(difference) > 3) {
                    return checkBadLevelReport(report);
                }
            }
            if (decreasing && increasing) {
                return checkBadLevelReport(report);
            }
            lastNumber = i;
        }
        return true;
    }

    private static boolean checkBadLevelReport(List<Integer> report) {
        boolean badLevelReportPossible = false;
        for (int i = 0; i < report.size(); i++) {
            badLevelReportPossible = badLevelReportPossible || checkReport(getCopyWithoutBadLevel(report, i));
        }
        return badLevelReportPossible;
    }

    private static List<Integer> getCopyWithoutBadLevel(List<Integer> report, int x) {
        List<Integer> copy = new ArrayList<>(List.copyOf(report));
        copy.remove(x);
        return copy;
    }

    private static boolean checkReport(List<Integer> report) {
        int lastNumber = 0;
        boolean decreasing = false;
        boolean increasing = false;
        for (Integer i : report) {
            int difference = i - lastNumber;

            if (lastNumber != 0) {
                if (difference > 0) {
                    increasing = true;
                } else if (difference < 0) {
                    decreasing = true;
                } else {
                    return false;
                }
                if (Math.abs(difference) > 3) {
                    return false;
                }
            }
            if (decreasing && increasing) {
                return false;
            }
            lastNumber = i;
        }
        return true;
    }

    private static List<List<Integer>> readReports(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<List<Integer>> reports = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                reports.add(readReport(line));
            }
            return reports;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Integer> readReport(String line) {
        String regex = "\\d+";
        List<Integer> report = new ArrayList<>();
        // Pattern und Matcher initialisieren
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            report.add(Integer.valueOf(matcher.group()));
        }
        return report;
    }

}
