package offline.dictionary;

public class WordOther {
    private Boolean LIKE = false;
    private Boolean DISLIKE = false;
    private String keyWord;

    public WordOther() {}

    public WordOther(Boolean LIKE, Boolean DISLIKE, String keyWord) {
        this.LIKE = LIKE;
        this.DISLIKE =DISLIKE;
        this.keyWord = keyWord;
    }

    public Boolean getLIKE() {
        return LIKE;
    }

    public Boolean getDISLIKE() {
        return DISLIKE;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setDISLIKE(Boolean DISLIKE) {
        this.DISLIKE = DISLIKE;
    }

    public void setLIKE(Boolean LIKE) {
        this.LIKE = LIKE;
    }
}
