package XMGJ.zsgj.model;

public interface IBusParser {
	
	default byte[] encode(){return null;};
	default void decode(byte[] data){};
	
}
