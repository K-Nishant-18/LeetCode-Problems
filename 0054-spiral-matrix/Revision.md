
# LC 54 — Spiral Matrix

**Difficulty:** Medium  
**Pattern:** Matrix Traversal | Boundary Simulation  
**Topic:** Arrays / 2D Arrays

---

## 1. Problem Statement

Given an `m x n` matrix, return all elements of the matrix in spiral order.

Spiral order means traversing the matrix in this sequence:

1. Left to Right
2. Top to Bottom
3. Right to Left
4. Bottom to Top

Repeat this process for the remaining inner matrix until every element is visited.

### Example 1

**Input:**
```java
matrix = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]
```

**Output:**
```java
[1, 2, 3, 6, 9, 8, 7, 4, 5]
```

### Example 2

**Input:**
```java
matrix = [
    [1, 2, 3, 4],
    [5, 6, 7, 8],
    [9, 10, 11, 12]
]
```

**Output:**
```java
[1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]
```

---

## 2. Core Intuition

Imagine peeling an onion layer by layer.

You first collect the outermost layer of the matrix. Once that layer is completely traversed, you move inward and collect the next layer.

Instead of tracking every visited cell, maintain four boundaries that define the unvisited region.

The four boundaries are:

| Boundary | Meaning |
|---|---|
| `top` | First unvisited row |
| `bottom` | Last unvisited row |
| `left` | First unvisited column |
| `right` | Last unvisited column |

Initially:

```java
int top = 0;
int bottom = m - 1;
int left = 0;
int right = n - 1;
```

---

## 3. The Four Traversal Steps

### Step 1: Traverse Top Row

Move from left to right.

```java
for (int i = left; i <= right; i++) {
    list.add(matrix[top][i]);
}
top++;
```

After completing the row, increment `top` because that row has been visited.

### Step 2: Traverse Right Column

Move from top to bottom.

```java
for (int i = top; i <= bottom; i++) {
    list.add(matrix[i][right]);
}
right--;
```

After completing the column, decrement `right`.

### Step 3: Traverse Bottom Row

Move from right to left.

```java
if (top <= bottom) {
    for (int i = right; i >= left; i--) {
        list.add(matrix[bottom][i]);
    }
    bottom--;
}
```

**Why check `top <= bottom`?**

The top row was already visited. If the top and bottom boundaries have crossed, traversing the bottom row would add duplicate elements.

### Step 4: Traverse Left Column

Move from bottom to top.

```java
if (left <= right) {
    for (int i = bottom; i >= top; i--) {
        list.add(matrix[i][left]);
    }
    left++;
}
```

**Why check `left <= right`?**

The right column was already visited. If the left and right boundaries have crossed, traversing the left column would add duplicate elements.

---

## 4. Final Java Solution

```java
import java.util.*;

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;

        List<Integer> list = new ArrayList<>();

        int top = 0;
        int right = n - 1;
        int bottom = m - 1;
        int left = 0;

        while (top <= bottom && left <= right) {

            // 1. Left to Right
            for (int i = left; i <= right; i++) {
                list.add(matrix[top][i]);
            }
            top++;

            // 2. Top to Bottom
            for (int i = top; i <= bottom; i++) {
                list.add(matrix[i][right]);
            }
            right--;

            // 3. Right to Left
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    list.add(matrix[bottom][i]);
                }
                bottom--;
            }

            // 4. Bottom to Top
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    list.add(matrix[i][left]);
                }
                left++;
            }
        }

        return list;
    }
}
```

---

## 5. Dry Run

Consider:

```java
matrix = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]
```

Initial boundaries:

```text
top = 0
bottom = 2
left = 0
right = 2
```

### First iteration

| Step | Elements added | Boundary update |
|---|---|---|
| Left → Right | 1, 2, 3 | `top = 1` |
| Top → Bottom | 6, 9 | `right = 1` |
| Right → Left | 8, 7 | `bottom = 1` |
| Bottom → Top | 4 | `left = 1` |

Current result:

```java
[1, 2, 3, 6, 9, 8, 7, 4]
```

### Second iteration

Remaining matrix:

```java
[5]
```

Only one element remains.

It gets added during the top-row traversal.

Final result:

```java
[1, 2, 3, 6, 9, 8, 7, 4, 5]
```

All elements are visited exactly once.

---

## 6. Why Does the Outer While Loop Work?

```java
while (top <= bottom && left <= right)
```

The loop continues only while an unvisited region exists.

- `top <= bottom` means at least one unvisited row remains.
- `left <= right` means at least one unvisited column remains.

Once either condition becomes false, the remaining region is empty.

---

## 7. Complexity Analysis

Let:
- `m` = number of rows
- `n` = number of columns

| Complexity | Analysis |
|---|---|
| Time | `O(m × n)` |
| Auxiliary Space | `O(1)` |
| Output Space | `O(m × n)` |

**Why O(m × n) time?**

Every matrix element is added to the result exactly once. Therefore, the total number of operations is proportional to the number of matrix elements.

The four boundary variables use constant auxiliary space. The returned list requires `O(m × n)` space.

---

## 8. Important Edge Cases

### Case 1: Single Row

```java
matrix = [[1, 2, 3, 4]]
```

Output:

```java
[1, 2, 3, 4]
```

### Case 2: Single Column

```java
matrix = [
    [1],
    [2],
    [3],
    [4]
]
```

Output:

```java
[1, 2, 3, 4]
```

### Case 3: One Element

```java
matrix = [[5]]
```

Output:

```java
[5]
```

### Case 4: Rectangular Matrix

```java
matrix = [
    [1, 2, 3, 4],
    [5, 6, 7, 8]
]
```

Output:

```java
[1, 2, 3, 4, 8, 7, 6, 5]
```

---

## 9. Common Mistakes

1. Using the wrong loop direction for the right column or left column.
2. Forgetting to update boundaries after completing a traversal.
3. Forgetting the `top <= bottom` check before traversing the bottom row.
4. Forgetting the `left <= right` check before traversing the left column.
5. Using incorrect loop conditions such as `i >= bottom` when traversing top to bottom.
6. Confusing row and column indices:
   - `matrix[row][column]`
   - `matrix[top][i]` traverses a row.
   - `matrix[i][right]` traverses a column.

---

## 10. Interview Explanation

"I use four pointers to represent the boundaries of the unvisited matrix: top, bottom, left, and right.

I traverse the outer layer in four directions: left to right, top to bottom, right to left, and bottom to top.

After each traversal, I update the corresponding boundary to shrink the remaining unvisited region.

Before traversing the bottom row and left column, I check whether the boundaries still form a valid region. This prevents duplicate elements when only one row or one column remains.

I repeat this process until the boundaries cross.

Every element is visited exactly once, giving O(m × n) time complexity and O(1) auxiliary space, excluding the output list."

---

## 11. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| LC 59 – Spiral Matrix II | Boundary Simulation |
| LC 48 – Rotate Image | Matrix Manipulation |
| LC 73 – Set Matrix Zeroes | Matrix Traversal |
| LC 885 – Spiral Matrix III | Direction Simulation |

---

## 12. Quick Revision

Before an interview, remember:

- Four boundaries: `top`, `bottom`, `left`, `right`.
- Four directions: Right → Down → Left → Up.
- Update the corresponding boundary after each traversal.
- Check `top <= bottom` before bottom-row traversal.
- Check `left <= right` before left-column traversal.
- Stop when `top > bottom` or `left > right`.
- Time: `O(m × n)`.
- Auxiliary space: `O(1)`.

**Pattern to remember:** Matrix + Four Boundaries + Layer-by-Layer Traversal.
