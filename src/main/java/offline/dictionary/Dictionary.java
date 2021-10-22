package offline.dictionary;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dictionary {
    public static final String user = "root";
    public static final String password = "";
    public static final String url = "jdbc:sqlite:edict.db";
    public static final String sql = "SELECT * FROM edict";

    // Chứa tất cả các keyWord, và Object Expalian

    public HashMap<String, Explain> ListEN_VN = new HashMap<String, Explain>();
    // Chứa danh sách các từ Like, Dislike
    public static List<WordOther> listWordOther = new ArrayList<WordOther>();
    // Chứa History
    public static List<History> historyEN = new ArrayList<History>();
    //Chỉ chứa keyWord các từ
    public static List<String> listKeyEN_VN = new ArrayList<String>();



    /**
     * Gọi hàm này trong lúc chương trình khởi động để load ra database vào HashMap ListEN_VN
     * Lấy data từ database field2 là word, field3 là detail phần in ra theo dạng HTML nên
     * có thể dùng javaFX in ra
     */
    public void ReadFileEN_VN() {
        Explain temp;
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                temp = new Explain(resultSet.getString("field2"), resultSet.getString("field3"));
                listKeyEN_VN.add(temp.getKeyWord().toLowerCase());
                ListEN_VN.put(temp.getKeyWord().toLowerCase(), temp);
            }

            statement.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    /**
     * Xử lý phần tìm kiếm
     */
    public Explain LookUpEN_VN(String keyWord) {
        return ListEN_VN.get(keyWord.toLowerCase());
    }



    /**
     * XỬ LÝ CÁC CHỨC NĂNG LIKE, DISLIKE
     * các thuộc tính trong Object WordOther
     */

    /**
     * kiểm tra các từ like, lưu trữ vào listWordOther
     * Ví dụ có một người đánh dấu từ Hello là thích thì thuộc tính LIKE sẽ đc set là true
     * @param keyWord
     */
    public void CheckLIKE(String keyWord){
        // Not in list OtherWord
        if(checkExist(keyWord) == -1){
            WordOther temp = new WordOther(true, false, keyWord);
            listWordOther.add(temp);
        }
        // Have in list OtherWord
        else{
            listWordOther.get(checkExist(keyWord)).setLIKE(true);
            listWordOther.get(checkExist(keyWord)).setDISLIKE(false);
        }
    }

    /**
     * tương tự check LIKE
     * @param keyWord
     */
    public void CheckDISLIKE(String keyWord){
        // Not in list OtherWord
        if(checkExist(keyWord) == -1) {
            WordOther temp = new WordOther(false, true, keyWord);
            listWordOther.add(temp);
        }
        // Have in list OtherWord
        else{
            listWordOther.get(checkExist(keyWord)).setLIKE(false);
            listWordOther.get(checkExist(keyWord)).setDISLIKE(true);
        }
    }

    /**
     * (Không quan trọng lắm)
     * Hàm này bổ trợ cho 2 ham checkLIKE và checkDISLIKE
     * @param keyWord
     */
    // check exist of word in list
    // return index key in listWordOther
    public int checkExist(String keyWord){
        for(int i = 0; i< listWordOther.size(); i++) {
            if(keyWord.equals(listWordOther.get(i).getKeyWord())){
                return i;
            }
        }

        return -1;
    }

    /**
     * Lưu lại những từ đc set LIKE or DISLIKE
     * Việc lưu danh sách từ ra file để lần sử dụng chương trình sau có thể
     * truy vấn lại các từ này
     */
    // Save ListWordOther to File("wordOtherEn_VN")
    public void SaveFileListWordOther() {
        ObjectOutputStream out = null;
        try {
            // save word love EN
            out = new ObjectOutputStream(new FileOutputStream("wordOtherEn_VN.txt"));
            out.writeObject(listWordOther.size());
            for(int i = 0; i < listWordOther.size(); i++) {
                out.writeObject(listWordOther.get(i));
            }
            out.close();
        }
        catch (IOException e) {
            System.out.println("SaveFile error!!!" + e.toString());
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * đọc dữ liệu từ file txt này để biết danh sách các từ LIKE or DISLIKE.
     * Việc này nên lm cùng lúc với đọc file database
     */
    // Read listWordOther from file
    public void ReadFileListWordOther() {
        ObjectInputStream in = null;
        listWordOther.clear();

        try {
            in = new ObjectInputStream(new FileInputStream("wordOtherEn_VN.txt"));

        }
        catch(IOException e) {
            System.out.println("File Not found!!!");
        }

        try {
            in = new ObjectInputStream(new FileInputStream("wordOtherEn_VN.txt"));
            int size = (int)in.readObject();
            for(int i = 0; i < size; i++ ) {
                listWordOther.add((WordOther)in.readObject());
            }

            in.close();

        } catch(Exception e) {
            System.out.println("Read File Error!!!");
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(Exception e) {

                }
            }
        }
    }

    /**
     * XỬ LÝ PHẦN THÊM SỬA XÓA
     */
    //DELETE WORD
    public void deleteWord(String keyWord) {
        String sqlLocal = "DELETE FROM edict WHERE field2 = ?";

        if (!listKeyEN_VN.contains(keyWord)) {
            System.out.println("Từ không có trong từ điển");
        }
        //Remove ra khỏi listKey and hashMap
        ListEN_VN.remove(keyWord);
        listKeyEN_VN.remove(keyWord);

        //Remove from database
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlLocal);

            preparedStatement.setString(1, keyWord);
            preparedStatement.executeUpdate();

            System.out.println("Success");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

    }

    //ADD NEW WORD (Cũng chỉ thêm vào trong HashMap, chỉ thêm vào theo form cố định)
    public void addNewWord(String keyWord, String attribute, String meaning, String pronounce) {
        String sqlLocal = "INSERT INTO edict values (?,?,?)";

        // Tạo form nhập
        String detail = "<C><F><I><N><Q>@" + keyWord + " /" + pronounce + "/<br />*  " + attribute + "<br />- "
                + meaning + "</Q></N></I></F></C>";
        Explain temp = new Explain(keyWord, detail);

        //KT từ có tồn tại trong DB ko?
        if (listKeyEN_VN.contains(keyWord)) {
            System.out.println("Từ đã có trong từ điển");
            return;
        }
        // Thêm vào hashmap
        ListEN_VN.put(keyWord, temp);
        listKeyEN_VN.add(keyWord);

        // Thêm vào Database
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlLocal);

            preparedStatement.setString(2, keyWord);
            preparedStatement.setString(3, detail);
            preparedStatement.executeUpdate();

            System.out.println("Success");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    //UPDATE WORD (cũng chỉ xử lý trong hashMap)
    public void updateWord(String keyWord, String attribute, String meaning, String pronounce) {
        deleteWord(keyWord);
        addNewWord(keyWord, attribute, meaning, pronounce);
    }

    /**
     * XỬ LÝ PHẦN LỊCH SỬ TRA CỨU
     * Khá giống phần LIKE, DISLIKE nhưng chưa xử lý chức năng History, chỉ làm sẵn hàm lưu trữ
     */
    // SAVE FILE HISTORY SEARCH
    public void SaveHistory() {
        ObjectOutputStream out = null;
        try {
            // save word love EN
            out = new ObjectOutputStream(new FileOutputStream("historyEN.txt"));
            out.writeObject(historyEN.size());
            for(int i = 0; i < historyEN.size(); i++) {
                out.writeObject(historyEN.get(i));
            }
            out.close();
        } catch (IOException e) {
            System.out.println("SaveFile error!!!" + e.toString());
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            }
            catch (Exception e) {
            }
        }
    }

    //READ FILE HISTORY
    public void ReadFileHistory() {
        ObjectInputStream in = null;
        historyEN.clear();

        try {
            in = new ObjectInputStream(new FileInputStream("historyEN.txt"));

        } catch(IOException e) {
            System.out.println("Notfound!!!");
        }

        try {
            int size = (int)in.readObject();
            for(int i = 0; i < size; i++) {
                historyEN.add((History)in.readObject());
            }

            in.close();
        } catch(Exception e) {
            System.out.println("ReadFile Error!!!");
        } finally {
            if(in != null) {
                try {
                    in.close();
                }
                catch(Exception e) {

                }
            }
        }
    }

}
