
# 485. Max Consecutive Ones

- **LeetCode:** https://leetcode.com/problems/max-consecutive-ones/
- **Difficulty:** Easy
- **Pattern:** Array, Linear Traversal, Counting
- **Approach:** Running Count + Maximum Tracking
- **Time Complexity:** O(n)
- **Auxiliary Space:** O(1)

---

## 1. Problem Statement

Given a binary array `nums`, return the maximum number of consecutive `1`s in the array.

A binary array contains only `0` and `1`.

### Examples

**Example 1:**

```text
Input: nums = [1,1,0,1,1,1]
Output: 3
```

Explanation:

The first group contains two consecutive ones.

The second group contains three consecutive ones.

The maximum consecutive ones is `3`.

**Example 2:**

```text
Input: nums = [1,0,1,1,0,1]
Output: 2
```

Explanation:

The maximum consecutive ones is `2`.

---

## 2. Key Observation

We need to find the longest continuous sequence of `1`s.

The important word is **consecutive**.

Whenever we encounter:
- `1`: Increase the current count.
- `0`: Reset the current count to zero.

At every step, update the maximum count.

We only need two variables:

- `count`: Tracks the current consecutive ones.
- `maxOne`: Stores the maximum consecutive ones found so far.

---

## 3. Approach: Running Count + Maximum Tracking

### Intuition

Think of counting consecutive wins in a game.

- Every win increases the current streak.
- A loss breaks the streak.
- We keep track of the longest streak achieved so far.

The same logic applies here.

### Java Solution

```java
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxOne = 0;
        int count = 0;

        for (int n : nums) {
            if (n == 1) {
                count++;

                if (count > maxOne) {
                    maxOne = count;
                }
            } else {
                count = 0;
            }
        }

        return maxOne;
    }
}
```

---

## 4. Understanding the Variables

```java
int maxOne = 0;
int count = 0;
```

| Variable | Purpose |
|---|---|
| `count` | Current consecutive sequence of ones |
| `maxOne` | Longest sequence of ones found so far |

### Why reset count to zero?

Consider:

```text
nums = [1, 1, 0, 1, 1, 1]
```

When we encounter `0`, the current sequence of consecutive ones ends.

Therefore:

```java
count = 0;
```

The next `1` starts a completely new sequence.

---

## 5. Dry Run

Input:

```text
nums = [1, 1, 0, 1, 1, 1]
```

Initially:

```text
count = 0
maxOne = 0
```

| Index | Element | count | maxOne | Action |
|---|---:|---:|---:|---|
| 0 | 1 | 1 | 1 | Increment count |
| 1 | 1 | 2 | 2 | Increment count |
| 2 | 0 | 0 | 2 | Reset count |
| 3 | 1 | 1 | 2 | Start new sequence |
| 4 | 1 | 2 | 2 | Increment count |
| 5 | 1 | 3 | 3 | Update maximum |

Final result:

```text
maxOne = 3
```

Output:

```text
3
```

---

## 6. Understanding Your Original Code

Your submitted implementation:

```java
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxOne = 0;
        int count = 0;

        for (int n : nums) {
            if (n != 1) {
                count = 0;
            }

            if (n == 1) {
                count++;

                if (count >= maxOne) {
                    maxOne = count;
                }
            }
        }

        return maxOne;
    }
}
```

Your implementation is correct.

### Small improvements

**1. Use `if-else` instead of two separate conditions.**

Since the input contains only `0` and `1`, the conditions are mutually exclusive.

**2. Use `>` instead of `>=`.**

When `count` equals `maxOne`, there is no need to assign the same value again.

These are readability improvements, not correctness fixes.

---

## 7. Complexity Analysis

Let `n` be the length of the array.

### Time Complexity: O(n)

We traverse the array exactly once.

Each iteration performs constant-time operations.

Therefore:

```text
O(n)
```

### Auxiliary Space Complexity: O(1)

We only use two variables:

```java
int count;
int maxOne;
```

No additional data structure is required.

Therefore:

```text
O(1)
```

---

## 8. Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `[1,1,1,1]` | 4 | All ones |
| `[0,0,0]` | 0 | No ones |
| `[1]` | 1 | Single one |
| `[0]` | 0 | Single zero |
| `[1,0,1,1]` | 2 | Longest sequence is two |
| `[1,1,0,1,1,1]` | 3 | Longest sequence is three |
| `[]` | 0 | Empty array |

---

## 9. Common Mistakes

### Mistake 1: Not resetting the current count

Incorrect:

```java
if (n == 1) {
    count++;
}
```

Without resetting the count when encountering zero, you end up counting total ones instead of consecutive ones.

### Mistake 2: Updating maximum only after the loop

You can update the maximum during traversal. This ensures every completed or ongoing sequence is considered.

### Mistake 3: Confusing total ones with consecutive ones

Example:

```text
nums = [1, 1, 0, 1, 1, 1]
```

Total ones = 5

Maximum consecutive ones = 3

These are different values.

---

## 10. Interview Explanation

"I traverse the array once while maintaining two variables.

The first variable tracks the current streak of consecutive ones. Whenever I encounter a one, I increment the count.

I also update the maximum streak whenever the current count becomes greater than the previous maximum.

Whenever I encounter zero, I reset the current count to zero because the consecutive sequence has ended.

After traversing the complete array, I return the maximum count.

The solution takes O(n) time and O(1) auxiliary space."

---

## 11. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| 485. Max Consecutive Ones | Running Count |
| 487. Max Consecutive Ones II | Sliding Window |
| 1004. Max Consecutive Ones III | Sliding Window |
| 1446. Consecutive Characters | Running Count |
| 674. Longest Continuous Increasing Subsequence | Running Count |

---

## 12. Quick Revision

**Q1. What are the two variables used?**

`count` and `maxOne`.

**Q2. What does `count` represent?**

The current consecutive sequence of ones.

**Q3. When do we reset count?**

Whenever we encounter zero.

**Q4. When do we update maxOne?**

Whenever the current count exceeds the previous maximum.

**Q5. What is the time complexity?**

O(n).

**Q6. What is the auxiliary space complexity?**

O(1).

**Q7. Why is this not a sliding window problem?**

A simple running count is sufficient because zero directly resets the sequence. We don't need to maintain or adjust a variable-size window.

---

## 13. Final Takeaway

**Pattern: Running Count + Maximum Tracking**

Whenever a problem asks you to find the longest consecutive sequence satisfying a simple condition, consider:

1. Maintain a current count.
2. Increase it when the condition is satisfied.
3. Reset it when the condition breaks.
4. Track the maximum throughout traversal.

Remember:

```java
if (condition) {
    count++;
    max = Math.max(max, count);
} else {
    count = 0;
}
```

This is a reusable pattern for many array and string problems.
