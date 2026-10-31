
# 2149. Rearrange Array Elements by Sign

- **LeetCode:** https://leetcode.com/problems/rearrange-array-elements-by-sign/
- **Difficulty:** Medium
- **Pattern:** Array, Two Pointers
- **Approach:** Separate Index Placement
- **Time Complexity:** O(n)
- **Auxiliary Space:** O(n)

---

## 1. Problem Statement

Given a 0-indexed integer array `nums` of even length consisting of an equal number of positive and negative integers.

Rearrange the elements such that:

1. Every consecutive pair of integers has opposite signs.
2. The relative order of positive integers is preserved.
3. The relative order of negative integers is preserved.
4. The first element must be positive.

Return the rearranged array.

### Examples

**Example 1:**

```text
Input:  [3, 1, -2, -5, 2, -4]
Output: [3, -2, 1, -5, 2, -4]
```

Explanation:

- Positive numbers: `[3, 1, 2]`
- Negative numbers: `[-2, -5, -4]`

The output alternates positive and negative numbers while preserving the relative order of both groups.

**Example 2:**

```text
Input:  [-1, 1]
Output: [1, -1]
```

---

## 2. Key Observations

The problem gives us some important guarantees:

- The array has an even length.
- Positive and negative numbers are equal in count.
- The first position must contain a positive number.
- Positive and negative numbers must alternate.
- Relative order within each group must be maintained.

The required index pattern is:

```text
Index:       0    1    2    3    4    5
Sign:        +    -    +    -    +    -
```

Notice:

- Positive numbers occupy even indices: `0, 2, 4, ...`
- Negative numbers occupy odd indices: `1, 3, 5, ...`

This observation gives us the solution.

---

## 3. Approach: Two Pointers with a Result Array

### Intuition

Instead of rearranging elements inside the original array, create a new array of the same length.

Maintain two pointers:

- `pos = 0`: Next available even index for a positive number.
- `neg = 1`: Next available odd index for a negative number.

Traverse the original array once.

For every element:

- If it is positive, place it at `arr[pos]` and increment `pos` by 2.
- If it is negative, place it at `arr[neg]` and increment `neg` by 2.

Why increment by 2?

Because positive numbers only belong at even indices, and negative numbers only belong at odd indices.

```text
Positive positions: 0 → 2 → 4 → 6

Negative positions: 1 → 3 → 5 → 7
```

### Java Solution

```java
class Solution {
    public int[] rearrangeArray(int[] nums) {
        int[] arr = new int[nums.length];

        int pos = 0;
        int neg = 1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                arr[pos] = nums[i];
                pos += 2;
            } else {
                arr[neg] = nums[i];
                neg += 2;
            }
        }

        return arr;
    }
}
```

**Note:** Your submitted code used `nums[i] >= 0`. The problem guarantees there are no zeroes, so it works. Using `> 0` expresses the positive-number condition more precisely.

---

## 4. Understanding the Variables

| Variable | Purpose |
|---|---|
| `arr` | Stores the rearranged result |
| `i` | Traverses the original array |
| `pos` | Tracks the next even index |
| `neg` | Tracks the next odd index |

Important:

`pos` and `neg` are independent pointers. Updating one does not affect the other.

---

## 5. Dry Run

Input:

```text
nums = [3, 1, -2, -5, 2, -4]
```

Initially:

```text
arr = [_, _, _, _, _, _]

pos = 0
neg = 1
```

| i | nums[i] | Condition | Destination | Pointer update |
|---|---:|---|---|---|
| 0 | 3 | Positive | arr[0] | pos = 2 |
| 1 | 1 | Positive | arr[2] | pos = 4 |
| 2 | -2 | Negative | arr[1] | neg = 3 |
| 3 | -5 | Negative | arr[3] | neg = 5 |
| 4 | 2 | Positive | arr[4] | pos = 6 |
| 5 | -4 | Negative | arr[5] | neg = 7 |

Final array:

```text
[3, -2, 1, -5, 2, -4]
```

Output:

```text
[3, -2, 1, -5, 2, -4]
```

---

## 6. Why Does This Approach Preserve Relative Order?

Consider the positive elements:

```text
Original: [3, 1, -2, -5, 2, -4]

Positive: [3, 1, 2]
```

The pointer `pos` moves from left to right:

```text
0 → 2 → 4
```

Therefore:

```text
arr[0] = 3
arr[2] = 1
arr[4] = 2
```

Their relative order remains unchanged.

Similarly, negative elements:

