
# LeetCode 26: Remove Duplicates from Sorted Array

- **Difficulty:** Easy
- **Pattern:** Two Pointers
- **Language:** Java
- **Problem:** [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)

---

## 1. Problem Statement

Given an integer array `nums` sorted in non-decreasing order, remove the duplicates **in-place** so that each unique element appears only once.

Return `k`, the number of unique elements.

The first `k` elements of `nums` must contain the unique elements in sorted order. The elements beyond index `k - 1` do not matter.

### Example 1

**Input:**
```text
nums = [1, 1, 2]
```

**Output:**
```text
k = 2
nums = [1, 2, _]
```

### Example 2

**Input:**
```text
nums = [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
```

**Output:**
```text
k = 5
nums = [0, 1, 2, 3, 4, _, _, _, _, _]
```

The underscores represent positions whose values do not matter.

---

## 2. Core Intuition

Because the array is sorted, duplicate values appear next to each other.

For example:

```text
[1, 1, 2, 2, 3, 3, 4]
```

We only need to keep the first occurrence of each value:

```text
[1, 2, 3, 4, ...]
```

Instead of creating another array or using a `Set`, we modify the original array.

We use two pointers:

- `i`: Scans the array to find new unique values.
- `k`: Tracks the next position where a unique value should be written.

### Real-world analogy

Imagine a sorted queue of people with duplicate ticket numbers. You want to compact the queue so that each ticket number appears only once.

One pointer checks each ticket, while another marks where the next distinct ticket should go.

---

