
# 🐞 Problem Statement

Given an array of strings `strs`, group the anagrams together. You can return the answer in any order.

Two strings are anagrams if they contain the same characters with the same frequencies, but the characters may appear in a different order.

**Example 1:**
```text
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
```

**Example 2:**
```text
Input: strs = [""]
Output: [[""]]
```

**Example 3:**
```text
Input: strs = ["a"]
Output: [["a"]]
```

---

## 📟 Your Logical Code

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);

            String temp = String.valueOf(chars);

            if (!map.containsKey(temp)) {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(temp, list);
            } else {
                List<String> currentList = map.get(temp);
                currentList.add(str);
            }
        }

        return new ArrayList<>(map.values());
    }
}
```

---

## 🎀 Explanation

### ✅ Step 1: Create a HashMap

```java
Map<String, List<String>> map = new HashMap<>();
```

The map stores:

- **Key:** A sorted version of each word.
- **Value:** A list of original words that share the same sorted characters.

For example:

```text
"aet" → ["eat", "tea", "ate"]
"ant" → ["tan", "nat"]
"abt" → ["bat"]
```

Why? Anagrams produce the same sorted string.

---

### 🔁 Step 2: Traverse each word

```java
for (String str : strs) {
    char[] chars = str.toCharArray();
    Arrays.sort(chars);

    String temp = String.valueOf(chars);
}
```

For each word, we convert it into a character array, sort the characters, and convert the sorted array back into a string.

Let's walk through a few words:

| Original word | Character array | Sorted characters | Key |
|---|---|---|---|
| `"eat"` | `['e','a','t']` | `['a','e','t']` | `"aet"` |
| `"tea"` | `['t','e','a']` | `['a','e','t']` | `"aet"` |
| `"tan"` | `['t','a','n']` | `['a','n','t']` | `"ant"` |
| `"ate"` | `['a','t','e']` | `['a','e','t']` | `"aet"` |
| `"nat"` | `['n','a','t']` | `['a','n','t']` | `"ant"` |
| `"bat"` | `['b','a','t']` | `['a','b','t']` | `"abt"` |

**Key insight:** Anagrams have identical sorted-character keys.

---

### 🔁 Step 3: Create a new group or add to an existing group

```java
if (!map.containsKey(temp)) {
    List<String> list = new ArrayList<>();
    list.add(str);
    map.put(temp, list);
} else {
    List<String> currentList = map.get(temp);
    currentList.add(str);
}
```

There are two possibilities.

**Case 1: The key doesn't exist**

For `"eat"`, the sorted key is `"aet"`. Since the map doesn't contain it, create a new list and store the word.

```text
map = {
    "aet" → ["eat"]
}
```

**Case 2: The key already exists**

When `"tea"` appears, its key is also `"aet"`. Retrieve the existing list and append the word.

```text
map = {
    "aet" → ["eat", "tea"]
}
```

When `"ate"` appears, it joins the same group:

```text
map = {
    "aet" → ["eat", "tea", "ate"]
}
```

Repeat this process for every word.

---

### 🔁 Step 4: Return the grouped anagrams

```java
return new ArrayList<>(map.values());
```

The method `map.values()` returns a collection containing all the groups:

```text
Collection<List<String>>
```

But our method must return:

```text
List<List<String>>
```

The `ArrayList` constructor accepts a collection, so we convert the map's values into a list of groups.

**Important:** `HashMap` doesn't guarantee the order of groups. That's acceptable because the problem permits any order.

---

## 🧠 Summary of the Idea

1. Traverse every string.
2. Convert it into a character array.
3. Sort the characters and convert them into a string key.
4. If the key exists, add the original string to its group.
5. Otherwise, create a new group.
6. Return all groups using `new ArrayList<>(map.values())`.

**Remember:** Sort to create a common key, then use a `HashMap` to group the original strings.

---

## ⏱️ Complexity Analysis

Let:
- `n` = number of strings.
- `k` = maximum length of a string.

| Complexity | Analysis |
|---|---|
| Time | **O(n × k log k)** — sorting each string dominates the work |
| Auxiliary space | **O(n × k)** — the map stores the original strings and their grouping keys |
| Output space | **O(n × k)** — all original strings are returned in groups |

The sorting cost for each string is O(k log k), giving O(n × k log k) overall when using `k` as the maximum string length.

---

## 🎯 Interview Takeaways

- **Pattern:** Hashing + sorting.
- **Why sort each word?** Anagrams become identical after sorting.
- **Why `Map<String, List<String>>`?** Multiple words can share the same sorted key.
- **Why `String.valueOf(chars)`?** It converts the sorted character array into the actual string key.
- **Why `map.get(temp)`?** It retrieves the existing group so we can add another word.
- **Why not use a `Set`?** We need to retain every original word, not just unique sorted keys.
- **Common mistake:** Using `chars.toString()`, which doesn't convert the character array into its character sequence.
- **Optimization opportunity:** For strings restricted to lowercase English letters, a character-frequency signature can avoid sorting each word.

**Key takeaway:** Anagrams share the same canonical representation. Use that representation as the key and group the original strings in a `HashMap`.
