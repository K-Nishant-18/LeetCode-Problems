
# 🐞 Problem Statement

**LeetCode 88 — Merge Sorted Array**

**Difficulty:** Easy  
**Topics:** Array, Two Pointers, Sorting

You are given two integer arrays, `nums1` and `nums2`, sorted in non-decreasing order, and two integers `m` and `n`, representing the number of valid elements in each array.

Merge `nums2` into `nums1` so that the resulting array is sorted in non-decreasing order.

The array `nums1` has a length of `m + n`. Its first `m` elements are valid, and its last `n` elements are empty space represented by zeros. These zeros are placeholders, not elements to merge.

**Important:** Modify `nums1` in-place. Do not return a new array.

## 📌 Examples

### Example 1

```text
Input:
nums1 = [1, 2, 3, 0, 0, 0], m = 3
nums2 = [2, 5, 6], n = 3

Output:
[1, 2, 2, 3, 5, 6]
```

### Example 2

```text
Input:
nums1 = [1], m = 1
nums2 = [], n = 0

Output:
[1]
```

### Example 3

```text
Input:
nums1 = [0], m = 0
nums2 = [1], n = 1

Output:
[1]
```

---

## 📟 Solution 1 — Original Approach

**Approach:** Copy and Sort

The initial idea is to copy the valid elements from `nums2` into the empty positions of `nums1`, then sort the entire array.

### Java Code

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Copy nums2 into the empty portion of nums1
        for (int i = m; i < m + n; i++) {
            nums1[i] = nums2[i - m];
        }

        // Sort the combined array using Bubble Sort
        for (int i = nums1.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (nums1[j] > nums1[j + 1]) {
                    int temp = nums1[j];
                    nums1[j] = nums1[j + 1];
                    nums1[j + 1] = temp;
                }
            }
        }
    }
}
```

### 🎀 Explanation

**Step 1: Copy `nums2` into `nums1`**

```java
for (int i = m; i < m + n; i++) {
    nums1[i] = nums2[i - m];
}
```

The first `m` positions of `nums1` already contain valid elements. Therefore, copying begins at index `m`.

For example:

```text
Before:
nums1 = [1, 3, 5, 0, 0, 0]
nums2 = [2, 4, 6]

After copying:
nums1 = [1, 3, 5, 2, 4, 6]
```

**Step 2: Sort the combined array**

Bubble Sort compares adjacent elements and swaps them when they are out of order.

```java
if (nums1[j] > nums1[j + 1]) {
    int temp = nums1[j];
    nums1[j] = nums1[j + 1];
    nums1[j + 1] = temp;
}
```

After sorting:

```text
nums1 = [1, 2, 3, 4, 5, 6]
```

### ⏱️ Complexity Analysis

Let `N = m + n`.

- **Time Complexity:** `O(N²)` — Bubble Sort takes quadratic time.
- **Auxiliary Space Complexity:** `O(1)` — the array is modified in-place, and only a temporary variable is used.

**Note:** The original code needs to start copying at index `m`, not `n`, for the general case. Also, the sorting approach is valid but inefficient because both input arrays are already sorted.

---

## 🚀 Solution 2 — Optimized Approach

**Approach:** Three Pointers, Merging from Right to Left

Instead of copying and sorting everything again, take advantage of the fact that both arrays are already sorted.

Use three pointers:

- `i`: Last valid element in `nums1`.
- `j`: Last element in `nums2`.
- `k`: Last available position in `nums1`.

Compare `nums1[i]` and `nums2[j]`, then place the larger element at `nums1[k]`.

### Java Code

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        while (i >= 0 && j >= 0) {
            if (nums1[i] >= nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }

            k--;
        }

        // Copy any remaining elements from nums2
        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }
    }
}
```

### 🎀 Explanation

#### Step 1: Initialize the pointers

```java
int i = m - 1;
int j = n - 1;
int k = m + n - 1;
```

For this example:

```text
nums1 = [1, 2, 3, 0, 0, 0]
nums2 = [2, 5, 6]

i = 2
j = 2
k = 5
```

