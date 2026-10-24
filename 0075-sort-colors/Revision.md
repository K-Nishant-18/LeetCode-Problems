
# 🐞 Problem Statement

**LeetCode 75 — Sort Colors**

**Difficulty:** Medium  
**Topics:** Array, Two Pointers, Sorting

Given an integer array `nums` containing only `0`, `1`, and `2`, sort the array in-place so that objects of the same color are adjacent.

- `0` represents red.
- `1` represents white.
- `2` represents blue.

You must solve this problem without using the library's built-in sorting function.

## 📌 Examples

### Example 1

```text
Input:  nums = [2, 0, 2, 1, 1, 0]
Output: [0, 0, 1, 1, 2, 2]
```

### Example 2

```text
Input:  nums = [2, 0, 1]
Output: [0, 1, 2]
```

### Example 3

```text
Input:  nums = [0]
Output: [0]
```

---

## 📟 Solution 1 — Basic Approach

**Approach:** Bubble Sort

A straightforward approach is to repeatedly compare adjacent elements and swap them if they are out of order.

### Java Code

```java
class Solution {
    public void sortColors(int[] nums) {
        for (int i = nums.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }
}
```

### 🎀 Explanation

1. The outer loop controls how many passes are performed.
2. The inner loop compares adjacent elements.
3. If the left element is greater than the right element, swap them.
4. Repeat until the array is sorted.

### 🔍 Dry Run

Input:

```text
nums = [2, 0, 2, 1, 1, 0]
```

After successive Bubble Sort passes, the array becomes:

```text
[0, 0, 1, 1, 2, 2]
```

### ⏱️ Complexity Analysis

Let `n` be the array length.

- **Time:** `O(n²)` in the worst case.
- **Auxiliary space:** `O(1)`.

This approach works, but it does unnecessary comparisons because the array contains only three possible values.

---

## 🚀 Solution 2 — Optimal Approach

**Approach:** Dutch National Flag Algorithm

This is the optimal solution using three pointers.

### Java Code

```java
class Solution {
    public void sortColors(int[] nums) {
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;

                low++;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;

                high--;
            }
        }
    }
}
```

### 🎀 Explanation

We divide the array into four regions:

| Region | Meaning |
|---|---|
| Before `low` | All `0`s |
| From `low` to `mid - 1` | All `1`s |
| From `mid` to `high` | Unknown elements |
| After `high` | All `2`s |

Initially:

```java
int low = 0;
int mid = 0;
int high = nums.length - 1;
```

#### Case 1: `nums[mid] == 0`

Swap `nums[mid]` with `nums[low]`.

Then increment both `low` and `mid`.

Why? The `0` belongs at the beginning, and the region before `mid` is already processed.

#### Case 2: `nums[mid] == 1`

Increment `mid`.

Why? The `1` already belongs in the middle.

#### Case 3: `nums[mid] == 2`

Swap `nums[mid]` with `nums[high]`, then decrement `high`.

**Important:** Do not increment `mid` here. The element swapped in from the right is not yet processed, so you must inspect it next.

---

## 🔍 Dry Run — Dutch National Flag

Input:

```text
nums = [2, 0, 2, 1, 1, 0]
```

| Step | `low` | `mid` | `high` | Action | Array |
|---:|---:|---:|---:|---|---|
| 1 | 0 | 0 | 5 | Swap `2` with `0` | `[0, 0, 2, 1, 1, 2]` |
| 2 | 1 | 1 | 5 | `0`: swap with itself | `[0, 0, 2, 1, 1, 2]` |
| 3 | 2 | 2 | 5 | `2`: swap with right | `[0, 0, 2, 1, 1, 2]` |
| 4 | 2 | 2 | 4 | `2`: swap with right | `[0, 0, 1, 1, 2, 2]` |
| 5 | 2 | 3 | 4 | `1`: move `mid` | `[0, 0, 1, 1, 2, 2]` |
| 6 | 2 | 4 | 4 | `2`: swap with itself | `[0, 0, 1, 1, 2, 2]` |

The loop ends when `mid > high`.

Final output:

```text
[0, 0, 1, 1, 2, 2]
```

---

## 📊 Comparison of Both Approaches

| Feature | Bubble Sort | Dutch National Flag |
|---|---|---|
| Time complexity | `O(n²)` | `O(n)` |
| Auxiliary space | `O(1)` | `O(1)` |
| In-place | Yes | Yes |
| Uses three pointers | No | Yes |
| Optimal for this problem | No | Yes |

---

## 🧠 Summary of the Idea

The Dutch National Flag algorithm sorts three distinct values in one pass.

1. Initialize `low = 0`, `mid = 0`, and `high = n - 1`.
2. If `nums[mid] == 0`, swap it toward the left and increment `low` and `mid`.
3. If `nums[mid] == 1`, increment `mid`.
4. If `nums[mid] == 2`, swap it toward the right and decrement `high`.
5. Stop when `mid > high`.

### 🔑 Memory Trick

- **0:** Move left — increment `low` and `mid`.
- **1:** Stay in the middle — increment `mid`.
- **2:** Move right — decrement `high`, but keep `mid` where it is.

---

## 🎯 Interview Takeaways

- **Pattern:** Three Pointers / Dutch National Flag.
- **Time:** `O(n)`.
- **Auxiliary space:** `O(1)`.
- The algorithm processes each element in-place.
- Do not increment `mid` after swapping a `2`, because the incoming element is unprocessed.
- The `while (mid <= high)` condition ensures every unknown element is examined.
- This approach is preferable to Bubble Sort because it exploits the fact that only three values are possible.
