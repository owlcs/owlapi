package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

/*
 * Copyright (C) 2010, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadChecker {
	public static final int defaultRep=10;
	private int rep = defaultRep;
	private final PrintStream p;
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	private boolean successful = false;

	public MultiThreadChecker(int i) {
		this();
		if (i > 0) {
			rep = i;
		}
	}

	public MultiThreadChecker() {
		p = new PrintStream(out);
	}

	public void check(final TestMultithreadCallBack cb) {
		p.println("MultiThreadChecker.check() " + cb.getId());
		final ConcurrentLinkedQueue<Long> results = new ConcurrentLinkedQueue<Long>();
		final long start = System.currentTimeMillis();
		ExecutorService service = Executors.newFixedThreadPool(rep);
		for (int i = 0; i < rep; i++) {
			service.execute(new Runnable() {
				public void run() {
					for (int j = 0; j < rep; j++) {
						try {
							cb.execute();
							results.offer(System.currentTimeMillis());
						} catch (Throwable e) {
							e.printStackTrace(p);
							printout(cb, start, results);
							//throw e;
						}
					}
				}
			});
		}
		service.shutdown();
		while(!service.isTerminated()) {
			try {
				service.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		printout(cb, start, results);
	}

	private void printout(final TestMultithreadCallBack cb, long start,
			ConcurrentLinkedQueue<Long> results) {
		List<Object> list = new ArrayList<Object>();
		list.addAll(Arrays.asList(results.toArray()));
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return ((Long) o1).compareTo((Long) o2);
			}
		});
		long end = System.currentTimeMillis();
		if (list.size() > 0) {
			end = (Long) list.get(list.size() - 1);
		}
		int expected = rep * rep;
		p.println(cb.getId() + ": elapsed time (ms): " + (end - start));
		p.println("Successful threads: " + list.size() + "\t expected: "
				+ expected);
		successful = list.size() == expected;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public String getTrace() {
		p.flush();
		return out.toString();
	}
}
