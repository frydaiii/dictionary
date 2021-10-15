package offline.dictionary;

public class History {
    private String keyWord;
    private int Day;
    private int Month;
    private int Year;

    public History() {}

    public History(String keyWord, int day, int month, int year) {
        this.keyWord = keyWord;
        this.Day = day;
        this.Month = month;
        this.Year = year;
    }

    public String getKey() {
        return this.keyWord;
    }

    public int getDay() {
        return this.Day;
    }

    public int getMonth() {
        return this.Month;
    }

    public int getYear() {
        return this.Year;
    }

    public void setKey(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setDay(int date) {
        this.Day = date;
    }

    public void setMonth(int month) {
        this.Month = month;
    }

    public void setYear(int year) {
        this.Year = year;
    }
}
