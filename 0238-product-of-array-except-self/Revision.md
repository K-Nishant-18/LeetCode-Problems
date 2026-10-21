
# 🐞 Problem Statement

Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal to the product of all the elements of `nums` except `nums[i]`.

The product of any prefix or suffix of `nums` is guaranteed to fit in a **32-bit integer**.

You must write an algorithm that runs in **O(n)** time without using the division operation.

**Example 1:**
```text
Input: nums = [1,2,3,4]
Output: [24,12,8,6]
```

**Example 2:**
```text
Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]
```

---

## 📟 Your Logical Code

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        int prefix = 1;

        // First pass: store products to the left
        for (int i = 0; i < nums.length; i++) {
            result[i] = prefix;
            prefix = prefix * nums[i];
        }

        int suffix = 1;

        // Second pass: multiply by products to the right
        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] = result[i] * suffix;
            suffix = suffix * nums[i];
        }

        return result;
    }
}
```

---

## 🎀 Explanation

### ✅ Step 1: Initialize the result array

```java
int[] result = new int[nums.length];
int prefix = 1;
```

- `result` stores the final answers.
- `prefix` tracks the product of elements to the left of the current index.
- We initialize `prefix` to `1` because multiplying by `1` doesn't change a product.

For `nums = [1, 2, 3, 4]`:

```text
result = [0, 0, 0, 0]
prefix = 1
```

Java initializes integer array elements to `0`. Each position will be assigned during the first pass.

---

### 🔁 Step 2: First loop — Left to right

```java
for (int i = 0; i < nums.length; i++) {
    result[i] = prefix;
    prefix = prefix * nums[i];
}
```

**Idea:** Store the product of all elements before index `i`.

| `i` | `nums[i]` | `prefix` before | `result[i]` | `prefix` after |
|---:|---:|---:|---:|---:|
| 0 | 1 | 1 | 1 | 1 |
| 1 | 2 | 1 | 1 | 2 |
| 2 | 3 | 2 | 2 | 6 |
| 3 | 4 | 6 | 6 | 24 |

After the first loop:

```text
result = [1, 1, 2, 6]
```

Each position now stores the product of all elements **to its left**.

For example, at index `3`, the left-side product is:

`1 × 2 × 3 = 6`

---

### 🔁 Step 3: Second loop — Right to left

```java
int suffix = 1;

for (int i = nums.length - 1; i >= 0; i--) {
    result[i] = result[i] * suffix;
    suffix = suffix * nums[i];
}
```

**Idea:** Multiply the existing prefix product by the product of all elements to the right.

`suffix` tracks the product of elements after the current index.

| `i` | `nums[i]` | `suffix` before | `result[i]` calculation | `suffix` after |
|---:|---:|---:|---|---:|
| 3 | 4 | 1 | 6 × 1 = 6 | 4 |
| 2 | 3 | 4 | 2 × 4 = 8 | 12 |
| 1 | 2 | 12 | 1 × 12 = 12 | 24 |
| 0 | 1 | 24 | 1 × 24 = 24 | 24 |

After the second loop:

```text
result = [24, 12, 8, 6]
```

The final answer is:

```text
[24, 12, 8, 6]
```

**Important:** We update `result[i]` before updating `suffix`, ensuring the current element is excluded from its own product.

---

## 🧠 Summary of the Idea

1. **First pass:** Store the product of all elements to the left of each index.
2. **Second pass:** Multiply each result by the product of all elements to its right.
3. Use the same output array for both passes.
4. Never use division.
5. Handle zeros naturally without special conditions.

The key formula is:

`answer[i] = product of elements left of i × product of elements right of i`

---

## ⏱️ Complexity Analysis

Let `n` be the length of `nums`.

| Complexity | Analysis |
|---|---|
| Time | **O(n)** — two linear passes |
| Auxiliary space | **O(1)** — only `prefix` and `suffix` variables |
| Output space | **O(n)** — the required result array |

The output array is excluded from auxiliary space because it is required by the problem.

---

## 🎯 Interview Takeaways

- **Pattern:** Prefix and suffix products.
- **Why two passes?** One calculates left products; the other combines right products.
- **Why initialize to `1`?** An empty prefix or suffix has product `1`.
- **Why not use division?** The problem explicitly prohibits it, and division would also be problematic when zeros occur.
- **Common mistake:** Overwriting `result[i]` with `suffix` instead of multiplying the existing prefix product by `suffix`.
- **Another common mistake:** Updating `suffix` before calculating `result[i]`.

**Remember:** Prefix first, suffix second, combine in place. This gives an O(n)-time solution with O(1) auxiliary space.
