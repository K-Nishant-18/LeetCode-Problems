//My solution
SELECT tweet_id 
FROM Tweets
WHERE CHAR_LENGTH(content)>15;

// Other solution
SELECT tweet_id 
FROM Tweets
WHERE LENGTH(content)>15;

/*
  If your tweets are ASCII-only (English letters, numbers, symbols), then LENGTH() and CHAR_LENGTH() give the same result — but LENGTH() is a little faster.
  If your tweets may contain Unicode characters, then LENGTH() may give the wrong result (too big).
/*
