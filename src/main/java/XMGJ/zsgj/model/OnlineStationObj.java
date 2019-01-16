package XMGJ.zsgj.model;

import java.io.IOException;
import java.util.List;

import org.aspectj.apache.bcel.util.ByteSequence;

import com.google.common.collect.Lists;
import com.zzy.base.utils.JsonUtil;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class OnlineStationObj implements IBusParser {

	private int userValidationFlag;
	private String userName;
	private int trialFlag;
	private int versionValidationFlag;
	private String serverVersion;
	private String downloadAddress;
	private String updateMessage;
	private String stationVersion;
	private List<String> stations = Lists.newArrayList();
	
	
	@Override
	@SneakyThrows
	public void decode(byte[] data) {
		ByteSequence stream = new ByteSequence(data);
		
		try {
			this.userValidationFlag = stream.readInt();
			this.userName = stream.readUTF();
			this.trialFlag = stream.readInt();
			this.versionValidationFlag = stream.readInt();
			this.serverVersion = stream.readUTF();
			this.downloadAddress = stream.readUTF();
			this.updateMessage = stream.readUTF();
			this.stationVersion = stream.readUTF();
			int dataLen = stream.readInt();
			long stationCount = stream.readInt();
			int offset = data.length - stream.available();
			for (int i = 0; i < stationCount; i++) {
				short strLen = stream.readShort();
				offset += 2 + strLen;
//				System.out.println("read:"+ strLen);
				byte[] b = new byte[strLen];
				stream.readFully(b);
				String station = new String(b, "utf-8");
				
//				System.out.println(offset);
//				System.out.println(stream.getIndex());
				
//				System.out.println("read3:"+ station.getBytes("utf-8").length);
//				System.out.println("read:"+ station);
				stations.add(station);
			}
			System.out.println(JsonUtil.bean2Json(stations));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
