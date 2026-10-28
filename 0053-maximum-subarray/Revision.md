
# LeetCode 53: Maximum Subarray

- **Difficulty:** Medium
- **Topics:** Array, Dynamic Programming, Greedy
- **Language:** Java
- **LeetCode:** https://leetcode.com/problems/maximum-subarray/
- **Approach:** Kadane's Algorithm
- **Status:** Solved

---

## 1. Problem Statement

Given an integer array `nums`, find the subarray with the largest sum and return its sum.

A **subarray** is a contiguous, non-empty sequence of elements within an array.

### Example 1

**Input:**
```text
nums = [-2,1,-3,4,-1,2,1,-5,4]
```

**Output:**
```text
6
```

**Explanation:**

The subarray `[4,-1,2,1]` has the maximum sum:

```text
4 + (-1) + 2 + 1 = 6
```

### Example 2

**Input:**
```text
nums = [1]
```

**Output:**
```text
1
```

### Example 3

**Input:**
```text
nums = [5,4,-1,7,8]
```

**Output:**
```text
23
```

**Explanation:**

The entire array has the maximum sum:

```text
5 + 4 + (-1) + 7 + 8 = 23
```

---

## 2. Core Intuition

The key question is:

**Should we extend the current subarray or start a new one?**

Suppose the running sum is negative. Adding it to a future element makes that element's resulting sum smaller than starting fresh.

For example:

```text
Current sum = -5
Next element = 4

Extending: -5 + 4 = -1
Starting fresh: 4
```

Starting fresh is better.

This leads to Kadane's Algorithm.

### Two Important Variables

- `currSum`: The maximum sum of a subarray ending at the current position.
- `maxSum`: The maximum subarray sum found anywhere so far.

### Algorithm

1. Initialize `currSum = 0`.
2. Initialize `maxSum = nums[0]`.
3. Traverse the array.
4. If `currSum` is negative, reset it to `0`.
5. Add the current element to `currSum`.
6. Update `maxSum` if `currSum` is larger.
7. Return `maxSum`.

---

## 3. My Original Java Solution

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int currSum = 0;
        int maxSum = nums[0];

        for (int i = 0; i < nums.length; i++) {
            if (currSum < 0) {
                currSum = 0;
            }

            currSum = currSum + nums[i];

            if (currSum > maxSum) {
                maxSum = currSum;
            }
        }

        return maxSum;
    }
}
```

### How It Works

**Step 1: Initialize the variables**

```java
int currSum = 0;
int maxSum = nums[0];
```

`currSum` begins at zero because no elements have been processed yet.

`maxSum` begins at the first element, not zero. This is essential when every element is negative.

**Step 2: Discard a negative running sum**

```java
if (currSum < 0) {
    currSum = 0;
}
```

A negative prefix can only reduce the sum of any subarray extended from it. Therefore, discard it before processing the next element.

**Step 3: Include the current element**

```java
currSum = currSum + nums[i];
```

Now `currSum` represents the sum of the best subarray ending at the current position.

**Step 4: Update the global maximum**

```java
if (currSum > maxSum) {
    maxSum = currSum;
}
```

This keeps track of the largest sum encountered anywhere in the array.

**Step 5: Return the result**

```java
return maxSum;
```

---

## 4. Detailed Dry Run

**Input:**

```text
nums = [-2,1,-3,4,-1,2,1,-5,4]
```

Initial values:

```text
currSum = 0
maxSum = -2
```

| Index | `nums[i]` | Sum before adding | `currSum` after adding | `maxSum` |
|---:|---:|---:|---:|---:|
| 0 | -2 | 0 | -2 | -2 |
| 1 | 1 | 0 (reset) | 1 | 1 |
| 2 | -3 | 1 | -2 | 1 |
| 3 | 4 | 0 (reset) | 4 | 4 |
| 4 | -1 | 4 | 3 | 4 |
| 5 | 2 | 3 | 5 | 5 |
| 6 | 1 | 5 | 6 | 6 |
| 7 | -5 | 6 | 1 | 6 |
| 8 | 4 | 1 | 5 | 6 |

Final answer:

```text
6
```

The maximum-sum subarray is:

```text
[4, -1, 2, 1]
```

Its sum is:

```text
4 + (-1) + 2 + 1 = 6
```

### Why Do We Reset at Index 3?

Before processing `4`, the previous running sum is `-2`.

Extending that subarray would give:

```text
-2 + 4 = 2
```

Starting a new subarray gives:

```text
4
```

Since `4` is better than `2`, we reset and start fresh.

---

## 5. Important Edge Case: All Elements Are Negative

Consider:

```text
nums = [-5, -2, -8]
```

Initialization:

```text
currSum = 0
maxSum = -5
```

| Element | `currSum` after processing | `maxSum` |
|---:|---:|---:|
| -5 | -5 | -5 |
| -2 | -2 | -2 |
| -8 | -8 | -2 |

Final answer:

```text
-2
```

The maximum-sum subarray is `[-2]`.

### Why Doesn't `maxSum` Remain Zero?

Because we initialized it with:

```java
int maxSum = nums[0];
```

Every element is processed, and `maxSum` is updated whenever the running sum is greater.

If we initialized `maxSum = 0`, the algorithm would incorrectly return `0` for an all-negative array, even though a subarray must be non-empty.

**Key lesson:** Initialize the global maximum to a valid array value.

---

## 6. Alternative: Kadane's Algorithm Using `Math.max()`

The same algorithm can be written more compactly.

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int currSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currSum = Math.max(nums[i], currSum + nums[i]);
            maxSum = Math.max(maxSum, currSum);
        }

        return maxSum;
    }
}
```

