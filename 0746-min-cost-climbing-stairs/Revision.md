
# LeetCode 746: Min Cost Climbing Stairs

- **Problem Number:** 746
- **Difficulty:** Easy
- **Topics:** Recursion, Memoization, Dynamic Programming
- **LeetCode:** https://leetcode.com/problems/min-cost-climbing-stairs/
- **Status:** Solved using Recursion + Memoization

---

## 1. Problem Statement

You are given an integer array `cost`, where `cost[i]` is the cost of stepping on stair `i`.

Once you pay the cost, you can climb either one or two steps.

You can start from stair `0` or stair `1`.

Return the minimum cost required to reach the top of the staircase. The top is just beyond the last stair.

### Example 1

**Input:**
```text
cost = [10, 15, 20]
```

**Output:**
```text
15
```

**Explanation:**

Start at stair `1`, pay `15`, and climb two steps directly to the top.

### Example 2

**Input:**
```text
cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
```

**Output:**
```text
6
```

**Explanation:**

The minimum-cost route pays a total of `6` to reach the top.

---

## 2. Core Intuition

To reach stair `i`, we can come from either:

- Stair `i - 1`
- Stair `i - 2`

We want the cheaper of these two routes.

Therefore, the recurrence is:

```text
dp[i] = cost[i] + min(dp[i - 1], dp[i - 2])
```

Here, `dp[i]` represents the minimum total cost required to land on stair `i`, including the cost of that stair.

### Base Cases

```java
if (n == 0) return cost[0];
if (n == 1) return cost[1];
```

We can start directly at either of the first two stairs, so their minimum costs are their individual costs.

### Important: Reaching the Top

The top is beyond the last stair. We do not pay a cost for the top itself.

If `n = cost.length`, the answer is:

```text
min(dp[n - 1], dp[n - 2])
```

We can reach the top from either of the last two stairs.

---

## 3. Approach 1: Basic Recursion

### My Original Solution

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length - 1;

        return Math.min(
            minCost(cost, n),
            minCost(cost, n - 1)
        );
    }

    public int minCost(int[] cost, int n) {
        if (n == 0) return cost[0];
        if (n == 1) return cost[1];

        return cost[n] + Math.min(
            minCost(cost, n - 1),
            minCost(cost, n - 2)
        );
    }
}
```

### How It Works

The helper method calculates the minimum cost of reaching a particular stair.

For every stair after the base cases:

1. Recursively calculate the minimum cost to reach the previous stair.
2. Recursively calculate the minimum cost to reach the stair before that.
3. Choose the cheaper route.
4. Add the cost of the current stair.

Finally, take the minimum of the costs of reaching the last two stairs.

### Dry Run: `cost = [10, 15, 20]`

Base cases:

```text
minCost(0) = 10
minCost(1) = 15
```

Calculate the cost of stair `2`:

```text
minCost(2)
= cost[2] + min(minCost(1), minCost(0))
= 20 + min(15, 10)
= 30
```

Calculate the final answer:

```text
answer = min(minCost(2), minCost(1))
       = min(30, 15)
       = 15
```

### Complexity

- **Time:** O(2^n), an upper bound due to repeated recursive calculations.
- **Auxiliary Space:** O(n) for the recursion stack.

### Limitation

The same subproblems are calculated multiple times, which can lead to Time Limit Exceeded (TLE).

---

## 4. Approach 2: Recursion + Memoization

### Idea

Memoization stores the result of each subproblem so it can be reused instead of recalculated.

We use a `HashMap<Integer, Integer>`:

- **Key:** Stair index.
- **Value:** Minimum cost to reach that stair.

### My Submitted Memoization Solution

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    Map<Integer, Integer> map = new HashMap<>();

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length - 1;

        return Math.min(
            minCost(cost, n),
            minCost(cost, n - 1)
        );
    }

    public int minCost(int[] cost, int n) {
        if (map.containsKey(n)) {
            return map.get(n);
        }

        if (n == 0) return cost[0];
        if (n == 1) return cost[1];

        int k = cost[n] + Math.min(
            minCost(cost, n - 1),
            minCost(cost, n - 2)
        );

        map.put(n, k);

        return k;
    }
}
```

### How It Works

**Step 1: Check the cache**

```java
if (map.containsKey(n)) {
    return map.get(n);
}
```

If the minimum cost for this stair has already been calculated, return it immediately.

**Step 2: Handle base cases**

```java
if (n == 0) return cost[0];
if (n == 1) return cost[1];
```

**Step 3: Calculate the minimum cost**

```java
int k = cost[n] + Math.min(
    minCost(cost, n - 1),
    minCost(cost, n - 2)
);
```

**Step 4: Store the result**

```java
map.put(n, k);
```

**Step 5: Return the result**

```java
return k;
```

### Dry Run: `cost = [10, 15, 20]`

The calculation proceeds as follows:

| Stair | Calculation | Minimum cost |
|---|---|---:|
| 0 | Base case: `cost[0]` | 10 |
| 1 | Base case: `cost[1]` | 15 |
| 2 | `20 + min(15, 10)` | 30 |

The final result is:

```text
min(30, 15) = 15
```

The map stores the result for stair `2`:

```text
{2 = 30}
```

