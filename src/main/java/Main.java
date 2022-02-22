import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        DateAndTime dateAndTime = new DateAndTime();
        Config config = new Config(getTextFileDirectory());
        RedditScraper redditScraper = new RedditScraper(config);
        HashSet alreadyPrinted = new HashSet();

        final ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(2);
        Runnable runnable = () -> {
            // Runs web scrapper to gather new data (if any)
            redditScraper.rerRunWebScrapper();

            for(String s: redditScraper.getResultsList()) {
                if(!alreadyPrinted.contains(s))
                    System.out.println(dateAndTime.getCurrentDateAndTime() + "\n" + s + "\n");
                alreadyPrinted.add(s);
            }
        }; scheduler.scheduleAtFixedRate(runnable,0,5,TimeUnit.MINUTES);
    }

    private static String getTextFileDirectory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please insert your text file directory!");
        String directory = scanner.nextLine();
        return directory;
    }
}
