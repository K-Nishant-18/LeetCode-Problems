
# 283. Move Zeroes

- **LeetCode:** https://leetcode.com/problems/move-zeroes/
- **Difficulty:** Easy
- **Pattern:** Two Pointers
- **Approach:** In-place Swapping
- **Time Complexity:** O(n)
- **Auxiliary Space:** O(1)

---

## 1. Problem Statement

Given an integer array `nums`, move all zeroes to the end of it while maintaining the relative order of all non-zero elements.

You must do this in-place without making a copy of the array.

### Examples

**Example 1:**

```text
Input:  [0, 1, 0, 3, 12]
Output: [1, 3, 12, 0, 0]
```

**Example 2:**

```text
Input:  [0]
Output: [0]
```

**Example 3:**

```text
Input:  [1, 2, 3]
Output: [1, 2, 3]
```

---

## 2. Key Observations

There are three important requirements:

1. All non-zero elements must come before zeroes.
2. The relative order of non-zero elements must remain unchanged.
3. The original array must be modified without using another array.

The problem is a good fit for the **Two Pointers** pattern.

---

## 3. Approach: Two Pointers

### Intuition

We use two pointers:

- `i`: Tracks the position where the next non-zero element should be placed.
- `j`: Traverses the entire array to find non-zero elements.

Whenever `nums[j]` is non-zero:

1. Swap `nums[i]` and `nums[j]`.
2. Increment `i`.

Regardless of whether the current element is zero or non-zero, increment `j`.

### Java Solution

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int i = 0;
        int j = 0;

        while (j < nums.length) {
            if (nums[j] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
            }
            j++;
        }
    }
}
```

---

## 4. Understanding the Pointers

Consider:

```text
nums = [0, 1, 0, 3, 12]
```

Initially:

```text
i = 0
j = 0
```

- `j` scans every element.
- `i` tracks the next position for a non-zero element.

The important invariant is:

**All elements before index `i` are non-zero and are in their original relative order.**

---

## 5. Dry Run

Input:

```text
[0, 1, 0, 3, 12]
```

| Step | j | i | Action | Array |
|---|---:|---:|---|---|
| 1 | 0 | 0 | Zero, skip | `[0,1,0,3,12]` |
| 2 | 1 | 0 | Swap positions 0 and 1 | `[1,0,0,3,12]` |
| 3 | 2 | 1 | Zero, skip | `[1,0,0,3,12]` |
| 4 | 3 | 1 | Swap positions 1 and 3 | `[1,3,0,0,12]` |
| 5 | 4 | 2 | Swap positions 2 and 4 | `[1,3,12,0,0]` |

Final output:

```text
[1, 3, 12, 0, 0]
```

---

## 6. Why Does the Relative Order Remain Unchanged?

Pointer `j` always moves from left to right.

Whenever it encounters a non-zero element, that element is placed at the next available position tracked by `i`.

Therefore, non-zero elements are placed in exactly the same order in which they were encountered.

Example:

```text
Original non-zero elements: [1, 3, 12]

Final non-zero elements:    [1, 3, 12]
```

Their order is preserved.

---

## 7. Complexity Analysis

Let `n` be the length of the array.

### Time Complexity: O(n)

- Pointer `j` traverses the array once.
- Pointer `i` advances only when a non-zero element is encountered.
- Each iteration performs at most one swap.

Therefore, time complexity is O(n).

### Auxiliary Space Complexity: O(1)

Only two pointers and one temporary variable are used.

No additional array or collection is created.

---

## 8. Small Optimization

Your original implementation swaps even when `i == j`.

For example, with:

```text
nums = [1, 2, 3]
```

Every element is already in the correct position.

You can avoid unnecessary self-swaps:

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int i = 0;

        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                if (i != j) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
                i++;
            }
        }
    }
}
```

Both implementations have O(n) time and O(1) auxiliary space.

---

## 9. Edge Cases

| Input | Output |
|---|---|
| `[0,1,0,3,12]` | `[1,3,12,0,0]` |
| `[0]` | `[0]` |
| `[1,2,3]` | `[1,2,3]` |
| `[0,0,0]` | `[0,0,0]` |
| `[1,0,0]` | `[1,0,0]` |
| `[0,1,2,0,3]` | `[1,2,3,0,0]` |

---

## 10. Common Mistakes

1. Using another array despite the in-place requirement.
2. Moving non-zero elements without preserving their original order.
3. Incrementing `i` even when the current element is zero.
4. Forgetting to increment `j` after processing an element.
5. Using nested loops unnecessarily.

---

## 11. Interview Explanation

"I use two pointers. The first pointer tracks the next position where a non-zero element should be placed, while the second pointer traverses the array.

Whenever the second pointer encounters a non-zero value, I swap it with the element at the first pointer's position and increment the first pointer.

This moves all non-zero elements to the beginning while maintaining their relative order. Zeroes naturally end up at the end.

The algorithm takes O(n) time and O(1) auxiliary space."

---

## 12. Related Problems

| Problem | Pattern |
|---|---|
| 26. Remove Duplicates from Sorted Array | Two Pointers |
| 27. Remove Element | Two Pointers |
| 283. Move Zeroes | Two Pointers |
| 905. Sort Array By Parity | Two Pointers |
| 88. Merge Sorted Array | Two Pointers |

---

## 13. Quick Revision

- **Pattern:** Two Pointers
- **First pointer:** Next position for a non-zero element.
- **Second pointer:** Traverses the array.
- **Condition:** `nums[j] != 0`
- **Operation:** Swap `nums[i]` and `nums[j]`.
- **Time:** O(n)
- **Space:** O(1)

### Key Takeaway

When a problem asks you to rearrange an array in-place while preserving the order of selected elements, consider the **Read-Write / Two Pointers** pattern.
