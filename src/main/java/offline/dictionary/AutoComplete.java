package offline.dictionary;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class AutoComplete {
    private TrieNode root;
    private int size;

    /**
     * Constructor
     */
    public AutoComplete()
    {
        root = new TrieNode();
        size = 0;
    }

    /** Insert a lowercase word into the trie.
     */
    public void addWord() {
        Dictionary dictionary = new Dictionary();
        dictionary.ReadFileEN_VN();
        List<String> listKeyWordEN = dictionary.listKeyEN_VN;
        int length = listKeyWordEN.size();

        for (int count = 0; count < length; count++) {
            String word = listKeyWordEN.get(count);
            String wordToAdd = word.toLowerCase();
            TrieNode node = root;

            for (int i = 0; i < wordToAdd.length(); i++) {
                char c = wordToAdd.charAt(i);
                if (node.getValidNextCharacters().contains(c)) {
                    node = node.getChild(c);
                } else {
                    node = node.insert(c);
                }
            }
            if (!node.endsWord()) {
                node.setEndsWord(true);
                size++;
            }
        }

    }


    /** Returns whether the string is a word in the trie */
    public boolean isWord(String s)
    {
        String sToCheck = s.toLowerCase();
        TrieNode node = root;
        for (int i = 0; i < sToCheck.length(); i++) {
            char c = sToCheck.charAt(i);
            if (node.getValidNextCharacters().contains(c)) {
                node = node.getChild(c);
            } else {
                return false;
            }
        }
        if (node.endsWord()) {
            return true;
        }
        return false;
    }

    /**
     * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * text: The text to use at the word stem
     * n: The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */
    public List<String> predictCompletions(String prefix, int numCompletions)
    {
        //Trying to find the stem in Trie
        String prefixToCheckLowerCase = prefix.toLowerCase();
        int completionsCount = 0;
        List<String> completions = new LinkedList<String>();
        TrieNode traversal = root;

        for (int i = 0; i < prefixToCheckLowerCase.length(); i++)
        {
            if (traversal.getValidNextCharacters().contains(prefixToCheckLowerCase.charAt(i)))
            {
                traversal = traversal.getChild(prefixToCheckLowerCase.charAt(i));
            }
            //Means  stem not found, returns an empty list
            else
                return completions;
        }
        //If current word is an end word, increment the counter and add it to compeltions list
        if (traversal.endsWord())
        {
            completionsCount=1;
            completions.add(traversal.getText());
        }

        List<TrieNode> nodesToBeSearched = new LinkedList<TrieNode>();
        List<Character> ChildCharaterList = new LinkedList<Character>(traversal.getValidNextCharacters());

        //Filling the list with children of the current node, first level of of the breadth first search
        for (int i=0; i<ChildCharaterList.size(); i++)
        {
            nodesToBeSearched.add(traversal.getChild(ChildCharaterList.get(i)));
        }

        //while loop for the linked list elements and see if any compeltions exists , inside it we will also check each node children and add them to the list!!!
        while (nodesToBeSearched!=null  && nodesToBeSearched.size()>0 && completionsCount < numCompletions)
        {
            TrieNode trieNode = nodesToBeSearched.remove(0);
            if (trieNode.endsWord())
            {
                completionsCount++;
                completions.add(trieNode.getText());
            }

            List<Character> subTrieNodeCholdren = new LinkedList<Character>(trieNode.getValidNextCharacters());
            //Adding all next level tries to the linked list , kinda recursive!!!
            for (int i=0; i<subTrieNodeCholdren.size();i++)
            {
                nodesToBeSearched.add(trieNode.getChild(subTrieNodeCholdren.get(i)));
            }
        }
        return completions;
    }

    // For debugging
    public void printTree()
    {
        printNode(root);
    }

    /** Do a pre-order traversal from this node down */
    public void printNode(TrieNode curr)
    {
        if (curr == null)
            return;

        System.out.println(curr.getText());

        TrieNode next = null;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }

    public int getSize() {
        return size;
    }
}