We start at the rightmost valid elements.

#### Step 2: Compare the largest remaining elements

```java
if (nums1[i] >= nums2[j]) {
    nums1[k] = nums1[i];
    i--;
} else {
    nums1[k] = nums2[j];
    j--;
}

k--;
```

Why choose the larger element?

Because `k` points to the last available position. The largest remaining element must go there to preserve sorted order.

#### Step 3: Continue merging

Dry run:

| Step | `nums1[i]` | `nums2[j]` | Write to | Array after write |
|---:|---:|---:|---:|---|
| 1 | 3 | 6 | `nums1[5] = 6` | `[1,2,3,0,0,6]` |
| 2 | 3 | 5 | `nums1[4] = 5` | `[1,2,3,0,5,6]` |
| 3 | 3 | 2 | `nums1[3] = 3` | `[1,2,3,3,5,6]` |
| 4 | 2 | 2 | `nums1[2] = 2` | `[1,2,2,3,5,6]` |

The loop stops when `j < 0`, meaning all elements from `nums2` have been placed.

#### Step 4: Copy remaining elements from `nums2`

```java
while (j >= 0) {
    nums1[k] = nums2[j];
    j--;
    k--;
}
```

If `nums2` still has elements, copy them into the remaining positions.

**Why don't we copy remaining elements from `nums1`?**

If `nums2` is exhausted first, any unprocessed elements in `nums1` are already in their correct positions. Because merging starts from the right, we haven't overwritten them.

---

## 🔍 Dry Run — Optimized Solution

Input:

```text
nums1 = [1, 2, 3, 0, 0, 0]
m = 3

nums2 = [2, 5, 6]
n = 3
```

Initial pointers:

```text
i = 2
j = 2
k = 5
```

| Iteration | Comparison | Action | `i` | `j` | `k` |
|---:|---|---|---:|---:|---:|
| 1 | 3 < 6 | Place 6 | 2 | 1 | 4 |
| 2 | 3 < 5 | Place 5 | 2 | 0 | 3 |
| 3 | 3 >= 2 | Place 3 | 1 | 0 | 2 |
| 4 | 2 >= 2 | Place 2 from `nums1` | 0 | 0 | 1 |
| 5 | 1 < 2 | Place 2 from `nums2` | 0 | -1 | 0 |

Final array:

```text
nums1 = [1, 2, 2, 3, 5, 6]
```

---

## 📊 Comparison of Both Approaches

| Feature | Copy + Bubble Sort | Three Pointers |
|---|---|---|
| Time Complexity | `O((m+n)²)` | `O(m+n)` |
| Auxiliary Space | `O(1)` | `O(1)` |
| Uses sorted input | No | Yes |
| Modifies `nums1` in-place | Yes | Yes |
| Recommended | Basic approach | Optimal approach |

---

## 🧠 Summary of the Idea

Both arrays are already sorted, so we don't need to sort the combined array again.

The optimized solution:

1. Start from the last valid elements of both arrays.
2. Compare `nums1[i]` and `nums2[j]`.
3. Put the larger element at `nums1[k]`.
4. Move the corresponding pointer backward.
5. Decrease `k`.
6. When the main loop ends, copy any remaining elements from `nums2`.

### 🔑 Memory Trick

**Compare → Place largest at the end → Move backward.**

Start from the right to avoid overwriting unprocessed elements.

---

## 🎯 Interview Takeaways

- **Pattern:** Two Pointers / Merge.
- **Optimal Time:** `O(m+n)`.
- **Auxiliary Space:** `O(1)`.
- Never include the placeholder zeros in the original valid portion of `nums1`.
- The pointer `i` starts at `m - 1`, not `nums1.length - 1`.
- The pointer `j` starts at `n - 1`.
- The destination pointer `k` starts at `m + n - 1`.
- Only remaining elements from `nums2` need to be copied after the main loop.
- Merging from right to left prevents overwriting unprocessed elements.

**One-line takeaway:** Since both arrays are sorted, merge them from the end in linear time instead of sorting the combined array again.
