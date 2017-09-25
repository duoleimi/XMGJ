package XMGJ.zsgj.model;

import lombok.Data;

@Data
public class BusSearchParamObj implements IBusParser {

	private String cityName;
	private String routeNumber;
	private String beginStation;
	private String endStation;
	private String passStation;
	private String busNumber;
	private String param1;
	private String parmm2;
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	private boolean isFirstSearch;
	
	
	@Override
	public byte[] encode() {
		DataOutputStream out = new DataOutputStream();
		out.writeUTF(this.cityName);
		out.writeUTF(this.routeNumber);
		out.writeUTF(this.beginStation);
		out.writeUTF(this.endStation);
		out.writeUTF(this.passStation);
		out.writeUTF(this.busNumber);
		out.writeUTF(this.param1);
		out.writeUTF(this.parmm2);
		out.writeDouble(this.minX);
		out.writeDouble(this.minY);
		out.writeDouble(this.maxX);
		out.writeDouble(this.maxY);
		out.writeBoolean(this.isFirstSearch);
		return out.getData().array();
	}

}