### Why Does This Work?

At every position, there are two choices:

1. Start a new subarray at the current element.
2. Extend the previous subarray by adding the current element.

The recurrence is:

\[
currSum_i = \max(nums[i], currSum_{i-1} + nums[i])
\]

Then update the best result:

\[
maxSum_i = \max(maxSum_{i-1}, currSum_i)
\]

This formulation makes the decision explicit and handles all-negative arrays naturally.

### Comparison with My Original Solution

| Original solution | `Math.max()` solution |
|---|---|
| Starts `currSum` at `0` | Starts `currSum` at `nums[0]` |
| Resets negative sums before adding | Chooses between starting fresh and extending |
| Uses `if` statements | Uses `Math.max()` |
| Correct | Correct |

Both approaches have the same asymptotic complexity.

---

## 7. Complexity Analysis

Let `n` be the length of `nums`.

### Time Complexity: O(n)

Each element is visited exactly once, and each iteration performs constant-time operations.

### Auxiliary Space Complexity: O(1)

The algorithm uses only a fixed number of integer variables, regardless of input size.

| Metric | Complexity |
|---|---|
| Time | O(n) |
| Auxiliary space | O(1) |

---

## 8. Common Mistakes and Lessons Learned

### Mistake 1: Initializing `maxSum` to zero

Incorrect:

```java
int maxSum = 0;
```

This fails when every element is negative.

Correct:

```java
int maxSum = nums[0];
```

### Mistake 2: Keeping a negative running sum

A negative prefix reduces the sum of any subarray extended from it.

**Lesson:** Discard a negative running sum, or explicitly choose the better of starting fresh and extending.

### Mistake 3: Confusing a subarray with a subsequence

A subarray must be contiguous.

For example:

```text
nums = [1, -5, 4, 2]
```

`[4, 2]` is a valid subarray, but `[1, 4, 2]` is not because the elements are not contiguous.

### Mistake 4: Tracking only the running sum

The current subarray may not be the best one encountered so far.

**Lesson:** Maintain two variables: the current best ending at this position and the global best.

---

## 9. Key Takeaways

- Kadane's Algorithm solves Maximum Subarray in linear time.
- A negative running sum is harmful to any future extension.
- At each index, either start fresh or extend the previous subarray.
- `currSum` represents the best subarray ending at the current position.
- `maxSum` represents the best subarray found anywhere so far.
- Initialize `maxSum` with a real array element to handle all-negative inputs.
- The algorithm uses O(n) time and O(1) auxiliary space.

---

## 10. Quick Revision Questions

1. What is a subarray?
2. Why can we discard a negative running sum?
3. What does `currSum` represent?
4. What does `maxSum` represent?
5. Why should `maxSum` not be initialized to zero?
6. How does Kadane's Algorithm handle an all-negative array?
7. What is the recurrence using `Math.max()`?
8. What are the time and auxiliary space complexities?
9. How does the algorithm differ from brute-force enumeration?
10. Can Kadane's Algorithm also return the subarray itself? What additional information would you track?

---

## 11. Interview Explanation

If asked to explain your solution in an interview, say:

"I use Kadane's Algorithm. I maintain a running sum for the best subarray ending at the current position and a global maximum for the best sum found so far. If the running sum becomes negative, I discard it because it would only reduce the sum of a future subarray. I then add the current element and update the global maximum. Every element is processed once, so the time complexity is O(n), and the auxiliary space complexity is O(1)."

---

## Final Summary

**Problem:** LeetCode 53 — Maximum Subarray

**Algorithm:** Kadane's Algorithm

**Core decision:**

```text
If current sum < 0:
    Reset current sum to 0

Add current element
Update global maximum
```

**Alternative recurrence:**

```text
currSum = max(nums[i], currSum + nums[i])
maxSum = max(maxSum, currSum)
```

**Time Complexity:** O(n)

**Auxiliary Space Complexity:** O(1)