The base cases are returned directly and are not stored in the map.

### Complexity

- **Time:** O(n), because each stair's non-base subproblem is calculated at most once per cache.
- **Auxiliary Space:** O(n) for the map and recursion stack combined.

### Improvement: Keep the Cache Local

In my solution, `map` is an instance variable. If the same `Solution` object is reused for a different `cost` array, cached results from the previous array could be incorrect.

A cleaner design creates a fresh cache for each invocation and passes it to a helper method.

For example:

```java
public int minCostClimbingStairs(int[] cost) {
    Map<Integer, Integer> memo = new HashMap<>();
    int n = cost.length;

    return Math.min(
        minCost(cost, n - 1, memo),
        minCost(cost, n - 2, memo)
    );
}
```

The helper would accept `Map<Integer, Integer> memo` as an additional parameter and use that map for cache lookups and storage.

---

## 5. Approach 3: Bottom-Up Dynamic Programming

### Idea

Instead of starting from the last stair and recursively solving smaller problems, calculate the minimum costs from the beginning.

We know:

```text
dp[0] = cost[0]
dp[1] = cost[1]
```

Then:

```text
dp[i] = cost[i] + min(dp[i - 1], dp[i - 2])
```

Finally:

```text
answer = min(dp[n - 1], dp[n - 2])
```

### Code

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;

        if (n == 2) {
            return Math.min(cost[0], cost[1]);
        }

        int prev2 = cost[0];
        int prev1 = cost[1];

        for (int i = 2; i < n; i++) {
            int current = cost[i] + Math.min(prev1, prev2);

            prev2 = prev1;
            prev1 = current;
        }

        return Math.min(prev1, prev2);
    }
}
```

### How It Works

- `prev2` stores the minimum cost to reach the stair two positions behind.
- `prev1` stores the minimum cost to reach the previous stair.
- `current` calculates the minimum cost for the current stair.
- The two variables are updated after each iteration.

Only the previous two results are needed, so we do not need a full DP array.

### Dry Run: `cost = [10, 15, 20]`

Initial values:

```text
prev2 = 10
prev1 = 15
```

Calculate stair `2`:

```text
current = 20 + min(15, 10)
        = 30
```

Update:

```text
prev2 = 15
prev1 = 30
```

Final answer:

```text
min(prev1, prev2)
= min(30, 15)
= 15
```

### Complexity

- **Time:** O(n)
- **Auxiliary Space:** O(1)

This approach avoids both repeated calculations and the recursion stack.

---

## 6. Approach Comparison

| Approach | Time Complexity | Auxiliary Space | Main Idea |
|---|---|---|---|
| Basic recursion | O(2^n) | O(n) | Recalculate subproblems |
| Memoization | O(n) | O(n) | Cache computed results |
| Bottom-up DP | O(n) | O(1) | Build answers using two variables |

---

## 7. Mistakes and Lessons Learned

### Mistake 1: Returning the cost of the last stair directly

The problem asks for the minimum cost to reach the top, not necessarily to land on the last stair.

**Lesson:** Compare the costs of reaching the last two stairs.

```java
return Math.min(minCost(cost, n), minCost(cost, n - 1));
```

Here, `n` is the last valid stair index.

### Mistake 2: Forgetting array index boundaries

If `cost.length == 3`, valid indices are `0`, `1`, and `2`. Accessing `cost[3]` throws an `ArrayIndexOutOfBoundsException`.

**Lesson:** Distinguish between the last valid index (`cost.length - 1`) and the top position (`cost.length`).

### Mistake 3: Recalculating subproblems

Basic recursion repeats the same calculations.

**Lesson:** Use memoization when the recursive tree contains overlapping subproblems.

### Mistake 4: Reusing a cache across different inputs

A cache keyed only by stair index can return incorrect results if it is reused for another cost array.

**Lesson:** Keep the memoization cache local to each problem invocation.

---

## 8. Key Takeaways

- This problem is about finding the minimum cost, not counting paths.
- The recurrence is:

```text
dp[i] = cost[i] + min(dp[i - 1], dp[i - 2])
```

- Base cases:

```text
dp[0] = cost[0]
dp[1] = cost[1]
```

- Final answer:

```text
min(dp[n - 1], dp[n - 2])
```

- Memoization is a top-down dynamic programming technique.
- Bottom-up DP calculates results iteratively.
- Space optimization works because each state depends only on the previous two states.

---

## 9. Quick Revision Questions

1. Why do we use `Math.min()` instead of addition?
2. Why is the cost of the top not included?
3. Why is the final answer the minimum of the last two stair costs?
4. Why can basic recursion cause TLE?
5. How does memoization avoid repeated calculations?
6. Why should the cache be local to each invocation?
7. How can we reduce auxiliary space to O(1)?
8. What is the difference between memoization and tabulation?

---

## Final Summary

**Problem:** LeetCode 746 — Min Cost Climbing Stairs

**Recurrence:**

```text
dp[i] = cost[i] + min(dp[i - 1], dp[i - 2])
```

**Final answer:**

```text
min(dp[n - 1], dp[n - 2])
```

**Best space-optimized approach:**
Bottom-up Dynamic Programming

- **Time Complexity:** O(n)
- **Auxiliary Space:** O(1)
