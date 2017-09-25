package XMGJ.zsgj.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class DataOutputStream {

	private ByteBuffer data;
	
	public DataOutputStream() {
		super();
		this.data = ByteBuffer.allocate(0);
		this.data.order(ByteOrder.LITTLE_ENDIAN);
	}
	
	void growBuffer(int capacity) {
        int pos = data.position();
        if (data.capacity() - pos < capacity) {
            int newCapacity = (data.capacity() + capacity);
            ByteBuffer newBuf = ByteBuffer.allocate(newCapacity);
            data.limit(pos).position(0);
            data = newBuf.put(data);
        }
        data.limit(pos + capacity);
        data.position(pos);
    }
	
	@SneakyThrows
	public void writeUTF(String msg) {
		byte[] msgData = msg.getBytes("utf-8");
		this.writeShort((short)msgData.length);
		this.growBuffer(msgData.length);
		this.data.put(msgData);
	}
	
	public void writeShort(short num) {
		this.growBuffer(2);
		this.data.putShort(num);
	}
	
	public void writeInt(int num) {
		this.growBuffer(4);
		this.data.putInt(num);
	}
	
	public void writeLong(long num) {
		this.growBuffer(8);
		this.data.putLong(num);
	}

	public void writeData(byte[] bytes) {
		this.writeInt(bytes.length);
		this.growBuffer(bytes.length);
		this.data.put(bytes);
	}
	
	public void writeBoolean(boolean num) {
		this.writeChar((char)(num?1:0));
	}
	
	public void writeChar(char num) {
		this.growBuffer(1);
		this.data.putChar(num);
	}
	
	public void writeDouble(double num) {
		this.growBuffer(8);
		this.data.putDouble(num);
	}
	
	public void writeFloat(float num) {
		this.growBuffer(4);
		this.data.putFloat(num);
	}
	
}
