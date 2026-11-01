
# LC 704 — Binary Search

**Difficulty:** Easy  
**Pattern:** Binary Search | Divide and Conquer  
**Topic:** Arrays

---

## 1. Problem Statement

Given an array of integers `nums` sorted in ascending order and an integer `target`, return the index of `target` if it exists in the array.

If the target does not exist, return `-1`.

You must write an algorithm with O(log n) runtime complexity.

### Example 1

**Input:**
```java
nums = [-1, 0, 3, 5, 9, 12]
target = 9
```

**Output:**
```java
4
```

Explanation: `nums[4] = 9`.

### Example 2

**Input:**
```java
nums = [-1, 0, 3, 5, 9, 12]
target = 2
```

**Output:**
```java
-1
```

Explanation: `2` does not exist in the array.

---

## 2. Core Intuition

Imagine searching for a word in a dictionary.

You don't start from the first page and check every word.

Instead:

1. Open the dictionary around the middle.
2. Compare the word with the target.
3. If the target comes later alphabetically, ignore the first half.
4. If the target comes earlier, ignore the second half.
5. Repeat until you find the target or no search space remains.

This is the fundamental idea of Binary Search.

**Important prerequisite:** The array must be sorted.

---

## 3. Three Important Variables

Binary search uses three pointers:

```java
int left = 0;
int right = nums.length - 1;
int mid;
```

| Variable | Meaning |
|---|---|
| `left` | First index of the current search range |
| `right` | Last index of the current search range |
| `mid` | Middle index of the current search range |

We use an inclusive search interval:

```java
while (left <= right)
```

Why `<=`?

Because when `left == right`, exactly one candidate element remains. We must still check it.

---

## 4. Three Possible Conditions

After calculating the middle index, compare `nums[mid]` with `target`.

### Case 1: Target Found

```java
if (nums[mid] == target) {
    return mid;
}
```

The target is found. Return its index.

### Case 2: Target Is Greater

```java
else if (nums[mid] < target) {
    left = mid + 1;
}
```

Since the array is sorted, every element to the left of `mid` is smaller.

Discard the left half, including `mid`.

Move the left pointer to `mid + 1`.

### Case 3: Target Is Smaller

```java
else {
    right = mid - 1;
}
```

Since the array is sorted, every element to the right of `mid` is greater.

Discard the right half, including `mid`.

Move the right pointer to `mid - 1`.

---

## 5. Visual Understanding

Consider:

```java
nums = [1, 3, 5, 7, 9, 11, 13]
target = 11
```

Initial state:

```text
Index:  0  1  2  3  4   5   6
Value: [1, 3, 5, 7, 9, 11, 13]
        L           M       R
```

### Iteration 1

```text
left = 0
right = 6
mid = 3

nums[mid] = 7
target = 11
```

Since `7 < 11`, search the right half.

```java
left = mid + 1;
```

New search range:

```text
Index:  0  1  2  3  4   5   6
Value: [1, 3, 5, 7, 9, 11, 13]
                        L   M R
```

### Iteration 2

```text
left = 4
right = 6
mid = 5

nums[mid] = 11
target = 11
```

Target found!

Return:

```java
5
```

---

## 6. Final Java Solution

```java
class Solution {
    public int search(int[] nums, int target) {

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }
}
```

---

## 7. Understanding Midpoint Calculation

A common implementation is:

```java
int mid = (left + right) / 2;
```

However, `left + right` can overflow the Java `int` range when working with very large indices.

A safer implementation is:

```java
int mid = left + (right - left) / 2;
```

This calculates the same midpoint while avoiding the potentially overflowing addition of `left` and `right`.

**Interview recommendation:** Prefer the safer expression by default.

---

## 8. Why Do We Use mid + 1 and mid - 1?

This is one of the most important binary search concepts.

Suppose:

```text
nums[mid] = 7
target = 11
```

We already know that `7` is not the target.

Therefore, keeping `mid` in the search range is pointless.

Incorrect:

```java
left = mid;
right = mid;
```

Correct:

```java
left = mid + 1;
right = mid - 1;
```

