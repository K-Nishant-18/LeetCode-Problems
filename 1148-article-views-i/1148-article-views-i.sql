# Write your MySQL query statement below
SELECT DISTINCT author_id AS id
FROM Views
WHERE author_id = viewer_id
ORDER BY id;

// Faster Solution
SELECT author_id AS id
FROM Views
WHERE author_id = viewer_id
GROUP BY author_id
ORDER BY id;
/*
why GROUP BY is faster then SELECT DISTINCT:

DISTINCT: the engine has to look at all rows, sort or hash them, then remove duplicates.
GROUP BY: the engine is already built to aggregate rows into groups. If there’s an index on author_id, it can group very efficiently without scanning everything in a “dedupe” way.

So in big datasets, GROUP BY sometimes uses the index more directly, while DISTINCT may involve an extra sort step.
*/


// Even more faster solution
SELECT DISTINCT author_id AS id
FROM Views
WHERE author_id = viewer_id
ORDER BY author_id;
/*
⚡ Which is faster and why?
✅ ORDER BY author_id is faster.

🔍 Reason:
In MySQL, using the real column name (author_id) in ORDER BY allows the query optimizer to use indexes, reducing sorting time.
Using an alias (id) requires MySQL to first evaluate the SELECT clause and resolve the alias, which can block index usage during the sort step.
On large datasets, this difference can cause significant performance degradation.
/*
