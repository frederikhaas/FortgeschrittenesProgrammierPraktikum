package utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class BufferedLineWriter extends BufferedWriter {

	public BufferedLineWriter(Writer arg0) {
		super(arg0);
	}

	public void writelnAndFlush(String text) throws IOException {
		this.write(text + "\n");
		this.flush();
	}
	
	
}
