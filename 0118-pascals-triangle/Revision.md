
# LC 118 — Pascal's Triangle

**Difficulty:** Easy  
**Pattern:** Array | Dynamic Programming | Simulation  
**Topic:** 2D Array / List of Lists

---

## 1. Problem Statement

Given an integer `numRows`, generate the first `numRows` of Pascal's Triangle.

In Pascal's Triangle:

- Every row starts with `1`.
- Every row ends with `1`.
- Every middle element is the sum of the two elements directly above it in the previous row.

### Example 1

**Input:**
```java
numRows = 5
```

**Output:**
```java
[
    [1],
    [1, 1],
    [1, 2, 1],
    [1, 3, 3, 1],
    [1, 4, 6, 4, 1]
]
```

### Example 2

**Input:**
```java
numRows = 1
```

**Output:**
```java
[
    [1]
]
```

---

## 2. Core Intuition

Think of building a wall row by row.

You cannot construct the next row without looking at the previous row.

For example:

```text
          1
         1 1
        1 2 1
       1 3 3 1
      1 4 6 4 1
```

To generate a new row:

1. Add `1` at the beginning.
2. Calculate the middle elements using the previous row.
3. Add `1` at the end.
4. Store the completed row in the result.

### The most important formula

```java
previousRow.get(j - 1) + previousRow.get(j)
```

This calculates each middle element using its two adjacent elements from the previous row.

---

## 3. Understand the Row Structure

Consider generating the fourth row:

Previous row:

```text
[1, 2, 1]
```

New row:

```text
[1, ?, ?, 1]
```

Calculate the middle elements:

```text
1 + 2 = 3
2 + 1 = 3
```

Therefore:

```text
[1, 3, 3, 1]
```

### Important observations

| Observation | Explanation |
|---|---|
| Row size | `i + 1` |
| First element | Always `1` |
| Last element | Always `1` |
| Middle elements | Sum of adjacent previous-row elements |
| Previous row | `result.get(i - 1)` |

Here, `i` is the zero-based row index.

---

## 4. Final Java Solution

```java
import java.util.*;

class Solution {
    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {

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

        return result;
    }
}
```

---

## 5. Code Explanation

### Step 1: Initialize the result

```java
List<List<Integer>> result = new ArrayList<>();
```

This stores all the rows of Pascal's Triangle.

Each element inside `result` is another `List<Integer>` representing one row.

### Step 2: Generate rows

```java
for (int i = 0; i < numRows; i++)
```

The outer loop controls how many rows are generated.

The current row contains `i + 1` elements.

### Step 3: Handle the first row

```java
if (i == 0) {
    list.add(1);
}
```

The first row is `[1]`.

There is no previous row to access.

### Step 4: Access the previous row

```java
List<Integer> previousRow = result.get(i - 1);
```

Every row after the first is generated using the previously completed row.

### Step 5: Add the first boundary element

```java
list.add(1);
```

Every row starts with `1`.

### Step 6: Generate middle elements

```java
for (int j = 1; j < rowSize - 1; j++) {
    list.add(
        previousRow.get(j - 1) +
        previousRow.get(j)
    );
}
```

The loop calculates only the middle elements.

For example:

```text
Previous row: [1, 2, 1]

1 + 2 = 3
2 + 1 = 3
```

### Step 7: Add the last boundary element

```java
list.add(1);
```

Every row ends with `1`.

### Step 8: Store the completed row

```java
result.add(list);
```

The completed row becomes the previous row for the next iteration.

Finally:

```java
return result;
```

---

## 6. Dry Run

Input:

```java
numRows = 5
```

| Iteration | Previous row | Generated row |
|---|---|---|
| `i = 0` | None | `[1]` |
| `i = 1` | `[1]` | `[1, 1]` |
| `i = 2` | `[1, 1]` | `[1, 2, 1]` |
| `i = 3` | `[1, 2, 1]` | `[1, 3, 3, 1]` |
| `i = 4` | `[1, 3, 3, 1]` | `[1, 4, 6, 4, 1]` |

Final result:

```java
[
    [1],
    [1, 1],
    [1, 2, 1],
    [1, 3, 3, 1],
    [1, 4, 6, 4, 1]
]
```

---

## 7. Understanding the Loop Boundaries

This condition is important:

```java
for (int j = 1; j < rowSize - 1; j++)
```

Why?

The first and last elements are already handled separately.

For a row of size `4`:

```text
Index:  0  1  2  3
Row:   [1, ?, ?, 1]
```

Only indices `1` and `2` need calculation.

Therefore, the middle-element loop starts at `1` and stops before `rowSize - 1`.

---

## 8. Complexity Analysis

Let `n = numRows`.

### Time Complexity: O(n²)

The number of elements generated is:

```text
1 + 2 + 3 + ... + n
```

Using the arithmetic series formula:

```text
n * (n + 1) / 2
```

Therefore:

```text
O(n²)
```

### Space Complexity

| Space | Complexity |
|---|---|
| Output structure | `O(n²)` |
| Auxiliary space | `O(1)` |

The output stores approximately `n² / 2` elements.

The algorithm uses only a constant number of additional variables, excluding the output.

---

## 9. Edge Cases

### Case 1: One row

```java
numRows = 1
```

Output:

```java
[[1]]
```

### Case 2: Two rows

```java
numRows = 2
```

Output:

```java
[
    [1],
    [1, 1]
]
```

### Case 3: Five rows

```java
numRows = 5
```

Output:

```java
[
    [1],
    [1, 1],
    [1, 2, 1],
    [1, 3, 3, 1],
    [1, 4, 6, 4, 1]
]
```

### Case 4: Zero rows

```java
numRows = 0
```

Output:

```java
[]
```

The outer loop never executes, so the empty result is returned.

---

## 10. Common Mistakes

1. Trying to access `result.get(i - 1)` when `i == 0`.
2. Forgetting to add the first `1`.
3. Forgetting to add the last `1`.
4. Using incorrect indices for the previous row.
5. Using `j <= rowSize - 1` instead of `j < rowSize - 1`.
6. Forgetting `result.add(list)` after completing each row.
7. Reusing the same mutable list for every row instead of creating a new list in each iteration.

---

## 11. Interview Explanation

"I use a list of lists to construct Pascal's Triangle row by row.

For every row, I first create a new list. The first and last elements are always 1.

For the middle elements, I access the previous row and add the two adjacent elements using their indices.

Once the current row is complete, I add it to the result. This allows the next iteration to use it as its previous row.

The total number of elements generated is n times n plus 1 divided by 2, so the time complexity is O(n²). The output takes O(n²) space, while the auxiliary space is O(1)."

---

## 12. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| LC 119 – Pascal's Triangle II | Array / Space Optimization |
| LC 118 – Pascal's Triangle | Row Construction |
| LC 54 – Spiral Matrix | Matrix Traversal |
| LC 59 – Spiral Matrix II | Matrix Simulation |

---

## 13. Quick Revision

Remember these five things:

1. Outer loop generates rows.
2. Every row has `i + 1` elements.
3. First and last elements are always `1`.
4. Middle elements come from the sum of two adjacent elements in the previous row.
5. Store each completed row in `result`.

**Core pattern: Previous Row → Calculate Middle Elements → Build Current Row.**

**Complexity:** O(n²) time, O(n²) output space.
