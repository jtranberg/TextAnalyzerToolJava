import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;

public class TextAnalysisToolGUI {

    public static void main(String[] args) {

        //Using JFrame to produce an external window for input and results display
        JFrame frame = new JFrame("My Text Analysis Tool");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        JLabel instructionLabel = new JLabel("Please enter a text to analyze, maximun 256 characters:");
        instructionLabel.setBounds(20, 20, 400, 20);
        frame.add(instructionLabel);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(20, 50, 540, 100);
        frame.add(textArea);

        JButton analyzeButton = new JButton("Analyze Text");
        analyzeButton.setBounds(20, 160, 120, 30);
        frame.add(analyzeButton);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(20, 200, 540, 150);
        resultArea.setEditable(false);
        frame.add(resultArea);

        analyzeButton.addActionListener((ActionEvent e) -> {
            String inputText = textArea.getText();
            if (inputText.length() > 256) {
                resultArea.setText("Please use less than 256 characters.");
                return;
            }
            
            // Remove spaces to count characters accurately
            String textWithoutSpaces = inputText.replace(" ", "");
            
            // Count the total amount of characters
            int charCount = textWithoutSpaces.length();
            
            // Map for most common character
            Map<Character, Integer> mostCommonCharacters = findMostCommonCharacters(textWithoutSpaces);
            int maxCount = mostCommonCharacters.values().iterator().next();
            
            //Use string builder to show results and use result as a variable
            StringBuilder result = new StringBuilder();
            result.append("The total number of characters in text excluding spaces: ").append(charCount).append("\n");
            
            //Use the method  result.append for output of results and messages
            
            //If loop for itorating the count of each character and alert for ties betweencharacters
            if (mostCommonCharacters.size() > 1) {
                result.append("There is a tie between characters: ");
                for (char c : mostCommonCharacters.keySet()) {
                    result.append("'").append(c).append("' ");
                }
                result.append("with ").append(maxCount).append(" occurrences each.\n");
            } else {
                char mostCommonChar = mostCommonCharacters.keySet().iterator().next();
                result.append("Most common character: ").append(mostCommonChar).append(" with ").append(maxCount).append(" occurrences.\n");
            }
            
            // Itorate to count and display number of words in the text
            String[] words = inputText.split("\\s+");
            int wordCount = words.length;
            result.append("Total number of words in the text: ").append(wordCount).append("\n");
            
            // Count and display number of unique words in text
            int uniqueWordCount = getUniqueWordCount(inputText);
            result.append("Number of unique words in text: ").append(uniqueWordCount).append("\n");
            
            // Add frequency input field
            result.append("Enter a character to count its frequency used in text:\n");
            
            //Input for user
            String charToFind = JOptionPane.showInputDialog("Please enter a character to count its frequency used in text:");
            if (charToFind != null && charToFind.length() == 1) {
                char chartoFind = charToFind.charAt(0);
                int charFrequency = getCharFrequency(textWithoutSpaces, chartoFind);
                result.append("Frequency of the character \"").append(chartoFind).append("\" is ").append(charFrequency).append(" times\n");
            }
            
            //Input for user
            String wordToFind = JOptionPane.showInputDialog("Please enter a word to find its frequency in the text:");
            if (wordToFind != null && !wordToFind.isEmpty()) {
                int wordFrequency = getWordFrequencyCount(inputText, wordToFind);
                result.append("The frequency of the word \"").append(wordToFind).append("\" is ").append(wordFrequency).append(" times\n");
            }
            //A cherry greeting
            result.append("Your program has completed, please enjoy your day or run the program on a new text block.\n");
            
            resultArea.setText(result.toString());
        });

        frame.setVisible(true);
    }
    //Map to findMostCommonCharacters
    private static Map<Character, Integer> findMostCommonCharacters(String text) {
        text = text.toLowerCase();
        int[] charCounts = new int[256];
        for (char c : text.toCharArray()) {
            if (c != ' ') {
                charCounts[c]++;
            }
        }
        //Get maxcoucnt of characters
        int maxCount = -1;
        for (int count : charCounts) {
            if (count > maxCount) {
                maxCount = count;
            }
        }
        //Use LinkedHashMap not hashmap
        Map<Character, Integer> mostCommonCharacters = new LinkedHashMap<>();
        for (int i = 0; i < charCounts.length; i++) {
            if (charCounts[i] == maxCount) {
                mostCommonCharacters.put((char) i, maxCount);
            }
        }
        return mostCommonCharacters;
    }
    //Itorate to find frequency of character
    private static int getCharFrequency(String text, char character) {
        text = text.toLowerCase();
        character = Character.toLowerCase(character);
        int charFrequency = 0;
        for (char c : text.toCharArray()) {
            if (c == character) {
                charFrequency++;
            }
        }
        return charFrequency;
    }
    //Itorate to get getUniqueWordCount
    private static int getUniqueWordCount(String text) {
        text = text.toLowerCase();
        String[] words = text.split("\\s+");
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));
        return uniqueWords.size();
    }
    //itorate to getWordFrequencyCount
    private static int getWordFrequencyCount(String text, String word) {
        text = text.toLowerCase();
        word = word.toLowerCase();
        String[] words = text.split("\\s+");
        int frequency = 0;
        for (String w : words) {
            if (w.equals(word)) {
                frequency++;
            }
        }
        return frequency;
    }
}