The midpoint must be excluded after it has been compared and found not to be the target.

Otherwise, the search range may not shrink, causing an infinite loop.

---

## 9. Dry Run

Input:

```java
nums = [-1, 0, 3, 5, 9, 12]
target = 9
```

Initial values:

```text
left = 0
right = 5
```

| Iteration | Left | Right | Mid | nums[mid] | Decision |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 5 | 2 | 3 | Search right |
| 2 | 3 | 5 | 4 | 9 | Target found |

Output:

```java
4
```

### Not-found example

Input:

```java
nums = [-1, 0, 3, 5, 9, 12]
target = 2
```

| Iteration | Left | Right | Mid | nums[mid] | Decision |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 5 | 2 | 3 | Search left |
| 2 | 0 | 1 | 0 | -1 | Search right |
| 3 | 1 | 1 | 1 | 0 | Search right |

Now:

```text
left = 2
right = 1
```

Since `left > right`, the loop terminates.

Return:

```java
-1
```

---

## 10. Complexity Analysis

Let `n` be the number of elements in the array.

| Complexity | Result |
|---|---|
| Best-case time | O(1) |
| Average-case time | O(log n) |
| Worst-case time | O(log n) |
| Auxiliary space | O(1) |

### Why O(log n)?

Every iteration eliminates approximately half of the remaining search space.

For example:

```text
64 → 32 → 16 → 8 → 4 → 2 → 1
```

The number of iterations grows logarithmically with the input size.

---

## 11. Edge Cases

### Case 1: Target at the beginning

```java
nums = [1, 3, 5, 7]
target = 1
```

Output:

```java
0
```

### Case 2: Target at the end

```java
nums = [1, 3, 5, 7]
target = 7
```

Output:

```java
3
```

### Case 3: Single-element array, target found

```java
nums = [5]
target = 5
```

Output:

```java
0
```

### Case 4: Single-element array, target absent

```java
nums = [5]
target = 10
```

Output:

```java
-1
```

### Case 5: Empty array

```java
nums = []
target = 5
```

Output:

```java
-1
```

The initial `right` becomes `-1`, so the loop never executes.

---

## 12. Common Mistakes

1. Forgetting that binary search requires sorted input.
2. Using `left < right` instead of `left <= right` with this inclusive-boundary implementation.
3. Updating `left = mid` instead of `left = mid + 1`.
4. Updating `right = mid` instead of `right = mid - 1`.
5. Calculating the midpoint using an expression that can overflow.
6. Forgetting to return `-1` when the target is absent.
7. Mixing inclusive and exclusive boundary conventions.

---

## 13. Interview Explanation

"I use binary search because the input array is sorted.

I initialize two pointers, left and right, representing the current search interval.

While the search interval is valid, I calculate the middle index and compare its value with the target.

If the middle value equals the target, I return its index.

If the middle value is smaller, I eliminate the left half and move the left pointer to mid plus one.

If the middle value is greater, I eliminate the right half and move the right pointer to mid minus one.

If the pointers cross, the target does not exist, so I return minus one.

Since the search space is halved in every iteration, the time complexity is O(log n), with O(1) auxiliary space."

---

## 14. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| LC 35 – Search Insert Position | Binary Search |
| LC 34 – Find First and Last Position of Element | Modified Binary Search |
| LC 33 – Search in Rotated Sorted Array | Binary Search |
| LC 69 – Sqrt(x) | Binary Search on Answer |
| LC 74 – Search a 2D Matrix | Binary Search |
| LC 153 – Find Minimum in Rotated Sorted Array | Binary Search |

---

## 15. Quick Revision

Remember these rules:

- Binary Search requires sorted input.
- Initialize `left = 0`.
- Initialize `right = nums.length - 1`.
- Use `while (left <= right)`.
- Calculate midpoint safely.
- Target found → return `mid`.
- Middle value smaller → `left = mid + 1`.
- Middle value greater → `right = mid - 1`.
- Search range exhausted → return `-1`.

**Core Pattern: Compare Middle → Eliminate Half → Repeat.**

**Time:** O(log n)  
**Auxiliary Space:** O(1)
