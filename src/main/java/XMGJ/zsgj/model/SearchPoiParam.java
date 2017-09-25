package XMGJ.zsgj.model;

import lombok.Data;

@Data
public class SearchPoiParam implements IBusParser {

	private String area;
	private String shangquan;
	private String lat;
	private String lng;
	private String radius;
	private String searchtype;
	private String keyword;
	private String price;
	private String qyflag;
	private String spflag;
	private String yhjflag;
	private String tgflag;
	private String perpageCount;
	private String pageIndex;
	private String citycode;
	
	
	@Override
	public byte[] encode() {
		DataOutputStream out = new DataOutputStream();
		out.writeUTF(this.area);
		out.writeUTF(this.shangquan);
		out.writeUTF(this.lat);
		out.writeUTF(this.lng);
		out.writeUTF(this.radius);
		out.writeUTF(this.searchtype);
		out.writeUTF(this.keyword);
		out.writeUTF(this.qyflag);
		out.writeUTF(this.spflag);
		out.writeUTF(this.yhjflag);
		out.writeUTF(this.tgflag);
		out.writeUTF(this.perpageCount);
		out.writeUTF(this.pageIndex);
		out.writeUTF(this.citycode);
		return out.getData().array();
	}

}
