import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    private String url;
    private String upvotes;
    private String keywords;
    private String budgetAmount;
    private String orAnd;
    private String fileDirectory;

    public Config(String fileDirectory) {
        this.fileDirectory = fileDirectory;
        gatherValuesFromTextFile();
    }

    private void gatherValuesFromTextFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileDirectory));
            String s;

            while((s = br.readLine()) != null) {
                if(s.startsWith("url")) {
                    String[] temp = s.split("url=");
                    this.url = temp[1].trim();
                } else if(s.startsWith("minimumUpvotes")) {
                    String[] temp = s.split("minimumUpvotes=");
                    this.upvotes = temp[1].trim();
                } else if(s.toLowerCase().startsWith("keywords")) {
                    String[] temp = s.split("keywords=");
                    this.keywords = temp[1].trim();
                } else if(s.startsWith("budgetAmount")) {
                    String[] temp = s.split("budgetAmount=");
                    this.budgetAmount = temp[1].trim();
                } else if(s.startsWith("orAnd")) {
                    String[] temp = s.split("orAnd=");
                    this.orAnd = temp[1].trim();
                }
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public String getKeywords() {
        return keywords;
    }

    public int getBudgetAmount() {
        return Integer.parseInt(budgetAmount);
    }

    public String getOrAnd() {
        return orAnd;
    }

}