package casualtest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@SuppressWarnings("javadoc")
public class bnodeidTest {
	public static void main(String[] args) {



		 Set<Integer> set=new HashSet<Integer>();
		for(int i=
			0x00C0;i<=0x00D6;i++) {
			set.add(i);
		}
		for(int i=
			0x00D8;i<=0x00F6;i++) {
			set.add(i);
		}
			for(int i=
				0x00F8;i<=0x02FF;i++) {
				set.add(i);
			}
				for(int i=
					0x0370;i<=0x037D;i++) {
					set.add(i);
				}
					for(int i=
						0x037F;i<=0x1FFF;i++) {
						set.add(i);
					}
			for(int i=
				0x200C;i<=0x200D;i++) {
				set.add(i);
			}
			for(int i=
				0x2070;i<=0x218F;i++) {
				set.add(i);
			}
			for(int i=
				0x2C00;i<=0x2FEF;i++) {
				set.add(i);
			}
			for(int i=
				0x3001;i<=0xD7FF;i++) {
				set.add(i);
			}
			for(int i=
				0xF900;i<=0xFDCF;i++) {
				set.add(i);
			}
			for(int i=
				0xFDF0;i<=0xFFFD;i++) {
				set.add(i);
			}
//			for(int i=
//				0x10000;i<=0xEFFFF;i++) {
//				set.add(i);
//			}
				set.add(0x00B7);
			for(int i=
				0x0300;i<=0x036F;i++) {
				set.add(i);
			}
			for(int i=
				0x203F;i<=0x2040;i++) {
				set.add(i);
			}

		for(int i:set) {
			System.out.println("bnodeidTest.main() '"+Arrays.toString(Character.toChars(i))+"'");
		}

	}
}