## 3. Your Final Java Solution

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int k = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[k] = nums[i];
                k++;
            }
        }

        return k;
    }
}
```

**Status:** Correct optimal approach.

---

## 4. Step-by-Step Explanation

### Step 1: Handle the empty array

```java
if (nums.length == 0) {
    return 0;
}
```

An empty array contains no unique elements, so we return `0`.

This check also prevents us from assuming that a first element exists.

### Step 2: Initialize `k`

```java
int k = 1;
```

For a non-empty array, the first element is always unique relative to the elements before it because there are no preceding elements.

Therefore, the first unique element is already in position `0`, and the next available position is `1`.

### Step 3: Scan from index 1

```java
for (int i = 1; i < nums.length; i++) {
```

We start at index `1` because we compare each element with its previous element.

### Step 4: Detect a new unique value

```java
if (nums[i] != nums[i - 1]) {
```

Since the array is sorted:

- If `nums[i] == nums[i - 1]`, the current value is a duplicate.
- If `nums[i] != nums[i - 1]`, we have found a new unique value.

### Step 5: Write the unique value

```java
nums[k] = nums[i];
k++;
```

We place the new unique value at the next available position and increment `k`.

### Step 6: Return the unique count

```java
return k;
```

After the loop, `k` equals the number of unique elements, and the first `k` positions contain them in sorted order.

---

## 5. Dry Run

Input:

```text
nums = [1, 1, 2, 2, 3]
```

Initial state:

```text
k = 1
```

| `i` | `nums[i]` | Previous value | Condition | Action | `k` |
|---:|---:|---:|---|---|---:|
| 1 | 1 | 1 | Equal | Skip duplicate | 1 |
| 2 | 2 | 1 | Different | `nums[1] = 2` | 2 |
| 3 | 2 | 2 | Equal | Skip duplicate | 2 |
| 4 | 3 | 2 | Different | `nums[2] = 3` | 3 |

Final array contents:

```text
nums = [1, 2, 3, 2, 3]
```

Returned value:

```text
k = 3
```

Only the first three elements matter:

```text
[1, 2, 3]
```

**Important:** The algorithm does not need to physically shrink the array. It only needs to place the unique values in the first `k` positions.

---

## 6. Why Compare `nums[i]` With `nums[i - 1]`?

The array is sorted, so all occurrences of a value are consecutive.

For example:

```text
[1, 1, 1, 2, 2, 3]
```

When `i` reaches the second or third `1`, the previous value is also `1`, so the duplicate is skipped.

When `i` reaches `2`, the previous value is `1`. The difference tells us that we have found a new unique value.

We do not need a `Set` to remember all previous values.

---

## 7. Why Does This Work?

We maintain this invariant:

> Before processing each element at index `i`, the first `k` positions contain all distinct values encountered so far, in sorted order.

Initially, the first element is unique, so `k = 1`.

For each subsequent element:

- If it equals the previous element, it is a duplicate and can be skipped.
- Otherwise, it is a new unique value. We write it at `nums[k]` and increment `k`.

Because the input is sorted, every distinct value is detected in order. Therefore, when the loop ends, the first `k` positions contain exactly the unique values.

---

## 8. Complexity Analysis

Let `n` be the length of the input array.

- **Time Complexity:** `O(n)`
- **Auxiliary Space Complexity:** `O(1)`

### Why `O(n)` time?

The loop visits each array element at most once. Each comparison and assignment takes constant time.

Therefore, the total running time is linear.

### Why `O(1)` auxiliary space?

We use only two integer variables, `i` and `k`, and modify the original array directly. No additional array or collection is created.

The algorithm therefore uses constant auxiliary space.

---

## 9. Common Mistakes

### Mistake 1: Starting `k` at zero

For a non-empty array, the first element is already unique. Starting `k` at `1` correctly reserves that first position.

### Mistake 2: Starting `i` at zero

The comparison uses `nums[i - 1]`. Starting at `i = 0` would access index `-1`.

Start the loop at `i = 1`.

### Mistake 3: Forgetting to increment `k`

Without `k++`, every new unique value would overwrite the same position.

### Mistake 4: Using an additional collection unnecessarily

A `HashSet` could identify distinct values, but it is unnecessary and would use extra space. The sorted input allows an in-place two-pointer solution.

### Mistake 5: Trying to shrink the array

Java arrays have a fixed length. You do not need to resize the array; return the unique count and ensure the first `k` positions are correct.

### Mistake 6: Returning `nums.length`

The original array length includes duplicates. The correct return value is `k`, the number of unique elements.

---

## 10. Edge Cases to Remember

| Input | Expected `k` | First `k` elements |
|---|---:|---|
| `[]` | 0 | `[]` |
| `[5]` | 1 | `[5]` |
| `[1, 1, 1]` | 1 | `[1]` |
| `[1, 2, 3]` | 3 | `[1, 2, 3]` |
| `[-3, -3, -1, 0, 0]` | 3 | `[-3, -1, 0]` |

---

## 11. Interview Explanation

"I use a two-pointer approach to remove duplicates in-place. Since the array is sorted, duplicate elements are adjacent. I initialize `k` to 1 because the first element is already unique, then scan from index 1. Whenever the current element differs from the previous element, I copy it to `nums[k]` and increment `k`. At the end, I return `k`. This solution takes O(n) time and O(1) auxiliary space."

---

## 12. Revision Questions

Try answering these without looking at the solution.

1. Why does sorting make this problem easier?
2. Why do we initialize `k = 1` for a non-empty array?
3. Why does the loop begin at `i = 1`?
4. Why do we compare `nums[i]` with `nums[i - 1]`?
5. What does `k` represent throughout the algorithm?
6. Why can we ignore the elements beyond index `k - 1`?
7. Why is the auxiliary space complexity O(1)?
8. What would happen if we forgot `k++`?
9. Why is using a `HashSet` unnecessary?
10. How would you modify the logic to allow each number to appear at most twice?

---

## 13. Follow-up Problem

**LeetCode 80: Remove Duplicates from Sorted Array II**

[Practice the problem](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/)

In this follow-up, each value may appear at most twice instead of once.

Try to reuse the two-pointer pattern. Focus on changing the condition that determines whether a value can be written into the unique section.

---

## Key Takeaways

- **Pattern:** Two Pointers
- **Main observation:** A sorted array places duplicates next to each other.
- **Read pointer:** `i` scans the original sequence.
- **Write pointer:** `k` tracks the next position for a unique value.
- **Time:** `O(n)`
- **Auxiliary space:** `O(1)`

**Remember:** Scan every element, write only new unique values, and return the number of values written.
