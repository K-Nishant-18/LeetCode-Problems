
# 🐞 Problem Statement

**LeetCode 27 — Remove Element**

**Difficulty:** Easy  
**Topics:** Array, Two Pointers

Given an integer array `nums` and an integer `val`, remove all occurrences of `val` in-place.

The relative order of the elements may be changed. Return the number of elements `k` that are not equal to `val`.

The first `k` elements of `nums` must contain the valid elements. The elements beyond index `k - 1` do not matter.

## 📌 Examples

### Example 1

```text
Input:  nums = [3, 2, 2, 3], val = 3
Output: 2
Modified array: [2, 2, _, _]
```

### Example 2

```text
Input:  nums = [0, 1, 2, 2, 3, 0, 4, 2], val = 2
Output: 5
Modified array: [0, 1, 3, 0, 4, _, _, _]
```

The order of the remaining elements is preserved by this solution.

---

## 📟 Your Logical Code

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int k = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }

        return k;
    }
}
```

---

## 🎀 Explanation

### 1. Understand the Two Pointers

We use two variables, `i` and `k`, with different responsibilities.

- **`i` — Read pointer:** Visits every element in the array.
- **`k` — Write pointer:** Tracks the next position where a valid element should be placed.

A valid element is any element that is not equal to `val`.

Initially:

```java
int k = 0;
```

This means no valid elements have been placed yet.

### 2. Traverse the Array

```java
for (int i = 0; i < nums.length; i++) {
    if (nums[i] != val) {
        nums[k] = nums[i];
        k++;
    }
}
```

For every element:

- If `nums[i] == val`, skip it.
- If `nums[i] != val`, copy it to `nums[k]`.
- Increment `k` because one more valid element has been placed.

### 3. Dry Run — Example 1

**Input:**

```text
nums = [3, 2, 2, 3]
val = 3
k = 0
```

| `i` | `nums[i]` | Action | Array after action | `k` |
|---:|---:|---|---|---:|
| 0 | 3 | Skip | `[3, 2, 2, 3]` | 0 |
| 1 | 2 | `nums[0] = 2` | `[2, 2, 2, 3]` | 1 |
| 2 | 2 | `nums[1] = 2` | `[2, 2, 2, 3]` | 2 |
| 3 | 3 | Skip | `[2, 2, 2, 3]` | 2 |

**Final result:**

```text
k = 2
First k elements = [2, 2]
```

Although the physical array still has four positions, only the first `k` positions matter.

### 4. Dry Run — Example 2

**Input:**

```text
nums = [0, 1, 2, 2, 3, 0, 4, 2]
val = 2
```

| `i` | `nums[i]` | Action | `k` after |
|---:|---:|---|---:|
| 0 | 0 | Write to index 0 | 1 |
| 1 | 1 | Write to index 1 | 2 |
| 2 | 2 | Skip | 2 |
| 3 | 2 | Skip | 2 |
| 4 | 3 | Write to index 2 | 3 |
| 5 | 0 | Write to index 3 | 4 |
| 6 | 4 | Write to index 4 | 5 |
| 7 | 2 | Skip | 5 |

The first five elements become:

```text
[0, 1, 3, 0, 4]
```

The method returns `5`.

---

## 🧠 Summary of the Idea

The main idea is to **overwrite unwanted elements by placing valid elements at the beginning of the array**.

1. Initialize `k = 0`.
2. Traverse the array using `i`.
3. If the current element is not equal to `val`:
   - Copy it to `nums[k]`.
   - Increment `k`.
4. Return `k`.

### Why does this work?

At every step, `k` represents the number of valid elements found so far.

Since `k` never moves ahead of `i`, writing to `nums[k]` cannot overwrite an element that has not yet been processed.

The algorithm preserves the relative order of the valid elements.

---

## ⏱️ Complexity Analysis

Let `n` be the length of the array.

### Time Complexity: O(n)

Every element is examined exactly once.

### Auxiliary Space Complexity: O(1)

Only the variables `i` and `k` are used. The array is modified in-place, without creating another array.

---

## 🎯 Interview Takeaways

- **Pattern:** Two Pointers — Read and Write.
- **In-place modification:** Reuse the existing array instead of allocating another one.
- **Important distinction:** The returned integer `k` represents the number of valid elements, not the new physical length of the array.
- **Order:** This solution preserves the relative order of elements that are kept.
- **Why skip matching elements?** Elements equal to `val` do not need to be copied.
- **Why overwrite?** Every valid element is placed at the next available position at the beginning of the array.
- **Edge cases to test:**
  - Empty array.
  - All elements equal `val`.
  - No elements equal `val`.
  - A single-element array.
  - All valid elements appearing after several unwanted elements.

### 🔑 Remember This Template

```java
int k = 0;

for (int i = 0; i < nums.length; i++) {
    if (nums[i] != val) {
        nums[k] = nums[i];
        k++;
    }
}

return k;
```

**One-line memory trick:**

> Read every element; write only the elements you want to keep.
