
# LeetCode 167: Two Sum II - Input Array Is Sorted

- **Difficulty:** Medium
- **Pattern:** Two Pointers (Opposite Direction)
- **Language:** Java
- **Problem:** [Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)

---

## 1. Problem Statement

Given a **1-indexed**, non-decreasing sorted array `numbers` and an integer `target`, find two numbers whose sum equals `target`.

Return the indices of these two numbers as an integer array `[index1, index2]`.

Requirements:

- `index1` must be less than `index2`.
- Both indices are **1-based**, not 0-based.
- Exactly one valid solution exists.
- You must use constant extra space.

### Example 1

**Input:**
```text
numbers = [2, 7, 11, 15]
target = 9
```

**Output:**
```text
[1, 2]
```

**Explanation:** `numbers[1] + numbers[2] = 2 + 7 = 9` using the problem's 1-based indexing.

### Example 2

**Input:**
```text
numbers = [2, 3, 4]
target = 6
```

**Output:**
```text
[1, 3]
```

### Example 3

**Input:**
```text
numbers = [-1, 0]
target = -1
```

**Output:**
```text
[1, 2]
```

---

## 2. Core Intuition

Because the array is sorted, we can use two pointers instead of checking every possible pair.

- `i`: Starts at the beginning, pointing to the smallest number.
- `j`: Starts at the end, pointing to the largest number.

Calculate:

```java
int sum = numbers[i] + numbers[j];
```

Then decide which pointer to move.

### Case 1: Sum equals target

```java
if (sum == target) {
    return new int[]{i + 1, j + 1};
}
```

We found the answer.

### Case 2: Sum is smaller than target

```java
else if (sum < target) {
    i++;
}
```

Move the left pointer forward to a larger value.

Because the array is sorted, increasing the left value increases the sum while the right pointer stays fixed.

### Case 3: Sum is greater than target

```java
else {
    j--;
}
```

Move the right pointer backward to a smaller value.

Because the array is sorted, decreasing the right value decreases the sum while the left pointer stays fixed.

---

