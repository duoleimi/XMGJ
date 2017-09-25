package XMGJ.zsgj.model;

import lombok.Data;

@Data
public class GeneralParam implements IBusParser {

	private String str1;
	private String str2;
	private int int1;
	private int int2;
	private String str3;
	private String str4;
	
	
	@Override
	public byte[] encode() {
		DataOutputStream out = new DataOutputStream();
		out.writeUTF(this.str1);
		out.writeUTF(this.str2);
		out.writeInt(this.int1);
		out.writeInt(this.int2);
		out.writeUTF(this.str3);
		out.writeUTF(this.str4);
		return out.getData().array();
	}
	
}
