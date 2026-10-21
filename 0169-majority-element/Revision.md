
# 🐞 Problem Statement

**LeetCode 169 — Majority Element**

**Difficulty:** Easy  
**Topics:** Array, Hashing, Divide and Conquer, Boyer–Moore Voting Algorithm

Given an integer array `nums` of size `n`, return the majority element.

A **majority element** is an element that appears more than `⌊n / 2⌋` times.

The problem guarantees that a majority element exists.

## 📌 Examples

### Example 1

```text
Input:  nums = [3, 2, 3]
Output: 3
```

### Example 2

```text
Input:  nums = [2, 2, 1, 1, 1, 2, 2]
Output: 2
```

---

## 📟 Your Logical Code

**Approach:** Boyer–Moore Voting Algorithm

```java
class Solution {
    public int majorityElement(int[] nums) {
        int candidate = nums[0];
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
            }

            if (nums[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
    }
}
```

---

## 🎀 Explanation

### 1. The Core Idea — Cancellation of Votes

Imagine every element in the array is voting for itself.

- When we encounter the same number as our current candidate, its vote count increases.
- When we encounter a different number, one vote is cancelled.
- When the vote count becomes zero, we select a new candidate.

**Why does this work?**

The majority element appears more than `n / 2` times. Therefore, it appears more often than all the other elements combined.

Even if every occurrence of another number cancels an occurrence of the majority element, at least one occurrence of the majority element remains uncancelled.

### 2. Initialize the Variables

```java
int candidate = nums[0];
int count = 1;
```

- `candidate` stores the current potential majority element.
- `count` stores its current vote balance.

We start with the first element as the candidate and one vote.

### 3. Traverse the Array

```java
for (int i = 1; i < nums.length; i++) {
    if (count == 0) {
        candidate = nums[i];
    }

    if (nums[i] == candidate) {
        count++;
    } else {
        count--;
    }
}
```

For every element, perform these steps:

**Step 1: Check whether the vote count is zero.**

```java
if (count == 0) {
    candidate = nums[i];
}
```

When no candidate votes remain, choose the current element as the new candidate.

**Step 2: Compare the current element with the candidate.**

```java
if (nums[i] == candidate) {
    count++;
} else {
    count--;
}
```

- Same number: increase the vote count.
- Different number: decrease the vote count.

**Important:** These are two separate `if` statements, not an `if-else` chain. After selecting a candidate, the current element must also contribute its vote.

### 4. Dry Run — Example 1

Input:

```text
nums = [3, 2, 3]
```

| Step | Current number | Candidate | Count | Action |
|---|---:|---:|---:|---|
| Initial | — | 3 | 1 | Initialize |
| 1 | 2 | 3 | 0 | Different number; cancel one vote |
| 2 | 3 | 3 | 1 | Count was zero, select 3; add its vote |

Final answer: `3`

### 5. Dry Run — Example 2

Input:

```text
nums = [2, 2, 1, 1, 1, 2, 2]
```

| Step | Current number | Candidate | Count | Action |
|---|---:|---:|---:|---|
| Initial | — | 2 | 1 | Initialize |
| 1 | 2 | 2 | 2 | Same candidate |
| 2 | 1 | 2 | 1 | Cancel one vote |
| 3 | 1 | 2 | 0 | Cancel one vote |
| 4 | 1 | 1 | 1 | Choose new candidate; add vote |
| 5 | 2 | 1 | 0 | Cancel one vote |
| 6 | 2 | 2 | 1 | Choose new candidate; add vote |

Final answer: `2`

Notice that when the count reaches zero, the next element becomes the new candidate and is immediately counted.

---

## 🧠 Summary of the Idea

The Boyer–Moore Voting Algorithm finds the majority element using one pass and constant extra space.

1. Initialize `candidate = nums[0]` and `count = 1`.
2. Traverse the remaining elements.
3. If `count == 0`, select the current element as the candidate.
4. If the current element equals the candidate, increment `count`.
5. Otherwise, decrement `count`.
6. Return the final candidate.

### 🔑 Remember This Template

```java
if (count == 0) {
    candidate = nums[i];
}

if (nums[i] == candidate) {
    count++;
} else {
    count--;
}
```

**Memory trick:** Same number = add a vote. Different number = cancel a vote. Zero votes = choose a new candidate.

---

## ⏱️ Complexity Analysis

Let `n` be the length of the array.

### Time Complexity: O(n)

We traverse the array exactly once, performing constant-time operations for each element.

### Auxiliary Space Complexity: O(1)

We use only two variables: `candidate` and `count`.

No additional array, list, or hash map is required.

---

## 🎯 Interview Takeaways

- **Pattern:** Boyer–Moore Voting Algorithm.
- **Optimal complexity:** O(n) time and O(1) auxiliary space.
- **Why not use a HashMap?** A HashMap can count all frequencies, but it requires O(n) extra space in the worst case.
- **Why does cancellation work?** The majority element appears more than all other elements combined, so it cannot be completely cancelled.
- **What does `count` represent?** The current candidate's vote balance, not its exact frequency in the entire array.
- **Do we need a second pass?** Not when the problem guarantees a majority element exists.
- **When is verification needed?** If a majority element is not guaranteed, a candidate must be verified by counting its actual occurrences.

### ⚠️ Edge Cases

- A single-element array, such as `[7]`.
- All elements are the same, such as `[4, 4, 4, 4]`.
- The majority element appears near the end, such as `[1, 2, 2, 2, 2]`.
- The majority element is the first element.
- An array where the candidate changes several times.

**Assumption:** This implementation expects a non-empty array and relies on the problem's guarantee that a majority element exists.
