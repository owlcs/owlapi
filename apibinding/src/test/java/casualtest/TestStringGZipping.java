package casualtest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TestStringGZipping {
	public static void main(String[] args) throws Exception {
		String test = "http://verylongstringtotesturicompressionofsorts.com/stillgoingon/andanotherbit/littlebitmore#almostthere_";
		//test=test+test+test+test+test;
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (int i = 0; i < 10000000; i++) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream zipout = new GZIPOutputStream(out);
			final String string = test + UUID.randomUUID();
			zipout.write(string.getBytes("UTF-8"));
			zipout.finish();
			zipout.flush();
			byte[] result = out.toByteArray();
			System.out.println("TestStringZipping.main() input length: "
					+ string.length() + "\t compressed: " + result.length
					+ "\t percent: " + (result.length * 100 / string.length()));
			arrays.add(result);
			ByteArrayInputStream in = new ByteArrayInputStream(result);
			GZIPInputStream zipin = new GZIPInputStream(in);
			StringBuilder b = new StringBuilder();

			int c = zipin.read();
			while (c > -1) {
				b.append((char) c);
				c = zipin.read();
			}
			System.out
					.println("TestStringGZipping.main() " + b.toString().equals(string));
		}
	}
}
