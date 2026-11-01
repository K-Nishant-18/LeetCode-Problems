
# LC 35 — Search Insert Position

**Difficulty:** Easy  
**Pattern:** Binary Search | Lower Bound  
**Topic:** Arrays

---

## 1. Problem Statement

Given a sorted array of distinct integers and a target value, return the index if the target is found.

If the target is not found, return the index where it would be inserted to maintain sorted order.

You must write an algorithm with O(log n) runtime complexity.

### Example 1

**Input:**
```java
nums = [1, 3, 5, 6]
target = 5
```

**Output:**
```java
2
```

Explanation: Target `5` already exists at index `2`.

### Example 2

**Input:**
```java
nums = [1, 3, 5, 6]
target = 2
```

**Output:**
```java
1
```

Explanation: Insert `2` at index `1`.

Result:
```text
[1, 2, 3, 5, 6]
```

### Example 3

**Input:**
```java
nums = [1, 3, 5, 6]
target = 7
```

**Output:**
```java
4
```

Explanation: Target `7` belongs at the end of the array.

---

## 2. Core Intuition

This problem is a direct extension of:

**LC 704 — Binary Search**

In LC 704:
- If the target exists, return its index.
- If the target does not exist, return `-1`.

In LC 35:
- If the target exists, return its index.
- If the target does not exist, return its correct insertion position.

The binary search algorithm remains almost identical.

The important difference is the final return statement.

---

## 3. Binary Search Foundation

Initialize two pointers:

```java
int left = 0;
int right = nums.length - 1;
```

Use an inclusive search interval:

```java
while (left <= right)
```

Calculate the middle index:

```java
int mid = left + (right - left) / 2;
```

Now compare the middle element with the target.

### Case 1: Target Found

```java
if (nums[mid] == target) {
    return mid;
}
```

Return the existing index.

### Case 2: Middle Element Is Smaller

```java
else if (nums[mid] < target) {
    left = mid + 1;
}
```

The target must be on the right side.

### Case 3: Middle Element Is Greater

```java
else {
    right = mid - 1;
}
```

The target must be on the left side.

---

## 4. The Most Important Concept: Why Return `left`?

This is the key insight of LC 35.

When binary search terminates:

```java
left > right
```

The search boundaries have crossed.

At this point:

- Every element before `left` is smaller than the target.
- Every element from `left` onward is greater than the target.

Therefore, `left` represents the correct insertion position.

**The final return statement is:**

```java
return left;
```

You do not need an additional variable to track the insertion position.

---

## 5. Visual Understanding

Consider:

```java
nums = [1, 3, 5, 6]
target = 2
```

Initially:

```text
Index:  0  1  2  3
Value: [1, 3, 5, 6]
        L     M  R
```

### Iteration 1

```text
left = 0
right = 3
mid = 1

nums[mid] = 3
target = 2
```

Since `3 > 2`:

```java
right = mid - 1;
```

New boundaries:

```text
left = 0
right = 0
```

### Iteration 2

```text
left = 0
right = 0
mid = 0

nums[mid] = 1
target = 2
```

Since `1 < 2`:

```java
left = mid + 1;
```

New boundaries:

```text
left = 1
right = 0
```

The loop terminates because:

```java
left > right
```

Final answer:

```java
return left; // 1
```

Target `2` belongs at index `1`.

---

## 6. Final Java Solution

```java
class Solution {
    public int searchInsert(int[] nums, int target) {

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

        return left;
    }
}
```

---

## 7. Dry Run

Input:

```java
nums = [1, 3, 5, 6]
target = 7
```

Initial boundaries:

```text
left = 0
right = 3
```

| Iteration | Left | Right | Mid | nums[mid] | Decision |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 3 | 1 | 3 | Search right |
| 2 | 2 | 3 | 2 | 5 | Search right |
| 3 | 3 | 3 | 3 | 6 | Search right |

After iteration 3:

```java
left = mid + 1;
```

