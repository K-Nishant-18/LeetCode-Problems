
# LC 119 — Pascal's Triangle II

**Difficulty:** Easy  
**Pattern:** Array | Dynamic Programming | In-place Update  
**Topic:** 1D Array / Space Optimization

---

## 1. Problem Statement

Given an integer `rowIndex`, return the `rowIndex`th (0-indexed) row of Pascal's Triangle.

In Pascal's Triangle:

- Every row starts with `1`.
- Every row ends with `1`.
- Every middle element is the sum of the two elements directly above it in the previous row.

### Example 1

**Input:**
```java
rowIndex = 3
```

**Output:**
```java
[1, 3, 3, 1]
```

### Example 2

**Input:**
```java
rowIndex = 0
```

**Output:**
```java
[1]
```

### Example 3

**Input:**
```java
rowIndex = 1
```

**Output:**
```java
[1, 1]
```

---

## 2. Core Intuition

LC 119 is an extension of LC 118 — Pascal's Triangle.

In LC 118, we generated the first `numRows` and stored the entire triangle.

In LC 119, we only need one specific row.

For example:

```text
Row 0:       [1]
Row 1:       [1, 1]
Row 2:       [1, 2, 1]
Row 3:       [1, 3, 3, 1]
Row 4:       [1, 4, 6, 4, 1]
```

If `rowIndex = 4`, we only need:

```java
[1, 4, 6, 4, 1]
```

We do not need to retain every previous row.

This gives us an opportunity to optimize space.

---

## 3. Approach 1 — Generate the Entire Triangle

This is the approach you implemented first.

### Logic

1. Create a list to store all rows.
2. Generate rows from index `0` to `rowIndex`.
3. For every row:
   - Add `1` at the beginning.
   - Calculate middle elements using the previous row.
   - Add `1` at the end.
4. Return the requested row.

### Java Solution

```java
import java.util.*;

class Solution {
    public List<Integer> getRow(int rowIndex) {

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i <= rowIndex; i++) {

            List<Integer> list = new ArrayList<>();

            if (i == 0) {
                list.add(1);
            } else {

                List<Integer> previousRow = result.get(i - 1);

                list.add(1);

                int rowSize = i + 1;

                for (int j = 1; j < rowSize - 1; j++) {
                    list.add(
                        previousRow.get(j - 1) +
                        previousRow.get(j)
                    );
                }

                list.add(1);
            }

            result.add(list);
        }

        return result.get(rowIndex);
    }
}
```

### Complexity

Let `n = rowIndex + 1`.

- **Time:** O(n²)
- **Space:** O(n²)

Why?

You generate and store all rows, even though only the last row is required.

This solution is correct, but it uses more memory than necessary.

---

## 4. Approach 2 — Optimized One-Dimensional List

This is the important interview optimization.

Instead of storing the entire triangle, maintain only one list.

### Key Observation

Consider the current row:

```text
[1, 3, 3, 1]
```

The next row should be:

```text
[1, 4, 6, 4, 1]
```

The middle elements are calculated using:

```java
row.get(j) + row.get(j - 1)
```

We can update the existing list instead of creating a new row.

But there is one important condition:

**We must update from right to left.**

---

## 5. Why Right to Left?

Suppose the current list is:

```text
Index:  0  1  2  3
Value: [1, 3, 3, 1]
```

We want to generate:

```text
[1, 4, 6, 4, 1]
```

### Right-to-left update

First append a `1`:

```text
[1, 3, 3, 1, 1]
```

Now update the middle elements from right to left.

```java
for (int j = i - 1; j > 0; j--) {
    row.set(j, row.get(j) + row.get(j - 1));
}
```

Updates:

```text
Index 3: 1 + 3 = 4
Index 2: 3 + 3 = 6
Index 1: 3 + 1 = 4
```

Result:

```text
[1, 4, 6, 4, 1]
```

### Why not left to right?

Suppose we update index `1` first:

```text
[1, 4, 3, 1, 1]
```

Now when calculating index `2`, the value at index `1` has already changed.

You would calculate:

```text
3 + 4 = 7
```

But the correct calculation is:

```text
3 + 3 = 6
```

The original previous-row value has been overwritten.

**Right-to-left updating prevents this problem.**

---

## 6. Optimal Java Solution

```java
import java.util.*;

class Solution {
    public List<Integer> getRow(int rowIndex) {

        List<Integer> row = new ArrayList<>();

        row.add(1);

        for (int i = 1; i <= rowIndex; i++) {

            row.add(1);

            for (int j = i - 1; j > 0; j--) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
        }

        return row;
    }
}
```

