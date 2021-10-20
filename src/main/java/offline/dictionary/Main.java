package offline.dictionary;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Load database để thực hiện tìm kiếm
        Dictionary dictionary = new Dictionary();
        dictionary.ReadFileEN_VN();

        // tìm kiếm từ Hello
        System.out.println(dictionary.LookUpEN_VN("A A A").getDetail());
    }
}
