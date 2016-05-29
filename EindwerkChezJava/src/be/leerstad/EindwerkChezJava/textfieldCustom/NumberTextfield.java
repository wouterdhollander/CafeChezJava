package be.leerstad.EindwerkChezJava.textfieldCustom;

import javafx.scene.control.TextField;
/**
 * Found and adaptied on the world wide web
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class NumberTextfield extends TextField {
	
	public NumberTextfield() {
		// TODO Auto-generated constructor stub
		this.setPromptText("hoeveelheid");
	}
	
	@Override
	public void replaceText(int start, int end, String text) {

		if ((text.matches("[0-9)]*")))// && this.getText().length()< 2 ))// || text.isEmpty())
		{
			super.replaceText(start, end, text);
		}	
	}
	
	public void setInt(int number)
	{
		this.setText(Integer.toString(number));
	}
	
	public int getInt() {
		// TODO Auto-generated method stub
		if (super.getText().isEmpty())
		{
			return 0;
		}
		return Integer.parseInt(super.getText());
	}
	
	
	
//	@Override
//	public void replaceText(IndexRange range, String text) {
//		if (text.matches("[0-9)]") || text.isEmpty())
//		{
//			super.replaceText(range, text);
//		}
//	}
//	
	@Override
	public void replaceSelection(String replacement) {
		// TODO Auto-generated method stub
		super.replaceSelection(replacement);
	}


	
}
