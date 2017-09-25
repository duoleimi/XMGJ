package XMGJ.zsgj.model;

import lombok.Data;

@Data
public class VersionParamObj implements IBusParser {

	private String userPhone = "00000000000";
	private String password = "000000";
	private String iPhoneVersion = "58";
	private String stationVersion = "";
	private String downloadStationRoute = "1";
	
	
	@Override
	public byte[] encode() {
		DataOutputStream out = new DataOutputStream();
		out.writeUTF(this.userPhone);
		out.writeUTF(this.password);
		out.writeUTF(this.iPhoneVersion);
		out.writeUTF(this.stationVersion);
		out.writeUTF(this.downloadStationRoute);
		return out.getData().array();
	}

}
