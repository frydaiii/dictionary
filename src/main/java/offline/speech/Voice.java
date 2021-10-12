package offline.speech;

import javax.sound.sampled.AudioInputStream;
import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;

public class Voice
{
	private MaryInterface marytts;
	private AudioPlayer ap;

	public Voice()
	{
		String voiceName = "cmu-slt-hsmm";
		try
		{
			marytts = new LocalMaryInterface();
			marytts.setVoice(voiceName);

			ap = new AudioPlayer();
		}
		catch (MaryConfigurationException ex)
		{
			ex.printStackTrace();
		}
	}

	public void Say(String input)
	{
		try
		{
			AudioInputStream audio = marytts.generateAudio(input);

			ap.setAudio(audio);
			ap.start();
		}
		catch (SynthesisException ex)
		{
			System.err.println("Error saying phrase.");
		}
	}

	public static void main(String[] args) {
		/**
		 * Test code goes here.*/

		Voice voice = new Voice();
		voice.Say("hey buddy, how are you today?");
	}
}