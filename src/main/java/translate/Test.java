package translate;

import java.util.Scanner;
import marytts.LocalMaryInterface;

public class Test {
	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		System.out.print("Enter sentence to translate: ");
//		String sentence = sc.nextLine();
//		System.out.println(Online.libreTrans(sentence));
		Voice voice = new Voice("cmu-slt-hsmm");
		voice.say("how are you");
	}
}
