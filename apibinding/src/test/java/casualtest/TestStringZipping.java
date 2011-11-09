package casualtest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.Deflater;

public class TestStringZipping {
	public static void main(String[] args) throws UnsupportedEncodingException{
		String test="http://verylongstringtotesturicompressionofsorts.com/stillgoingon/andanotherbit/littlebitmore#almostthere_";
		test=test+test+test+test+test;
		List<byte[]> arrays=new ArrayList<byte[]>();
		int portion=1000;

		for(int i=0;i<10000000;i++) {
			Deflater d=new Deflater();
			final String string = test+UUID.randomUUID();
			d.setInput(string.getBytes("UTF-8"));
			   d.finish();
			   int deflateLength=0;
			   List<byte[]> portions=new ArrayList<byte[]>();
			   do {
				   byte[] deflated=new byte[portion];
				   int chunk=d.deflate(deflated);
				   deflateLength+=chunk;
				   if(chunk>0) {
					   if(chunk==portion) {
						   // the array has been filled
						   portions.add(deflated);
					   }
					   else {
						   // only part of the array contains data
						   byte[] toAdd=new byte[chunk];
						   System.arraycopy(deflated, 0, toAdd, 0, chunk);
						   portions.add(toAdd);
					   }
				   }

			   }
			   while(!d.finished()) ;
			   System.out.println("TestStringZipping.main() input length: "+string.length()+"\t compressed: "+deflateLength+"\t percent: "+(deflateLength*100/string.length()));
			   byte[] result=new byte[deflateLength];
			   int index=0;
			   for(byte[] b:portions) {
				   System.arraycopy(b, 0, result, index, b.length);
				   index+=b.length;
			   }
			   arrays.add(result);


		}
	}

}