### Code Explanation

#### Step 1: Initialize the first row

```java
List<Integer> row = new ArrayList<>();
row.add(1);
```

Initially, Pascal's Triangle contains only:

```text
[1]
```

#### Step 2: Generate each subsequent row

```java
for (int i = 1; i <= rowIndex; i++)
```

The loop generates rows from index `1` through `rowIndex`.

#### Step 3: Add the last element

```java
row.add(1);
```

Every new row ends with `1`.

Appending this element also increases the list size by one.

#### Step 4: Update middle elements

```java
for (int j = i - 1; j > 0; j--) {
    row.set(j, row.get(j) + row.get(j - 1));
}
```

This updates the middle elements using their original previous-row values.

The loop runs backward to avoid overwriting values before they are used.

#### Step 5: Return the requested row

```java
return row;
```

Only the required row is stored.

---

## 7. Dry Run

Input:

```java
rowIndex = 4;
```

Initial list:

```text
[1]
```

### Iteration 1: i = 1

Append `1`:

```text
[1, 1]
```

No middle elements.

### Iteration 2: i = 2

Append `1`:

```text
[1, 1, 1]
```

Update index `1`:

```text
1 + 1 = 2
```

Result:

```text
[1, 2, 1]
```

### Iteration 3: i = 3

Append `1`:

```text
[1, 2, 1, 1]
```

Update from right to left:

```text
Index 2: 1 + 2 = 3
Index 1: 2 + 1 = 3
```

Result:

```text
[1, 3, 3, 1]
```

### Iteration 4: i = 4

Append `1`:

```text
[1, 3, 3, 1, 1]
```

Update from right to left:

```text
Index 3: 1 + 3 = 4
Index 2: 3 + 3 = 6
Index 1: 3 + 1 = 4
```

Final result:

```java
[1, 4, 6, 4, 1]
```

---

## 8. Complexity Analysis

Let `n = rowIndex + 1`.

| Approach | Time | Space |
|---|---|---|
| Entire triangle | O(n²) | O(n²) |
| One-dimensional list | O(n²) | O(n) |

### Optimized approach

**Time Complexity: O(n²)**

The algorithm performs approximately:

```text
1 + 2 + 3 + ... + n
```

element operations.

**Space Complexity: O(n)**

Only one list is maintained, containing the requested row.

The returned list itself requires O(n) space.

---

## 9. Common Mistakes

1. Treating `rowIndex` as a one-based index instead of zero-based.
2. Using `i < rowIndex` instead of `i <= rowIndex`.
3. Using `rowIndex` instead of the current row size in the inner loop.
4. Accessing a previous row when `i == 0`.
5. Updating the list from left to right.
6. Forgetting to append the final `1` before updating middle elements.
7. Confusing `List.set(index, value)` with `List.add(index, value)`.

### Important Java Difference

```java
row.set(index, value);
```

Replaces the existing element.

```java
row.add(value);
```

Appends a new element.

```java
row.add(index, value);
```

Inserts an element at a position and shifts subsequent elements.

For this problem, use `set()` to update existing middle elements.

---

## 10. Interview Explanation

"I need to return only one specific row of Pascal's Triangle, so generating and storing the entire triangle is unnecessary.

I maintain a single list initialized with `[1]`.

For each subsequent row, I append `1` at the end. Then I update the middle elements using the sum of the current element and its previous element.

I perform these updates from right to left so that the original values from the previous row are not overwritten before they are used.

This reduces space complexity from O(n²) to O(n), while time complexity remains O(n²)."

---

## 11. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| LC 118 – Pascal's Triangle | Row Construction |
| LC 119 – Pascal's Triangle II | In-place Update |
| LC 70 – Climbing Stairs | Dynamic Programming |
| LC 198 – House Robber | Space Optimization |
| LC 62 – Unique Paths | 1D DP Optimization |

---

## 12. Quick Revision

Remember these points:

- `rowIndex` is zero-based.
- Start with `[1]`.
- Append `1` for every new row.
- Calculate middle elements using adjacent values.
- Update from right to left.
- Use `List.set()` to replace existing elements.
- Return only the requested row.

**Core Pattern: One-Dimensional DP + In-place Update + Right-to-Left Traversal.**

**Complexity:**
- Time: O(n²)
- Space: O(n)

**Interview takeaway:** When a problem asks for only the final DP row, check whether previous rows can be discarded and reused in place.
