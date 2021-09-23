module oop.dictionary.dictionary {
	requires javafx.controls;
	requires javafx.fxml;


	opens oop.dictionary.dictionary to javafx.fxml;
	exports oop.dictionary.dictionary;
}