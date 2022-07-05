import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedditScraper {

    private List<String> threadTitleList = new ArrayList();
    private List<String> dealUrlList = new ArrayList();
    private List<String> upvotesList = new ArrayList();
    private List<String> resultsList = new ArrayList<>();
    private List<String> redditThreadLinkList = new ArrayList<>();
    private StringBuilder sb = new StringBuilder();
    private int index = 0;
    private Config config;

    public RedditScraper(Config config) {
        this.config = config;
        extractData();
        addToResultListIfValidThreads();
    }

    public void reRunWebScrapper() {
        extractData();
        addToResultListIfValidThreads();
    }

    public void extractData() {
        try {
            Document doc = Jsoup.connect(config.getUrl()).get();
            Element siteTable = doc.selectFirst("div#siteTable");
            Elements posts = siteTable.select("div.thing");

            for (Element post : posts) {

                //If thread is an ad, do nothing
                if (!post.hasAttr("data-adserver-imp-pixel") || !post.hasAttr("data-adserver-impression-id")) {
                    String dealUrl = post.attr("data-url");
                    dealUrlList.add(dealUrl);

                    //Advertisements give weird symbol for upvote value. This fixes it
                    String upvotes = "";
                    Element scoreElement = post.selectFirst("div.score.unvoted");
                    upvotes = scoreElement.ownText();
                    if (!upvotes.equals("•")) {
                        upvotesList.add(upvotes);
                    } else {
                        upvotesList.add("0");
                    }

                    Element threadTitle = post.selectFirst("a.title");
                    threadTitleList.add(threadTitle.ownText());

                    String redditLink = "https://old.reddit.com" + post.attr("data-permalink");
                    redditThreadLinkList.add(redditLink);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if each thread contains budgetAmount or upvoteAmount or keywords
    public void addToResultListIfValidThreads() {
        for (String threadTitle : threadTitleList) {
            if (config.getOrAnd().equals("or")) {
                if (isBudgetAmount(threadTitle) || hasKeywordsInThreadTitle(threadTitle) || isEnoughUpvote()) {
                    sb.append(threadTitleList.get(index) + "\n" + dealUrlList.get(index) + "\n" + redditThreadLinkList.get(index));
                    if (!resultsList.contains(sb.toString())) {
                        resultsList.add(sb.toString());
                    }
                    sb.setLength(0);
                }
            } else if (config.getOrAnd().equals("and")) {
                if (isBudgetAmount(threadTitle) && hasKeywordsInThreadTitle(threadTitle) && isEnoughUpvote()) {
                    sb.append(threadTitleList.get(index) + "\n" + dealUrlList.get(index) + "\n" + redditThreadLinkList.get(index));
                    if (!resultsList.contains(sb.toString())) {
                        resultsList.add(sb.toString());
                    }
                    sb.setLength(0);
                }
            }
            index++;
        }
        index = 0;
    }

    private boolean hasKeywordsInThreadTitle(String threadTitle) {
        String[] eachKeyword = config.getKeywords().split(",");
        for (String keyword : eachKeyword) {
            if (threadTitle.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBudgetAmount(String threadTitle) {
        Pattern pattern = Pattern.compile("\\$[0-9]+");
        Matcher matcher = pattern.matcher(threadTitle);
        if (matcher.find()) {
            int cost = Integer.parseInt(new String(matcher.group()).substring(1));
            if (cost < config.getBudgetAmount()) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnoughUpvote() {
        if (Integer.parseInt(upvotesList.get(index)) >= Integer.parseInt(config.getUpvotes())) {
            return true;
        }
        return false;
    }

    public List<String> getResultsList() {
        return resultsList;
    }

}