## 3. Your Java Solution

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int[] arr = new int[2];

        int i = 0;
        int j = numbers.length - 1;

        while (i < j) {
            if (numbers[i] + numbers[j] == target) {
                arr[0] = i + 1;
                arr[1] = j + 1;
                return arr;
            } else if (numbers[i] + numbers[j] < target) {
                i++;
            } else {
                j--;
            }
        }

        return arr;
    }
}
```

**Status:** Correct optimal approach.

The problem guarantees exactly one solution, so a valid input will return from inside the loop. The final `return arr` is technically unnecessary under that guarantee, but it keeps the method's control flow complete.

---

## 4. Dry Run

Input:

```text
numbers = [2, 7, 11, 15]
target = 9
```

Initial state:

```text
i = 0
j = 3
```

| Step | `i` | `j` | Left value | Right value | Sum | Action |
|---:|---:|---:|---:|---:|---:|---|
| 1 | 0 | 3 | 2 | 15 | 17 | Sum too large; decrement `j` |
| 2 | 0 | 2 | 2 | 11 | 13 | Sum too large; decrement `j` |
| 3 | 0 | 1 | 2 | 7 | 9 | Match found |

The matching values are `2` and `7`.

Their Java array indices are `0` and `1`, but the problem requires 1-based indices.

Therefore:

```text
[0 + 1, 1 + 1] = [1, 2]
```

**Output:**
```text
[1, 2]
```

---

## 5. Why Does This Algorithm Work?

The key is that the array is sorted.

### When the sum is too small

Suppose:

```text
numbers[i] + numbers[j] < target
```

Since `numbers[j]` is already the largest available value in the current search range, keeping `i` fixed and moving `j` left would only make the sum smaller or equal.

Therefore, the current left value cannot form a valid pair with any remaining right-side value. We can safely increment `i`.

### When the sum is too large

Suppose:

```text
numbers[i] + numbers[j] > target
```

Since `numbers[i]` is the smallest available value in the current search range, keeping `j` fixed and moving `i` right would only make the sum larger or equal.

Therefore, the current right value cannot form a valid pair with any remaining left-side value. We can safely decrement `j`.

Each pointer movement eliminates impossible candidates without missing the solution.

---

## 6. Why Use Two Pointers Instead of Brute Force?

### Brute-force approach

Check every pair using two nested loops.

```java
for (int i = 0; i < numbers.length; i++) {
    for (int j = i + 1; j < numbers.length; j++) {
        if (numbers[i] + numbers[j] == target) {
            return new int[]{i + 1, j + 1};
        }
    }
}
```

- Time: `O(n²)`
- Auxiliary space: `O(1)`, excluding the returned array.

### Two-pointer approach

Start at opposite ends and move one pointer per unsuccessful comparison.

- Time: `O(n)`
- Auxiliary space: `O(1)`, excluding the returned array.

The two-pointer solution is optimal for this problem's sorted input and constant-space requirement.

---

## 7. Complexity Analysis

Let `n` be the length of `numbers`.

- **Time Complexity:** `O(n)`
- **Auxiliary Space Complexity:** `O(1)`

Why `O(n)`?

Each iteration moves either `i` forward or `j` backward. Neither pointer reverses direction, so there are at most `n - 1` pointer movements before the pointers meet.

Why `O(1)` space?

Only a fixed number of variables and pointers are used. No additional data structure is required.

The returned two-element array is the required output, not auxiliary working space.

---

## 8. Common Mistakes

### Mistake 1: Forgetting 1-based indexing

Incorrect:

```java
return new int[]{i, j};
```

Correct:

```java
return new int[]{i + 1, j + 1};
```

Java arrays use 0-based indexing, but the problem explicitly requires 1-based indices.

### Mistake 2: Moving the wrong pointer

If the sum is too small, increment `i`.

If the sum is too large, decrement `j`.

The sorted order makes these movements safe.

### Mistake 3: Using `i <= j`

The problem requires two different elements. Using `while (i < j)` ensures the pointers refer to distinct positions.

### Mistake 4: Using a `HashMap` unnecessarily

A `HashMap` is a common solution for the general Two Sum problem, but here the input is already sorted and the problem requires constant extra space.

Two pointers solve it in linear time without a map.

### Mistake 5: Assuming the array is sorted in descending order

The given array is sorted in non-decreasing order. The pointer logic depends on that ordering.

### Mistake 6: Comparing the wrong values

Always compare the sum of the values at the current pointers:

```java
numbers[i] + numbers[j]
```

Do not compare the indices themselves.

---

## 9. Interview Explanation

"I use two pointers because the input array is sorted. I initialize one pointer at the beginning and the other at the end. If their sum equals the target, I return their 1-based indices. If the sum is smaller than the target, I increment the left pointer to increase the sum. If the sum is larger, I decrement the right pointer to reduce the sum. Since each iteration moves one pointer inward, the algorithm runs in O(n) time and uses O(1) auxiliary space."

---

## 10. Revision Questions

Try answering these without looking at the solution.

1. Why does sorting make the two-pointer approach possible?
2. Why do we initialize `i = 0` and `j = numbers.length - 1`?
3. If the sum is smaller than the target, why do we move `i`?
4. If the sum is larger than the target, why do we move `j`?
5. Why is `while (i < j)` used instead of `while (i <= j)`?
6. Why must the returned indices be incremented by one?
7. What is the time complexity of the brute-force approach?
8. Why does the two-pointer approach use O(1) auxiliary space?
9. What property of the input array makes this algorithm work?
10. How would you solve the ordinary Two Sum problem if the array were unsorted?

---

## 11. Related Problems

Practice these to strengthen your two-pointer pattern recognition:

1. [LeetCode 1: Two Sum](https://leetcode.com/problems/two-sum/)
2. [LeetCode 26: Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
3. [LeetCode 125: Valid Palindrome](https://leetcode.com/problems/valid-palindrome/)
4. [LeetCode 344: Reverse String](https://leetcode.com/problems/reverse-string/)
5. [LeetCode 15: 3Sum](https://leetcode.com/problems/3sum/)

---

## 12. Key Takeaways

- **Pattern:** Two Pointers, moving inward from opposite ends.
- **Prerequisite:** Sorted array.
- **If sum is too small:** Move the left pointer right.
- **If sum is too large:** Move the right pointer left.
- **If sum matches:** Return 1-based indices.
- **Time Complexity:** `O(n)`.
- **Auxiliary Space:** `O(1)`.

**Remember this rule:**

> Sorted array + target sum + constant-space requirement = consider two pointers starting at opposite ends.
