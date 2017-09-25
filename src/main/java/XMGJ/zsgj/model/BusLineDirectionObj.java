package XMGJ.zsgj.model;

import lombok.Data;

@Data
public class BusLineDirectionObj implements IBusParser {

	private String routeNumber;
	private String upperOrDown;
	private boolean readAllStation;
	private String stationName;

	public byte[] encode() {
		DataOutputStream out = new DataOutputStream();
		out.writeUTF(this.routeNumber);
		out.writeUTF(this.upperOrDown);
		out.writeBoolean(this.readAllStation);
		out.writeUTF(this.stationName);
		return out.getData().array();
	}

}
