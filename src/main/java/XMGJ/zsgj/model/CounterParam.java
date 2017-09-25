package XMGJ.zsgj.model;

import lombok.Data;

@Data
public class CounterParam implements IBusParser {

	private int visitType;

	@Override
	public byte[] encode() {
		DataOutputStream out = new DataOutputStream();
		out.writeInt(this.visitType);
		return out.getData().array();
	}
}
