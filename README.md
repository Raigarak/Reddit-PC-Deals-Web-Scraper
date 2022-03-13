# RedditWebScraper

I made this program mainly for this subreddit https://old.reddit.com/r/buildapcsales/ , but it can be easily modified for any other subreddit. (This program does work for any subreddit, but the features
are optimized for this one). The webscraper will return any thread link that contains all the parameters you described below, saving time since you don't have to manually check the subreddit every few hours.

Setup -
Make a text file called reddit.txt 

url=https://old.reddit.com/r/buildapcsales/
minimumUpvotes=40
keywords=[cpu],[ram],[case]
budgetAmount=500
orAnd=or

Copy and paste the above, you can modify any of the variables. 
url = which subreddit you want the webscraper to scrape information from.
minimumUpvotes = It will only scrape the threads that have an upvote value higher than this amount.
keywords = It will scrape any thread title that contain any of these words.
budgetAmount = It will scrape any thread title that contains a lower value than this amount. 

IE. If thread title is "GPU $499" (it will scrape this because it's below budgetAmount) 
If thread title is "CPU $600" (it won't scrape this because budgetAmount is $500)

orAnd = or means if any of the variables above is true then scrape that thread. and means ALL THE VARIABLES ABOVE have to be true for it to scrape the thread.

After you finish with all the parameters above, just compile/run the program and paste the directory where the .txt file is located. The webscraper will check every 5 minutes, you can change this by
going into the Main.java file and look for scheduler.scheduleAtFixedRate(runnable,0,5,TimeUnit.MINUTES); at line 26.
