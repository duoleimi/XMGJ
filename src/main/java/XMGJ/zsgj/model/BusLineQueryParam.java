package XMGJ.zsgj.model;

import lombok.Data;

@Data
public class BusLineQueryParam implements IBusParser {

	private String cityName;
	private String routeNumber;
	private String upperOrDown;
	private String stationName;
	private String stationOrder;
	private boolean isFirst;
	
	
	@Override
	public byte[] encode() {
		DataOutputStream out = new DataOutputStream();
		out.writeUTF(this.cityName);
		out.writeUTF(this.routeNumber);
		out.writeUTF(this.upperOrDown);
		out.writeUTF(this.stationName);
		out.writeUTF(this.stationOrder);
		out.writeBoolean(this.isFirst);
		return out.getData().array();
	}

}
