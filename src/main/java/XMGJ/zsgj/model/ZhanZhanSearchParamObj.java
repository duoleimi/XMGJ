package XMGJ.zsgj.model;

import lombok.Data;

@Data
public class ZhanZhanSearchParamObj implements IBusParser {

	private String startPointName;
	private String startPointLat;
	private String startPointLng;
	private String endPointName;
	private String endPointLat;
	private String endPointLng;
	private int version;
	
	
	@Override
	public byte[] encode() {
		DataOutputStream out = new DataOutputStream();
		out.writeUTF(this.startPointName);
		out.writeUTF(this.startPointLat);
		out.writeUTF(this.startPointLng);
		out.writeUTF(this.endPointName);
		out.writeUTF(this.endPointLat);
		out.writeUTF(this.endPointLng);
		out.writeInt(this.version);
		return out.getData().array();
	}

}
