package xxl.java.support.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LockableTest {

	@Test
	public void deadlockIfFails() {
		competeForLock(50);
		competeForLock(75);
		competeForLock(100);
	}
	
	private void competeForLock(int threads) {
		int[] position = new int[] {1};
		final int[] finished = new int[] {0};
		final boolean[] go = new boolean[] {false};
		final Lockable<int[]> lockable = new Lockable<int[]>(position);
		for (int i = 1; i <= threads; i ++) {
			Runnable competitor = new Runnable() {
				
				@Override
				public void run() {
					while (! go[0]) { /* ready, set */ };
					try {
						int[] next = lockable.acquire();
						finished[0] = next[0]++;
						lockable.release();
					} catch (InterruptedException e) {
						throw new RuntimeException("Thread interrupted");
					}
				}
			};
			new Thread(competitor).start();
		}
		go[0] = true;
		while (finished[0] != threads) { /* do nothing */ };
		assertEquals(position[0], finished[0] + 1);
		lockable.release();
	}
}
