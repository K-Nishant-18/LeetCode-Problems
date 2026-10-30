
# LeetCode 189: Rotate Array

- **Difficulty:** Medium
- **Pattern:** Array + In-place Reversal
- **Language:** Java
- **Problem:** [Rotate Array](https://leetcode.com/problems/rotate-array/)

---

## 1. Problem Statement

Given an integer array `nums`, rotate the array to the right by `k` steps, where `k` is non-negative.

You must perform the rotation in-place.

### Example 1

**Input:**
```text
nums = [1, 2, 3, 4, 5, 6, 7]
k = 3
```

**Output:**
```text
[5, 6, 7, 1, 2, 3, 4]
```

**Explanation:**

After one right rotation:

```text
[7, 1, 2, 3, 4, 5, 6]
```

After two right rotations:

```text
[6, 7, 1, 2, 3, 4, 5]
```

After three right rotations:

```text
[5, 6, 7, 1, 2, 3, 4]
```

### Example 2

**Input:**
```text
nums = [-1, -100, 3, 99]
k = 2
```

**Output:**
```text
[3, 99, -1, -100]
```

---

## 2. Core Intuition

We need to rotate an array to the right by `k` positions.

Instead of shifting elements one by one, we can use the **three-reversal technique**.

The idea is:

1. Reverse the first `n-k` elements.
2. Reverse the last `k` elements.
3. Reverse the entire array.

Here, `n` represents the array length.

### Why does this work?

Consider:

```text
nums = [1, 2, 3, 4, 5, 6, 7]
k = 3
```

Divide the array into two parts:

```text
A = [1, 2, 3, 4]
B = [5, 6, 7]
```

We want:

```text
B + A
```

The original array is:

```text
A + B
```

The three reversals transform it into the required order without using another array.

---

## 3. Step-by-Step Approach

### Step 1: Handle large values of k

```java
k = k % nums.length;
```

Why?

Rotating an array by its length produces the original array.

For example:

```text
Array length = 5
k = 7

7 % 5 = 2
```

Therefore, rotating 7 times is equivalent to rotating 2 times.

This also handles cases where `k` is greater than the array length.

### Step 2: Reverse the first `n-k` elements

```java
int i = 0;
int j = nums.length - 1 - k;

while (i <= j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;

    i++;
    j--;
}
```

### Step 3: Reverse the last `k` elements

```java
int m = j + 1;
int n = nums.length - 1;

while (m <= n) {
    int temp = nums[m];
    nums[m] = nums[n];
    nums[n] = temp;

    m++;
    n--;
}
```

**Important implementation detail:** In this version, `j` has been modified by the first reversal loop. The value of `j + 1` is not guaranteed to be the start of the second section.

Instead, calculate the second section's starting index independently:

```java
int m = nums.length - k;
int n = nums.length - 1;
```

### Step 4: Reverse the entire array

```java
int start = 0;
int end = nums.length - 1;

while (start <= end) {
    int temp = nums[start];
    nums[start] = nums[end];
    nums[end] = temp;

    start++;
    end--;
}
```

---

## 4. Your Final Java Solution

This is the corrected, cleaner implementation of your three-reversal approach.

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int len = nums.length;

        if (len == 0) {
            return;
        }

        k = k % len;

        // Reverse first n-k elements
        reverse(nums, 0, len - k - 1);

        // Reverse last k elements
        reverse(nums, len - k, len - 1);

        // Reverse entire array
        reverse(nums, 0, len - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;

            start++;
            end--;
        }
    }
}
```

**Status:** Correct optimal solution.

This implementation extracts the repeated reversal logic into a helper method, making the code shorter and easier to maintain.

---

## 5. Dry Run

Input:

```text
nums = [1, 2, 3, 4, 5, 6, 7]
k = 3
```

Array length:

```text
n = 7
k = 3
n-k = 4
```

### Step 1: Reverse the first `n-k` elements

Reverse indices `0` to `3`.

Before:

```text
[1, 2, 3, 4, 5, 6, 7]
```

After:

```text
[4, 3, 2, 1, 5, 6, 7]
```

### Step 2: Reverse the last `k` elements

Reverse indices `4` to `6`.

Before:

```text
[4, 3, 2, 1, 5, 6, 7]
```

After:

```text
[4, 3, 2, 1, 7, 6, 5]
```

### Step 3: Reverse the entire array

Reverse indices `0` to `6`.

Before:

```text
[4, 3, 2, 1, 7, 6, 5]
```

After:

```text
[5, 6, 7, 1, 2, 3, 4]
```

**Final output:**

```text
[5, 6, 7, 1, 2, 3, 4]
```

---

## 6. Understanding the Three Reversals Mathematically

Let:

```text
Original array = A + B
```

Where:

- `A` contains the first `n-k` elements.
- `B` contains the last `k` elements.

Our target is:

```text
B + A
```

First, reverse both sections:

```text
reverse(A) + reverse(B)
```

Then reverse the entire combined array:

```text
reverse(reverse(B)) + reverse(reverse(A))
```

Since reversing a reversed sequence restores its original order:

```text
B + A
```

This is exactly the required right rotation.

---

## 7. Complexity Analysis

Let `n` be the length of the array.

- **Time Complexity:** `O(n)`
- **Auxiliary Space Complexity:** `O(1)`

### Why O(n) time?

We perform three reversals.

Each reversal takes at most `O(n)` time.

Therefore:

```text
O(n) + O(n) + O(n) = O(3n) = O(n)
```

### Why O(1) space?

We modify the original array directly.

We only use a few integer variables:

- `start`
- `end`
- `temp`
- `k`
- `len`

No additional array or collection is created.

---

## 8. Common Mistakes

### Mistake 1: Forgetting modulo

Incorrect:

```java
k = k;
```

If `k` is greater than the array length, unnecessary rotations are performed.

Correct:

```java
k = k % nums.length;
```

### Mistake 2: Division by zero

If the array is empty, this causes an error:

```java
k = k % nums.length;
```

Check the length before calculating modulo.

### Mistake 3: Incorrect reversal boundaries

For right rotation:

- First section: indices `0` to `n-k-1`.
- Second section: indices `n-k` to `n-1`.
- Entire array: indices `0` to `n-1`.

Remember these boundaries.

### Mistake 4: Using a modified pointer to calculate the next section

If you modify `j` inside a reversal loop, don't assume it still holds its original boundary value afterward.

Calculate each section's boundaries independently.

### Mistake 5: Rotating one position at a time

Repeatedly shifting elements can take `O(n*k)` time.

The reversal algorithm achieves `O(n)` time.

### Mistake 6: Confusing left and right rotation

For right rotation:

```java
reverse(nums, 0, n-k-1);
reverse(nums, n-k, n-1);
reverse(nums, 0, n-1);
```

For left rotation by `k`, the reversal boundaries are different.

---

## 9. Edge Cases

| Input | k | Expected output |
|---|---:|---|
| `[1, 2, 3, 4, 5]` | 2 | `[4, 5, 1, 2, 3]` |
| `[1, 2, 3, 4, 5]` | 5 | `[1, 2, 3, 4, 5]` |
| `[1, 2, 3, 4, 5]` | 7 | `[4, 5, 1, 2, 3]` |
| `[1]` | 10 | `[1]` |
| `[]` | 3 | `[]` |

---

## 10. Interview Explanation

"I use the three-reversal technique to rotate the array in-place. First, I calculate the effective rotation using `k % n`. Then I reverse the first `n-k` elements, reverse the last `k` elements, and finally reverse the entire array. This rearranges the two sections into the required right-rotated order. The solution takes O(n) time and O(1) auxiliary space."

---

## 11. Revision Questions

Try answering these without looking at the solution.

1. Why do we use `k % nums.length`?
2. What happens when `k` equals the array length?
3. Why do we reverse the first `n-k` elements?
4. Why do we reverse the last `k` elements?
5. Why is the entire array reversed at the end?
6. What are the correct boundaries for all three reversals?
7. Why is this approach better than shifting elements one by one?
8. What is the time complexity of three reversals?
9. Why is the auxiliary space complexity O(1)?
10. How would you modify the approach to rotate an array to the left?

---

## 12. Related Problems

Practice these to strengthen your array manipulation skills.

1. [LeetCode 26: Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
2. [LeetCode 283: Move Zeroes](https://leetcode.com/problems/move-zeroes/)
3. [LeetCode 344: Reverse String](https://leetcode.com/problems/reverse-string/)
4. [LeetCode 189: Rotate Array](https://leetcode.com/problems/rotate-array/)
5. [LeetCode 151: Reverse Words in a String](https://leetcode.com/problems/reverse-words-in-a-string/)

---

## 13. Key Takeaways

- **Pattern:** Array Reversal.
- **Technique:** Three reversals.
- **Step 1:** Reverse the first `n-k` elements.
- **Step 2:** Reverse the last `k` elements.
- **Step 3:** Reverse the entire array.
- **Modulo:** Normalize `k` using `k % n`.
- **Time Complexity:** `O(n)`.
- **Auxiliary Space:** `O(1)`.

**Remember:**

> Right rotation by k = Reverse first n-k + Reverse last k + Reverse everything.
