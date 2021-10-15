package offline.dictionary;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dictionary {
    public final String user = "root";
    public final String password = "";
    public final String url = "jdbc:sqlite:edict.db";
    public final String sql = "SELECT * FROM edict";
    public HashMap<String,Expalain> ListEN_VN = new HashMap<String, Expalain>();
    public List<WordOther> listWordOther = new ArrayList<WordOther>();
    public List<History> historyEN = new ArrayList<History>();
    public List<String> listKeyEN_VN = new ArrayList<String>();
    public final int MAX = 20;


    /**
     * Lấy data từ database field2 là word, field3 là detail phần in ra theo dạng HTML nên
     * có thể dùng javaFX in ra
     */
    public void ReadFileEN_VN() {
        Expalain temp;
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                temp = new Expalain(resultSet.getString("field2"), resultSet.getString("field3"));
                listKeyEN_VN.add(temp.getKeyWord());
                ListEN_VN.put(temp.getKeyWord(), temp);
            }

            statement.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    /**
     * XỬ LÝ PHẦN TÌM KIẾM
     * if(keyWord exist) -> Expalain
     * else @return 1 list 20 keyWord giống với keyWord cần tìm
     * @param keyWord
     */
    public List<String> searchSimilar(String keyWord) {
        List<String> temp = new ArrayList<String>();
        int count = 0;
        for(int i = 0; i < temp.size(); i++){
            if(listKeyEN_VN.get(i).startsWith(keyWord) == true){
                count++;
                temp.add(listKeyEN_VN.get(i));
            }
            if(count >= MAX){
                break;
            }
        }

        return temp;
    }

    //Tim kiem tu can thiet
    public Expalain LookUpEN_VN(String keyWord) {
        return ListEN_VN.get(keyWord);
    }

    /**
     * XỬ LÝ CÁC CHỨC NĂNG LIKE, DISLIKE
     *
     */

    // check like word
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

    // check dislike word
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

    // Save ListWordOther to File
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
        ListEN_VN.remove(keyWord);
    }

    //ADD NEW WORD
    public void addNewWord(String keyWord, String attribute, String meaning, String pronounce) {
        String detail = "<C><F><I><N><Q>@" + keyWord + " /" + pronounce + "/<br />*  " + attribute + "<br />- "
                + meaning + "</Q></N></I></F></C>";
        Expalain temp = new Expalain(keyWord, detail);

        ListEN_VN.put(keyWord, temp);
    }

    //UPDATE WORD
    public void updateWord(String keyWord, String attribute, String meaning, String pronounce) {
        deleteWord(keyWord);
        addNewWord(keyWord, attribute, meaning, pronounce);
    }

    /**
     * XỬ LÝ PHẦN LỊCH SỬ TRA CỨU
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
        }
        catch (IOException e) {
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

        }
        catch(IOException e) {
            System.out.println("Notfound!!!");
        }

        try {
            int size = (int)in.readObject();
            for(int i = 0; i < size; i++) {
                historyEN.add((History)in.readObject());
            }

            in.close();
        }
        catch(Exception e) {
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