```text
Negative: [-2, -5, -4]
```

The pointer `neg` moves:

```text
1 → 3 → 5
```

Therefore:

```text
arr[1] = -2
arr[3] = -5
arr[5] = -4
```

Their relative order also remains unchanged.

---

## 7. Complexity Analysis

Let `n` be the length of the input array.

### Time Complexity: O(n)

- We traverse the original array exactly once.
- Every element is placed exactly once.
- Each placement takes O(1) time.

Therefore:

```text
O(n)
```

### Auxiliary Space Complexity: O(n)

We create a new array of size `n`.

```java
int[] arr = new int[nums.length];
```

Therefore:

```text
O(n)
```

The result array is required by the problem's rearrangement approach.

---

## 8. Alternative Approach: Separate Positive and Negative Lists

Another possible approach is to collect positive and negative elements into separate lists and then merge them alternately.

However, this requires additional collections and more code.

The separate-index approach is cleaner and more efficient in terms of implementation.

---

## 9. Approach Comparison

| Approach | Time | Auxiliary Space |
|---|---|---|
| Separate positive/negative lists | O(n) | O(n) |
| Separate index placement | O(n) | O(n) |

**Recommendation:** Use separate index placement. It is simple, efficient, and directly uses the problem's index pattern.

---

## 10. Common Mistakes

### Mistake 1: Incrementing pointers by 1

Incorrect:

```java
pos++;
neg++;
```

This would not maintain the required even/odd index pattern.

Correct:

```java
pos += 2;
neg += 2;
```

### Mistake 2: Returning the original array

The problem asks for a rearranged array. Creating and returning a separate result array is the natural solution.

### Mistake 3: Using nested loops unnecessarily

You don't need to search repeatedly for positive and negative elements.

One traversal is sufficient.

### Mistake 4: Not preserving relative order

The problem requires positive and negative elements to retain their original relative order.

Traversing the original array from left to right ensures this.

### Mistake 5: Confusing positive and negative indices

Remember:

```text
Even indices → Positive numbers
Odd indices  → Negative numbers
```

---

## 11. Edge Cases

| Input | Output |
|---|---|
| `[3, 1, -2, -5, 2, -4]` | `[3, -2, 1, -5, 2, -4]` |
| `[-1, 1]` | `[1, -1]` |
| `[1, -1, 2, -2]` | `[1, -1, 2, -2]` |
| `[5, 4, -3, -2]` | `[5, -3, 4, -2]` |

---

## 12. Interview Explanation

"I observe that positive numbers must occupy even indices and negative numbers must occupy odd indices.

I create a new result array and maintain two pointers: one starting at index zero for positive numbers and another starting at index one for negative numbers.

I traverse the original array once.

Whenever I encounter a positive number, I place it at the current positive pointer and increment that pointer by two.

Whenever I encounter a negative number, I place it at the current negative pointer and increment that pointer by two.

Since I traverse the original array from left to right, the relative order of positive and negative elements is preserved.

The solution takes O(n) time and O(n) auxiliary space."

---

## 13. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| 283. Move Zeroes | Two Pointers |
| 905. Sort Array By Parity | Two Pointers |
| 922. Sort Array By Parity II | Two Pointers |
| 75. Sort Colors | In-place Array Manipulation |
| 26. Remove Duplicates from Sorted Array | Read-Write Pointers |

---

## 14. Quick Revision

**Q1. Which indices store positive numbers?**

Even indices: `0, 2, 4, ...`

**Q2. Which indices store negative numbers?**

Odd indices: `1, 3, 5, ...`

**Q3. Why do we increment pointers by 2?**

To move to the next index with the same parity.

**Q4. Why do we use a new array?**

To place positive and negative elements directly at their required positions without disturbing the original traversal.

**Q5. How is relative order preserved?**

We process the original array from left to right and place each sign at its next available position.

**Q6. What is the time complexity?**

O(n).

**Q7. What is the auxiliary space complexity?**

O(n).

---

## 15. Final Takeaway

**Pattern: Separate Index Placement using Two Pointers**

Whenever a problem requires placing different categories of elements at specific index patterns, look for independent pointers.

For this problem:

```java
int pos = 0;
int neg = 1;
```

And:

```java
pos += 2;
neg += 2;
```

The key insight is not complicated swapping. It is identifying the destination index for each element before placing it.

Remember the pattern:

- Traverse the original array once.
- Decide the destination based on the element's category.
- Advance only the corresponding pointer.
- Preserve order through left-to-right traversal.
