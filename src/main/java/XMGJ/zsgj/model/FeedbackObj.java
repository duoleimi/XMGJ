package XMGJ.zsgj.model;

import lombok.Data;

@Data
public class FeedbackObj implements IBusParser {

	private int fromType;
	private String adviceType;
	private String content;
	
	@Override
	public byte[] encode() {
		DataOutputStream out = new DataOutputStream();
		out.writeInt(this.fromType);
		out.writeUTF(this.adviceType);
		out.writeUTF(this.content);
		return out.getData().array();
	}

}
