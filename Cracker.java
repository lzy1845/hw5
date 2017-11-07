package assign5;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;


public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();	
	private String hashString;
	private int maxLength;
	static CountDownLatch latch;


	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/

	public Cracker (String hash, int length){
		this.hashString = hash;
		this.maxLength = length;
	}

	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	// possible test values:
	// a ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb
	// fm 440f3041c89adee0f2ad780704bcc0efae1bdb30f8d77dc455a2f6c823b87ca0
	// a! 242ed53862c43c5be5f2c5213586d50724138dea7ae1d8760752c91f315dcd31
	// xyz 3608bca1e44ea6c4d268eb6db02260269892c0b42b86bbf1e77a6fa16c3c9282

	public static String generateHashValue(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			return hexToString(md.digest());
		} catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return "";
	}

	public class Worker extends Thread {
		int start, end;
		public Worker(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public void run() {
			for (int i = start; i <= end; i++) {
				crackHelper(String.valueOf(CHARS[i]), 1);
			}
			latch.countDown();
		}

		private void crackHelper(String currentKey, int length) {
			if (length <= maxLength) {
				if (Cracker.generateHashValue(currentKey).equals(hashString)) {
					System.out.println(currentKey);
				}
				else {
					for (int j = 0; j < CHARS.length; j++) {
						crackHelper(currentKey+CHARS[j], length+1);
					}
				}
			}
		}
	}

	public void crackHashValue(int maxLength, int numThreads, String hash) {
		int avg = CHARS.length / numThreads;
		for (int i = 0; i < numThreads; i++) {
			Worker w = new Worker(i*avg, Math.min((i+1)*avg-1, CHARS.length-1));
			w.start();
		}
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			String password = args[0];
			System.out.println(generateHashValue(password));
		} else if (args.length == 3) {
			String hash = args[0];
			int maxLength = Integer.parseInt(args[1]);
			int numThreads = Integer.parseInt(args[2]);
			Cracker cracker = new Cracker(hash, maxLength);
			latch = new CountDownLatch(numThreads);
			cracker.crackHashValue(maxLength, numThreads, hash);
			try{
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("all done");
		} else {
			throw new RuntimeException("The number of arguments is wrong.");
		}
	}

}
