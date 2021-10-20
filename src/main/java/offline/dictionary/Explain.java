package offline.dictionary;

public class Explain {
    private String detail;
    private String keyWord;

    public Explain() {}

    public Explain(String keyWord, String detail) {
        this.keyWord = keyWord;
        this.detail = detail;
    }


    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetails(String details) {
        this.detail = details;
    }
}