Final boundaries:

```text
left = 4
right = 3
```

The loop terminates.

Return:

```java
4
```

The target belongs at the end of the array.

---

## 8. Why Your `temp` Approach Was Incorrect

Your initial implementation used:

```java
int temp = 0;
```

And updated it using:

```java
temp = mid;
```

The problem is that `mid` represents the middle index of the current search range, not necessarily the insertion position.

Consider:

```java
nums = [1, 3, 5, 6]
target = 0
```

The correct insertion position is `0`.

But your code could store a midpoint of `1` and incorrectly return it.

The binary search boundary already tracks the insertion position.

**Better approach:**

```java
return left;
```

No extra variable is required.

---

## 9. Edge Cases

### Case 1: Target Exists

```java
nums = [1, 3, 5, 6]
target = 5
```

Output:
```java
2
```

### Case 2: Insert at Beginning

```java
nums = [1, 3, 5, 6]
target = 0
```

Output:
```java
0
```

### Case 3: Insert at End

```java
nums = [1, 3, 5, 6]
target = 7
```

Output:
```java
4
```

### Case 4: Insert Between Elements

```java
nums = [1, 3, 5, 6]
target = 2
```

Output:
```java
1
```

### Case 5: Single Element

```java
nums = [5]
target = 2
```

Output:
```java
0
```

### Case 6: Single Element, Target Greater

```java
nums = [5]
target = 8
```

Output:
```java
1
```

---

## 10. Complexity Analysis

Let `n` be the number of elements.

| Complexity | Result |
|---|---|
| Best-case time | O(1) |
| Average-case time | O(log n) |
| Worst-case time | O(log n) |
| Auxiliary space | O(1) |

### Why O(log n)?

Every iteration eliminates approximately half of the remaining search space.

### Why O(1) space?

Only three variables are used:
- `left`
- `right`
- `mid`

No additional data structure is required.

---

## 11. Common Mistakes

1. Returning `-1` when the target is absent.
2. Returning the last midpoint instead of the insertion position.
3. Creating an unnecessary `temp` variable.
4. Using `left = mid` instead of `left = mid + 1`.
5. Using `right = mid` instead of `right = mid - 1`.
6. Forgetting that the insertion position can be `nums.length`.
7. Mixing inclusive and exclusive binary search boundaries.

---

## 12. Interview Explanation

"I use binary search because the input array is sorted.

I initialize left and right pointers to represent the current search range.

For each iteration, I calculate the middle index and compare its value with the target.

If the target is found, I return the middle index.

If the middle element is smaller than the target, I move the left pointer to mid plus one.

Otherwise, I move the right pointer to mid minus one.

If the loop terminates without finding the target, the left pointer represents the first position where the target can be inserted while maintaining sorted order.

Therefore, I return left.

The time complexity is O(log n), and the auxiliary space complexity is O(1)."

---

## 13. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| LC 704 – Binary Search | Basic Binary Search |
| LC 34 – Find First and Last Position | Modified Binary Search |
| LC 744 – Find Smallest Letter Greater Than Target | Lower Bound |
| LC 278 – First Bad Version | Binary Search |
| LC 153 – Find Minimum in Rotated Sorted Array | Modified Binary Search |

---

## 14. Quick Revision

Remember:

- Sorted array → Binary Search.
- Target found → Return `mid`.
- Middle value smaller → `left = mid + 1`.
- Middle value greater → `right = mid - 1`.
- Loop terminates when `left > right`.
- Target absent → Return `left`.
- `left` represents the insertion position.

**Core Pattern: Binary Search + Lower Bound.**

**Time:** O(log n)  
**Auxiliary Space:** O(1)

---

## 15. Interview Takeaway

LC 704 and LC 35 use the same binary search foundation.

The difference is what you return when the target is absent:

- LC 704 → `-1`
- LC 35 → `left`

**Remember:** When using an inclusive binary search interval, the final `left` pointer identifies the insertion position.
