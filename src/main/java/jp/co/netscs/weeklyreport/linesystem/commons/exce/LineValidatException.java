package jp.co.netscs.weeklyreport.linesystem.commons.exce;

/**
 * 返却メッセージがLineMessageAPIの仕様にあっていない場合に発生する
 * @author SCS036
 *
 */
public class LineValidatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LineValidatException(Validate message) {
		super(message.getMessage());
	}

	
	/**
	 * LineMessageingAPIの仕様を守れなかった内容
	 * @author SCS036
	 *
	 */
	public enum Validate {
		TEXTMAX("テキストの最大文字数は1000文字以下です"),MESSAGEMAX("返却するメッセージは最大５件までです");
		
		private String message;
		
		private Validate(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
	}
}
