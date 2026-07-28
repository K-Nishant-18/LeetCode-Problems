class Solution {
    public boolean isPalindrome(String s) {

        StringBuilder sb = new StringBuilder();

        // Step 1: Keep only letters and digits, convert to lowercase
        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                sb.append(Character.toLowerCase(ch));
            }
        }

        // Step 2: Convert StringBuilder to String
        String cleaned = sb.toString();

        // Step 3: Compare from both ends
        int left = 0;
        int right = cleaned.length() - 1;

        while (left < right) {

            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}