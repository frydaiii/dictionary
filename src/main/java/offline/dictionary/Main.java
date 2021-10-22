package offline.dictionary;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Load database để thực hiện tìm kiếm
        Dictionary dictionary = new Dictionary();
        dictionary.ReadFileEN_VN();

        dictionary.addNewWord("aothatday", "test", "just for test", "Tung");

        String result = dictionary.fixLookUp("helo");

        if (result != null) {
            System.out.println(result);
        }

        // tìm kiếm từ Hello
//        Expalain ex = dictionary.LookUpEN_VN("nome");
//        System.out.println(ex.getDetail());

//        // test chức năng dự đoán từ
//        AutoComplete autoComplete = new AutoComplete();
//        // Đưa các keyWord trong Dictionary vào trieNote
//        autoComplete.addWord();
//        // dự đoán các từ với string "th" đầu vào và numCompletions là giới hạn số lượng số từ dự đoán ỉn ra theo thứ tự
//        List<String> listAutoComplete = autoComplete.predictCompletions("th", 5);
//        for (String i: listAutoComplete) {
//            System.out.println(i);
//        }
//
    }
}
