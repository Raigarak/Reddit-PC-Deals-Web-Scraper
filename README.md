# RedditWebScraper

I made this program mainly for this subreddit https://old.reddit.com/r/buildapcsales/ , but it can be easily modified for any other subreddit. The webscraper will return any thread link that contains all the parameters you described below, saving time since you don't have to manually check the subreddit every hour/day.

**Setup**

`url` = determines which subreddit you want the webscraper to scrape information from. (Note you have to use the old version of reddit, just replace www with old)

`minimumUpvotes` = It will only scrape the threads that have an upvote value higher than this amount.

`keywords` = It will scrape any thread title that contain any of these words.

`budgetAmount` = It will scrape any thread title that contains a lower value than this amount. (This value does not get subtracted. For example if you had this value set to 200. If it scraped a $200 graphics card, the budgetAmount will still be $200 and keep scraping items that are below this amount)

`orAnd` = or means if any of the variables above is true then it will scrape the thread. And means all the parameters above have to be true for it to scrape the thread.

**How to change duration between each scrape**

The webscraper will check every 5 minutes, you can change this by going into the `Main.java`file and look for 

`scheduler.scheduleAtFixedRate(runnable,0,5,TimeUnit.MINUTES);` line 26.

Days - `TimeUnit.DAYS`

Hours - `TimeUnit.HOURS`

Minutes - `TimeUnit.MINUTES`

Seconds - `TimeUnit.SECONDS`

The first digit in the parameter means how many minutes to wait for before starting the first search. (0 in the example above)

The second digit in the parameter means the duration between each scrape, 5 minutes between each scrape in the example above.

For more information, please check the java docs [TimeUnit docs](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeUnit.html) [ExecutorService](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)

**After configuration** 
After you finish defining all the parameters above, just run the program and paste the directory with the txt file name (ie. D:\reddit.txt). 
